package tbektenov.com.sau.models.user.userRoles;

import tbektenov.com.sau.models.Appointment;
import tbektenov.com.sau.models.Hospitalization;
import tbektenov.com.sau.models.OrderEntity;
import tbektenov.com.sau.models.hospital.Laboratory;
import tbektenov.com.sau.models.pharmacy.HospitalPharmacy;

public interface IDoctor {
    void assignNurseToHospitalization(Nurse nurse, Hospitalization hospitalization);
    void addAppointmentToDoctor(Appointment appointment);
    void setLaboratory(Laboratory laboratory);
    OrderEntity sendOrderToHospPharmacy(HospitalPharmacy pharmacy, String order);
}
