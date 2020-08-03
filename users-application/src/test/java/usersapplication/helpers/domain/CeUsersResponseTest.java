package usersapplication.helpers.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import usersapplication.domain.response.workingcondition.WorkingConditionsResponse;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class CeUsersResponseTest {

    private Long id;
    private boolean isActive;
    private String firstName;
    private String lastName;
    private String address;
    private String occupation;
    private WorkingConditionsResponse workingConditionsResponse;

}
