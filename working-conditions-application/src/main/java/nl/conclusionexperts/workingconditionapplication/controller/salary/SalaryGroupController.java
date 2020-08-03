package nl.conclusionexperts.workingconditionapplication.controller.salary;

import nl.conclusionexperts.workingconditionapplication.domain.salary.SalaryGroup;
import nl.conclusionexperts.workingconditionapplication.enums.SalaryGroups;
import nl.conclusionexperts.workingconditionapplication.domain.salary.UpdateSalaryGroup;
import nl.conclusionexperts.workingconditionapplication.repository.salary.SalaryGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("/api/workingconditions/salarygroups")
public class SalaryGroupController {

    @Autowired
    private SalaryGroupRepository salaryGroupRepository;

    @GetMapping("/")
    public Iterable<SalaryGroup> findAllSalaryGroups() {
        return salaryGroupRepository.findAll();
    }

    @GetMapping("/{salaryGroupCode}")
    public ResponseEntity<SalaryGroup> findSalaryGroup(@PathVariable(value = "salaryGroupCode") SalaryGroups salaryGroups) throws ResponseStatusException {
        SalaryGroup salaryGroup = ofNullable(salaryGroupRepository.findUserBySalaryGroupCode(salaryGroups))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok().body(salaryGroup);
    }

    @PutMapping("/{salaryGroupCode}")
    public ResponseEntity<SalaryGroup> updateSalaryGroup(@PathVariable(value = "salaryGroupCode") SalaryGroups salaryGroups,
                                                @Valid @RequestBody UpdateSalaryGroup updatedSalaryGroup) throws ResponseStatusException {

        SalaryGroup salaryGroup = ofNullable(salaryGroupRepository.findUserBySalaryGroupCode(salaryGroups))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        salaryGroup.setMinAmount(updatedSalaryGroup.getMinAmount());
        salaryGroup.setMaxAmount(updatedSalaryGroup.getMaxAmount());

        return ResponseEntity.ok().body(salaryGroup);
    }

    @PatchMapping("/{salaryGroupCode}")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String patchSalaryGroup(@PathVariable(value = "salaryGroupCode") SalaryGroups salaryGroups) {
        return "Not implemented.";
    }

}
