CREATE TABLE IF NOT EXISTS products
(
    product_id  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name        VARCHAR(250)                          NOT NULL,
    description VARCHAR(500)                          NOT NULL,
    price       DECIMAL(10, 2)                        NOT NULL,
    popularity  INT                                   NOT NULL,
    image_url   VARCHAR(500),
    created_at  TIMESTAMP   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by  VARCHAR(50)                           NOT NULL,
    updated_at  TIMESTAMP   DEFAULT NULL,
    updated_by  VARCHAR(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS contacts
(
    contact_id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name          VARCHAR(100)                          NOT NULL,
    email         VARCHAR(100)                          NOT NULL,
    mobile_number VARCHAR(15)                           NOT NULL,
    message       VARCHAR(500)                          NOT NULL,
    created_at    TIMESTAMP   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by    VARCHAR(50)                           NOT NULL,
    updated_at    TIMESTAMP   DEFAULT NULL,
    updated_by    VARCHAR(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS customers
(
    customer_id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name          VARCHAR(100)                          NOT NULL,
    email         VARCHAR(100)                          NOT NULL UNIQUE,
    mobile_number VARCHAR(15)                           NOT NULL,
    password      VARCHAR(500)                          NOT NULL,
    created_at    TIMESTAMP   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by    VARCHAR(50)                           NOT NULL,
    updated_at    TIMESTAMP   DEFAULT NULL,
    updated_by    VARCHAR(50) DEFAULT NULL,
    CONSTRAINT unique_email UNIQUE (email),
    CONSTRAINT unique_mobile_number UNIQUE (mobile_number)
);

CREATE TABLE IF NOT EXISTS cart_items
(
    cart_item_id  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    customer_name VARCHAR(100)  NOT NULL,
    product_id    BIGINT        NOT NULL,
    quantity      INT           NOT NULL
);