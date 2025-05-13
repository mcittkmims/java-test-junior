FROM postgres:15

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update \
  && apt-get install -y wget gnupg2 lsb-release \
  && echo "deb http://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" \
    > /etc/apt/sources.list.d/pgdg.list \
  && wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | apt-key add - \
  && apt-get update \
  && apt-get install -y curl postgresql-15-cron \
  && echo "shared_preload_libraries = 'pg_cron'" >> /usr/share/postgresql/postgresql.conf.sample \
  && echo "cron.database_name = 'marketplace'" >> /usr/share/postgresql/postgresql.conf.sample
