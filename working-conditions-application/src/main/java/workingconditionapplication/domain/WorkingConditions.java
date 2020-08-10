package workingconditionapplication.domain;

import lombok.*;
import workingconditionapplication.enums.CompanyLaptopTypes;
import workingconditionapplication.enums.SalaryGroups;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkingConditions {

    // TODO: profile with ENUM (DEV, TEST, TAE, FAB, ETC...)

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private @Enumerated(EnumType.STRING) @Column(nullable = false) SalaryGroups salaryGroup;
    private @Column(nullable = false) boolean companyCar;
    private @Enumerated(EnumType.STRING) @Column(nullable = false)
    CompanyLaptopTypes companyLaptop;

}
