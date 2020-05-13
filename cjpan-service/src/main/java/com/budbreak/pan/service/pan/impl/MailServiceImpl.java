package com.budbreak.pan.service.pan.impl;

import cn.hutool.extra.mail.MailUtil;
import com.budbreak.pan.entity.pan.MailSendConfig;
import com.budbreak.pan.service.MailProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author Administrator
 * @date 2018/9/1
 */
@Service
public class MailServiceImpl {

    @Autowired
    private MailProperties mailProperties;


    /**
     * 监听消费邮件发送
     *
     * @param mailSendConfig
     */
    @RabbitListener(queues = "${mail.queue.name}", containerFactory = "singleListenerContainer")
    public void consumeMailQueue(@Payload MailSendConfig mailSendConfig) {
        try {
            sendEmail(mailSendConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(MailSendConfig mailSendConfig) throws Exception {
        //注册成功发送邮件
        MailUtil.send(mailSendConfig.getSendMail(),
                mailSendConfig.getTopic(),
                mailSendConfig.getContent(),
                true);
//        Properties properties = new Properties();
//        properties.setProperty("mail.host", mailProperties.getHost());
//        properties.setProperty("mail.transport.protocol", mailProperties.getProtocol());
//        properties.setProperty("mail.smtp.auth", mailProperties.getNeedAuth());
//        properties.setProperty("mail.smtp.socketFactory.class", mailProperties.getSslClass());
//        properties.setProperty("mail.smtp.port", mailProperties.getPort() + "");
//
//
//        Authenticator auth = new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(mailProperties.getUserName(), mailProperties.getPassword());
//            }
//        };
//        Session session = Session.getInstance(properties, auth);
//
//        MimeMessage mimeMessage = new MimeMessage(session);
//        mimeMessage.setSubject(mailSendConfig.getTopic());
//        mimeMessage.setContent(mailSendConfig.getContent(), "text/html;charset=utf-8");
//
////        //TODO：批量发送多个收件人
////        String arr[]=env.getProperty("mail.to").split(",");
////        Address[] addresses=new Address[arr.length];
////        for(int i=0;i<arr.length;i++){
////            addresses[i]=new InternetAddress(arr[i]);
////        }
////        mimeMessage.addRecipients(Message.RecipientType.TO, addresses);
//
//        // 只发送一个收件人
//        mimeMessage.addRecipients(Message.RecipientType.TO, mailSendConfig.getSendMail());
//        //mimeMessage.addRecipients(Message.RecipientType.CC, "linsenzhong@126.com");
//
//        Transport transport = session.getTransport();
//        transport.connect(mailProperties.getHost(), mailProperties.getUserName(), mailProperties.getPassword());
//        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
//        transport.close();
    }


}