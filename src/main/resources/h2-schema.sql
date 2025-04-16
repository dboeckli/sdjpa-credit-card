DROP TABLE IF EXISTS credit_card;

CREATE TABLE credit_card (
                             id bigint not null auto_increment,
                             credit_card_number varchar(40), -- size increased for encryption
                             cvv varchar(4), -- size increased for encryption
                             expiration_date varchar(32), -- size increased for encryption
                             secret varchar(64),
                             PRIMARY KEY (id)
);