package nl.conclusionexperts.workingconditionapplication.domain;

import lombok.*;
import nl.conclusionexperts.workingconditionapplication.enums.CompanyLaptops;
import nl.conclusionexperts.workingconditionapplication.enums.SalaryGroups;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewUpdateCEWorkingConditions {

    private @Enumerated(EnumType.STRING) @Column(nullable = false) SalaryGroups salaryGroup;
    private @Column(nullable = false) boolean companyCar;
    private @Enumerated(EnumType.STRING) @Column(nullable = false) CompanyLaptops companyLaptop;

}
