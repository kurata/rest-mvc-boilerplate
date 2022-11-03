package br.com.aqueteron.boilerplate.controller;

import br.com.aqueteron.boilerplate.api.CustomerApiSchema;
import br.com.aqueteron.boilerplate.api.CustomerResource;
import br.com.aqueteron.boilerplate.api.GetCustomerRequest;
import br.com.aqueteron.boilerplate.service.CustomerService;
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
        return this.service.getClients(getCustomerRequest).map(CustomerHelper::toClientApiSchemaPage);
    }

    @Override
    public Mono<CustomerApiSchema> postCustomer(final CustomerApiSchema postClientRequest) {
        return this.service.postClient(postClientRequest).map(CustomerHelper::toClientApiSchema);
    }

    @Override
    public Mono<CustomerApiSchema> getCustomer(final Long key) {
        return this.service.getClient(key).map(CustomerHelper::toClientApiSchema);
    }

    @Override
    public Mono<CustomerApiSchema> putCustomer(final Long key, final CustomerApiSchema customerApiSchema) {
        return this.service.putClient(key, customerApiSchema).map(CustomerHelper::toClientApiSchema);
    }

    @Override
    public Mono<CustomerApiSchema> patchCustomer(final Long key, final CustomerApiSchema customerApiSchema) {
        return this.service.patchClient(key, customerApiSchema).map(CustomerHelper::toClientApiSchema);
    }

    @Override
    public Mono<Void> deleteCustomer(final Long key) {
        return this.service.deleteClient(key);
    }
}
