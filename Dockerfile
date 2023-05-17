# 指定基础镜像，可以根据项目需要选择合适的基础镜像
FROM openjdk:17

# 将项目构建生成的jar文件复制到镜像中
COPY target/shop-1.0.jar /app/shop-1.0.jar
# 将项目的target目录挂载到镜像的app文件夹
VOLUME /app

# 设置工作目录
WORKDIR /app

# 安装项目依赖（如果有其他依赖项）
# RUN apt-get update && apt-get install -y ...

# 暴露端口（根据项目需要）
EXPOSE 8080

# 指定启动命令
CMD ["java", "-jar", "shop-1.0.jar"]
