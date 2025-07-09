package br.com.aqueteron.bp.service.customer;

import br.com.aqueteron.bp.api.CustomerApi;
import br.com.aqueteron.bp.api.model.CustomerApiSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
public class CustomerController implements CustomerApi {

    @Override
    public ResponseEntity<CustomerApiSchema> createCustomer(CustomerApiSchema customerApiSchema) {
        return null;
    }

    @Override
    public ResponseEntity<CustomerApiSchema> fullUpdateCustomer(UUID customerId, CustomerApiSchema customerApiSchema) {
        return null;
    }

    @Override
    public ResponseEntity<CustomerApiSchema> partialUpdateCustomer(UUID customerId, CustomerApiSchema customerApiSchema) {
        return null;
    }

    @Override
    public ResponseEntity<CustomerApiSchema> retrieveCustomer(UUID customerId) {
        return null;
    }

    @Override
    public ResponseEntity<List<CustomerApiSchema>> searchCustomers() {
        return null;
    }
}
