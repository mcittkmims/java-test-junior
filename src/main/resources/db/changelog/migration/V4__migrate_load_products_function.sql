CREATE OR REPLACE FUNCTION load_products(file TEXT, default_user BIGINT)
RETURNS INT AS $$
DECLARE
inserted_rows INT;
BEGIN
BEGIN
EXECUTE format(
        'CREATE TEMP TABLE temp_product (
            name TEXT,
            price NUMERIC(10,2),
            description TEXT,
            user_id BIGINT DEFAULT %s
        ) ON COMMIT DROP',
        default_user
        );

EXECUTE format(
        'COPY temp_product (name, price, description) FROM %L WITH CSV HEADER',
        file
        );

INSERT INTO product (name, price, description, user_id)
SELECT name, price, description, user_id
FROM temp_product;

GET DIAGNOSTICS inserted_rows = ROW_COUNT;

RETURN inserted_rows;

EXCEPTION
        WHEN OTHERS THEN

            RETURN 0;
END;
END;
$$ LANGUAGE plpgsql;