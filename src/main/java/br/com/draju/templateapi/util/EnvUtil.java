package br.com.draju.templateapi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import static br.com.draju.templateapi.util.Contants.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Environment util.
 */
@Component
public class EnvUtil {
    
	@Autowired
    Environment environment;

    private String port;
    private String hostname;

    /**
     * Get port.
     *
     * @return
     */
    public String getPort() {
        if (port == null) port = environment.getProperty("server.port");
        return port;
    }

    /**
     * Get port, as Integer.
     *
     * @return
     */
    public Integer getPortAsInt() {
        return Integer.valueOf(getPort());
    }

    /**
     * Get hostname.
     *
     * @return
     */
    public String getHostname() throws UnknownHostException {
        if (hostname == null) hostname = InetAddress.getLocalHost().getHostAddress();
        return hostname;
    }

    public String getServerUrlPrefi() {
        try {
            String hostName = getHostname() == null ? getHostname() : DEFAULT_HOST;
            return PROTOCOL_URL + hostName + ":" + getPort();
        } catch (UnknownHostException ex) {
            return PROTOCOL_URL + DEFAULT_HOST + getPort();
        }
    }
}
