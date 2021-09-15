# 工程简介
> IM聊天项目

# 下载-打包

```shell
git clone https://github.com/lmxdawn/im-java.git

# 整体打包
mvn -Dmaven.test.skip=true clean package
# 单独打包某个模块，这里的 user 就是模块名称
mvn -Dmaven.test.skip=true -pl user -am clean package

# 运行
nohup java -jar -Dspring.profiles.active=prod admin/target/admin-0.0.1-SNAPSHOT.jar &
nohup java -jar -Dspring.profiles.active=prod gateway/target/gateway-0.0.1-SNAPSHOT.jar &
nohup java -jar -Dspring.profiles.active=prod im/target/im-0.0.1-SNAPSHOT.jar &
nohup java -jar -Dspring.profiles.active=prod im-route/target/im-route-0.0.1-SNAPSHOT.jar &
nohup java -jar -Dspring.profiles.active=prod user/target/user-0.0.1-SNAPSHOT.jar &

```

# 模块说明
> admin：后台

> common：公共模块

> dubbo-api：dubbo的API

> gateway：网关

> im：IM推送

> im-common：IM公共模块

> im-route：IM路由（路由到具体的IM服务）

> user：用户

# Swagger

> gateway的方式访问swagger，gateway启动后访问： `http://ip:prot/swagger-ui/index.html`


# 使用

> 使用 jwt 做dubbo服务间的鉴权

# 依赖

> Java 8

> Maven 3.6.1

> Nacos 2.0.3  操作文档：https://nacos.io/zh-cn/docs/quick-start.html

> Redis 3

> MySQL 5.7

# 其它

> `Generate MyPOJOs.groovy` 生成数据库Model

