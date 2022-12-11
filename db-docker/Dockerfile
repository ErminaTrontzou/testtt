FROM mariadb:10

ENV MYSQL_ROOT_PASSWORD=rootpass
ENV MYSQL_DATABASE=tourguide
ENV MYSQL_USER=ermina
ENV MYSQL_PASSWORD=hypers

COPY c4petalidis_proj.sql /docker-entrypoint-initdb.d/
