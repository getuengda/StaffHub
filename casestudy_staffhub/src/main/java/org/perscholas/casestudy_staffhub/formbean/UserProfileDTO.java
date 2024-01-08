package org.perscholas.casestudy_staffhub.formbean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class UserProfileDTO {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private Integer office_Id;

    private String jobTitle;

    private String imageUrl;

    private String address;

    private Integer departmentId;

    private String departmentName;

    private String description;

    private String departmentDetail;

    private List<UserTrainingFormBean> userTrainings;

    public void setUserTrainings(List<UserTrainingFormBean> userTrainings) {
        this.userTrainings = userTrainings;
    }

    public List<UserTrainingFormBean> getUserTrainings() {
        return userTrainings;
    }

}
