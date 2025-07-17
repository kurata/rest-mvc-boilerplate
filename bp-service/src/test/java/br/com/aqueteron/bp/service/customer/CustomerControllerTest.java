package br.com.aqueteron.bp.service.customer;

import br.com.aqueteron.bp.api.model.CustomerApiSchema;
import br.com.aqueteron.bp.api.model.PageResultCustomerApiSchema;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MockitoSettings
class CustomerControllerTest {

    Faker faker = new Faker();

    @InjectMocks
    CustomerController customerController;

    @Mock
    CustomerMapper customerMapperMock;

    @Mock
    CustomerService customerServiceMock;

    @Test
    void createCustomer() {
        CustomerApiSchema customerApiSchema = CustomerStubs.newCustomerApiSchemaStub();
        Customer customer = new Customer();
        CustomerApiSchema customerApiSchemaResponse = new CustomerApiSchema();
        when(this.customerMapperMock.toCustomer(any(), eq(customerApiSchema))).thenReturn(customer);
        when(this.customerServiceMock.createCustomer(customer)).thenReturn(customer);
        when(this.customerMapperMock.toCustomerApiSchema(customer)).thenReturn(customerApiSchemaResponse);

        ResponseEntity<CustomerApiSchema> responseEntity = this.customerController.createCustomer(customerApiSchema);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void deleteCustomer() {
        ResponseEntity<Void> responseEntity = this.customerController.deleteCustomer(faker.random().nextLong());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void fullUpdateCustomer() {
        Long customerId = faker.random().nextLong();
        CustomerApiSchema customerApiSchema = CustomerStubs.newCustomerApiSchemaStub();
        Customer customer = new Customer();
        CustomerApiSchema customerApiSchemaResponse = new CustomerApiSchema();
        when(this.customerMapperMock.toCustomer(eq(customerId), eq(customerApiSchema))).thenReturn(customer);
        when(this.customerServiceMock.fullUpdateCustomer(customer)).thenReturn(customer);
        when(this.customerMapperMock.toCustomerApiSchema(customer)).thenReturn(customerApiSchemaResponse);

        ResponseEntity<CustomerApiSchema> responseEntity = this.customerController.fullUpdateCustomer(customerId, customerApiSchema);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void partialUpdateCustomer() {
        Long customerId = faker.random().nextLong();
        CustomerApiSchema customerApiSchema = CustomerStubs.newCustomerApiSchemaStub();
        Customer customer = new Customer();
        CustomerApiSchema customerApiSchemaResponse = new CustomerApiSchema();
        when(this.customerMapperMock.toCustomer(eq(customerId), eq(customerApiSchema))).thenReturn(customer);
        when(this.customerServiceMock.partialUpdateCustomer(customer)).thenReturn(customer);
        when(this.customerMapperMock.toCustomerApiSchema(customer)).thenReturn(customerApiSchemaResponse);

        ResponseEntity<CustomerApiSchema> responseEntity = this.customerController.partialUpdateCustomer(customerId, customerApiSchema);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void retrieveCustomer() {
        Long customerId = faker.random().nextLong();
        Customer customer = new Customer();
        CustomerApiSchema customerApiSchemaResponse = new CustomerApiSchema();
        when(this.customerServiceMock.readCustomer(customerId)).thenReturn(customer);
        when(this.customerMapperMock.toCustomerApiSchema(customer)).thenReturn(customerApiSchemaResponse);

        ResponseEntity<CustomerApiSchema> responseEntity = this.customerController.retrieveCustomer(customerId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void searchCustomers() {
        Integer page = faker.random().nextInt(0,100);
        Integer size = faker.random().nextInt(1,100);
        Page<Customer> pageResult = mock(Page.class);
        PageResultCustomerApiSchema pageResultCustomerApiSchema = new PageResultCustomerApiSchema();
        when(this.customerServiceMock.searchCustomer(any())).thenReturn(pageResult);
        when(this.customerMapperMock.toPageResultCustomerApiSchema(pageResult)).thenReturn(pageResultCustomerApiSchema);

        ResponseEntity<PageResultCustomerApiSchema> responseEntity = this.customerController.searchCustomers(page, size);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
