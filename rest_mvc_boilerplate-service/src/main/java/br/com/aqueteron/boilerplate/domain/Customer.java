package br.com.aqueteron.boilerplate.domain;

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

    @Column(name = "name")
    private String fullName;

    @Column(name = "birth")
    private LocalDate birthDate;

    public Customer() {
        this(null);
    }

}
