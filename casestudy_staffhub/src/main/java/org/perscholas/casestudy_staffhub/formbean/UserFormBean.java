package org.perscholas.casestudy_staffhub.formbean;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    private String jobTitle;

    private String imageUrl;

    private String address;
}
