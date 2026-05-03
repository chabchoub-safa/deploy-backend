package com.example.PFE.controller;

import com.example.PFE.dto.StartScanRequest;
import com.example.PFE.dto.StopScanRequest;
import com.example.PFE.model.*;
import com.example.PFE.repository.ScanEventRepo;
import com.example.PFE.repository.TissuRepo;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/scan")
public class ScanController {

    private final ScanEventRepo scanRepo;
    private final TissuRepo tissuRepo;

    public ScanController(ScanEventRepo scanRepo, TissuRepo tissuRepo) {
        this.scanRepo = scanRepo;
        this.tissuRepo = tissuRepo;
    }

    @GetMapping("/tissu/{tissuId}/workflow")
    public Map<String, Object> getWorkflow(@PathVariable String tissuId) {
        Tissu tissu = tissuRepo.findById(tissuId).orElseThrow();

        Map<String, Object> res = new HashMap<>();
        res.put("tissu", tissu);
        res.put("events", scanRepo.findByTissuIdOrderByTsAsc(tissuId));
        return res;
    }

    @PostMapping("/start")
    public Map<String, Object> start(@RequestBody StartScanRequest body, Principal principal) {
        String tissuId = body.getTissuId();
        String machineId = body.getMachineId();
        Long estimatedMinutes = body.getEstimatedMinutes();

        Tissu tissu = tissuRepo.findById(tissuId).orElseThrow();

        if (tissu.getRouteMachineIds() == null || tissu.getRouteMachineIds().isEmpty()) {
            throw new RuntimeException("Route machines non définie pour ce tissu");
        }

        if (!tissu.getRouteMachineIds().contains(machineId)) {
            throw new RuntimeException("Cette machine ne fait pas partie du workflow de ce tissu");
        }

        if (tissu.getWorkflowStates() == null || tissu.getWorkflowStates().isEmpty()) {
            List<WorkflowStepState> states = new ArrayList<>();
            for (String mId : tissu.getRouteMachineIds()) {
                WorkflowStepState s = new WorkflowStepState();
                s.setMachineId(mId);
                s.setStatus("NON_COMMENCE");
                states.add(s);
            }
            tissu.setWorkflowStates(states);
        }

        WorkflowStepState step = tissu.getWorkflowStates()
                .stream()
                .filter(s -> machineId.equals(s.getMachineId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Etape workflow introuvable"));

        if ("EN_COURS".equals(step.getStatus())) {
            throw new RuntimeException("Veuillez d'abord faire STOP de la machine " + machineId + " pour ce tissu.");
        }

        // Si tu veux empêcher le START d'une étape tant que la précédente n'est pas TERMINEE :
        // enlever ce bloc si tu veux permettre complètement le parallèle
        int currentIndex = tissu.getRouteMachineIds().indexOf(machineId);
        for (int i = 0; i < currentIndex; i++) {
            String prevMachineId = tissu.getRouteMachineIds().get(i);
            WorkflowStepState prev = tissu.getWorkflowStates().stream()
                    .filter(s -> prevMachineId.equals(s.getMachineId()))
                    .findFirst()
                    .orElse(null);

            if (prev != null && !"TERMINE".equals(prev.getStatus())) {
                throw new RuntimeException("Impossible de démarrer cette machine avant la fin de la machine précédente : " + prevMachineId);
            }
        }

        Date now = new Date();
        Date reminderAt = null;
        if (estimatedMinutes != null && estimatedMinutes > 0) {
            reminderAt = new Date(now.getTime() + estimatedMinutes * 60_000);
        }

        step.setStatus("EN_COURS");
        step.setStartedAt(now);
        step.setStoppedAt(null);
        step.setEstimatedMinutes(estimatedMinutes);
        step.setReminderAt(reminderAt);
        step.setStartedBy(principal != null ? principal.getName() : "unknown");
        step.setStoppedBy(null);

        if (tissu.getDemande() != null && tissu.getDemande().getDateLancement() == null) {
            tissu.getDemande().setDateLancement(now);
        }

        tissu.setStatut(StatutTissu.EN_TRAITEMENT);
        tissu.setUpdatedAt(now);
        tissuRepo.save(tissu);

        ScanEvent e = new ScanEvent();
        e.setTissuId(tissuId);
        e.setMachineId(machineId);
        e.setTechnicianEmail(principal != null ? principal.getName() : "unknown");
        e.setAction(ScanAction.START);
        e.setTs(now);
        e.setEstimatedMinutes(estimatedMinutes);
        e.setExpectedStopAt(reminderAt);
        e.setClosed(false);
        scanRepo.save(e);

        Map<String, Object> res = new HashMap<>();
        res.put("message", "START enregistré avec succès");
        res.put("event", e);
        res.put("workflowStates", tissu.getWorkflowStates());
        return res;
    }

    @PostMapping("/stop")
    public Map<String, Object> stop(@RequestBody StopScanRequest body, Principal principal) {
        String tissuId = body.getTissuId();
        String machineId = body.getMachineId();

        Tissu tissu = tissuRepo.findById(tissuId).orElseThrow();

        if (tissu.getWorkflowStates() == null || tissu.getWorkflowStates().isEmpty()) {
            throw new RuntimeException("Aucun workflow initialisé pour ce tissu");
        }

        WorkflowStepState step = tissu.getWorkflowStates()
                .stream()
                .filter(s -> machineId.equals(s.getMachineId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Etape workflow introuvable"));

        if (!"EN_COURS".equals(step.getStatus())) {
            throw new RuntimeException("Impossible de faire STOP: cette machine n'est pas en cours pour ce tissu");
        }

        ScanEvent openStart = scanRepo
                .findTopByTissuIdAndMachineIdAndActionAndClosedFalseOrderByTsDesc(tissuId, machineId, ScanAction.START)
                .orElseThrow(() -> new RuntimeException("Aucun START ouvert trouvé pour cette machine"));

        Date now = new Date();

        step.setStatus("TERMINE");
        step.setStoppedAt(now);
        step.setStoppedBy(principal != null ? principal.getName() : "unknown");

        openStart.setClosed(true);
        scanRepo.save(openStart);

        ScanEvent stopEvent = new ScanEvent();
        stopEvent.setTissuId(tissuId);
        stopEvent.setMachineId(machineId);
        stopEvent.setTechnicianEmail(principal != null ? principal.getName() : "unknown");
        stopEvent.setAction(ScanAction.STOP);
        stopEvent.setTs(now);
        stopEvent.setClosed(true);
        scanRepo.save(stopEvent);

        boolean allDone = tissu.getWorkflowStates().stream()
                .allMatch(s -> "TERMINE".equals(s.getStatus()));

        if (allDone) {
            tissu.setStatut(StatutTissu.LIVRE);
            tissu.setDeliveredAt(now);
        } else {
            tissu.setStatut(StatutTissu.EN_TRAITEMENT);
        }

        tissu.setUpdatedAt(now);
        tissuRepo.save(tissu);

        Map<String, Object> res = new HashMap<>();
        res.put("message", "STOP enregistré avec succès");
        res.put("event", stopEvent);
        res.put("workflowStates", tissu.getWorkflowStates());
        res.put("allDone", allDone);
        return res;
    }
}