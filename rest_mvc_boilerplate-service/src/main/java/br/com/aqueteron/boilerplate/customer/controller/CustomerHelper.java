package br.com.aqueteron.boilerplate.customer.controller;

import br.com.aqueteron.boilerplate.components.utilities.Helper;
import br.com.aqueteron.boilerplate.customer.api.CustomerApiSchema;
import br.com.aqueteron.boilerplate.customer.domain.Customer;
import br.com.aqueteron.boilerplate.components.utilities.PageResult;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CustomerHelper implements Helper<Customer, CustomerApiSchema, Long> {

    private CustomerHelper() {
        super();
    }

    public PageResult<CustomerApiSchema> toClientApiSchemaPage(final Page<Customer> clientApiSchemas) {
        return new PageResult<>(
                clientApiSchemas.getContent().stream()
                        .map(this::toApiSchema)
                        .collect(Collectors.toList()),
                clientApiSchemas.getTotalPages(),
                clientApiSchemas.getTotalElements()
        );
    }

    @Override
    public CustomerApiSchema toApiSchema(final Customer customer) {
        return new CustomerApiSchema(customer.getId(), customer.getFullName(), customer.getBirthDate());
    }

    @Override
    public Customer toDomainSchema(final CustomerApiSchema customerApiSchema) {
        Customer customer = new Customer(customerApiSchema.getId());
        customer.setFullName(customerApiSchema.getFullName());
        customer.setBirthDate(customerApiSchema.getBirthDate());
        return customer;
    }

    @Override
    public Customer mergeDomainSchema(final Customer originalObject, final CustomerApiSchema newObjectSchema) {
        Customer customerToUpdate = new Customer(originalObject);
        if (newObjectSchema.getFullName() != null && !newObjectSchema.getFullName().isEmpty()) {
            customerToUpdate.setFullName(newObjectSchema.getFullName());
        }
        if (newObjectSchema.getBirthDate() != null) {
            customerToUpdate.setBirthDate(newObjectSchema.getBirthDate());
        }
        return customerToUpdate;
    }

    @Override
    public Long extractId(final CustomerApiSchema newObjectSchema) {
        return newObjectSchema.getId();
    }
}
