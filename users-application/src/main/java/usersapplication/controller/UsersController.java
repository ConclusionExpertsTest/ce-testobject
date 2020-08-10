package usersapplication.controller;

import usersapplication.domain.Users;
import usersapplication.domain.NewUpdateUsers;
import usersapplication.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Users> findAllCeUsers() {
        return usersRepository.findAllByIsActive();
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Users> findCeUser(@PathVariable(value = "id") Long ceUserId) throws ResponseStatusException {
        Users ceUser = usersRepository.findById(ceUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok().body(ceUser);
    }

    @GetMapping("/inactive")
    @CrossOrigin(origins = "http://localhost:4200")
    public Iterable<Users> findAllInActiveCeUsers() {
        return usersRepository.findAllByIsInActive();
    }

    @PostMapping("/")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Users> createCeUser(@Valid @NotEmpty @RequestBody NewUpdateUsers newCeUser) throws URISyntaxException {
        Users users = usersRepository.save(
                Users.builder()
                        .isActive(newCeUser.isActive())
                        .firstName(newCeUser.getFirstName())
                        .lastName(newCeUser.getLastName())
                        .address(newCeUser.getAddress())
                        .occupation(newCeUser.getOccupation())
                        .workingConditionsId(newCeUser.getWorkingConditionsId()).build());

        return ResponseEntity.created(new URI("/" + users.getId())).body(users);
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Users> updateCeUser(@PathVariable(value = "id") Long ceUserId,
                                              @Valid @RequestBody NewUpdateUsers updateCeUser) throws ResponseStatusException {
        Users users = usersRepository.findById(ceUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        users.setActive(updateCeUser.isActive());
        users.setFirstName(updateCeUser.getFirstName());
        users.setLastName(updateCeUser.getLastName());
        users.setAddress(updateCeUser.getAddress());
        users.setOccupation(updateCeUser.getOccupation());
        users.setWorkingConditionsId(updateCeUser.getWorkingConditionsId());

        final Users savedUpdatedCeUser = usersRepository.saveAndFlush(users);
        return ResponseEntity.ok(savedUpdatedCeUser);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> deleteCeUser(@PathVariable(value = "id") Long ceUserId) throws ResponseStatusException {
        Users users = usersRepository.findById(ceUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        usersRepository.delete(users);

        return ResponseEntity.ok().body("CE User with ID '" + ceUserId + "' was successfully deleted.");
    }

    @PatchMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String patchCeUser(@PathVariable(value = "id") Long ceUserId) {
        return "Not implemented.";
    }
}
