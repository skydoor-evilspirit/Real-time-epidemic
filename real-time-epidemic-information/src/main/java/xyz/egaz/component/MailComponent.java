package xyz.egaz.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;

@Component
public class MailComponent {
    @Autowired
    private JavaMailSender mailSender;

    public void send() {
        System.out.println("执行send方法。。。");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("我是邮件主题。。。");
        mailMessage.setText("我是邮件的内容");
        mailMessage.setTo("evil_spirit@foxmail.com");
        mailMessage.setFrom("evil_spirit@foxmail.com");
        mailSender.send(mailMessage);
    }
    @Autowired
    private TemplateEngine engine;

    public void send2() throws MessagingException {
        System.out.println("发送带网页的模板邮件");
//        创建复杂邮件对象
        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        创建复杂邮件帮助工具
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setSubject("俺是邮箱的主题");
        //使用thymeleaf渲染的容器
        Context context = new Context();
        HashMap<String, Object> map = new HashMap<>();
        map.put("title", "我是正文标题");
        map.put("content", "我是正文内容");
        context.setVariables(map);
        //告诉模板引擎，需要渲染的页面以及数据context
        String content = engine.process("mail", context);
        helper.setText(content,true);
        //设置发件人和收件人
        helper.setFrom("evil_spirit@foxmail.com");
        helper.setTo("evil_spirit@foxmail.com");
        //附加附件
        FileSystemResource fileSystemResource = new FileSystemResource(new File("C:\\Users\\55313\\Desktop\\TimeOfTop.jar"));
        helper.addAttachment("timeJar",fileSystemResource);
        //执行发送
        mailSender.send(mimeMessage);

    }


}
