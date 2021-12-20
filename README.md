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
