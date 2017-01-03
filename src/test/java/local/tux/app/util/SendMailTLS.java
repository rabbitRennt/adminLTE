package local.tux.app.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SendMailTLS {

	public static void main(String[] args) throws MessagingException {
		//System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
		//sendMail1();
		sendMail2();
		//sendFileMail();
	}

	public static void sendMail1() {

		final String username = "3325304073";
		final String password = "glcbjhijefoqebjf";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.qq.com");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		session.setDebug(true);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("3325304073@qq.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("2205380769@qq.com"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler, \n\n No spam to my email, please!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void sendMail2() {
		// 创建邮件发送服务器
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("mail.maisulang.com");
		//mailSender.setPort(25);
		mailSender.setUsername("admin@jumore.com");
		mailSender.setPassword("jumore.en#201508");
		// 加认证机制
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", true);
		//javaMailProperties.put("mail.smtp.starttls.enable", true);
		//javaMailProperties.put("mail.smtp.timeout", 5000);
		mailSender.setJavaMailProperties(javaMailProperties);
		// 创建邮件内容
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("admin@jumore.com");
		message.setTo("2205380769@qq.com");
		message.setSubject("tgest");
		message.setText("test");
		// 发送邮件
		mailSender.send(message);
	}
	
	
	
	
	/** 
	  *用spring mail 发送邮件,依赖jar：spring.jar，activation.jar，mail.jar  
	  */     
	  public static void sendFileMail() throws MessagingException {  
	          JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();  
	    
	          // 设定mail server  
	          senderImpl.setHost("smtp.126.com");  
	          senderImpl.setUsername("fans_2046@126.com");  
	          senderImpl.setPassword("jumore2017");  
	          // 建立HTML邮件消息  
	          MimeMessage mailMessage = senderImpl.createMimeMessage();  
	          // true表示开始附件模式  
	          MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");  
	    
	          // 设置收件人，寄件人  
	          messageHelper.setTo("2205380769@qq.com");  
	          messageHelper.setFrom("fans_2046@126.com");  
	          messageHelper.setSubject("测试邮件！");  
	          // true 表示启动HTML格式的邮件  
	          messageHelper.setText("<html><head></head><body><h1>你好：附件！！</h1></body></html>", true);  
	    
	          // 添加2个附件  
	          //messageHelper.addAttachment("logo.jpg", file1);  
	            
	          // 发送邮件  
	          senderImpl.send(mailMessage);  
	          System.out.println("邮件发送成功.....");  
	    
	      }
}