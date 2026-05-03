package com.example.PFE.service;

import com.example.PFE.model.Machine;
import com.example.PFE.model.ScanAction;
import com.example.PFE.model.ScanEvent;
import com.example.PFE.model.Tissu;
import com.example.PFE.repository.MachineRepo;
import com.example.PFE.repository.ScanEventRepo;
import com.example.PFE.repository.TissuRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ScanReminderService {

    private final ScanEventRepo scanRepo;
    private final EmailService emailService;
    private final TissuRepo tissuRepo;
    private final MachineRepo machineRepo;

    public ScanReminderService(
            ScanEventRepo scanRepo,
            EmailService emailService,
            TissuRepo tissuRepo,
            MachineRepo machineRepo
    ) {
        this.scanRepo = scanRepo;
        this.emailService = emailService;
        this.tissuRepo = tissuRepo;
        this.machineRepo = machineRepo;
    }

    @Scheduled(fixedRate = 60000)
    public void checkOpenStarts() {
        List<ScanEvent> openStarts = scanRepo.findByActionAndClosedFalse(ScanAction.START);
        Date now = new Date();

        for (ScanEvent e : openStarts) {
            boolean overdue = e.getExpectedStopAt() != null && now.after(e.getExpectedStopAt());

            if (overdue && !e.isReminderEmailSent()) {
                Tissu tissu = tissuRepo.findById(e.getTissuId()).orElse(null);
                Machine machine = machineRepo.findById(e.getMachineId()).orElse(null);

                String tissuCode = (tissu != null && tissu.getCode() != null) ? tissu.getCode() : e.getTissuId();
                String machineCode = (machine != null && machine.getCode() != null) ? machine.getCode() : e.getMachineId();

                System.out.println("ALERTE STOP OUBLIE -> tissu=" + tissuCode + ", machine=" + machineCode);

                if (e.getTechnicianEmail() != null && !e.getTechnicianEmail().isBlank()) {
                    emailService.sendStopForgottenEmail(
                            e.getTechnicianEmail(),
                            tissuCode,
                            machineCode
                    );

                    e.setReminderEmailSent(true);
                    scanRepo.save(e);
                }
            }
        }
    }
}