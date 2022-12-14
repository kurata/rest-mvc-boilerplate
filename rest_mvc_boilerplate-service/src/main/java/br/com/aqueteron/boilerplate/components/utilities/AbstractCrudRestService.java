package br.com.aqueteron.boilerplate.components.utilities;

import br.com.aqueteron.boilerplate.components.MessageKeys;
import br.com.aqueteron.boilerplate.exception.BusinessExceptionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Optional;

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

    public ResponseEntity<U> post(final U newObjectSchema) {
        I objectKey = this.helper.extractId(newObjectSchema);
        if (objectKey != null) {
            Optional<T> resultOptional = this.repository.findById(objectKey);
            if (resultOptional.isPresent()) {
                throw this.businessExceptionFactory.build(
                        HttpStatus.CONFLICT,
                        MessageKeys.KEY_ALREADY_EXISTS,
                        objectKey
                );
            }
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.helper.toApiSchema(this.repository.save(this.helper.toDomainSchema(newObjectSchema))));
    }

    public ResponseEntity<U> getUnique(final I id) {
        Optional<T> resultOptional = this.repository.findById(id);
        if (resultOptional.isEmpty()) {
            throw this.businessExceptionFactory.build(HttpStatus.NOT_FOUND, MessageKeys.DEFAULT_NOT_FOUND, this.entityClass.getSimpleName(), id);
        }
        return ResponseEntity.ok(this.helper.toApiSchema(resultOptional.get()));
    }

    public ResponseEntity<U> put(final I id, final U newObjectSchema) {
        Optional<T> resultOptional = this.repository.findById(id);
        if (resultOptional.isEmpty()) {
            throw this.businessExceptionFactory.build(HttpStatus.NOT_FOUND, MessageKeys.DEFAULT_NOT_FOUND, this.entityClass.getSimpleName(), id);
        }
        return ResponseEntity.ok(this.helper.toApiSchema(this.helper.mergeDomainSchema(resultOptional.get(), newObjectSchema)));
    }

    public ResponseEntity<U> patch(final I id, final U newObjectSchema) {
        Optional<T> resultOptional = this.repository.findById(id);
        if (resultOptional.isEmpty()) {
            throw this.businessExceptionFactory.build(HttpStatus.NOT_FOUND, MessageKeys.DEFAULT_NOT_FOUND, this.entityClass.getSimpleName(), id);
        }
        return ResponseEntity.ok(this.helper.toApiSchema(this.helper.mergeDomainSchema(resultOptional.get(), newObjectSchema)));
    }

    public ResponseEntity<Void> delete(final I id) {
        Optional<T> resultOptional = this.repository.findById(id);
        if (resultOptional.isEmpty()) {
            throw this.businessExceptionFactory.build(HttpStatus.NOT_FOUND, MessageKeys.DEFAULT_NOT_FOUND, this.entityClass.getSimpleName(), id);
        }
        this.repository.delete(resultOptional.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
