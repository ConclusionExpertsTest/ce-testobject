package nl.conclusionexperts.workingconditionapplication.controller;

import nl.conclusionexperts.workingconditionapplication.domain.CEWorkingConditions;
import nl.conclusionexperts.workingconditionapplication.domain.NewUpdateCEWorkingConditions;
import nl.conclusionexperts.workingconditionapplication.repository.CEWorkingConditionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/workingconditions")
public class CEWorkingConditionsController {

    // TODO: query based on the profile, not salary group

    @Autowired
    private CEWorkingConditionsRepository ceWorkingConditionsRepository;

    private void setAllFields(@RequestBody @Valid @NotEmpty NewUpdateCEWorkingConditions newUpdateCEWorkingConditions, CEWorkingConditions ceWorkingConditions) {
        ceWorkingConditions.setSalaryGroup(newUpdateCEWorkingConditions.getSalaryGroup());
        ceWorkingConditions.setCompanyCar(newUpdateCEWorkingConditions.isCompanyCar());
        ceWorkingConditions.setCompanyLaptop(newUpdateCEWorkingConditions.getCompanyLaptop());
    }

    @GetMapping("/")
    public Iterable<CEWorkingConditions> findAllWorkingConditions() {
        return ceWorkingConditionsRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CEWorkingConditions> findWorkingCondition(@PathVariable(value = "id") Long workingConditionId) throws ResponseStatusException {
        CEWorkingConditions ceWorkingConditions = ceWorkingConditionsRepository.findById(workingConditionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok().body(ceWorkingConditions);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CEWorkingConditions createWorkingCondition(@Valid @NotEmpty @RequestBody NewUpdateCEWorkingConditions newCEWorkingConditions) {
        CEWorkingConditions ceWorkingConditions = CEWorkingConditions.builder().build();

        setAllFields(newCEWorkingConditions, ceWorkingConditions);

        return ceWorkingConditionsRepository.save(ceWorkingConditions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CEWorkingConditions> updateWorkingCondition(@PathVariable(value = "id") Long workingConditionId,
                                                             @Valid @RequestBody NewUpdateCEWorkingConditions updateCEWorkingConditions) throws ResponseStatusException {
        CEWorkingConditions ceWorkingConditions = ceWorkingConditionsRepository.findById(workingConditionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        setAllFields(updateCEWorkingConditions, ceWorkingConditions);

        final CEWorkingConditions savedCeWorkingConditions = ceWorkingConditionsRepository.saveAndFlush(ceWorkingConditions);
        return ResponseEntity.ok(savedCeWorkingConditions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkingCondition(@PathVariable(value = "id") Long workingConditionId) throws ResponseStatusException {
        CEWorkingConditions ceWorkingConditions = ceWorkingConditionsRepository.findById(workingConditionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        ceWorkingConditionsRepository.delete(ceWorkingConditions);

        return ResponseEntity.ok().body("Working condition with ID '" + workingConditionId + "' was successfully deleted.");
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String patchWorkingCondition(@PathVariable(value = "id") Long workingConditionId) {
        return "Not implemented.";
    }

}
