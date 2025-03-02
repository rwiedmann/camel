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
package org.apache.camel.component.file.remote.integration;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FtpConsumerMaxMessagesPerPollIT extends FtpServerTestSupport {

    private String getFtpUrl() {
        return "ftp://admin@localhost:{{ftp.server.port}}/poll/?password=admin&delay=6000&delete=true&sortBy=file:name&maxMessagesPerPoll=2";
    }

    @BeforeEach
    public void prepareFtpServer() {
        sendFile(getFtpUrl(), "Bye World", "bye.txt");
        sendFile(getFtpUrl(), "Hello World", "hello.txt");
        sendFile(getFtpUrl(), "Godday World", "godday.txt");
    }

    @Test
    public void testMaxMessagesPerPoll() throws Exception {
        // start route
        context.getRouteController().startRoute("foo");

        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedBodiesReceived("Bye World", "Godday World");
        mock.setResultWaitTime(4000);
        mock.expectedPropertyReceived(Exchange.BATCH_SIZE, 2);

        assertMockEndpointsSatisfied();

        mock.reset();
        mock.expectedBodiesReceived("Hello World");
        mock.expectedPropertyReceived(Exchange.BATCH_SIZE, 1);

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from(getFtpUrl()).noAutoStartup().routeId("foo").to("mock:result");
            }
        };
    }
}
