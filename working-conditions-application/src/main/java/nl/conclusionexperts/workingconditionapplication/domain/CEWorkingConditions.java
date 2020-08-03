package nl.conclusionexperts.workingconditionapplication.domain;

import lombok.*;
import nl.conclusionexperts.workingconditionapplication.enums.CompanyLaptops;
import nl.conclusionexperts.workingconditionapplication.enums.SalaryGroups;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CEWorkingConditions {

    // TODO: profile with ENUM (DEV, TEST, TAE, FAB, ETC...)

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private @Enumerated(EnumType.STRING) @Column(nullable = false) SalaryGroups salaryGroup;
    private @Column(nullable = false) boolean companyCar;
    private @Enumerated(EnumType.STRING) @Column(nullable = false) CompanyLaptops companyLaptop;

}
