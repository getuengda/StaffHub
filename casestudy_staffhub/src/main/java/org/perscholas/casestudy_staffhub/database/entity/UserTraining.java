package org.perscholas.casestudy_staffhub.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="user_training")
public class UserTraining {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

    @Column(name = "enrollment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enrollmentDate;

    @Column(name = "completion_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date completionDate;

    @Column(name = "status")
    private String status;
}
