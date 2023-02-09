package br.com.aqueteron.boilerplate.customer.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Customer implements Serializable {

    @Id
    @SequenceGenerator(name = "customer_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq")
    private final Long id;

    @Column(name = "name", unique = true)
    private String fullName;

    @Column(name = "birth")
    private LocalDate birthDate;

    public Customer() {
        this((Long) null);
    }

    public Customer(final Customer originalCustomer) {
        this(originalCustomer.getId());
        this.fullName = originalCustomer.getFullName();
        this.birthDate = originalCustomer.getBirthDate();
    }

}
