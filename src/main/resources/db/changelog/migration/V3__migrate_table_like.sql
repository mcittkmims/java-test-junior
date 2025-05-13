CREATE TABLE IF NOT EXISTS "product_like"
(
    id         BIGSERIAL PRIMARY KEY,
    product_id BIGINT  NOT NULL,
    user_id    BIGINT  NOT NULL,
    liked      BOOLEAN,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
    CONSTRAINT uq_user_product UNIQUE (user_id, product_id)
);
