package br.com.aqueteron.boilerplate.customer.api;

import br.com.aqueteron.boilerplate.components.utilities.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Api(value = "customers", tags = {"customers"})
@RequestMapping("/api/customers")
@Transactional
public interface CustomerResource {

    @ApiOperation(value = "Get customers", nickname = "getCustomers", notes = "Retrieve a customer list",
            response = CustomerApiSchema.class, tags = {"customers"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success to retrieve customers.", response = CustomerApiSchema.class)})
    @GetMapping
    ResponseEntity<PageResult<CustomerApiSchema>> getCustomers(GetCustomerRequest getCustomerRequest);

    @ApiOperation(value = "Post new customer", nickname = "postCustomer", notes = "Create a new customer",
            response = CustomerApiSchema.class, tags = {"customers"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success to create a new customer.", response = Void.class)})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<CustomerApiSchema> postCustomer(@RequestBody CustomerApiSchema postClientRequest);

    @ApiOperation(value = "Get customer", nickname = "getCustomer", notes = "Retrieve a customer", response = CustomerApiSchema.class, tags = {"customers"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success to load a customer.", response = CustomerApiSchema.class)})
    @GetMapping("/{key}")
    ResponseEntity<CustomerApiSchema> getCustomer(@PathVariable Long key);

    @ApiOperation(value = "Put customer", nickname = "putCustomer", notes = "Update the customer attributes", response = CustomerApiSchema.class, tags = {"customers"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success to update customer attributes.", response = CustomerApiSchema.class)})
    @PutMapping("/{key}")
    ResponseEntity<CustomerApiSchema> putCustomer(@PathVariable Long key, @RequestBody CustomerApiSchema customerApiSchema);

    @ApiOperation(value = "Patch customer", nickname = "patchCustomer", notes = "Update the customer attributes", response = CustomerApiSchema.class, tags = {"customers"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success to update customer attributes.", response = CustomerApiSchema.class)})
    @PatchMapping("/{key}")
    ResponseEntity<CustomerApiSchema> patchCustomer(@PathVariable Long key, @RequestBody CustomerApiSchema customerApiSchema);

    @ApiOperation(value = "Delete customer", nickname = "deleteCustomer", notes = "Delete the customer", response = Void.class, tags = {"customers"})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success to delete a customer.", response = Void.class)})
    @DeleteMapping("/{key}")
    ResponseEntity<Void> deleteCustomer(@PathVariable Long key);

}
