package usersapplication.unit.repository;

import usersapplication.domain.CEUsers;
import usersapplication.repository.CEUsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class CEUsersRepositoryTest {

    @Autowired
    private CEUsersRepository ceUsersRepository;

    @Test
    @Sql("data.sql")
    public void findCeUserById() {
        // Act
        Optional<CEUsers> ceUsers = ceUsersRepository.findById(100L);

        // Assert
        assertThat(ceUsers).isNotNull();
    }

    @Test
    public void createUserAndAssertIdCountingAnsResult() {
        // Arrange
        CEUsers newCeUsers1 = CEUsers.builder()
                .firstName("test 1")
                .isActive(true)
                .lastName("de tester 1")
                .address("testweg 456")
                .occupation("what he does all day").build();

        CEUsers newCeUsers2 = CEUsers.builder()
                .firstName("test 2")
                .isActive(true)
                .lastName("de tester 2")
                .address("testweg 456")
                .occupation("what he does all day").build();

        ceUsersRepository.save(newCeUsers1);
        ceUsersRepository.save(newCeUsers2);

        // Act
        Optional<CEUsers> ceUsers1 = ceUsersRepository.findById(1L);
        Optional<CEUsers> ceUsers2 = ceUsersRepository.findById(2L);

        // Assert
        assertThat(ceUsers1.get().getFirstName()).isEqualTo(newCeUsers1.getFirstName());
        assertThat(ceUsers1.get().getLastName()).isEqualTo(newCeUsers1.getLastName());
        assertThat(ceUsers1.get().getAddress()).isEqualTo(newCeUsers1.getAddress());
        assertThat(ceUsers1.get().getOccupation()).isEqualTo(newCeUsers1.getOccupation());

        assertThat(ceUsers2.get().getFirstName()).isEqualTo(newCeUsers2.getFirstName());
        assertThat(ceUsers2.get().getLastName()).isEqualTo(newCeUsers2.getLastName());
        assertThat(ceUsers2.get().getAddress()).isEqualTo(newCeUsers2.getAddress());
        assertThat(ceUsers2.get().getOccupation()).isEqualTo(newCeUsers2.getOccupation());
    }

    @Test
    @Sql("data.sql")
    public void findUserByIsActive() {
        // Act
        Collection<CEUsers> ceUsers = ceUsersRepository.findAllByIsActive();
        Collection<CEUsers> ceUsersInActive = ceUsersRepository.findAllByIsInActive();

        // Assert
        assertThat(ceUsers).isNotEmpty();
        assertThat(ceUsersInActive).isNotEmpty();
    }

    @Test
    public void findUserAndAssertEmptyResponseIsGivenInsteadOfException() {
        // Act
        Optional<CEUsers> ceUsers = ceUsersRepository.findById(1L);

        // Assert
        assertThat(ceUsers).isEmpty();
    }

    @Test
    public void tryCreateCeUserWithEmptyBody() {
        // Act
        assertThrows(DataIntegrityViolationException.class, () -> {
            ceUsersRepository.save(CEUsers.builder().build());
        });

        Optional<CEUsers> ceUsers = ceUsersRepository.findById(1L);

        // Assert
        assertThat(ceUsers).isEmpty();
    }

}