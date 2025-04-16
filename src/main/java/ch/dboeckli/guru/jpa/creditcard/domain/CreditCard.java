package ch.dboeckli.guru.jpa.creditcard.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@EntityListeners(CreditCardJPACallback.class)
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String creditCardNumber;

    private String cvv;

    private String expirationDate;

    @Convert(converter = CreditCardConverter.class)
    private String secret;

}
