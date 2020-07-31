FROM java:alpine
#定制的镜像都是基于 FROM 的镜像
VOLUME /tmp
#定义匿名数据卷。在启动容器时忘记挂载数据卷，会自动挂载到匿名卷。
ADD organization-0.0.1-SNAPSHOT.jar app.jar
#ADD 指令和 COPY 的使用格式一致（同样需求下，官方推荐使用 COPY）
# 在容器启动的时候运行命令行来启动我们的项目（这其实是一段Linux命令）
# 这个启动命令里根据你的实际情况更改
ENTRYPOINT ["java","-jar","/app.jar"]
#类似于 CMD 指令，但其不会被 docker run 的命令行参数指定的指令所覆盖，而且这些命令行参数会被当作参数送给 ENTRYPOINT 指令指定的程序。
