package com.nix.zhylina.entitie;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * <p><h2>User</h2>
 * <b>Class whose objects are used for database operations</b>
 *
 * @author Zhilina Svetlana
 * @version 3.0
 * @since 08.03.2022
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "USERDAO")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "(?=[a-zA-Z ]+$).{4,20}$")
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String login;

    @Pattern(regexp = "(?=\\S+$).{4,20}$")
    @Column(nullable = false)
    @NotEmpty
    private String password;

    @Column(unique = true)
    @Email
    @NotEmpty
    private String email;

    @Pattern(regexp = "(?=[a-zA-Z ]+$).{2,20}$")
    @Column(nullable = false)
    @NotEmpty
    private String firstName;

    @Pattern(regexp = "(?=[a-zA-Z ]+$).{2,20}$")
    @Column(nullable = false)
    @NotEmpty
    private String lastName;

    @Past
    @NotNull
    @Column(nullable = false)
    private LocalDate birthday;

    @ManyToOne
    @JoinColumn(name = "id_Role", nullable = false)
    private Role role;

    public int getUserAge() {
        return Period.between(birthday, LocalDate.now()).getYears();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
