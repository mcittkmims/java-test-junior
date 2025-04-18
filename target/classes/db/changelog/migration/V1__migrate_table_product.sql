--
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--
CREATE TABLE IF NOT EXISTS "product"
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    price       NUMERIC(10, 2) NOT NULL,
    description TEXT,
    user_id     BIGINT         NOT NULL,
    created_at  TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP      NOT NULL DEFAULT NOW()
);