# 2021-sse-soa-project-orderService
订单状态：

    1.已创建：最初的状态，此时订单里记录了乘客id，出发地，目的地
    
    2.已派单：为乘客匹配到司机，此时订单里记录了乘客id，司机id，出发地，目的地
    
    3.已取消：可选流终态，乘客在未上车（已创建或者已派单状态下）都可以取消订单
    
    4.已开始：乘客上车，订单更新时间，此时记录了乘客id，司机id，出发地，目的地，出发时间
    
    5.已结束：正常流终态，乘客到达，订单结束，更新时间和金额，此时记录了乘客id，司机id，出发地，目的地，出发时间，到达时间，金额
    
    可能的订单状态变化路程：
    1. 1->3
    
    2. 1->2->3
    
    3. 1->2->4->5

订单API：

   1.获取某笔订单("/getOrderByID/{order_id}")
     
     需要参数：订单id(String)
     
     返回：订单信息(订单id，顾客id，司机id，出发地，目的地，金额，出发时间，结束/到达时间)
   
   2.按照时间顺序获取顾客的所有订单("/getOrdersForPassenger/{passenger_id}")
     
     需要参数：顾客id(String)
     
     返回：该顾客的所有订单(List<>)
   
   3.按照时间顺序获取司机的所有订单("/getOrdersForDriver/{driver_id}")
     
     需要参数：司机id(String)
     
     返回：该顾客的所有订单(List<>)
     
订单微服务需要接收的消息：

    1.创建订单的消息
      需要信息：passenger_id 乘客id
               departure    出发地
               destination  目的地
              
    2.派单消息
      需要信息：order_id  订单id
               driver_id 司机id
    
    3.取消订单消息
      需要信息：order_id 订单id
    
    4.乘客上车消息
      需要信息：order_id 订单id
    
    5.乘客到达消息
      需要信息：order_id 订单id
               price    金额
      
 订单微服务需要发出的消息：
 
     1.通知派单微服务派单，以匹配司机和乘客
       发出的消息格式： {
                         order_id:"";
                         passenger_id:"";
                         departure:"";
                         destination:"";
                       }
       
     2.通知派单微服务已取消订单，以释放司机资源
       发出的消息格式： {
                         order_id:"";
                         driver_id:"";
                       }
=======
# 2021-sse-soa-project
2021同济大学软件学院Web系统与SOA课程小组项目  
指导老师：刘岩

## 基本功能
简单实现了一个微服务架构下的网约车系统。  
1. 乘客使用移动端app发起打车  
2. 司机使用移动端app接单  
3. 管理中台刷新监控车辆位置信息  
本项目用Jenkins + github + maven + docker的形式自动部署到服务器上。

## 开发工具
后端IDE：IntelliJ IDEA  
前端IDE：Android Studio、Visual Studio  
前端框架：Vue  
项目构建工具：Maven  
数据库：mySQL、redis、TDengine(时序数据库) + GrafanaLabs(可视化) 、MyCat(读写分离)  
调试工具：Swagger  
消息中间件：rabbitMQ(SpringAMQ)  
微服务组件：Zuul(网关)、Ribbon(负载均衡)、Nginx(前端负载均衡)、Eureka(服务注册中心)、Feign(同步调用)
自动化部署：Jenkins  

## 开发环境
JDK：1.8  

## 分支说明
### main
比较旧的分支，主要是中期的一些测试服务  
### cyh:
位置服务、zuul网关、eureka服务注册中心  
我们模拟从硬件设备上采集位置信息数据的这个过程，数据是从数据集当中读取的，位置服务向外提供获取位置信息的接口。  
├── README.md                     // help  
└── BackEnd                       // 后端服务  
    ├── new-zuul                  // zuul网关  
    ├── position-service          // 获取位置服务  
    ├── ribbon-service-consumer   
    └── springcloud-eureka-server // 服务注册中心  
### frontend
移动端前端，仍待完善（有些部分是写死的）
### hnc
用户服务，主要管理用户的信息，包括登录注册、修改个人信息等。
### hth
订单服务（但好像有些改坏了，所以没在跑），主要是对订单的操作。
### lzr
监控服务，用于给监控的网页前端传入数据。
### order_taking_service
接单服务，前端触发后修改订单的状态。
### sxy
派单服务，为乘客匹配司机，目前采用的算法是匈牙利算法，但是因为订单服务发送的消息无法实现一次包含多个，因此算法部分表现性能不好。目前只调用了信用度和距离信息计算。
### taxi_service
打车服务，前端触发后生成一个待匹配司机的订单。
### try-to-save-order
回退订单服务到没配好swagger的阶段（目前正在跑）
### zsy
稍微旧一点的派单服务