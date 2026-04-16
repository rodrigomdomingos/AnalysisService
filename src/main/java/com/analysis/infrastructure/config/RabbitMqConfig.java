package com.analysis.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String MARKET_DATA_UPDATED_QUEUE = "market.data.updated";
    public static final String MARKET_DATA_UPDATED_DLQ = "market.data.updated.dlq";
    public static final String MARKET_DATA_UPDATED_DLX = "market.data.updated.dlx";

    @Bean
    Queue marketDataUpdatedQueue() {
        return QueueBuilder.durable(MARKET_DATA_UPDATED_QUEUE)
                .deadLetterExchange(MARKET_DATA_UPDATED_DLX)
                .deadLetterRoutingKey(MARKET_DATA_UPDATED_DLQ)
                .build();
    }

    @Bean
    Queue marketDataUpdatedDlq() {
        return QueueBuilder.durable(MARKET_DATA_UPDATED_DLQ).build();
    }

    @Bean
    DirectExchange marketDataUpdatedDeadLetterExchange() {
        return new DirectExchange(MARKET_DATA_UPDATED_DLX, true, false);
    }

    @Bean
    Binding marketDataUpdatedDlqBinding(
            @Qualifier("marketDataUpdatedDlq") Queue marketDataUpdatedDlq,
            DirectExchange marketDataUpdatedDeadLetterExchange
    ) {
        return BindingBuilder.bind(marketDataUpdatedDlq)
                .to(marketDataUpdatedDeadLetterExchange)
                .with(MARKET_DATA_UPDATED_DLQ);
    }

    @Bean
    MessageConverter rabbitMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    SimpleRabbitListenerContainerFactory analysisRabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter rabbitMessageConverter
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(rabbitMessageConverter);
        factory.setDefaultRequeueRejected(false);
        factory.setErrorHandler(new ConditionalRejectingErrorHandler());
        factory.setAdviceChain(
                RetryInterceptorBuilder.stateless()
                        .maxRetries(3)
                        .backOffOptions(1000, 2.0, 10000)
                        .recoverer(new RejectAndDontRequeueRecoverer())
                        .build()
        );
        return factory;
    }
}
