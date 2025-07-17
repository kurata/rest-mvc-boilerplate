package br.com.aqueteron.bp.service.customer;

import br.com.aqueteron.bp.api.model.CustomerApiSchema;
import net.datafaker.Faker;
import org.jetbrains.annotations.NotNull;

public class CustomerStubs {

    private static final Faker faker = new Faker();

    public static @NotNull CustomerApiSchema newCustomerApiSchemaStub() {
        CustomerApiSchema customerApiSchema = new CustomerApiSchema();
        customerApiSchema.setName(faker.name().name());
        customerApiSchema.setBirth(faker.timeAndDate().birthday());
        return customerApiSchema;
    }

    public static @NotNull Customer customerStub() {
        Customer customer = new Customer(faker.random().nextLong());
        customer.setName(faker.name().name());
        customer.setBirth(faker.timeAndDate().birthday());
        return customer;
    }
}
