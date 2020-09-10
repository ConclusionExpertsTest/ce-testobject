package workingconditionapplication.controller.salary;

import workingconditionapplication.domain.companylaptop.CompanyLaptop;
import workingconditionapplication.domain.salary.SalaryGroup;
import workingconditionapplication.enums.SalaryGroups;
import workingconditionapplication.domain.salary.UpdateSalaryGroup;
import workingconditionapplication.repository.salary.SalaryGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("/api/workingconditions/salarygroups")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8081"})
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

        final SalaryGroup savedSalaryGroup = salaryGroupRepository.saveAndFlush(salaryGroup);

        return ResponseEntity.ok().body(savedSalaryGroup);
    }

    @PatchMapping("/{salaryGroupCode}")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String patchSalaryGroup(@PathVariable(value = "salaryGroupCode") SalaryGroups salaryGroups) {
        return "Not implemented.";
    }

}
