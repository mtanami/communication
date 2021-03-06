/*
 * Copyright 2015 Cisco Systems, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.cisco.oss.foundation.http.server;

import com.cisco.oss.foundation.configuration.ConfigurationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Yair Ogen
 * Date: 10/16/13
 * Time: 3:26 PM
 */
//@Controller("probe")
public class PingServlet extends HttpServlet {

    private final static Logger LOGGER = LoggerFactory.getLogger(PingServlet.class);

    private boolean enableLogging = false;

    @Value("${spring.application.name}")
    private String serviceName = null;

    public PingServlet(){
        enableLogging = ConfigurationFactory.getConfiguration().getBoolean(serviceName + "http.pingFilter.enableLogging", false);
    }

    public PingServlet(String serviceName) {
        super();
        this.serviceName = serviceName;
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ping(req, resp);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ping(req, resp);    //To change body of overridden methods use File | Settings | File Templates.
    }

    private void ping(HttpServletRequest req, HttpServletResponse resp) {
        if (enableLogging) {
            LOGGER.debug("HTTP Ping received from " + AbstractInfraHttpFilter.getOriginalClient(req));
        }
        ((HttpServletResponse) resp).setDateHeader("Date", System.currentTimeMillis());
        ((HttpServletResponse) resp).setStatus(HttpServletResponse.SC_OK);
    }
}
