package com.example.PFE.service;

import com.example.PFE.model.ScanAction;
import com.example.PFE.repository.ScanEventRepo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TimeService {
    private final ScanEventRepo scanRepo;

    public TimeService(ScanEventRepo scanRepo) { this.scanRepo = scanRepo; }

    public Map<String, Long> computeSecondsByMachine(String tissuId) {
        var events = scanRepo.findByTissuIdOrderByTsAsc(tissuId);

        Map<String, Date> opened = new HashMap<>(); // machineId -> start time
        Map<String, Long> totals = new HashMap<>();

        for (var e : events) {
            String m = e.getMachineId();
            if (e.getAction() == ScanAction.START) {
                opened.put(m, e.getTs());
            } else {
                Date start = opened.remove(m);
                if (start != null) {
                    long sec = (e.getTs().getTime() - start.getTime()) / 1000;
                    totals.put(m, totals.getOrDefault(m, 0L) + Math.max(sec, 0));
                }
            }
        }
        return totals;
    }
}
