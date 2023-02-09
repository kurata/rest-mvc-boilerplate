package br.com.aqueteron.boilerplate.customer.api;

import br.com.aqueteron.boilerplate.components.utilities.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "customers")
@RequestMapping("/api/customers")
public interface CustomerResource {

    @Operation(summary = "Get customers", operationId = "getCustomers", description = "Retrieve a customer list",
            tags = {"customers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success to retrieve customers.")})
    @GetMapping
    ResponseEntity<PageResult<CustomerApiSchema>> getCustomers(@ParameterObject GetCustomerRequest getCustomerRequest);

    @Operation(summary = "Post new customer", operationId = "postCustomer", description = "Create a new customer",
            tags = {"customers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success to create a new customer.")})
    @PostMapping
    ResponseEntity<CustomerApiSchema> postCustomer(@RequestBody CustomerApiSchema postClientRequest);

    @Operation(summary = "Get customer", operationId = "getCustomer", description = "Retrieve a customer", tags = {"customers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success to load a customer.")})
    @GetMapping("/{key}")
    ResponseEntity<CustomerApiSchema> getCustomer(@PathVariable Long key);

    @Operation(summary = "Put customer", operationId = "putCustomer", description = "Update the customer attributes", tags = {"customers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success to update customer attributes.")})
    @PutMapping("/{key}")
    ResponseEntity<CustomerApiSchema> putCustomer(@PathVariable Long key, @RequestBody CustomerApiSchema customerApiSchema);

    @Operation(summary = "Patch customer", operationId = "patchCustomer", description = "Update the customer attributes", tags = {"customers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success to update customer attributes.")})
    @PatchMapping("/{key}")
    ResponseEntity<CustomerApiSchema> patchCustomer(@PathVariable Long key, @RequestBody CustomerApiSchema customerApiSchema);

    @Operation(summary = "Delete customer", operationId = "deleteCustomer", description = "Delete the customer", tags = {"customers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success to delete a customer.")})
    @DeleteMapping("/{key}")
    ResponseEntity<Void> deleteCustomer(@PathVariable Long key);

}
