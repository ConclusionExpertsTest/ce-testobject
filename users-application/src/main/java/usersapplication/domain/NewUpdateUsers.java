package usersapplication.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewUpdateUsers {

    private @Column(nullable = false) boolean isActive;
    private @Column(nullable = false) String firstName;
    private @Column(nullable = false) String lastName;
    private @Column(nullable = false) String address;
    private @Column(nullable = false) String occupation;
    private @Column(nullable = false) int workingConditionsId;

}
