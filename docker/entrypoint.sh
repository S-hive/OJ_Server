#!/bin/sh
set -e

echo "Starting OJ backend..."

# 与 application-prod.yml 保持一致；云托管控制台环境变量可覆盖
MYSQL_HOST="${MYSQL_HOST:-10.11.101.163}"
MYSQL_PORT="${MYSQL_PORT:-3306}"
MYSQL_DATABASE="${MYSQL_DATABASE:-my_db}"
MYSQL_USERNAME="${MYSQL_USERNAME:-root}"
MYSQL_PASSWORD="${MYSQL_PASSWORD:-Root@321}"

# 数据库已导入完成，默认不再重复导入；需要重新导入时可设环境变量 INIT_DB=true
INIT_DB_DEFAULT=false
INIT_DB="${INIT_DB:-$INIT_DB_DEFAULT}"

if [ "$INIT_DB" = "true" ]; then
  echo "INIT_DB=true, importing sql/my_db_dump.sql to ${MYSQL_HOST}:${MYSQL_PORT}/${MYSQL_DATABASE} ..."
  mysql -h"$MYSQL_HOST" -P"$MYSQL_PORT" -u"$MYSQL_USERNAME" -p"$MYSQL_PASSWORD" \
    -e "CREATE DATABASE IF NOT EXISTS \`${MYSQL_DATABASE}\` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
  mysql -h"$MYSQL_HOST" -P"$MYSQL_PORT" -u"$MYSQL_USERNAME" -p"$MYSQL_PASSWORD" \
    "$MYSQL_DATABASE" < /app/sql/my_db_dump.sql
  echo "Database import finished."
fi

exec java -Djava.security.egd=file:/dev/./urandom -Dserver.port="${PORT:-80}" \
  -jar /app/app.jar --spring.profiles.active=prod