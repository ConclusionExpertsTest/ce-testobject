package usersapplication.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private @Column(nullable = false) boolean isActive;
    private @Column(nullable = false) String firstName;
    private @Column(nullable = false) String lastName;
    private @Column(nullable = false) String address;
    private @Column(nullable = false) String occupation;
    private @Column(nullable = false) int workingConditionsId;

}
