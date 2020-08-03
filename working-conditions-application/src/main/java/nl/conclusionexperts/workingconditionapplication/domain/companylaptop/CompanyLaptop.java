package nl.conclusionexperts.workingconditionapplication.domain.companylaptop;

import lombok.*;
import nl.conclusionexperts.workingconditionapplication.enums.CompanyLaptops;
import nl.conclusionexperts.workingconditionapplication.enums.OperatingSystem;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyLaptop {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private @Enumerated(EnumType.STRING) @Column(nullable = false) CompanyLaptops companyLaptops;
    private @Column(nullable = false) boolean available;
    private @Column(nullable = false) String brandAndType;
    private @Column(nullable = false) String memory;
    private @Column(nullable = false) String diskspace;
    private @Enumerated(EnumType.STRING) @Column(nullable = false) OperatingSystem firstOperatingSystem;
    private @Enumerated(EnumType.STRING) OperatingSystem secondOperatingSystem;

}
