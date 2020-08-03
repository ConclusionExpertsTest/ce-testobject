package usersapplication.helpers.workingconditions.companylaptops;

import nl.conclusionexperts.workingconditionapplication.enums.CompanyLaptopTypes;
import nl.conclusionexperts.workingconditionapplication.enums.OperatingSystem;
import usersapplication.domain.response.workingcondition.companylaptop.CompanyLaptopResponse;

public class CompanyLaptopsResponseTestData {

    public static CompanyLaptopResponse companyLaptopResponse(CompanyLaptopTypes companyLaptopTypes) {

        switch (companyLaptopTypes) {
            default:
                CompanyLaptopResponse.builder().build();
            case Type1:
                return CompanyLaptopResponse.builder()
                        .id(0L)
                        .companyLaptopTypes(companyLaptopTypes)
                        .available(true)
                        .brandAndType("Apple Macbook Pro 15")
                        .memory("32GB")
                        .diskspace("512GB")
                        .firstOperatingSystem(OperatingSystem.MacOSX)
                        .build();
            case Type2:
                return CompanyLaptopResponse.builder()
                        .id(1L)
                        .companyLaptopTypes(companyLaptopTypes)
                        .available(true)
                        .brandAndType("HP ProBook G6")
                        .memory("32GB")
                        .diskspace("512GB")
                        .firstOperatingSystem(OperatingSystem.Windows)
                        .secondOperatingSystem(OperatingSystem.Linux)
                        .build();
            case Type3:
                return CompanyLaptopResponse.builder()
                        .id(2L)
                        .companyLaptopTypes(companyLaptopTypes)
                        .available(true)
                        .brandAndType("Dell XPS 13,3")
                        .memory("16GB")
                        .diskspace("512GB")
                        .firstOperatingSystem(OperatingSystem.Linux)
                        .build();
            case Type4:
                return CompanyLaptopResponse.builder()
                        .id(3L)
                        .companyLaptopTypes(companyLaptopTypes)
                        .available(true)
                        .brandAndType("Dell XPS 15")
                        .memory("64GB")
                        .diskspace("1024GB")
                        .firstOperatingSystem(OperatingSystem.Windows)
                        .secondOperatingSystem(OperatingSystem.BSD)
                        .build();
            case Type5:
                return CompanyLaptopResponse.builder()
                        .id(4L)
                        .companyLaptopTypes(companyLaptopTypes)
                        .available(true)
                        .brandAndType("Apple Macbook Pro 13")
                        .memory("8GB")
                        .diskspace("128GB")
                        .firstOperatingSystem(OperatingSystem.MacOSX)
                        .build();
        }
    }

}
