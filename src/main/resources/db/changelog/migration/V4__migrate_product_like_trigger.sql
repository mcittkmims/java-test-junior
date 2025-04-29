CREATE OR REPLACE FUNCTION delete_if_liked_is_null()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.liked IS NULL THEN
DELETE FROM product_like WHERE id = NEW.id;
RETURN NULL;
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER delete_liked_if_null_after_update
AFTER UPDATE ON product_like
FOR EACH ROW
EXECUTE FUNCTION delete_if_liked_is_null();