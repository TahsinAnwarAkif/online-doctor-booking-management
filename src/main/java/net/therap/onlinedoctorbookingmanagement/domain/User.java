package net.therap.onlinedoctorbookingmanagement.domain;

import net.therap.onlinedoctorbookingmanagement.annotations.UniqueUsernamePhoneAndEmail;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static net.therap.onlinedoctorbookingmanagement.utilities.constants.RegexPatterns.*;

/**
 * @author anwar
 * @since 2/7/18
 */
@Entity
@UniqueUsernamePhoneAndEmail
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"phone"}),
        @UniqueConstraint(columnNames = {"email"})})
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Column(name = "username")
    @NotNull
    @Size(min = 1, max = 255)
    @Pattern(regexp = USERNAME_PATTERN)
    private String username;

    @Column(name = "password")
    @NotNull
    @Size(min = 1, max = 255)
    private String password;

    @Column(name = "address")
    @NotNull
    @Size(min = 1, max = 255)
    private String address;

    @Column(name = "phone")
    @NotNull
    @Pattern(regexp = PHONE_NO_PATTERN)
    private String phone;

    @Column(name = "email")
    @NotNull
    @Pattern(regexp = EMAIL_PATTERN)
    @Size(max = 255)
    private String email;

    @ManyToOne
    @NotNull
    @Valid
    @JoinColumn(name = "role_type")
    private Role role;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}