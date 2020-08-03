package usersapplication.controller;

import usersapplication.domain.CEUsers;
import usersapplication.domain.NewUpdateCEUsers;
import usersapplication.domain.response.CeUsersResponse;
import usersapplication.repository.CEUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static usersapplication.mappers.CeUsersResponseMapper.getCeUsersResponse;

@RestController
@RequestMapping("/api/users")
public class CEUsersController {

    @Autowired
    private CEUsersRepository ceUsersRepository;

    @GetMapping("/")
    public ResponseEntity<List<CeUsersResponse>> findAllCeUsers() {
        List<CeUsersResponse> ceUsersResponses = new ArrayList<>(emptyList());

        ceUsersRepository.findAllByIsActive().forEach(ceUser ->
                ceUsersResponses.add(getCeUsersResponse(ceUser)));

        return ResponseEntity.ok().body(ceUsersResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CeUsersResponse> findCeUser(@PathVariable(value = "id") Long ceUserId) throws ResponseStatusException {
        CEUsers ceUser = ceUsersRepository.findById(ceUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok().body(getCeUsersResponse(ceUser));
    }

    @GetMapping("/inactive")
    public Iterable<CEUsers> findAllInActiveCeUsers() {
        return ceUsersRepository.findAllByIsInActive();
    }

    @PostMapping("/")
    public ResponseEntity<CeUsersResponse> createCeUser(@Valid @NotEmpty @RequestBody NewUpdateCEUsers newCeUser) throws URISyntaxException {
        CEUsers ceUser = ceUsersRepository.save(
                CEUsers.builder()
                        .isActive(newCeUser.isActive())
                        .firstName(newCeUser.getFirstName())
                        .lastName(newCeUser.getLastName())
                        .address(newCeUser.getAddress())
                        .occupation(newCeUser.getOccupation())
                        .workingConditionsId(newCeUser.getWorkingConditionsId()).build());

        return ResponseEntity.created(new URI("/" + ceUser.getId())).body(getCeUsersResponse(ceUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CeUsersResponse> updateCeUser(@PathVariable(value = "id") Long ceUserId,
                                                @Valid @RequestBody NewUpdateCEUsers updateCeUser) throws ResponseStatusException {
        CEUsers ceUsers = ceUsersRepository.findById(ceUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        ceUsers.setActive(updateCeUser.isActive());
        ceUsers.setFirstName(updateCeUser.getFirstName());
        ceUsers.setLastName(updateCeUser.getLastName());
        ceUsers.setAddress(updateCeUser.getAddress());
        ceUsers.setOccupation(updateCeUser.getOccupation());
        ceUsers.setWorkingConditionsId(updateCeUser.getWorkingConditionsId());

        final CEUsers savedUpdatedCeUser = ceUsersRepository.saveAndFlush(ceUsers);
        return ResponseEntity.ok(getCeUsersResponse(savedUpdatedCeUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCeUser(@PathVariable(value = "id") Long ceUserId) throws ResponseStatusException {
        CEUsers ceUsers = ceUsersRepository.findById(ceUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        ceUsersRepository.delete(ceUsers);

        return ResponseEntity.ok().body("CE User with ID '" + ceUserId + "' was successfully deleted.");
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String patchCeUser(@PathVariable(value = "id") Long ceUserId) {
        return "Not implemented.";
    }
}
