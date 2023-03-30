package com.shpp.mentoring.okushin.task3;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.jms.JMSException;
import javax.validation.Validator;
import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ReceiverTest extends Receiver {

    @Mock
    ActiveMqManager amqManagerMock = mock(ActiveMqManager.class);
    @Mock
    Validator validatorMock = mock(Validator.class);


    @Test
    void closeConsumerConnectionCallVerifying() {
        POJOMessage poisonPillPOJO = new POJOMessage("poison pill", -1, LocalDateTime.now());
        Receiver receiverTest = new Receiver(amqManagerMock, validatorMock, poisonPillPOJO);
        receiverTest.run();

        verify(amqManagerMock).closeConsumerConnection();

    }


}