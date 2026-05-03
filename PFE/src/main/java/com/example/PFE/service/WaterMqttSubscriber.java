package com.example.PFE.service;

import com.example.PFE.dto.WaterPayload;
import com.example.PFE.model.WaterMeasurement;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PreDestroy;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class WaterMqttSubscriber implements MqttCallbackExtended {

    private final WaterService waterService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    private final String brokerUrl;
    private final String waterTopic;
    private final String clientId;

    private MqttClient client;

    private final BlockingQueue<String> pendingMessages = new LinkedBlockingQueue<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public WaterMqttSubscriber(
            WaterService waterService,
            SimpMessagingTemplate messagingTemplate,
            ObjectMapper objectMapper,
            @Value("${mqtt.broker}") String brokerUrl,
            @Value("${mqtt.topic.water}") String waterTopic,
            @Value("${mqtt.client-id}") String clientId
    ) {
        this.waterService = waterService;
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = objectMapper;
        this.brokerUrl = brokerUrl;
        this.waterTopic = waterTopic;
        this.clientId = clientId;

        connect();
        startBufferProcessor();
    }

    private void connect() {
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setServerURIs(new String[]{brokerUrl});
            options.setAutomaticReconnect(true);
            options.setCleanSession(false);
            options.setKeepAliveInterval(60);
            options.setConnectionTimeout(30);

            String uniqueClientId = clientId + "-water";

            client = new MqttClient(
                    brokerUrl,
                    uniqueClientId,
                    new MqttDefaultFilePersistence(System.getProperty("java.io.tmpdir"))
            );

            client.setCallback(this);
            client.connect(options);

            System.out.println("✅ MQTT connecté à : " + brokerUrl);

        } catch (MqttException e) {
            System.err.println("❌ Connexion MQTT échouée : " + e.getMessage());
            scheduleReconnect();
        }
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        System.out.println("✅ MQTT " + (reconnect ? "re" : "") + "connecté");

        try {
            if (client != null && client.isConnected()) {
                client.subscribe(waterTopic, 1);
                System.out.println("✅ Abonné au topic : " + waterTopic);
            }

            flushPendingMessages();

        } catch (MqttException e) {
            System.err.println("❌ Erreur abonnement MQTT : " + e.getMessage());
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        String reason = (cause != null && cause.getMessage() != null)
                ? cause.getMessage()
                : "cause inconnue";

        System.err.println("⚠️ Connexion MQTT perdue : " + reason);
        System.err.println("🔄 Les messages en attente restent dans le buffer.");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
        System.out.println("📨 Message reçu sur " + topic + " : " + payload);

        pendingMessages.offer(payload);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Non utilisé ici car ce service ne publie pas vers MQTT
    }

    private void startBufferProcessor() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                if (!pendingMessages.isEmpty()) {
                    flushPendingMessages();
                }
            } catch (Exception e) {
                System.err.println("❌ Erreur buffer processor : " + e.getMessage());
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    private void flushPendingMessages() {
        String payload;
        int processedCount = 0;

        while ((payload = pendingMessages.poll()) != null) {
            boolean success = processMessage(payload);

            if (success) {
                processedCount++;
            } else {
                pendingMessages.offer(payload);
                break;
            }
        }

        if (processedCount > 0) {
            System.out.println("✅ " + processedCount + " message(s) traité(s) depuis le buffer");
        }
    }

    private boolean processMessage(String payload) {
        try {
            WaterPayload dto = objectMapper.readValue(payload, WaterPayload.class);

            WaterMeasurement entity = new WaterMeasurement(
                    dto.getDeviceId(),
                    dto.getFlowLMin(),
                    dto.getTotalLiters(),
                    dto.getPulseCount(),
                    LocalDateTime.now()
            );

            WaterMeasurement saved = waterService.save(entity);

            messagingTemplate.convertAndSend("/topic/water", saved);

            System.out.println(
                    "✅ Sauvegardé : " + saved.getDeviceId()
                            + " | flow=" + saved.getFlowLMin()
                            + " | total=" + saved.getTotalLiters()
                            + " | pulse=" + saved.getPulseCount()
            );

            return true;

        } catch (Exception e) {
            System.err.println("❌ Erreur traitement message MQTT : " + e.getMessage());
            return false;
        }
    }

    private void scheduleReconnect() {
        scheduler.schedule(() -> {
            try {
                System.out.println("🔄 Tentative de reconnexion MQTT...");
                connect();
            } catch (Exception e) {
                System.err.println("❌ Échec reconnexion MQTT : " + e.getMessage());
            }
        }, 5, TimeUnit.SECONDS);
    }

    @PreDestroy
    public void cleanup() {
        try {
            scheduler.shutdown();

            if (client != null) {
                if (client.isConnected()) {
                    client.disconnect();
                }
                client.close();
            }

            System.out.println("🛑 WaterMqttSubscriber arrêté proprement.");

        } catch (Exception e) {
            System.err.println("⚠️ Erreur fermeture MQTT : " + e.getMessage());
        }
    }
}