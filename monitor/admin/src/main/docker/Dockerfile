FROM java:alpine
# 这是基础镜像
VOLUME /tmp
# 复制jar包到镜像中，并且将名字改成 app.jar
ADD admin-0.0.1-SNAPSHOT.jar app.jar
# 在容器启动的时候运行命令行来启动我们的项目（这其实是一段Linux命令）
# 这个启动命令里根据你的实际情况更改
ENTRYPOINT ["java","-jar","/app.jar"]
