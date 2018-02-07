package net.therap.onlinedoctorbookingmanagement.domain;

import net.therap.onlinedoctorbookingmanagement.annotations.TimeAndDateNotPastAndScheduleForDoctorIsValid;
import net.therap.onlinedoctorbookingmanagement.annotations.UniqueAppointment;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.APPOINTMENT_STATUS;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIME;
import static net.therap.onlinedoctorbookingmanagement.utilities.constants.RegexPatterns.DATE_PATTERN;
import static net.therap.onlinedoctorbookingmanagement.utilities.constants.RegexPatterns.TIME_PATTERN;

/**
 * @author anwar
 * @since 2/7/18
 */
@Entity
@UniqueAppointment
@TimeAndDateNotPastAndScheduleForDoctorIsValid
@Table(name = "appointment", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"date", "time", "doctor_id"}),
        @UniqueConstraint(columnNames = {"date", "time", "patient_id"})})
public class Appointment implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "time")
    @Temporal(TIME)
    @DateTimeFormat(pattern = TIME_PATTERN)
    private Date time;

    @Column(name = "date")
    @Temporal(DATE)
    @DateTimeFormat(pattern = DATE_PATTERN)
    private Date date;

    @Column(name = "status")
    @NotNull
    @Enumerated(STRING)
    private APPOINTMENT_STATUS status;

    @ManyToOne
    @NotNull
    @Valid
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @NotNull
    @Valid
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Transient
    private boolean isDateTimeGenerationTypeAuto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDateTimeGenerationTypeAuto() {
        return isDateTimeGenerationTypeAuto;
    }

    public void setDateTimeGenerationTypeAuto(boolean dateTimeGenerationTypeAuto) {
        isDateTimeGenerationTypeAuto = dateTimeGenerationTypeAuto;
    }

    public APPOINTMENT_STATUS getStatus() {
        return status;
    }

    public void setStatus(APPOINTMENT_STATUS status) {
        this.status = status;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Appointment that = (Appointment) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}