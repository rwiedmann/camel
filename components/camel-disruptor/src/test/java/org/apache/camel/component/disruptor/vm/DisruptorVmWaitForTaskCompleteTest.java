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
package org.apache.camel.component.disruptor.vm;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DisruptorVmWaitForTaskCompleteTest extends AbstractVmTestSupport {

    @Test
    void testInOut() throws Exception {
        getMockEndpoint("mock:result").expectedBodiesReceived("Bye World");

        String out = template2.requestBody("direct:start", "Hello World", String.class);
        assertEquals("Bye World", out);

        assertMockEndpointsSatisfied();
    }

    @Test
    void testInOnly() throws Exception {
        getMockEndpoint("mock:result").expectedBodiesReceived("Bye World");

        // we send an in only but we use Always to wait for it to complete
        // and since the route changes the payload we can get the response anyway
        Exchange out = template2.send("direct:start", new Processor() {
            public void process(Exchange exchange) {
                exchange.getIn().setBody("Hello World");
                exchange.setPattern(ExchangePattern.InOnly);
            }
        });
        assertEquals("Bye World", out.getIn().getBody());

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("disruptor-vm:foo?waitForTaskToComplete=Always").transform(constant("Bye World"))
                        .to("mock:result");
            }
        };
    }

    @Override
    protected RouteBuilder createRouteBuilderForSecondContext() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:start").to("disruptor-vm:foo?waitForTaskToComplete=Always");
            }
        };
    }
}
