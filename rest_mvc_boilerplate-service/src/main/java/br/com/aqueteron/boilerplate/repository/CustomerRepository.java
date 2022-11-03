package br.com.aqueteron.boilerplate.repository;

import br.com.aqueteron.boilerplate.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

    @Query("FROM Customer c WHERE lower(c.fullName) like %:fullName%")
    Page<Customer> search(@Param("fullName") String fullName, Pageable toPageRequest);

}
