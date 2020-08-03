package nl.conclusionexperts.workingconditionapplication.controller.companylaptop;


import nl.conclusionexperts.workingconditionapplication.domain.companylaptop.CompanyLaptop;
import nl.conclusionexperts.workingconditionapplication.domain.companylaptop.NewUpdateCompanyLaptop;
import nl.conclusionexperts.workingconditionapplication.enums.CompanyLaptops;
import nl.conclusionexperts.workingconditionapplication.repository.companylaptop.CompanyLaptopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("/api/workingconditions/companylaptop")
public class CompanyLaptopController {

    @Autowired
    private CompanyLaptopRepository companyLaptopRepository;

    private void setAllFields(@RequestBody @Valid @NotEmpty NewUpdateCompanyLaptop newCompanyLaptop, CompanyLaptop companyLaptop) {
        companyLaptop.setAvailable(newCompanyLaptop.isAvailable());
        companyLaptop.setCompanyLaptops(newCompanyLaptop.getCompanyLaptops());
        companyLaptop.setBrandAndType(newCompanyLaptop.getType());
        companyLaptop.setMemory(newCompanyLaptop.getMemory());
        companyLaptop.setDiskspace(newCompanyLaptop.getDiskspace());
        companyLaptop.setFirstOperatingSystem(newCompanyLaptop.getFirstOperatingSystem());
        companyLaptop.setSecondOperatingSystem(newCompanyLaptop.getSecondOperatingSystem());
    }

    @GetMapping("/")
    public Iterable<CompanyLaptop> findAllAvailableCompanyLaptops() {
        return companyLaptopRepository.findAllByAvailable();
    }

    @GetMapping("/unavailable")
    public Iterable<CompanyLaptop> findAllUnvailableCompanyLaptops() {
        return companyLaptopRepository.findAllByUnavailable();
    }

    @GetMapping("/{companyLaptopType}")
    public ResponseEntity<CompanyLaptop> findCompanyLaptop(@PathVariable(value = "companyLaptopType") CompanyLaptops companyLaptops) throws ResponseStatusException {
        CompanyLaptop companyLaptop = ofNullable(companyLaptopRepository.findCompanyLaptopByCompanyLaptops(companyLaptops))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok().body(companyLaptop);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyLaptop createCompanyLaptop(@Valid @NotEmpty @RequestBody NewUpdateCompanyLaptop newCompanyLaptop) {
        CompanyLaptop companyLaptop = CompanyLaptop.builder().build();

        setAllFields(newCompanyLaptop, companyLaptop);

        return companyLaptopRepository.save(companyLaptop);
    }

    @PutMapping("/{companyLaptopType}")
    public ResponseEntity<CompanyLaptop> updateCompanyLaptop(@PathVariable(value = "companyLaptopType") CompanyLaptops companyLaptops,
                                                @Valid @RequestBody NewUpdateCompanyLaptop updateCompanyLaptop) throws ResponseStatusException {
        CompanyLaptop companyLaptop = ofNullable(companyLaptopRepository.findCompanyLaptopByCompanyLaptops(companyLaptops))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        setAllFields(updateCompanyLaptop, companyLaptop);

        final CompanyLaptop savedCompanyLaptop = companyLaptopRepository.saveAndFlush(companyLaptop);
        return ResponseEntity.ok(savedCompanyLaptop);
    }

    @PatchMapping("/{companyLaptopType}")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String patchCompanyLaptop(@PathVariable(value = "id") CompanyLaptops companyLaptops) {
        return "Not implemented.";
    }

}
