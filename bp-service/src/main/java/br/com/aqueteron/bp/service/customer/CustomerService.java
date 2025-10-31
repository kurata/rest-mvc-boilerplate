package br.com.aqueteron.bp.service.customer;

import br.com.aqueteron.bp.service.exception.BusinessFactory;
import br.com.aqueteron.bp.service.utils.MessageKeys;
import io.micrometer.observation.annotation.Observed;
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

    private final BusinessFactory businessFactory;

    @Observed(name = "customer.create",
            contextualName = "creating-customer",
            lowCardinalityKeyValues = {"customerType", "customerType2"})
    public Customer createCustomer(final Customer customer) {
        return this.customerRepository.save(customer);
    }

    @Observed(name = "customer.delete",
            contextualName = "deleting-customer",
            lowCardinalityKeyValues = {"customerType", "customerType2"})
    public void deleteCustomer(final Long customerId) {
        Customer customer = readCustomer(customerId);
        this.customerRepository.delete(customer);
    }

    @Observed(name = "customer.update",
            contextualName = "full-updating-customer",
            lowCardinalityKeyValues = {"customerType", "customerType2"})
    public Customer fullUpdateCustomer(final Customer customer) {
        this.readCustomer(customer.getId());
        return this.customerRepository.save(customer);
    }

    @Observed(name = "customer.update",
            contextualName = "partial-updating-customer",
            lowCardinalityKeyValues = {"customerType", "customerType2"})
    public Customer partialUpdateCustomer(final Customer customer) {
        Customer customerPersisted = this.readCustomer(customer.getId());
        if (customer.getName() != null) {
            customerPersisted.setName(customer.getName());
        }
        if (customer.getBirth() != null) {
            customerPersisted.setBirth(customer.getBirth());
        }
        return customerPersisted;
    }

    @Observed(name = "customer.read",
            contextualName = "getting-customer-by-id",
            lowCardinalityKeyValues = {"customerType", "customerType2"})
    public Customer readCustomer(final Long customerId) {
        return this.customerRepository
                .findById(customerId)
                .orElseThrow(() -> businessFactory.build(HttpStatus.NOT_FOUND, MessageKeys.DEFAULT_NOT_FOUND, Customer.class.getSimpleName(), customerId));
    }

    @Observed(name = "customer.search",
            contextualName = "searching-customer",
            lowCardinalityKeyValues = {"customerType", "customerType2"})
    public Page<Customer> searchCustomer(Pageable pageable) {
        return this.customerRepository.findAll(pageable);
    }
}
