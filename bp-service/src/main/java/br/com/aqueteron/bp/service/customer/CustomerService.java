package br.com.aqueteron.bp.service.customer;

import br.com.aqueteron.bp.service.exception.ExceptionFactory;
import br.com.aqueteron.bp.service.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final ExceptionFactory exceptionFactory;

    public Customer createCustomer(final Customer customer) {
        return this.customerRepository.save(customer);
    }

    public void deleteCustomer(final Long customerId) {
        this.customerRepository.deleteById(customerId);
    }

    public Customer fullUpdateCustomer(final Customer customer) {
        return this.customerRepository.save(customer);
    }

    public Customer partialUpdateCustomer(final Customer customer) {
        Customer customerPersisted = this.readCustomer(customer.getId());
        if (customer.getName() != null) {
            customerPersisted.setName(customer.getName());
        }
        return customerPersisted;
    }

    public Customer readCustomer(final Long customerId) {
        return this.customerRepository
                .findById(customerId)
                .orElseThrow(() -> exceptionFactory.build(HttpStatus.NOT_FOUND, MessageKeys.DEFAULT_NOT_FOUND, Customer.class.getSimpleName(), customerId));
    }

    public Page<Customer> searchCustomer(Pageable pageable) {
        return this.customerRepository.findAll(pageable);
    }
}
