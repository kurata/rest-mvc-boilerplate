package br.com.aqueteron.bp.service.customer;

import br.com.aqueteron.bp.service.exception.BusinessFactory;
import br.com.aqueteron.bp.service.utils.MessageKeys;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings
class CustomerServiceTest {

    Faker faker = new Faker();

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepositoryMock;

    @Mock
    BusinessFactory businessFactoryMock;

    @Test
    void createCustomer() {
        Customer customer = CustomerStubs.customerStub();
        when(this.customerRepositoryMock.save(customer)).thenReturn(customer);

        Customer customerResponse = this.customerService.createCustomer(customer);
        assertEquals(customerResponse, customer);
    }

    @Test
    void deleteCustomer() {
        Long customerId = faker.random().nextLong();
        Customer customer = CustomerStubs.customerStub();
        when(this.customerRepositoryMock.findById(eq(customerId))).thenReturn(Optional.of(customer));

        this.customerService.deleteCustomer(customerId);

        verify(this.customerRepositoryMock).delete(customer);
    }

    @Test
    void fullUpdateCustomer() {
        Customer customer = CustomerStubs.customerStub();
        when(this.customerRepositoryMock.findById(eq(customer.getId()))).thenReturn(Optional.of(customer));
        when(this.customerRepositoryMock.save(customer)).thenReturn(customer);

        Customer customerResponse = this.customerService.fullUpdateCustomer(customer);
        assertEquals(customerResponse, customer);
    }

    @Test
    void partialUpdateCustomer() {
        Customer customer = CustomerStubs.customerStub();
        when(this.customerRepositoryMock.findById(customer.getId())).thenReturn(Optional.of(customer));

        Customer customerResult = this.customerService.partialUpdateCustomer(customer);

        assertEquals(customerResult, customer);
    }

    @Test
    void readCustomer() {
        Long customerId = faker.random().nextLong();
        Customer customer = CustomerStubs.customerStub();
        when(this.customerRepositoryMock.findById(customerId)).thenReturn(Optional.of(customer));

        Customer customerResult = this.customerService.readCustomer(customerId);

        assertEquals(customerResult, customer);
    }

    @Test
    void readCustomer_whenNotFound() {
        Long customerId = faker.random().nextLong();
        when(this.customerRepositoryMock.findById(customerId)).thenReturn(Optional.empty());
        when(this.businessFactoryMock.build(HttpStatus.NOT_FOUND, MessageKeys.DEFAULT_NOT_FOUND, Customer.class.getSimpleName(), customerId)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> this.customerService.readCustomer(customerId));
    }

    @Test
    void searchCustomer() {
    }
}
