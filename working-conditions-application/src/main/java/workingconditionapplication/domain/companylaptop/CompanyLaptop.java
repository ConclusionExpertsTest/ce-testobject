package workingconditionapplication.domain.companylaptop;

import lombok.*;
import workingconditionapplication.enums.CompanyLaptopTypes;
import workingconditionapplication.enums.OperatingSystem;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyLaptop {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private @Enumerated(EnumType.STRING) @Column(nullable = false)
    CompanyLaptopTypes companyLaptopTypes;
    private @Column(nullable = false) boolean available;
    private @Column(nullable = false) String brandAndType;
    private @Column(nullable = false) String memory;
    private @Column(nullable = false) String diskspace;
    private @Enumerated(EnumType.STRING) @Column(nullable = false) OperatingSystem firstOperatingSystem;
    private @Enumerated(EnumType.STRING) OperatingSystem secondOperatingSystem;

}
