package CeUsersTests.domain;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CEUsers {

    private Long id;
    private boolean isActive;
    private String firstName;
    private String lastName;
    private String address;
    private String occupation;

}
