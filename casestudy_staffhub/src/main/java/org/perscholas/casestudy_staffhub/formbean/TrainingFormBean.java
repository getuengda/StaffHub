package org.perscholas.casestudy_staffhub.formbean;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class TrainingFormBean {

    private Integer id;

    @NotEmpty(message="Training name is required.")
    private String trainingName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String datePosted;

    @NotEmpty(message="Training description is required.")
    private String description;

    @NotEmpty(message="Training prerequisite is required.")
    private String prerequisite;

    private String imageUrl;

    private Integer userId;

    private String trainingDetail;
}
