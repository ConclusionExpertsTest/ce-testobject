package usersapplication.helpers;

import usersapplication.domain.CEUsers;
import usersapplication.domain.response.CeUsersResponse;
import usersapplication.helpers.workingconditions.WorkingConditionsResponseTestData;

public class CeUsersResponseTestData {

    public static CeUsersResponse ceUsersResponse(CEUsers testUser) {
        return CeUsersResponse.builder()
                .id(testUser.getId())
                .firstName(testUser.getFirstName())
                .lastName(testUser.getLastName())
                .address(testUser.getAddress())
                .occupation(testUser.getOccupation())
                .workingConditionsResponse(WorkingConditionsResponseTestData
                        .workingConditionsResponse(testUser.getWorkingConditionsId()))
                .isActive(testUser.isActive())
                .build();
    }

}
