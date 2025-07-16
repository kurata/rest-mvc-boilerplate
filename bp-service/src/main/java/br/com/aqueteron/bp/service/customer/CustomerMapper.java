package br.com.aqueteron.bp.service.customer;

import br.com.aqueteron.bp.api.model.CustomerApiSchema;
import br.com.aqueteron.bp.api.model.PageResultCustomerApiSchema;
import org.mapstruct.*;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.server.mvc.BasicLinkBuilder;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    static String CUSTOMER_RESOURCE_PATH = "api/v1/customers";

    @Mapping(source = "customerId", target = "id")
    @Mapping(source = "customerApiSchema.name", target = "name")
    @Mapping(source = "customerApiSchema.birth", target = "birth")
    Customer toCustomer(Long customerId, CustomerApiSchema customerApiSchema);

    @Mapping(target = "links", ignore = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "birth", target = "birth")
    CustomerApiSchema toCustomerApiSchema(Customer customer);

    @Mapping(target = "links", ignore = true)
    @Mapping(source = "totalPages", target = "totalPages")
    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "content", target = "result")
    PageResultCustomerApiSchema toPageResultCustomerApiSchema(Page<Customer> pageResult);

    @AfterMapping
    default void setCustomerApiSchemaSelfRelLink(@MappingTarget CustomerApiSchema customerApiSchema) {
        customerApiSchema.add(BasicLinkBuilder.linkToCurrentMapping()
                .slash(CUSTOMER_RESOURCE_PATH)
                .slash(customerApiSchema.getId())
                .withSelfRel());
    }

    @AfterMapping
    default void setPageResultCustomerApiSchemaSelfRelLink(@MappingTarget PageResultCustomerApiSchema pageResultCustomerApiSchema) {
        pageResultCustomerApiSchema.add(BasicLinkBuilder.linkToCurrentMapping()
                .slash(CUSTOMER_RESOURCE_PATH)
                .withSelfRel());
    }

}
