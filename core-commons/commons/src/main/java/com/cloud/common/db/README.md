# 作者
Wang Pan(王攀)

## 主要功能
- 目标数据目前只支持MySQL和SQL Sever，源数据库为任何支持sql语法的数据库
- 根据cron表达式配置数据同步的周期和时间
- 执行多个数据同步任务
- 源数据是根据配置的sql语句查询得到，使用者可以非常灵活根据需要进行修改
- 根据配置的字段，判断同步数据是插入还是更新

## 基本使用
cat jobs.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<root>
    <code>0001</code>
    <source> <!-- 源数据库连接方式 -->
        <url>jdbc:oracle:thin:@192.168.1.179:1521:XE</url>
        <username>test</username>
        <password>test</password>
        <dbtype>oracle</dbtype>
        <driver>oracle.jdbc.driver.OracleDriver</driver>
    </source>
    <dest> <!-- 目标数据库连接方式 -->
        <url>jdbc:sqlserver://192.168.1.191:1433;DatabaseName=test</url>
        <username>test</username>
        <password>test</password>
        <dbtype>sqlserver</dbtype>
        <driver>com.microsoft.sqlserver.jdbc.SQLServerDriver</driver>
    </dest>
    <jobs> <!-- 数据库同步任务，可以根据实际需要添加多个job -->
        <job>
            <name>1</name> <!-- job的名称，每一个job的名称最好不一样 -->
            <cron>0/10 * * * * ?</cron> <!-- 定时调度cron表达式 -->
            <srcSql>select username as username,pwd as password from user</srcSql> <!-- 源数据库的查询语句 -->
            <destTable>user</destTable> <!-- 目标数据库的数据表 -->
            <destTableFields>username,password</destTableFields> <!-- 目标数据库数据表的字段，必须和源数据库中查询语句的字段保持一致 -->
            <destTableKey>username</destTableKey> <!-- 根据此字段判断同步的数据是否在目标数据库总存在 -->
            <destTableUpdate>password</destTableUpdate> <!-- 如果目标数据库中存在destTableKey标签字段相同的数据，则更新此字段 -->
        </job>
    </jobs>
</root>
```
