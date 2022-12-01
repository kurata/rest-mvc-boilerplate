package br.com.aqueteron.boilerplate.customer.controller;

import br.com.aqueteron.boilerplate.customer.api.CustomerApiSchema;
import br.com.aqueteron.boilerplate.customer.api.CustomerResource;
import br.com.aqueteron.boilerplate.customer.api.GetCustomerRequest;
import br.com.aqueteron.boilerplate.customer.service.CustomerService;
import br.com.aqueteron.boilerplate.components.utilities.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Transactional
@Slf4j
public class CustomerController implements CustomerResource {

    private final CustomerService service;

    @Autowired
    public CustomerController(final CustomerService customerService) {
        this.service = customerService;
    }

    @Override
    public Mono<PageResult<CustomerApiSchema>> getCustomers(final GetCustomerRequest getCustomerRequest) {
        return this.service.getByRequest(getCustomerRequest);
    }

    @Override
    public Mono<CustomerApiSchema> postCustomer(final CustomerApiSchema postClientRequest) {
        return this.service.post(postClientRequest);
    }

    @Override
    public Mono<CustomerApiSchema> getCustomer(final Long key) {
        return this.service.getUnique(key);
    }

    @Override
    public Mono<CustomerApiSchema> putCustomer(final Long key, final CustomerApiSchema customerApiSchema) {
        return this.service.put(key, customerApiSchema);
    }

    @Override
    public Mono<CustomerApiSchema> patchCustomer(final Long key, final CustomerApiSchema customerApiSchema) {
        return this.service.patch(key, customerApiSchema);
    }

    @Override
    public Mono<Void> deleteCustomer(final Long key) {
        return this.service.delete(key);
    }
}
