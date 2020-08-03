package nl.conclusionexperts.workingconditionapplication.domain.salary;

import lombok.*;

import javax.persistence.Column;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSalaryGroup {

    private @Column(nullable = false) double minAmount;
    private @Column(nullable = false) double maxAmount;

}
