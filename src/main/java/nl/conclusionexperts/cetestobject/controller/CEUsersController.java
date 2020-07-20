package nl.conclusionexperts.cetestobject.controller;

import nl.conclusionexperts.cetestobject.domain.CEUsers;
import nl.conclusionexperts.cetestobject.repository.CEUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class CEUsersController {

    @Autowired
    private CEUsersRepository ceUsersRepository;

    @GetMapping("/")
    public Iterable<CEUsers> findAllCeUsers() {
        return ceUsersRepository.findAllByIsActive();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CEUsers> findCeUser(@PathVariable(value = "id") Long ceUserId) throws ResponseStatusException {
        CEUsers ceUsers = ceUsersRepository.findById(ceUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok().body(ceUsers);
    }

    @GetMapping("/inactive")
    public Iterable<CEUsers> findAllInActiveCeUsers() {
        return ceUsersRepository.findAllByIsInActive();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CEUsers createCeUser(@Valid @NotEmpty @RequestBody CEUsers ceUser) {
        return ceUsersRepository.save(ceUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CEUsers> updateCeUser(@PathVariable(value = "id") Long ceUserId,
                                                @Valid @RequestBody CEUsers updatedCeUser) throws ResponseStatusException {
        CEUsers ceUsers = ceUsersRepository.findById(ceUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        ceUsers.setFirstName(updatedCeUser.getFirstName());
        ceUsers.setLastName(updatedCeUser.getLastName());
        ceUsers.setAddress(updatedCeUser.getAddress());
        ceUsers.setOccupation(updatedCeUser.getOccupation());

        final CEUsers savedUpdatedCeUser = ceUsersRepository.saveAndFlush(ceUsers);
        return ResponseEntity.ok(savedUpdatedCeUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCeUser(@PathVariable(value = "id") Long ceUserId) throws ResponseStatusException {
        CEUsers ceUsers = ceUsersRepository.findById(ceUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        ceUsersRepository.delete(ceUsers);

        return ResponseEntity.ok().body("CE User with ID '" + ceUserId + "' was successfully deleted.");
    }
}
