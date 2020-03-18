package xyz.egaz;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.egaz.component.MailComponent;

import javax.mail.MessagingException;

@SpringBootTest
class AppTests {
    @Autowired
    private MailComponent mail;

    @Test
    void contextLoads() throws MessagingException {
//        mail.send();
        mail.send2();
    }

}
