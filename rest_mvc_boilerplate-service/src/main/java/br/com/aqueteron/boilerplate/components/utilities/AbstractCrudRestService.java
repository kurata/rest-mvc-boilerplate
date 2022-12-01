package br.com.aqueteron.boilerplate.components.utilities;

import br.com.aqueteron.boilerplate.components.MessageKeys;
import br.com.aqueteron.boilerplate.exception.BusinessExceptionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Optional;
import java.util.stream.StreamSupport;

public abstract class AbstractCrudRestService<T extends Serializable, U extends Serializable, I extends Serializable> {

    private final Class<T> entityClass;

    private final BusinessExceptionFactory businessExceptionFactory;

    private final PagingAndSortingRepository<T, I> repository;

    private final Helper<T, U, I> helper;

    @Autowired
    protected AbstractCrudRestService(final BusinessExceptionFactory businessExceptionFactory,
                                      final PagingAndSortingRepository<T, I> repository,
                                      final Helper<T, U, I> helper,
                                      final Class<T> entityClass
    ) {
        this.entityClass = entityClass;
        this.businessExceptionFactory = businessExceptionFactory;
        this.repository = repository;
        this.helper = helper;
    }

    public Flux<U> getAll() {
        return Flux.fromStream(
                StreamSupport.stream(this.repository.findAll().spliterator(), true)
                        .map(this.helper::toApiSchema)
        );
    }

    public Mono<U> post(final U newObjectSchema) {
        I objectKey = this.helper.extractId(newObjectSchema);
        if (objectKey != null) {
            Optional<T> resultOptional = this.repository.findById(objectKey);
            if (resultOptional.isPresent()) {
                return Mono.error(this.businessExceptionFactory.build(
                        HttpStatus.CONFLICT,
                        MessageKeys.KEY_ALREADY_EXISTS,
                        objectKey
                ));
            }
        }
        return Mono.just(this.helper.toApiSchema(this.repository.save(this.helper.toDomainSchema(newObjectSchema))));
    }

    public Mono<U> getUnique(final I id) {
        Optional<T> resultOptional = this.repository.findById(id);
        if (resultOptional.isEmpty()) {
            return Mono.error(this.businessExceptionFactory.build(HttpStatus.NOT_FOUND, MessageKeys.DEFAULT_NOT_FOUND, this.entityClass.getSimpleName(), id));
        }
        return Mono.just(this.helper.toApiSchema(resultOptional.get()));
    }

    public Mono<U> put(final I id, final U newObjectSchema) {
        Optional<T> resultOptional = this.repository.findById(id);
        if (resultOptional.isEmpty()) {
            return Mono.error(this.businessExceptionFactory.build(HttpStatus.NOT_FOUND, MessageKeys.DEFAULT_NOT_FOUND, this.entityClass.getSimpleName(), id));
        }
        return Mono.just(this.helper.toApiSchema(this.helper.mergeDomainSchema(resultOptional.get(), newObjectSchema)));
    }

    public Mono<U> patch(final I id, final U newObjectSchema) {
        Optional<T> resultOptional = this.repository.findById(id);
        if (resultOptional.isEmpty()) {
            return Mono.error(this.businessExceptionFactory.build(HttpStatus.NOT_FOUND, MessageKeys.DEFAULT_NOT_FOUND, this.entityClass.getSimpleName(), id));
        }
        return Mono.just(this.helper.toApiSchema(this.helper.mergeDomainSchema(resultOptional.get(), newObjectSchema)));
    }

    public Mono<Void> delete(final I id) {
        Optional<T> resultOptional = this.repository.findById(id);
        if (resultOptional.isEmpty()) {
            return Mono.error(this.businessExceptionFactory.build(HttpStatus.NOT_FOUND, MessageKeys.DEFAULT_NOT_FOUND, this.entityClass.getSimpleName(), id));
        }
        this.repository.delete(resultOptional.get());
        return Mono.empty();
    }

}
