package com.nix.zhylina.models;

import com.nix.zhylina.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ROLEDAO")
public class RoleEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;

    @Size(min = Constants.MIN_INPUT_SYMBOLS, max = Constants.MAX_INPUT_SYMBOLS)
    @Pattern(regexp = Constants.REGEX_FOR_ONLY_LETTER_AND_NUMBER)
    private String name;
}