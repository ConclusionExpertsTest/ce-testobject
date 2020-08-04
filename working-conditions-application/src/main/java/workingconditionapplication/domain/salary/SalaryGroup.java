package workingconditionapplication.domain.salary;

import lombok.*;
import workingconditionapplication.enums.SalaryGroups;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryGroup {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private @Enumerated(EnumType.STRING) @Column(nullable = false) SalaryGroups salaryGroupCode;
    private @Column(nullable = false) double minAmount;
    private @Column(nullable = false) double maxAmount;

}
