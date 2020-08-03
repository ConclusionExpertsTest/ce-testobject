package nl.conclusionexperts.workingconditionapplication.repository.companylaptop;

import nl.conclusionexperts.workingconditionapplication.domain.companylaptop.CompanyLaptop;
import nl.conclusionexperts.workingconditionapplication.enums.CompanyLaptops;
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

    CompanyLaptop findCompanyLaptopByCompanyLaptops(CompanyLaptops companyLaptops);

}