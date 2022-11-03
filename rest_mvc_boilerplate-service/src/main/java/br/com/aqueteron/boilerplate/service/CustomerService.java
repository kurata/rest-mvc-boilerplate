package br.com.aqueteron.boilerplate.service;

import br.com.aqueteron.boilerplate.api.CustomerApiSchema;
import br.com.aqueteron.boilerplate.api.GetCustomerRequest;
import br.com.aqueteron.boilerplate.controller.CustomerHelper;
import br.com.aqueteron.boilerplate.domain.Customer;
import br.com.aqueteron.boilerplate.repository.CustomerRepository;
import br.com.aqueteron.boilerplate.components.MessageKeys;
import br.com.aqueteron.boilerplate.exception.BusinessExceptionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Slf4j
public class CustomerService {

    private final BusinessExceptionFactory businessExceptionFactory;

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(final BusinessExceptionFactory businessExceptionFactory,
                           final CustomerRepository customerRepository) {
        this.businessExceptionFactory = businessExceptionFactory;
        this.customerRepository = customerRepository;
    }

    public Mono<Page<Customer>> getClients(final GetCustomerRequest getCustomerRequest) {
        if (getCustomerRequest.getId() == null && getCustomerRequest.getFullName() == null) {
            return Mono.just(this.customerRepository.findAll(getCustomerRequest.toPageRequest()));
        }
        return Mono.just(this.customerRepository.search(getCustomerRequest.getFullName(), getCustomerRequest.toPageRequest()));
    }

    public Mono<Customer> postClient(final CustomerApiSchema postClientRequest) {
        return Mono.just(this.customerRepository.save(CustomerHelper.toClient(postClientRequest)));
    }

    public Mono<Customer> getClient(final Long key) {
        Optional<Customer> clientOptional = this.customerRepository.findById(key);
        if (clientOptional.isEmpty()) {
            return Mono.error(this.businessExceptionFactory.build(HttpStatus.NOT_FOUND, MessageKeys.CLIENT_NOT_FOUND, key));
        }
        return Mono.just(clientOptional.get());
    }

    public Mono<Customer> putClient(final Long key, final CustomerApiSchema customerApiSchema) {
        Optional<Customer> clientOptional = this.customerRepository.findById(key);
        if (clientOptional.isEmpty()) {
            return Mono.error(this.businessExceptionFactory.build(HttpStatus.NOT_FOUND, MessageKeys.CLIENT_NOT_FOUND, key));
        }
        Customer customer = clientOptional.get();
        customer.setFullName(customerApiSchema.getFullName());
        customer.setBirthDate(customerApiSchema.getBirthDate());
        return Mono.just(this.customerRepository.save(customer));
    }

    public Mono<Customer> patchClient(final Long key, final CustomerApiSchema customerApiSchema) {
        Optional<Customer> clientOptional = this.customerRepository.findById(key);
        if (clientOptional.isEmpty()) {
            return Mono.error(this.businessExceptionFactory.build(HttpStatus.NOT_FOUND, MessageKeys.CLIENT_NOT_FOUND, key));
        }
        Customer customer = clientOptional.get();
        if (customerApiSchema.getFullName() != null) {
            customer.setFullName(customerApiSchema.getFullName());
        }
        if (customerApiSchema.getBirthDate() != null) {
            customer.setBirthDate(customerApiSchema.getBirthDate());
        }
        return Mono.just(this.customerRepository.save(customer));
    }

    public Mono<Void> deleteClient(final Long key) {
        Optional<Customer> clientOptional = this.customerRepository.findById(key);
        if (clientOptional.isEmpty()) {
            return Mono.error(this.businessExceptionFactory.build(HttpStatus.NOT_FOUND, MessageKeys.CLIENT_NOT_FOUND, key));
        }
        this.customerRepository.delete(clientOptional.get());
        return Mono.empty();
    }
}
