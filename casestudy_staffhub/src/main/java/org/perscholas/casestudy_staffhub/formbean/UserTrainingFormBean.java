package org.perscholas.casestudy_staffhub.formbean;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.perscholas.casestudy_staffhub.database.entity.Training;
import org.perscholas.casestudy_staffhub.database.entity.User;

import java.util.Date;

@Getter
@Setter
public class UserTrainingFormBean {

    private Integer id;

    private User user;

    private Training training;

    @Temporal(TemporalType.TIMESTAMP)
    private Date enrollmentDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date completionDate;

    private String status;
}
