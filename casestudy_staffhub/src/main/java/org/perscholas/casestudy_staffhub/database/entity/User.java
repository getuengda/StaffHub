package org.perscholas.casestudy_staffhub.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "user")
    private List<UserTraining> trainings;

    @Column(name = "office_id")
    private Integer office_Id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name="create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "profile_image_url")
    private String profileImage;

    @Column(name = "vacation_hours")
    private Integer vacationHours;

    @Column(name = "address")
    private String address;

    @Column(name = "role")
    private String role;

    @Column(name = "image_url")
    private String imageUrl;

}
