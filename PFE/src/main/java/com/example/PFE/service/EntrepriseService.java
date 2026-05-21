package com.example.PFE.service;

import com.example.PFE.dto.EntrepriseStatsDTO;
import com.example.PFE.model.Entreprise;
import com.example.PFE.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class EntrepriseService {

    private final EntrepriseRepository entrepriseRepository;
    private final FormationRepository formationRepository;
    private final AssTechRepository assTechRepository;
    private final DiagnosticRepository diagnosticRepository;
    private final SuiviITPRepository itsRepository;
    private final SuiviPlanActionRepository planActionRepository;

    public EntrepriseService(
            EntrepriseRepository entrepriseRepository,
            FormationRepository formationRepository,
            AssTechRepository assTechRepository,
            DiagnosticRepository diagnosticRepository,
            SuiviITPRepository itsRepository,
            SuiviPlanActionRepository planActionRepository
    ) {
        this.entrepriseRepository = entrepriseRepository;
        this.formationRepository = formationRepository;
        this.assTechRepository = assTechRepository;
        this.diagnosticRepository = diagnosticRepository;
        this.itsRepository = itsRepository;
        this.planActionRepository = planActionRepository;
    }

    public List<EntrepriseStatsDTO> getAll() {
        return entrepriseRepository.findAll()
                .stream()
                .map(this::toStatsDTO)
                .sorted(Comparator.comparingLong(EntrepriseStatsDTO::getTotalCollaborations).reversed())
                .toList();
    }

    public Entreprise getById(String id) {
        return entrepriseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entreprise non trouvée"));
    }

    public EntrepriseStatsDTO getDetailsWithStats(String id) {
        Entreprise e = getById(id);
        return toStatsDTO(e);
    }



//    public Entreprise create(Entreprise entreprise) {
//        return entrepriseRepository.save(entreprise);
//    }

    public Entreprise update(String id, Entreprise updated) {
        Entreprise e = getById(id);

        e.setNomEntreprise(updated.getNomEntreprise());
        e.setCao(updated.getCao());
        e.setAdresse(updated.getAdresse());
        e.setContact(updated.getContact());
        e.setSpecialite(updated.getSpecialite());
        e.setExtensions(updated.getExtensions());

        return entrepriseRepository.save(e);
    }

//    public void delete(String id) {
//        entrepriseRepository.deleteById(id);
//    }
//
//    public List<EntrepriseStatsDTO> search(String q) {
//        return entrepriseRepository
//                .findByNomEntrepriseContainingIgnoreCaseOrSpecialiteContainingIgnoreCase(q, q)
//                .stream()
//                .map(this::toStatsDTO)
//                .sorted(Comparator.comparingLong(EntrepriseStatsDTO::getTotalCollaborations).reversed())
//                .toList();
//    }

    private EntrepriseStatsDTO toStatsDTO(Entreprise e) {

        long formations = formationRepository.countByEntrepriseIgnoreCase(e.getNomEntreprise());
        long assTech = assTechRepository.countByEntrepriseIgnoreCase(e.getNomEntreprise());
        long diagnostics = diagnosticRepository.countByEntrepriseIgnoreCase(e.getNomEntreprise());
        long its = itsRepository.countByEntrepriseIgnoreCase(e.getNomEntreprise());
        long plansAction = planActionRepository.countByEntrepriseIgnoreCase(e.getNomEntreprise());

        return new EntrepriseStatsDTO(
                e,
                formations,
                assTech,
                diagnostics,
                its,
                plansAction
        );
    }
//    public List<EntrepriseStatsDTO> getActive() {
//        return getAll()
//                .stream()
//                .filter(e -> e.getDeleted() == null || e.getDeleted() == false)
//                .toList();
//    }

//    public List<EntrepriseStatsDTO> getTrash() {
//        return getAll()
//                .stream()
//                .filter(e -> e.getDeleted() != null && e.getDeleted() == true)
//                .toList();
//    }

    public Entreprise create(Entreprise entreprise) {
        entreprise.setDeleted(false);
        entreprise.setDeletedAt(null);
        return entrepriseRepository.save(entreprise);
    }

    public void delete(String id) {
        Entreprise e = getById(id);
        e.setDeleted(true);
        e.setDeletedAt(LocalDateTime.now());
        entrepriseRepository.save(e);
    }

    public Entreprise restore(String id) {
        Entreprise e = getById(id);
        e.setDeleted(false);
        e.setDeletedAt(null);
        return entrepriseRepository.save(e);
    }

    public List<EntrepriseStatsDTO> getActive() {
        return getAll()
                .stream()
                .filter(e -> e.getDeleted() == null || !e.getDeleted())
                .toList();
    }

    public List<EntrepriseStatsDTO> getTrash() {
        return getAll()
                .stream()
                .filter(e -> e.getDeleted() != null && e.getDeleted())
                .toList();
    }

    public List<EntrepriseStatsDTO> search(String q) {
        return entrepriseRepository
                .findByNomEntrepriseContainingIgnoreCaseOrSpecialiteContainingIgnoreCase(q, q)
                .stream()
                .filter(e -> e.getDeleted() == null || !e.getDeleted())
                .map(this::toStatsDTO)
                .sorted(Comparator.comparingLong(EntrepriseStatsDTO::getTotalCollaborations).reversed())
                .toList();
    }
}