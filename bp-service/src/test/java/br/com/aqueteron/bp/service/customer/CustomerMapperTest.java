package br.com.aqueteron.bp.service.customer;

import br.com.aqueteron.bp.api.model.CustomerApiSchema;
import br.com.aqueteron.bp.api.model.PageResultCustomerApiSchema;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.Link;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    private static final String CUSTOMERS_URL = "http://localhost/api/v1/customers";

    private final Faker faker = new Faker();

    private final CustomerMapper customerMapper = new CustomerMapperImpl();

    @BeforeAll
    static void setUpBeforeClass() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void toCustomer() {
        Long customerId = faker.random().nextLong();
        CustomerApiSchema customerApiSchema = CustomerStubs.newCustomerApiSchemaStub();

        Customer customerResult = this.customerMapper.toCustomer(customerId, customerApiSchema);
        assertEquals(customerId, customerResult.getId());
        assertEquals(customerApiSchema.getName(), customerResult.getName());
        assertEquals(customerApiSchema.getBirth(), customerResult.getBirth());
    }

    @Test
    void toCustomerApiSchema() {
        Customer customer = CustomerStubs.customerStub();

        CustomerApiSchema customerApiSchemaResult = this.customerMapper.toCustomerApiSchema(customer);
        assertEquals(customer.getId(), customerApiSchemaResult.getId());
        assertEquals(customer.getName(), customerApiSchemaResult.getName());
        assertEquals(customer.getBirth(), customerApiSchemaResult.getBirth());

        Optional<Link> selfLinkOptional = customerApiSchemaResult.getLink("self");
        assertTrue(selfLinkOptional.isPresent());
        Link selfLink = selfLinkOptional.get();
        assertEquals(CUSTOMERS_URL + "/" + customer.getId(), selfLink.getHref());
    }

    @Test
    void toPageResultCustomerApiSchema() {
        List<Customer> customerList = new ArrayList<>(1);
        customerList.add(CustomerStubs.customerStub());
        Page<Customer> customerPage = new PageImpl<>(customerList);

        PageResultCustomerApiSchema pageResultCustomerApiSchema = this.customerMapper.toPageResultCustomerApiSchema(customerPage);

        assertEquals(1, pageResultCustomerApiSchema.getTotalPages());
        assertEquals(1, pageResultCustomerApiSchema.getTotalElements());
        List<CustomerApiSchema> customerApiSchemas = pageResultCustomerApiSchema.getResult();
        assertEquals(1, customerApiSchemas.size());

        Optional<Link> selfLinkOptional = pageResultCustomerApiSchema.getLink("self");
        assertTrue(selfLinkOptional.isPresent());
        Link selfLink = selfLinkOptional.get();
        assertEquals(CUSTOMERS_URL, selfLink.getHref());
    }

}
