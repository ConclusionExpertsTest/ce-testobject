package usersapplication.domain.response.workingcondition.companylaptop;

import lombok.*;
import nl.conclusionexperts.workingconditionapplication.enums.CompanyLaptopTypes;
import nl.conclusionexperts.workingconditionapplication.enums.OperatingSystem;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class CompanyLaptopResponse {

    private Long id;
    private CompanyLaptopTypes companyLaptopTypes;
    private boolean available;
    private String brandAndType;
    private String memory;
    private String diskspace;
    private OperatingSystem firstOperatingSystem;
    private OperatingSystem secondOperatingSystem;

}
