package org.perscholas.casestudy_staffhub.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

//    @OneToMany( mappedBy = "employees", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    List<Employee> employeeList;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;
}
