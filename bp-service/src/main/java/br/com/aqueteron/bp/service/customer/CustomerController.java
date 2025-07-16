package br.com.aqueteron.bp.service.customer;

import br.com.aqueteron.bp.api.CustomerApi;
import br.com.aqueteron.bp.api.model.CustomerApiSchema;
import br.com.aqueteron.bp.api.model.PageResultCustomerApiSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class CustomerController implements CustomerApi {

    private final CustomerMapper customerMapper;

    private final CustomerService customerService;

    @Override
    public ResponseEntity<CustomerApiSchema> createCustomer(final CustomerApiSchema customerApiSchema) {
        Customer customer = this.customerMapper.toCustomer(null, customerApiSchema);
        customer = this.customerService.createCustomer(customer);
        CustomerApiSchema customerApiSchemaResponse = this.customerMapper.toCustomerApiSchema(customer);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerApiSchemaResponse);
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(final Long customerId) {
        this.customerService.deleteCustomer(customerId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CustomerApiSchema> fullUpdateCustomer(final Long customerId, final CustomerApiSchema customerApiSchema) {
        Customer customer = this.customerMapper.toCustomer(customerId, customerApiSchema);
        customer = this.customerService.fullUpdateCustomer(customer);
        return ResponseEntity.ok(this.customerMapper.toCustomerApiSchema(customer));
    }

    @Override
    public ResponseEntity<CustomerApiSchema> partialUpdateCustomer(final Long customerId, final CustomerApiSchema customerApiSchema) {
        Customer customer = this.customerMapper.toCustomer(customerId, customerApiSchema);
        customer = this.customerService.partialUpdateCustomer(customer);
        return ResponseEntity.ok(this.customerMapper.toCustomerApiSchema(customer));
    }

    @Override
    public ResponseEntity<CustomerApiSchema> retrieveCustomer(final Long customerId) {
        Customer customer = this.customerService.readCustomer(customerId);
        CustomerApiSchema customerApiSchemaResponse = this.customerMapper.toCustomerApiSchema(customer);
        return ResponseEntity.ok(customerApiSchemaResponse);
    }

    @Override
    public ResponseEntity<PageResultCustomerApiSchema> searchCustomers(final Integer page, final Integer size) {
        Page<Customer> pageResult = this.customerService.searchCustomer(PageRequest.of(page, size));
        return ResponseEntity.ok(this.customerMapper.toPageResultCustomerApiSchema(pageResult));
    }
}
