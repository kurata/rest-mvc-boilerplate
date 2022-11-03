package br.com.aqueteron.boilerplate.controller;

import br.com.aqueteron.boilerplate.api.CustomerApiSchema;
import br.com.aqueteron.boilerplate.domain.Customer;
import br.com.aqueteron.boilerplate.components.utilities.PageResult;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class CustomerHelper {

    private CustomerHelper() {
        super();
    }

    public static PageResult<CustomerApiSchema> toClientApiSchemaPage(final Page<Customer> clientApiSchemas) {
        return new PageResult<>(
                clientApiSchemas.getContent().stream()
                        .map(CustomerHelper::toClientApiSchema)
                        .collect(Collectors.toList()),
                clientApiSchemas.getTotalPages(),
                clientApiSchemas.getTotalElements()
        );
    }

    public static CustomerApiSchema toClientApiSchema(final Customer customer) {
        return new CustomerApiSchema(customer.getId(), customer.getFullName(), customer.getBirthDate());
    }

    public static Customer toClient(final CustomerApiSchema customerApiSchema) {
        Customer customer = new Customer(customerApiSchema.getId());
        customer.setFullName(customerApiSchema.getFullName());
        customer.setBirthDate(customerApiSchema.getBirthDate());
        return customer;
    }
}
