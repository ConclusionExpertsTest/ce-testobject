package workingconditionapplication.controller.companylaptop;


import workingconditionapplication.domain.companylaptop.CompanyLaptop;
import workingconditionapplication.domain.companylaptop.NewUpdateCompanyLaptop;
import workingconditionapplication.enums.CompanyLaptopTypes;
import workingconditionapplication.repository.companylaptop.CompanyLaptopRepository;
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
        companyLaptop.setCompanyLaptopTypes(newCompanyLaptop.getCompanyLaptopTypes());
        companyLaptop.setBrandAndType(newCompanyLaptop.getBrandAndType());
        companyLaptop.setMemory(newCompanyLaptop.getMemory());
        companyLaptop.setDiskspace(newCompanyLaptop.getDiskspace());
        companyLaptop.setFirstOperatingSystem(newCompanyLaptop.getFirstOperatingSystem());
        companyLaptop.setSecondOperatingSystem(newCompanyLaptop.getSecondOperatingSystem());
    }

    @GetMapping("/")
    @CrossOrigin(origins = "http://localhost:4200")
    public Iterable<CompanyLaptop> findAllAvailableCompanyLaptops() {
        return companyLaptopRepository.findAllByAvailable();
    }

    @GetMapping("/unavailable")
    @CrossOrigin(origins = "http://localhost:4200")
    public Iterable<CompanyLaptop> findAllUnvailableCompanyLaptops() {
        return companyLaptopRepository.findAllByUnavailable();
    }

    @GetMapping("/{companyLaptopType}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CompanyLaptop> findCompanyLaptop(@PathVariable(value = "companyLaptopType") CompanyLaptopTypes companyLaptopTypes) throws ResponseStatusException {
        CompanyLaptop companyLaptop = ofNullable(companyLaptopRepository.findCompanyLaptopByCompanyLaptopTypes(companyLaptopTypes))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok().body(companyLaptop);
    }

    @PostMapping("/")
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyLaptop createCompanyLaptop(@Valid @NotEmpty @RequestBody NewUpdateCompanyLaptop newCompanyLaptop) {
        CompanyLaptop companyLaptop = CompanyLaptop.builder().build();

        setAllFields(newCompanyLaptop, companyLaptop);

        return companyLaptopRepository.save(companyLaptop);
    }

    @PutMapping("/{companyLaptopType}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CompanyLaptop> updateCompanyLaptop(@PathVariable(value = "companyLaptopType") CompanyLaptopTypes companyLaptopTypes,
                                                @Valid @RequestBody NewUpdateCompanyLaptop updateCompanyLaptop) throws ResponseStatusException {
        CompanyLaptop companyLaptop = ofNullable(companyLaptopRepository.findCompanyLaptopByCompanyLaptopTypes(companyLaptopTypes))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        setAllFields(updateCompanyLaptop, companyLaptop);

        final CompanyLaptop savedCompanyLaptop = companyLaptopRepository.saveAndFlush(companyLaptop);
        return ResponseEntity.ok(savedCompanyLaptop);
    }

    @PatchMapping("/{companyLaptopType}")
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String patchCompanyLaptop(@PathVariable(value = "id") CompanyLaptopTypes companyLaptopTypes) {
        return "Not implemented.";
    }

}
