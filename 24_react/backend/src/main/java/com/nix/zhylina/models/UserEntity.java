package com.nix.zhylina.models;

import com.nix.zhylina.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "USERDAO")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = Constants.MIN_INPUT_SYMBOLS, max = Constants.MAX_INPUT_SYMBOLS)
    @Pattern(regexp = Constants.REGEX_FOR_ONLY_LETTER_AND_NUMBER)
    private String login;

    @NotNull
    @Size(min = Constants.MIN_INPUT_SYMBOLS, max = Constants.MAX_INPUT_SYMBOLS)
    private String password;

    @NotNull
    @Email
    @Size(min = Constants.MIN_INPUT_SYMBOLS, max = Constants.MAX_INPUT_SYMBOLS)
    @Pattern(regexp = Constants.REGEX_FOR_EMAIL)
    private String email;

    @NotNull
    @Size(min = Constants.MIN_INPUT_SYMBOLS, max = Constants.MAX_INPUT_SYMBOLS)
    @Pattern(regexp = Constants.REGEX_FOR_ONLY_LETTER_AND_NUMBER)
    private String firstName;

    @NotNull
    @Size(min = Constants.MIN_INPUT_SYMBOLS, max = Constants.MAX_INPUT_SYMBOLS)
    @Pattern(regexp = Constants.REGEX_FOR_ONLY_LETTER_AND_NUMBER)
    private String lastName;

    @NotNull
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    @Past
    private Date birthday;

    @ManyToOne
    @JoinColumn(name="idRole")
    private RoleEntity roleEntity;
}