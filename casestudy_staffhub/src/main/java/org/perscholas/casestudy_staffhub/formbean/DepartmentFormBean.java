package org.perscholas.casestudy_staffhub.formbean;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentFormBean {

    private Integer id;

    @NotEmpty(message="Department Name is required.")
    private String departmentName;

    private String description;

    private String imageUrl;

    private String departmentDetail;
}
