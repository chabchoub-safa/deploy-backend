package com.example.PFE.service;

import com.example.PFE.dto.EnergyPayload;
import com.example.PFE.model.EnergyMeasurement;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PreDestroy;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.*;

@Service
public class EnergyMqttSubscriber implements MqttCallbackExtended {

    private final EnergyService energyService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    private final String brokerUrl;
    private final String energyTopic;
    private final String clientId;

    private MqttClient client;

    private final BlockingQueue<String> pendingMessages = new LinkedBlockingQueue<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public EnergyMqttSubscriber(
            EnergyService energyService,
            SimpMessagingTemplate messagingTemplate,
            ObjectMapper objectMapper,
            @Value("${mqtt.broker}") String brokerUrl,
            @Value("${mqtt.topic.energy}") String energyTopic,
            @Value("${mqtt.client-id}") String clientId
    ) {
        this.energyService = energyService;
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = objectMapper;
        this.brokerUrl = brokerUrl;
        this.energyTopic = energyTopic;
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

            client = new MqttClient(
                    brokerUrl,
                    clientId + "-energy",
                    new MqttDefaultFilePersistence(System.getProperty("java.io.tmpdir"))
            );

            client.setCallback(this);
            client.connect(options);

        } catch (MqttException e) {
            scheduleReconnect();
        }
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        try {
            if (client != null && client.isConnected()) {
                client.subscribe(energyTopic, 1);
            }
            flushPendingMessages();
        } catch (MqttException e) {
            System.err.println("Erreur abonnement energy MQTT : " + e.getMessage());
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.err.println("Connexion MQTT energy perdue");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
        pendingMessages.offer(payload);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    private void startBufferProcessor() {
        scheduler.scheduleAtFixedRate(() -> {
            if (!pendingMessages.isEmpty()) {
                flushPendingMessages();
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    private void flushPendingMessages() {
        String payload;
        while ((payload = pendingMessages.poll()) != null) {
            boolean success = processMessage(payload);
            if (!success) {
                pendingMessages.offer(payload);
                break;
            }
        }
    }

    private boolean processMessage(String payload) {
        try {
            EnergyPayload dto = objectMapper.readValue(payload, EnergyPayload.class);

            EnergyMeasurement entity = new EnergyMeasurement(
                    dto.getDeviceId(),
                    dto.getVoltage(),
                    dto.getCurrent(),
                    dto.getPower(),
                    dto.getEnergy(),
                    dto.getFrequency(),
                    dto.getPowerFactor(),
                    LocalDateTime.now()
            );

            EnergyMeasurement saved = energyService.save(entity);
            messagingTemplate.convertAndSend("/topic/energy", saved);
            return true;

        } catch (Exception e) {
            System.err.println("Erreur traitement energy : " + e.getMessage());
            return false;
        }
    }

    private void scheduleReconnect() {
        scheduler.schedule(this::connect, 5, TimeUnit.SECONDS);
    }

    @PreDestroy
    public void cleanup() {
        try {
            scheduler.shutdown();
            if (client != null) {
                if (client.isConnected()) client.disconnect();
                client.close();
            }
        } catch (Exception e) {
            System.err.println("Erreur fermeture MQTT energy : " + e.getMessage());
        }
    }
}