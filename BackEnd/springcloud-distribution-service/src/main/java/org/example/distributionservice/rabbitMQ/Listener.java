package org.example.distributionservice.rabbitMQ;

import org.apache.juli.logging.Log;
import org.example.distributionservice.service.DistributionService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Listener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.test.queue", durable = "true"),
            exchange = @Exchange(
                    value = "spring.test.queue",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.DIRECT
            )
            ))

    public void newDistributionListen(String msg){
            System.out.println("接到的消息是:"+msg);
            /*DistributionService distributionService = new DistributionService(2,2);
            distributionService.distribute();
            int[] result =distributionService.getResult();
            System.out.println("匹配信息是:"+result[0]+","+result[1]);

            String routingKey="";
            String message ="test_message";
            rabbitTemplate.convertAndSend(routingKey,message);*/

    }

    //一个发送消息的例子
    public void testSendMsg(){
        String queueName="spring.test.queue";
        String message="hello";

    }

}