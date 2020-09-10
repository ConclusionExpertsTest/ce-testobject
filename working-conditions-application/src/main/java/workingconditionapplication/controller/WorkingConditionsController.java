package workingconditionapplication.controller;

import workingconditionapplication.domain.WorkingConditions;
import workingconditionapplication.domain.NewUpdateWorkingConditions;
import workingconditionapplication.repository.WorkingConditionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/workingconditions")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8081"})
public class WorkingConditionsController {

    // TODO: query based on the profile, not salary group

    @Autowired
    private WorkingConditionsRepository workingConditionsRepository;

    private void setAllFields(@RequestBody @Valid @NotEmpty NewUpdateWorkingConditions newUpdateWorkingConditions, WorkingConditions workingConditions) {
        workingConditions.setSalaryGroup(newUpdateWorkingConditions.getSalaryGroup());
        workingConditions.setCompanyCar(newUpdateWorkingConditions.isCompanyCar());
        workingConditions.setCompanyLaptop(newUpdateWorkingConditions.getCompanyLaptop());
    }

    @GetMapping("/")
    public Iterable<WorkingConditions> findAllWorkingConditions() {
        return workingConditionsRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkingConditions> findWorkingCondition(@PathVariable(value = "id") Long workingConditionId) throws ResponseStatusException {
        WorkingConditions workingConditions = workingConditionsRepository.findById(workingConditionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok().body(workingConditions);
    }

    @PostMapping("/")
    public ResponseEntity<WorkingConditions> createWorkingCondition(@Valid @NotEmpty @RequestBody NewUpdateWorkingConditions newWorkingConditions) throws URISyntaxException {
        WorkingConditions workingConditions = WorkingConditions.builder()
                .salaryGroup(newWorkingConditions.getSalaryGroup())
                .companyCar(newWorkingConditions.isCompanyCar())
                .companyLaptop(newWorkingConditions.getCompanyLaptop()).build();

        workingConditionsRepository.save(workingConditions);

        return ResponseEntity.created(new URI("/" + workingConditions.getId())).body(workingConditions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkingConditions> updateWorkingCondition(@PathVariable(value = "id") Long workingConditionId,
                                                                    @Valid @RequestBody NewUpdateWorkingConditions updateCEWorkingConditions) throws ResponseStatusException {
        WorkingConditions workingConditions = workingConditionsRepository.findById(workingConditionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        setAllFields(updateCEWorkingConditions, workingConditions);

        final WorkingConditions savedWorkingConditions = workingConditionsRepository.saveAndFlush(workingConditions);
        return ResponseEntity.ok(savedWorkingConditions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkingCondition(@PathVariable(value = "id") Long workingConditionId) throws ResponseStatusException {
        WorkingConditions workingConditions = workingConditionsRepository.findById(workingConditionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        workingConditionsRepository.delete(workingConditions);

        return ResponseEntity.ok().body("{\"succesMessage\": \"Working condition with ID '" + workingConditionId + "' was successfully deleted.\"}");
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String patchWorkingCondition(@PathVariable(value = "id") Long workingConditionId) {
        return "Not implemented.";
    }

}
