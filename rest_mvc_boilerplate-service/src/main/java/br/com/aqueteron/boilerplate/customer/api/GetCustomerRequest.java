package br.com.aqueteron.boilerplate.customer.api;

import br.com.aqueteron.boilerplate.components.utilities.PagingRequest;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
public class GetCustomerRequest implements PagingRequest {

    private Integer page;

    private Integer size;

    private Integer id;

    private String fullName;

    public GetCustomerRequest() {
        this.page = 0;
        this.size = 10;
    }

    @Override
    public Pageable toPageRequest() {
        return PageRequest.of(this.page, this.size);
    }
}
