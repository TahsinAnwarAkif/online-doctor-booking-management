package net.therap.onlinedoctorbookingmanagement.domain;

import net.therap.onlinedoctorbookingmanagement.utilities.enums.ROLE_TYPES;
import net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.StringListConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;

/**
 * @author anwar
 * @since 2/12/18
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Enumerated(STRING)
    private ROLE_TYPES type;

    @Column(name = "actions")
    @NotNull
    @Convert(converter = StringListConverter.class)
    private List<String> actions;

    @OneToMany(mappedBy = "role")
    private List<User> users;

    public Role() {
        this.users = new ArrayList<>();
    }

    public Role(ROLE_TYPES type) {
        this.type = type;
    }

    public ROLE_TYPES getType() {
        return type;
    }

    public void setType(ROLE_TYPES type) {
        this.type = type;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}