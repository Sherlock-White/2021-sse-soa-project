# 2021-sse-soa-project
2021同济大学软件学院Web系统与SOA课程小组项目  
指导老师：刘岩

##基本功能
简单实现了一个微服务架构下的网约车系统。  
1. 乘客使用移动端app发起打车  
2. 司机使用移动端app接单  
3. 管理中台刷新监控车辆位置信息  
本项目用Jenkins + github + maven + docker的形式自动部署到服务器上。

##开发工具
后端IDE：IntelliJ IDEA  
前端IDE：Android Studio、Visual Studio  
前端框架：Vue  
项目构建工具：Maven  
数据库：mySQL、redis、TDengine(时序数据库) + GrafanaLabs(可视化) 、MyCat(读写分离)  
调试工具：Swagger  
消息中间件：rabbitMQ(SpringAMQ)  
微服务组件：Zuul(网关)、Ribbon(负载均衡)、Nginx(前端负载均衡)、Eureka(服务注册中心)、Feign(同步调用)
自动化部署：Jenkins  

##开发环境
JDK：1.8  
