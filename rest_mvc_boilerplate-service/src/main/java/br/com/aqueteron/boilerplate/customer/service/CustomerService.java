package br.com.aqueteron.boilerplate.customer.service;

import br.com.aqueteron.boilerplate.components.utilities.AbstractCrudRestService;
import br.com.aqueteron.boilerplate.components.utilities.PageResult;
import br.com.aqueteron.boilerplate.customer.api.CustomerApiSchema;
import br.com.aqueteron.boilerplate.customer.api.GetCustomerRequest;
import br.com.aqueteron.boilerplate.customer.controller.CustomerHelper;
import br.com.aqueteron.boilerplate.customer.domain.Customer;
import br.com.aqueteron.boilerplate.customer.repository.CustomerRepository;
import br.com.aqueteron.boilerplate.exception.BusinessExceptionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CustomerService extends AbstractCrudRestService<Customer, CustomerApiSchema, Long> {

    private final CustomerRepository repository;

    private final CustomerHelper helper;

    @Autowired
    public CustomerService(final BusinessExceptionFactory businessExceptionFactory, final CustomerRepository repository, final CustomerHelper helper) {
        super(businessExceptionFactory, repository, helper, Customer.class);
        this.repository = repository;
        this.helper = helper;
    }

    public Mono<PageResult<CustomerApiSchema>> getByRequest(final GetCustomerRequest getCustomerRequest) {
        return Mono.just(helper.toClientApiSchemaPage(this.repository.search(getCustomerRequest.getFullName(), getCustomerRequest.toPageRequest())));

    }
}
