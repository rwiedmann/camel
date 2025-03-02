/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.jms.issues;

import java.util.concurrent.atomic.LongAdder;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.test.spring.junit5.CamelSpringTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for issue CAMEL-706
 */
public class TransactionErrorHandlerRedeliveryDelayTest extends CamelSpringTestSupport {

    private static final LongAdder COUNTER = new LongAdder();

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext(
                "org/apache/camel/component/jms/issues/TransactionErrorHandlerRedeliveryDelayTest-context.xml");
    }

    @Test
    public void testTransactedRedeliveryDelay() throws Exception {
        getMockEndpoint("mock:result").expectedBodiesReceived("Bye World");

        template.sendBody("activemq:queue:TransactionErrorHandlerRedeliveryDelayTest.in", "Hello World");

        assertMockEndpointsSatisfied();
    }

    public static class MyFailureProcessor implements Processor {

        public MyFailureProcessor() {
        }

        @Override
        public void process(Exchange exchange) {
            int counterValue = COUNTER.intValue();
            COUNTER.increment();
            if (counterValue < 3) {
                throw new IllegalArgumentException("Forced exception as counter is " + counterValue);
            }
            assertTrue(exchange.isTransacted(), "Should be transacted");
            exchange.getIn().setBody("Bye World");
        }
    }
}
