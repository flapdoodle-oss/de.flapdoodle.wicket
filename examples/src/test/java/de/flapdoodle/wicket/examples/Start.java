/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Jan Bernitt <unknown@email.de>
 *
 * with contributions from
 * 	nobody yet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.wicket.examples;

import org.apache.wicket.protocol.ws.javax.WicketServerEndpointConfig;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.jakarta.server.config.JakartaWebSocketServletContainerInitializer;

import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;

public class Start {
    /**
     * Main function, starts the jetty server.
     *
     * @param args
     */
    public static void main(String[] args) throws Exception
    {
        System.setProperty("wicket.configuration", "development");

        Server server = new Server();

        HttpConfiguration http_config = new HttpConfiguration();
        http_config.setSecureScheme("https");
        http_config.setSecurePort(8443);
        http_config.setOutputBufferSize(32768);

        ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(http_config));
        http.setPort(8080);
        http.setIdleTimeout(1000 * 60 * 60);

        server.addConnector(http);

        Resource keystore = Resource.newClassPathResource("/keystore");
        if (keystore != null && keystore.exists())
        {
            // if a keystore for a SSL certificate is available, start a SSL
            // connector on port 8443.
            // By default, the quickstart comes with a Apache Wicket Quickstart
            // Certificate that expires about half way september 2031. Do not
            // use this certificate anywhere important as the passwords are
            // available in the source.

            org.eclipse.jetty.util.ssl.SslContextFactory.Server sslContextFactory = new org.eclipse.jetty.util.ssl.SslContextFactory.Server();
            sslContextFactory.setKeyStoreResource(keystore);
            sslContextFactory.setKeyStorePassword("wicket");
            sslContextFactory.setKeyManagerPassword("wicket");

            HttpConfiguration https_config = new HttpConfiguration(http_config);
            SecureRequestCustomizer src = new SecureRequestCustomizer();
            src.setSniHostCheck(false);
            https_config.addCustomizer(src);

            ServerConnector https = new ServerConnector(server, new SslConnectionFactory(
              sslContextFactory, "http/1.1"), new HttpConnectionFactory(https_config));
            https.setPort(8443);
            https.setIdleTimeout(500000);

            server.addConnector(https);
            System.out.println("SSL access to the examples has been enabled on port 8443");
            System.out
              .println("You can access the application using SSL on https://localhost:8443");
            System.out.println();
        }

        WebAppContext bb = new WebAppContext();
        bb.setServer(server);
        bb.setContextPath("/");
        bb.setWar("src/main/webapp");

        // uncomment the next two lines if you want to start Jetty with WebSocket (JSR-356) support
        // you need org.apache.wicket:wicket-native-websocket-javax in the classpath!
        // ServerContainer serverContainer = WebSocketServerContainerInitializer.configureContext(bb);
        // serverContainer.addEndpoint(new WicketServerEndpointConfig());

//		bb.getSessionHandler().setSessionCache(sessionCache);

        ServletContextHandler contextHandler = ServletContextHandler.getServletContextHandler(bb.getServletContext());
        JakartaWebSocketServletContainerInitializer.configure(contextHandler,
          (servletContext, container) -> container.addEndpoint(new WicketServerEndpointConfig()));
        // uncomment next line if you want to test with JSESSIONID encoded in the urls
        // ((AbstractSessionManager)
        // bb.getSessionHandler().getSessionManager()).setUsingCookies(false);

        server.setHandler(bb);

        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
        server.addEventListener(mBeanContainer);
        server.addBean(mBeanContainer);

        try
        {
            server.start();
            server.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(100);
        }
    }
    
//    public static void main(String[] args) throws Exception {
//        int timeout = (int) Duration.ONE_HOUR.getMilliseconds();
//
//        Server server = new Server();
//        SocketConnector connector = new SocketConnector();
//
//        // Set some timeout options to make debugging easier.
//        connector.setMaxIdleTime(timeout);
//        connector.setSoLingerTime(-1);
//        connector.setPort(8080);
//        server.addConnector(connector);
//
//		// check if a keystore for a SSL certificate is available, and
//		// if so, start a SSL connector on port 8443. By default, the
//		// quickstart comes with a Apache Wicket Quickstart Certificate
//		// that expires about half way september 2021. Do not use this
//		// certificate anywhere important as the passwords are available
//		// in the source.
//
//        Resource keystore = Resource.newClassPathResource("/keystore");
//        if (keystore != null && keystore.exists()) {
//            connector.setConfidentialPort(8443);
//
//            SslContextFactory factory = new SslContextFactory();
//            factory.setKeyStoreResource(keystore);
//            factory.setKeyStorePassword("wicket");
//            factory.setTrustStoreResource(keystore);
//            factory.setKeyManagerPassword("wicket");
//            SslSocketConnector sslConnector = new SslSocketConnector(factory);
//            sslConnector.setMaxIdleTime(timeout);
//            sslConnector.setPort(8443);
//            sslConnector.setAcceptors(4);
//            server.addConnector(sslConnector);
//
//            System.out.println("SSL access to the quickstart has been enabled on port 8443");
//            System.out.println("You can access the application using SSL on https://localhost:8443");
//            System.out.println();
//        }
//
//        WebAppContext bb = new WebAppContext();
//        bb.setServer(server);
//        bb.setContextPath("/");
//        bb.setWar("src/main/webapp");
//
//        // START JMX SERVER
//        // MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
//        // MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
//        // server.getContainer().addEventListener(mBeanContainer);
//        // mBeanContainer.start();
//
//        server.setHandler(bb);
//
//        try {
//            System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
//            server.start();
//            System.in.read();
//            System.out.println(">>> STOPPING EMBEDDED JETTY SERVER");
//            server.stop();
//            server.join();
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.exit(1);
//        }
//    }
}