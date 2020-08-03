package nl.conclusionexperts.workingconditionapplication.controller;

import nl.conclusionexperts.workingconditionapplication.domain.WorkingConditions;
import nl.conclusionexperts.workingconditionapplication.domain.NewUpdateWorkingConditions;
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

    private void setAllFields(@RequestBody @Valid @NotEmpty NewUpdateWorkingConditions newUpdateWorkingConditions, WorkingConditions workingConditions) {
        workingConditions.setSalaryGroup(newUpdateWorkingConditions.getSalaryGroup());
        workingConditions.setCompanyCar(newUpdateWorkingConditions.isCompanyCar());
        workingConditions.setCompanyLaptop(newUpdateWorkingConditions.getCompanyLaptop());
    }

    @GetMapping("/")
    public Iterable<WorkingConditions> findAllWorkingConditions() {
        return ceWorkingConditionsRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkingConditions> findWorkingCondition(@PathVariable(value = "id") Long workingConditionId) throws ResponseStatusException {
        WorkingConditions workingConditions = ceWorkingConditionsRepository.findById(workingConditionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok().body(workingConditions);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public WorkingConditions createWorkingCondition(@Valid @NotEmpty @RequestBody NewUpdateWorkingConditions newCEWorkingConditions) {
        WorkingConditions workingConditions = WorkingConditions.builder().build();

        setAllFields(newCEWorkingConditions, workingConditions);

        return ceWorkingConditionsRepository.save(workingConditions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkingConditions> updateWorkingCondition(@PathVariable(value = "id") Long workingConditionId,
                                                                    @Valid @RequestBody NewUpdateWorkingConditions updateCEWorkingConditions) throws ResponseStatusException {
        WorkingConditions workingConditions = ceWorkingConditionsRepository.findById(workingConditionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        setAllFields(updateCEWorkingConditions, workingConditions);

        final WorkingConditions savedWorkingConditions = ceWorkingConditionsRepository.saveAndFlush(workingConditions);
        return ResponseEntity.ok(savedWorkingConditions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkingCondition(@PathVariable(value = "id") Long workingConditionId) throws ResponseStatusException {
        WorkingConditions workingConditions = ceWorkingConditionsRepository.findById(workingConditionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        ceWorkingConditionsRepository.delete(workingConditions);

        return ResponseEntity.ok().body("Working condition with ID '" + workingConditionId + "' was successfully deleted.");
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String patchWorkingCondition(@PathVariable(value = "id") Long workingConditionId) {
        return "Not implemented.";
    }

}
