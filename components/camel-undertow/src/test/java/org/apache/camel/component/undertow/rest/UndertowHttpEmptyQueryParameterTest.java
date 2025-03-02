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
package org.apache.camel.component.undertow.rest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.undertow.BaseUndertowTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UndertowHttpEmptyQueryParameterTest extends BaseUndertowTest {

    @Test
    public void testEmpty() throws Exception {
        getMockEndpoint("mock:input").expectedHeaderReceived("id", 123);
        String out = fluentTemplate.to("undertow:http://localhost:{{port}}/foo?id=123").request(String.class);
        assertEquals("Header: 123", out);
        assertMockEndpointsSatisfied();

        resetMocks();

        getMockEndpoint("mock:input").expectedHeaderReceived("id", "");
        out = fluentTemplate.to("undertow:http://localhost:{{port}}/foo?id=").request(String.class);
        assertEquals("Header: ", out);
        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("undertow:http://0.0.0.0:{{port}}/foo")
                        .to("mock:input")
                        .transform().simple("Header: ${header.id}");
            }
        };
    }

}
