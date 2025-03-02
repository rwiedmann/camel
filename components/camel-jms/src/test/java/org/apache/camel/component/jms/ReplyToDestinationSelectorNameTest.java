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
package org.apache.camel.component.jms;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 */
public class ReplyToDestinationSelectorNameTest extends AbstractJMSTest {

    @Test
    public void testReplyToDestinationSelectorName() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedBodiesReceived("Bye World", "Bye Camel");

        Object body1 = template.requestBody("direct:start", "World");
        Object body2 = template.requestBody("direct:start", "Camel");

        assertMockEndpointsSatisfied();

        assertEquals("Bye World", body1);
        assertEquals("Bye Camel", body2);
    }

    @Override
    protected String getComponentName() {
        return "activemq";
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:start")
                        .to("activemq:queue:ReplyToDestinationSelectorNameTest.foo?replyTo=queue:ReplyToDestinationSelectorNameTest.bar&replyToDestinationSelectorName=replyId")
                        .to("mock:result");

                from("activemq:queue:ReplyToDestinationSelectorNameTest.foo")
                        .log("Using header named replyId with value as correlation - ${header.replyId}")
                        .transform(body().prepend("Bye "));
            }
        };
    }
}
