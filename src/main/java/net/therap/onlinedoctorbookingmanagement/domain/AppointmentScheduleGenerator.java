package net.therap.onlinedoctorbookingmanagement.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIME;
import static net.therap.onlinedoctorbookingmanagement.utilities.constants.RegexPatterns.DATE_PATTERN;
import static net.therap.onlinedoctorbookingmanagement.utilities.constants.RegexPatterns.TIME_PATTERN;

/**
 * @author anwar
 * @since 2/7/18
 */
@Entity
@Table(name = "schedule_generator")
public class AppointmentScheduleGenerator implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    @Valid
    @OneToOne
    @JoinColumn(name = "id")
    private Doctor doctor;

    @Column(name = "next_time")
    @NotNull
    @Temporal(TIME)
    @DateTimeFormat(pattern = TIME_PATTERN)
    private Date nextTime;

    @Column(name = "next_date")
    @NotNull
    @Temporal(DATE)
    @DateTimeFormat(pattern = DATE_PATTERN)
    private Date nextDate;

    public AppointmentScheduleGenerator() {

    }

    public AppointmentScheduleGenerator(Date nextTime, Date nextDate, Doctor doctor) {
        this.nextTime = nextTime;
        this.nextDate = nextDate;
        this.doctor = doctor;
    }

    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }

    public Date getNextDate() {
        return nextDate;
    }

    public void setNextDate(Date nextDate) {
        this.nextDate = nextDate;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}