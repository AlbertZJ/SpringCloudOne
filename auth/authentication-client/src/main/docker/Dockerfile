FROM java:alpine
VOLUME /tmp
ADD authentication-client-0.0.1-SNAPSHOT.jar app.jar
# 在容器启动的时候运行命令行来启动我们的项目（这其实是一段Linux命令）
# 这个启动命令里根据你的实际情况更改
ENTRYPOINT ["java","-jar","/app.jar"]
