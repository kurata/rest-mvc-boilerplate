package br.com.aqueteron.bp.service.customer;

import br.com.aqueteron.bp.service.utils.PastLocalDate;
import br.com.aqueteron.bp.service.utils.SimpleTextAttribute;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@RequiredArgsConstructor
@Data
@Entity
public class Customer implements Serializable {

    @Id
    @SequenceGenerator(name = "customer_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq")
    private final Long id;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "value", column = @Column(name = "name"))
    })
    private SimpleTextAttribute name;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "value", column = @Column(name = "birth"))
    })
    private PastLocalDate birth;

    protected Customer() {
        this(null);
    }

    public String getName() {
        if (this.name == null) {
            return null;
        }
        return this.name.value();
    }

    public void setName(final String name) {
        this.name = new SimpleTextAttribute(name);
    }

    public LocalDate getBirth() {
        if (this.birth == null) {
            return null;
        }
        return birth.value();
    }

    public void setBirth(final LocalDate birth) {
        this.birth = new PastLocalDate(birth);
    }

}
