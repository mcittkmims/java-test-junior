CREATE extension IF NOT EXISTS pg_cron;


SELECT cron.schedule(
               '0 0 * * *',
               $$DELETE FROM product_like WHERE liked IS NULL$$
       );
