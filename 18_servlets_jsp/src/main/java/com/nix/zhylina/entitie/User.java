package com.nix.zhylina.entitie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

/**
 * <p><h2>User</h2>
 * <b>Class whose objects are used for database operations</b>
 *
 * @author Zhilina Svetlana
 * @version 1.0
 * @since 26.01.2022 </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private Long id;
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthday;
    private Long idRole;

    /**
     * Constructor with one parameter. Used to create a tuple in
     * database, since the ID is assigned automatically.
     * Also, this constructor can be used for queries that
     * do not require ID
     *
     * @see com.nix.zhylina.dao.impl.JdbcUserDao#create(User)
     * @see com.nix.zhylina.dao.impl.JdbcUserDao#remove(User)
     */
    public User(String login, String password, String email, String firstName
            , String lastName, Date birthday, Long idRole) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.idRole = idRole;
    }

    public int getUserAge() {
        return Period.between(birthday.toLocalDate(), LocalDate.now()).getYears();
    }
}