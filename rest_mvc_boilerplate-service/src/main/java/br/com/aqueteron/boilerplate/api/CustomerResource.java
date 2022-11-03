package br.com.aqueteron.boilerplate.api;

import br.com.aqueteron.boilerplate.components.utilities.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Api(value = "Cliente", tags = {"cliente"})
@RequestMapping("/api/cliente")
@Transactional
public interface CustomerResource {

    @ApiOperation(value = "Get clientes", nickname = "getClientes", notes = "Retorna uma lista de clientes",
            response = CustomerApiSchema.class, tags = {"cliente"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso em carregar clientes.", response = CustomerApiSchema.class)})
    @GetMapping
    Mono<PageResult<CustomerApiSchema>> getCustomers(GetCustomerRequest getCustomerRequest);

    @ApiOperation(value = "Post novo cliente", nickname = "postCliente", notes = "Create um cliente",
            response = CustomerApiSchema.class, tags = {"cliente"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Sucesso em criar um cliente.", response = Void.class)})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<CustomerApiSchema> postCustomer(@RequestBody CustomerApiSchema postClientRequest);

    @ApiOperation(value = "Get cliente", nickname = "getCliente", notes = "Retorna um cliente", response = CustomerApiSchema.class, tags = {"cliente"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso em carregar um cliente.", response = CustomerApiSchema.class)})
    @GetMapping("/{key}")
    Mono<CustomerApiSchema> getCustomer(@PathVariable Long key);

    @ApiOperation(value = "Put cliente", nickname = "putCliente", notes = "Atualiza os atributos do cliente", response = CustomerApiSchema.class, tags = {"cliente"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso em atualizar o cliente.", response = CustomerApiSchema.class)})
    @PutMapping("/{key}")
    Mono<CustomerApiSchema> putCustomer(@PathVariable Long key, @RequestBody CustomerApiSchema customerApiSchema);

    @ApiOperation(value = "Patch cliente", nickname = "patchCliente", notes = "Atualiza os atributos do cliente", response = CustomerApiSchema.class, tags = {"cliente"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso em atualizar o cliente.", response = CustomerApiSchema.class)})
    @PatchMapping("/{key}")
    Mono<CustomerApiSchema> patchCustomer(@PathVariable Long key, @RequestBody CustomerApiSchema customerApiSchema);

    @ApiOperation(value = "Delete cliente", nickname = "deleteCliente", notes = "Apaga um cliente", response = Void.class, tags = {"cliente"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso ao apagar um cliente.", response = Void.class)})
    @DeleteMapping("/{key}")
    Mono<Void> deleteCustomer(@PathVariable Long key);

}
