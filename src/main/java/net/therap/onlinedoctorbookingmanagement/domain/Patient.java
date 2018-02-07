package net.therap.onlinedoctorbookingmanagement.domain;

import net.therap.onlinedoctorbookingmanagement.annotations.UniquePatient;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static net.therap.onlinedoctorbookingmanagement.utilities.constants.RegexPatterns.SSN_PATTERN;

/**
 * @author anwar
 * @since 2/7/18
 */
@Entity
@UniquePatient
@Table(name = "patient", uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"ssn"})})
@PrimaryKeyJoinColumn(name = "ID")
public class Patient extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "ssn")
    @NotNull
    @Pattern(regexp = SSN_PATTERN)
    @Size(min = 1, max = 255)
    private String ssn;

    @Column(name = "problem_overview")
    @NotNull
    @Size(min = 1, max = 255)
    private String problem;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

    public Patient() {
        this.appointments = new ArrayList<>();
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}