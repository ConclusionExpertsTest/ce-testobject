package workingconditionapplication.repository.companylaptop;

import workingconditionapplication.domain.companylaptop.CompanyLaptop;
import workingconditionapplication.enums.CompanyLaptopTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CompanyLaptopRepository extends JpaRepository<CompanyLaptop, Long> {

    @Query("SELECT cl FROM CompanyLaptop cl WHERE cl.available = true")
    Collection<CompanyLaptop> findAllByAvailable();

    @Query("SELECT cl FROM CompanyLaptop cl WHERE cl.available = false")
    Collection<CompanyLaptop> findAllByUnavailable();

    CompanyLaptop findCompanyLaptopByCompanyLaptopTypes(CompanyLaptopTypes companyLaptopTypes);

}