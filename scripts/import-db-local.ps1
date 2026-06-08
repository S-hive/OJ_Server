# 从本机导入数据库（使用云 MySQL 外网地址）
# 用法：先填写下方变量，再执行 .\scripts\import-db-local.ps1

$MYSQL_HOST = "sh-cynosdbmysql-grp-eu2kbk7y.sql.tencentcdb.com"
$MYSQL_PORT = "27670"
$MYSQL_DATABASE = "my_db"
$MYSQL_USERNAME = "root"
$MYSQL_PASSWORD = "Root@321"

$dumpFile = Join-Path $PSScriptRoot "..\sql\my_db_dump.sql"

mysql -h $MYSQL_HOST -P $MYSQL_PORT -u $MYSQL_USERNAME -p$MYSQL_PASSWORD `
  -e "CREATE DATABASE IF NOT EXISTS ``$MYSQL_DATABASE`` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

Get-Content $dumpFile -Raw | mysql -h $MYSQL_HOST -P $MYSQL_PORT -u $MYSQL_USERNAME -p$MYSQL_PASSWORD $MYSQL_DATABASE

Write-Host "Import completed."
