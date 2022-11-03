package br.com.aqueteron.boilerplate.components.utilities;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
public class PageResult<T extends Serializable> implements Serializable {

    private final Collection<T> result;

    private final Integer totalPages;

    private final Long totalElements;

}
