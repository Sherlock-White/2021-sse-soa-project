
package org.example.rabbitMQ;

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

    /*@Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.test.queue", durable = "true"),
            exchange = @Exchange(
                    value = "spring.test.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.DIRECT
            ),
            key = {"#.#"}
            ))
    public void newDistributionListen(String msg){
            System.out.println("接到的消息是:"+msg);
    }

    //一个发送消息的例子
    public void testSendMsg(){
        String queueName="spring.test.queue";
        String message="hello";
        rabbitTemplate.convertAndSend(queueName,message);
    }*/

}