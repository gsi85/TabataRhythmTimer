package com.sisa.tabata.report.crash.email;

import java.util.Properties;

import com.google.inject.Singleton;

/**
 * Factory for creating properties required for sending email via SMTP.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class SmtpEmailPropertiesFactory {

    /**
     * Creates a new, configured {@link Properties} instance.
     *
     * @param hostName the host name of e-mail server
     * @return {@link Properties}
     */
    public Properties createProperties(final String hostName) {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.host", hostName);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.quitwait", "false");
        return properties;
    }

}
