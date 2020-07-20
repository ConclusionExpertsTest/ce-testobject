package nl.conclusionexperts.cetestobject.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CEUsers {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private @Column(nullable = false) boolean isActive;
    private @Column(nullable = false) String firstName;
    private @Column(nullable = false) String lastName;
    private @Column(nullable = false) String address;
    private @Column(nullable = false) String occupation;

}
