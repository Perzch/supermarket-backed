# java -jar

```cmd
java -jar target/xxx.jar --spring.datasource.url=jdbc:mysql://localhost:3306/xxx?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC --spring.data.redis.host=your-redis-host
```

# docker-compose.yml
```docker-compose
version: '3'
services:
  # 后端 Spring Boot 应用
  backend:
    image: supermarket-backend:latest
    ports:
      - "8080:8080"  # 映射 Spring Boot 服务的端口
    depends_on:
      - redis  # 指定依赖 Redis 服务
      - mysql
    # environment:
    #   - spring.datasource.url=jdbc:mysql://mysql:3306/supermarket?serverTimezone=UTC
    #   - spring.datasource.password=perzch
    #   - spring.redis.host=redis

  # Redis 服务
  redis:
    image: redis:latest
    ports:
      - "6379:6379"  # 映射 Redis 的端口

  # Vue 前端应用
  frontend:
    image: supermarket-front:latest
    ports:
      - "80:80"  # 映射 Vue 前端应用的端口
    depends_on:
      - backend  # 指定依赖后端 Spring Boot 应用

  mysql:
    image: mysql:latest
    ports:
      - "3306:3306"
    environment:
      MYSQL_ROOT_PASSWORD: perzch
#      MYSQL_DATABASE: supermarket
#      MYSQL_USER: supermarket
#      MYSQL_PASSWORD: perzch
networks:
    default:
        external:
            name: supermarket-network

```
