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
package org.apache.camel.component.sjms.producer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.sjms.SjmsComponent;
import org.apache.camel.component.sjms.support.MyAsyncComponent;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AsyncTopicProducerTest extends CamelTestSupport {

    private static String beforeThreadName;
    private static String afterThreadName;
    private static String sedaThreadName;
    private static String route = "";

    @Test
    public void testAsyncTopicProducer() throws Exception {
        getMockEndpoint("mock:before").expectedBodiesReceived("Hello Camel");
        getMockEndpoint("mock:after").expectedBodiesReceived("Bye Camel");
        getMockEndpoint("mock:result").expectedBodiesReceived("Bye Camel");

        template.sendBody("direct:start", "Hello Camel");
        // we should run before the async processor that sets B
        route += "A";

        assertMockEndpointsSatisfied();

        assertFalse(beforeThreadName.equalsIgnoreCase(afterThreadName), "Should use different threads");
        assertFalse(beforeThreadName.equalsIgnoreCase(sedaThreadName), "Should use different threads");
        assertFalse(afterThreadName.equalsIgnoreCase(sedaThreadName), "Should use different threads");

        assertEquals("AB", route);
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext camelContext = super.createCamelContext();

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "vm://broker?broker.persistent=false&broker.useJmx=false");
        SjmsComponent component = new SjmsComponent();
        component.setConnectionFactory(connectionFactory);
        camelContext.addComponent("sjms", component);

        return camelContext;
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                context.addComponent("async", new MyAsyncComponent());

                from("direct:start")
                        .to("mock:before")
                        .to("log:before")
                        .process(new Processor() {
                            public void process(Exchange exchange) {
                                beforeThreadName = Thread.currentThread().getName();
                            }
                        })
                        .to("async:bye:camel")
                        .process(new Processor() {
                            public void process(Exchange exchange) {
                                afterThreadName = Thread.currentThread().getName();
                            }
                        })
                        .to("sjms:topic:foo");

                from("sjms:topic:foo")
                        .to("mock:after")
                        .to("log:after")
                        .delay(1000)
                        .process(new Processor() {
                            public void process(Exchange exchange) {
                                route += "B";
                                sedaThreadName = Thread.currentThread().getName();
                            }
                        })
                        .to("mock:result");
            }
        };
    }
}
