package net.therap.onlinedoctorbookingmanagement.domain;

import net.therap.onlinedoctorbookingmanagement.annotations.UniqueSpecialty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anwar
 * @since 2/7/18
 */
@Entity
@UniqueSpecialty
@Table(name = "specialty", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})})
public class Specialty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Column(name = "description")
    @NotNull
    @Size(min = 1, max = 255)
    private String description;

    @OneToMany(mappedBy = "specialty")
    private List<Doctor> doctors;

    public Specialty() {
        this.doctors = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }
}