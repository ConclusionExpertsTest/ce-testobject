package usersapplication.domain.response;

import usersapplication.domain.response.workingcondition.WorkingConditionsResponse;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class CeUsersResponse {

    private Long id;
    private boolean isActive;
    private String firstName;
    private String lastName;
    private String address;
    private String occupation;
    private WorkingConditionsResponse workingConditionsResponse;

}
