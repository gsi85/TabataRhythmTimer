package com.sisa.tabata.report.crash.email;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.inject.Inject;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;

/**
 * Class for sending emails with via SMTP.
 *
 * @author Laszlo Sisa
 */
public class EmailSender extends javax.mail.Authenticator {

    private static final String TYPE = "text/html";

    private final ApplicationContextProvider applicationContextProvider;
    private final InternetAddress crashReportEmailAddress;
    private final String crashReportEmail;
    private final String password;
    private final Session session;

    static {
        Security.addProvider(new com.provider.JSSEProvider());
    }

    /**
     * DI constructor.
     *
     * @param applicationContextProvider {@link ApplicationContextProvider}
     * @param smtpEmailPropertiesFactory {@link SmtpEmailPropertiesFactory}
     * @throws AddressException if internet address can't be parsed
     */
    @Inject
    public EmailSender(final ApplicationContextProvider applicationContextProvider, final SmtpEmailPropertiesFactory smtpEmailPropertiesFactory)
        throws AddressException {
        this.applicationContextProvider = applicationContextProvider;
        crashReportEmail = applicationContextProvider.getStringResource(R.string.crash_report_email_user_name);
        String hostName = applicationContextProvider.getStringResource(R.string.crash_report_email_host);
        session = Session.getDefaultInstance(smtpEmailPropertiesFactory.createProperties(hostName), this);
        crashReportEmailAddress = new InternetAddress(crashReportEmail);
        password = applicationContextProvider.getStringResource(R.string.crash_report_email_password);
    }

    /**
     * Sends crash report email.
     *
     * @param crashReport crash report message body
     * @throws Exception if error occurred while sending email
     */
    public synchronized void sendMail(String crashReport) throws Exception {
        MimeMessage message = new MimeMessage(session);
        DataHandler handler = new DataHandler(new ByteArrayDataSource(crashReport.getBytes(), TYPE));
        message.setSender(crashReportEmailAddress);
        message.setSubject(applicationContextProvider.getStringResource(R.string.crash_report_email_subject));
        message.setDataHandler(handler);
        message.setRecipient(Message.RecipientType.TO, crashReportEmailAddress);
        Transport.send(message);
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(crashReportEmail, password);
    }

    private class ByteArrayDataSource implements DataSource {
        private static final String NAME = "ByteArrayDataSource";
        private static final String NOT_SUPPORTED = "Not Supported";

        private byte[] data;
        private String type;

        ByteArrayDataSource(byte[] data, String type) {
            this.data = data;
            this.type = type;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            throw new IOException(NOT_SUPPORTED);
        }

        @Override
        public String getContentType() {
            return type;
        }

        @Override
        public String getName() {
            return NAME;
        }
    }
}
