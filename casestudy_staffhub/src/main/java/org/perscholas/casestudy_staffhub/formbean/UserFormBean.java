package org.perscholas.casestudy_staffhub.formbean;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.perscholas.casestudy_staffhub.database.entity.Department;
import org.perscholas.casestudy_staffhub.database.entity.UserTraining;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserFormBean {

    private Integer id;

    private Integer departmentId;

    private Department department;

    private List<UserTraining> trainings;

    private Integer office_Id;

    @NotEmpty(message="First Name is required.")
    @Length(max= 45, message = "First Name must be less than 45 characters.")
    @Pattern(regexp = "^[a-zA-Z]{1,30}$")
    private String firstName;

    @NotEmpty(message="First Name is required.")
    @Length(max= 45, message = "Last Name must be less than 45 characters.")
    private String lastName;

    @Email(message="Email is not a valid email.")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;

    @NotEmpty(message="Password is required.")
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    private String jobTitle;

    private String imageUrl;

    private String address;
}
