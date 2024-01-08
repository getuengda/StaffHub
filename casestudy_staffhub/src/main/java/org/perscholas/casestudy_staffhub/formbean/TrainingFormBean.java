package org.perscholas.casestudy_staffhub.formbean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class TrainingFormBean {

    private Integer id;

    private String trainingName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String datePosted;

    private String description;

    private String prerequisite;

    private String imageUrl;

    private Integer userId;

    private String trainingDetail;
}
