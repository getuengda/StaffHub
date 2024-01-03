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
    @Pattern(regexp = "^[a-zA-Z]{1,30}$")
    private String departmentName;

    private String description;

    private String imageUrl;
}
