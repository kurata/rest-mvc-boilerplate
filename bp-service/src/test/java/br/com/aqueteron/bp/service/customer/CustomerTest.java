package br.com.aqueteron.bp.service.customer;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNull;

class CustomerTest {

    @Test
    void attributesNullTest() {
        Customer customer = new Customer();
        assertNull(customer.getId());
        assertNull(customer.getName());
        assertNull(customer.getBirth());
    }
}
