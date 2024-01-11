package org.perscholas.casestudy_staffhub.formbean;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class DepartmentFormBean {

    private Integer id;

    @NotEmpty(message="Department Name is required.")
    @Length(max= 45, message = "Department Name must be less than 200 characters.")
    private String departmentName;

    @NotEmpty(message="Department prerequisite is required.")
    private String description;

    private String imageUrl;

    private String departmentDetail;
}
