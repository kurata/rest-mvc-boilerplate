package br.com.aqueteron.boilerplate.components.utilities;

public interface Helper<T, U, I> {

    U toApiSchema(T t);

    T toDomainSchema(U u);

    T mergeDomainSchema(T originalObject, U newObjectSchema);

    I extractId(U newObjectSchema);
}
