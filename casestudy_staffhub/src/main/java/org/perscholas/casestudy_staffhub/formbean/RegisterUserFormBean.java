package org.perscholas.casestudy_staffhub.formbean;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Getter
@Setter
public class RegisterUserFormBean {

    @NotEmpty(message="First Name is required.")
    @Length(max= 45, message = "First Name must be less than 45 characters.")
    @Pattern(regexp = "^[a-zA-Z]{1,30}$")
    private String firstName;

    @NotEmpty(message="Last Name is required.")
    @Length(max= 45, message = "Last Name must be less than 45 characters.")
    private String lastName;

    @Email(message = "Email must be a valid email address")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @Length(min=8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit")
    private String password;

    @NotEmpty(message = "Confirm Password cannot be empty")
    private String confirmPassword;

    private Integer office_Id;

    private String jobTitle;

    private String imageUrl;

    private String address;

    private Date createDate;

}