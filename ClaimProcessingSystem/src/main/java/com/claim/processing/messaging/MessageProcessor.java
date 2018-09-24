package com.claim.processing.messaging;

import java.io.IOException;
import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.claim.processing.constant.FlowConstant;
import com.claim.processing.service.impl.AbstractService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rabbitmq.client.Channel;

@Component
public class MessageProcessor implements BeanFactoryAware {

	private BeanFactory beanFactory;

	@Autowired
	MappingJackson2MessageConverter converter;

	@Autowired
	RabbitTemplate rabbitTemplate;

	@RabbitListener(queues = "claimSystemInbound")
	public void receiveMessage(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)
			throws IOException {
		try {
			String body = new String(message.getBody(),"UTF-8").replace("\"{", "{").replace("}\"", "}").replace("\\", "");
			Map<String, Object> result = converter.getObjectMapper().readValue(body,
					new TypeReference<Map<String, Object>>() {
					});
			System.out.println("Received <" + new String(message.getBody()) + ">");
			AbstractService service = beanFactory.getBean(result.get(FlowConstant.ACTIVITY_ID).toString(),
					AbstractService.class);
			service.executeService(result);
			channel.basicAck(tag, false);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error occured requeuing message for processing again");
			channel.basicReject(tag, true);
		}
	}

	public void sendMessage(String routeKey, Object msg) {
		try {
			String serializedMsg = converter.getObjectMapper().writeValueAsString(msg);
			rabbitTemplate.convertAndSend(routeKey, serializedMsg);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
}
