#!/bin/sh
set -e

echo "Starting OJ backend..."

# 首次部署默认导入；导入成功后把 INIT_DB_DEFAULT 改成 false 并重新发布
INIT_DB_DEFAULT=true
INIT_DB="${INIT_DB:-$INIT_DB_DEFAULT}"

if [ "$INIT_DB" = "true" ]; then
  echo "INIT_DB=true, importing sql/my_db_dump.sql ..."
  if [ -z "$MYSQL_HOST" ] || [ -z "$MYSQL_USERNAME" ] || [ -z "$MYSQL_PASSWORD" ]; then
    echo "ERROR: INIT_DB requires MYSQL_HOST, MYSQL_USERNAME, MYSQL_PASSWORD"
    exit 1
  fi

  MYSQL_PORT="${MYSQL_PORT:-3306}"
  MYSQL_DATABASE="${MYSQL_DATABASE:-my_db}"

  mysql -h"$MYSQL_HOST" -P"$MYSQL_PORT" -u"$MYSQL_USERNAME" -p"$MYSQL_PASSWORD" \
    -e "CREATE DATABASE IF NOT EXISTS \`${MYSQL_DATABASE}\` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

  mysql -h"$MYSQL_HOST" -P"$MYSQL_PORT" -u"$MYSQL_USERNAME" -p"$MYSQL_PASSWORD" \
    "$MYSQL_DATABASE" < /app/sql/my_db_dump.sql

  echo "Database import finished."
fi

exec java -Djava.security.egd=file:/dev/./urandom -Dserver.port="${PORT:-80}" \
  -jar /app/app.jar --spring.profiles.active=prod
