package com.nix.zhylina.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <p><h2>Role</h2>
 * <b>Class whose objects are used for database operations</b>
 * <br>
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
public class Role {
    private Long id;
    private String name;

    /**
     * Constructor with one parameter. Used to create a tuple in
     * database, since the ID is assigned automatically.
     * Also, this constructor can be used for queries that
     * do not require ID
     *
     * @see com.nix.zhylina.jdbc.JdbcRoleDao#create(Role)
     * @see com.nix.zhylina.jdbc.JdbcRoleDao#remove(Role)
     */
    public Role(String name) {
        this.name = name;
    }
}



