package org.perscholas.casestudy_staffhub.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="trainings")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String trainingName;

    @Column(name="date_posted")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePosted;

    @Column(name = "description")
    private String description;

    @Column(name = "prerequisite")
    private String prerequisite;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "training_detail")
    private String trainingDetail;
}
