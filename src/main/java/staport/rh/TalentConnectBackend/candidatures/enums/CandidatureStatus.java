package staport.rh.TalentConnectBackend.candidatures.enums;

public enum CandidatureStatus {
    // New status for initial contact and scheduling the phone interview
    ENTRETIEN_TELEPHONIQUE_PLANIFIE, // Entretien téléphonique planifié

    // Status for scheduling the first physical interview
    ENTRETIEN_PHYSIQUE_1_PLANIFIE, // Entretien physique (1er) planifié

    // Status for scheduling the second physical interview
    ENTRETIEN_PHYSIQUE_2_PLANIFIE, // Entretien physique (2ème) planifié

    // Final statuses
    EMBAUCHE, // Embauché (Hired)
    REJETE // Rejeté (Rejected)
}