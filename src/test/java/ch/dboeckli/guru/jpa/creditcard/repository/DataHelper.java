package ch.dboeckli.guru.jpa.creditcard.repository;

import ch.dboeckli.guru.jpa.creditcard.domain.CreditCard;

public class DataHelper {

    public static final String CREDIT_CARD = "12345678900000";
    public static final String CVV = "123";
    public static final String EXPIRATION_DATE = "12/2028";
    public static final String SECRET = "very-secret";

    public static final String UPDATED_CREDIT_CARD = "12345678900001";
    public static final String UPDATED_CVV = "321";
    public static final String UPDATED_EXPIRATION_DATE = "12/2029";
    public static final String UPDATED_SECRET = "updated-secret";

    public static CreditCard createAndSaveCreditCard(CreditCardRepository creditCardRepository) {
        CreditCard creditCard = new CreditCard();
        creditCard.setCreditCardNumber(CREDIT_CARD);
        creditCard.setCvv(CVV);
        creditCard.setExpirationDate(EXPIRATION_DATE);
        creditCard.setSecret(SECRET);

        return creditCardRepository.saveAndFlush(creditCard);
    }

}
