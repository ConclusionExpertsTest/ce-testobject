package nl.conclusionexperts.cetestobject.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CEUsers {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private @NotNull String firstName;
    private @NotNull String lastName;
    private @NotNull String address;
    private @NotNull String occupation;

}
