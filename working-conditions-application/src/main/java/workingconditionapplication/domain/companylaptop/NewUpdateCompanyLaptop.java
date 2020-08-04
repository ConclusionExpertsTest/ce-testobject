package workingconditionapplication.domain.companylaptop;

import lombok.*;
import workingconditionapplication.enums.CompanyLaptopTypes;
import workingconditionapplication.enums.OperatingSystem;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewUpdateCompanyLaptop {

    private @Enumerated(EnumType.STRING) @Column(nullable = false)
    CompanyLaptopTypes companyLaptopTypes;
    private @Column(nullable = false) boolean available;
    private @Column(nullable = false) String type;
    private @Column(nullable = false) String memory;
    private @Column(nullable = false) String diskspace;
    private @Enumerated(EnumType.STRING) @Column(nullable = false) OperatingSystem firstOperatingSystem;
    private @Enumerated(EnumType.STRING) OperatingSystem secondOperatingSystem;

}
