package tbektenov.com.sau.models.user.userRoles;

import tbektenov.com.sau.models.Hospitalization;
import tbektenov.com.sau.models.hospital.HospitalWard;
import tbektenov.com.sau.models.user.UserEntity;

import java.util.Set;

public interface INurse {
    void addHospitalization(Hospitalization hospitalization);

    /**
     * Removes the given hospitalization from this nurse's list.
     * Also removes the nurse from the hospitalization's list of nurses.
     *
     * @param hospitalization The hospitalization to remove. If null or not found, nothing happens.
     */
    void removeHospitalization(Hospitalization hospitalization);

    /**
     * Returns a set of patients assigned to this nurse through their hospitalizations.
     *
     * @return A set of assigned patients.
     */
    Set<UserEntity> getAssignedPatients();

    /**
     * Returns a set of hospital wards assigned to this nurse through their hospitalizations.
     *
     * @return A set of assigned hospital wards.
     */
     Set<HospitalWard> getAssignedWards();
}
