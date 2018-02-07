package net.therap.onlinedoctorbookingmanagement.domain;

import net.therap.onlinedoctorbookingmanagement.annotations.JoiningScheduleNotUpdatableIfPatientIsAlreadyAppointed;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIME;
import static net.therap.onlinedoctorbookingmanagement.utilities.constants.RegexPatterns.DATE_PATTERN;
import static net.therap.onlinedoctorbookingmanagement.utilities.constants.RegexPatterns.TIME_PATTERN;

/**
 * @author anwar
 * @since 2/7/18
 */
@Entity
@JoiningScheduleNotUpdatableIfPatientIsAlreadyAppointed
@Table(name = "doctor")
@PrimaryKeyJoinColumn(name = "ID")
public class Doctor extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "joining_date")
    @NotNull
    @Temporal(DATE)
    @DateTimeFormat(pattern = DATE_PATTERN)
    private Date joiningDate;

    @Column(name = "daily_schedule_start")
    @NotNull
    @Temporal(TIME)
    @DateTimeFormat(pattern = TIME_PATTERN)
    private Date dailyScheduleStart;

    @Column(name = "daily_schedule_end")
    @NotNull
    @Temporal(TIME)
    @DateTimeFormat(pattern = TIME_PATTERN)
    private Date dailyScheduleEnd;

    @Column(name = "estimated_visits_per_day")
    @NotNull
    private Integer estimatedVisitsPerDay;

    @Column(name = "visiting_amount")
    @NotNull
    @Max(value = 1000)
    private Integer visitingAmount;

    @ManyToOne
    @NotNull
    @Valid
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

    @OneToOne(mappedBy = "doctor")
    private AppointmentScheduleGenerator appointmentScheduleGenerator;

    public Doctor() {
        this.appointments = new ArrayList<>();
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Date getDailyScheduleStart() {
        return dailyScheduleStart;
    }

    public void setDailyScheduleStart(Date dailyScheduleStart) {
        this.dailyScheduleStart = dailyScheduleStart;
    }

    public Date getDailyScheduleEnd() {
        return dailyScheduleEnd;
    }

    public void setDailyScheduleEnd(Date dailyScheduleEnd) {
        this.dailyScheduleEnd = dailyScheduleEnd;
    }

    public Integer getEstimatedVisitsPerDay() {
        return estimatedVisitsPerDay;
    }

    public void setEstimatedVisitsPerDay(Integer estimatedVisitsPerDay) {
        this.estimatedVisitsPerDay = estimatedVisitsPerDay;
    }

    public Integer getVisitingAmount() {
        return visitingAmount;
    }

    public void setVisitingAmount(Integer visitingAmount) {
        this.visitingAmount = visitingAmount;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public AppointmentScheduleGenerator getAppointmentScheduleGenerator() {
        return appointmentScheduleGenerator;
    }

    public void setAppointmentScheduleGenerator(AppointmentScheduleGenerator appointmentScheduleGenerator) {
        this.appointmentScheduleGenerator = appointmentScheduleGenerator;
    }
}