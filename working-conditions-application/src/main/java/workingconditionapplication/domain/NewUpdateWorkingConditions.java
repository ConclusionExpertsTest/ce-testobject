package workingconditionapplication.domain;

import lombok.*;
import workingconditionapplication.enums.CompanyLaptopTypes;
import workingconditionapplication.enums.SalaryGroups;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewUpdateWorkingConditions {

    private @Enumerated(EnumType.STRING) @Column(nullable = false) SalaryGroups salaryGroup;
    private @Column(nullable = false) boolean companyCar;
    private @Enumerated(EnumType.STRING) @Column(nullable = false)
    CompanyLaptopTypes companyLaptop;

}
