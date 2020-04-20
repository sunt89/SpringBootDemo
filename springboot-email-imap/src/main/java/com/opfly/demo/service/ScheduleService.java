package com.opfly.demo.service;

import java.util.Properties;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
	@Value("${spring.mail.host}")
	private String mailHost;
	@Value("${spring.mail.username}")
	private String mailUserName;
	@Value("${spring.mail.password}")
	private String mailPassword;
	private int imapsPort = 993;
	
	@Scheduled(fixedDelay=30000)
	public void handleEmail() throws Exception {
		// 准备连接服务器的会话信息 
	    Properties props = new Properties(); 
	    Session session = Session.getDefaultInstance(props); 
	    //session.setDebug(true);
	    Store store = session.getStore("imaps"); 
	    store.connect(mailHost, imapsPort, mailUserName, mailPassword); 
	    Folder folder = store.getFolder("INBOX"); 
	    folder.open(Folder.READ_WRITE);
	    
	    // 获得收件箱的邮件列表 
        Flags seen = new Flags(Flags.Flag.SEEN);
        Message[] messages = folder.search(new FlagTerm(seen, false));
        //Message[] messages = folder.getMessages();
        
        int messageCount = folder.getMessageCount();
        int unreadMessageCount = folder.getUnreadMessageCount();
        int newMessageCount = folder.getNewMessageCount();
        System.out.println("收件箱邮件数量：" + messageCount);
        System.out.println("未读邮件数量：" + unreadMessageCount);
        System.out.println("新的邮件数量：" + newMessageCount);
        
        for (int i = 0; i < messages.length; i++) {
        	MimeMessage msg = (MimeMessage) messages[i];
        	
        	MailService mailService = new MailService(msg);
        	System.out.println("邮件　" + i + "　主题:　" + mailService.getSubject());
        	System.out.println("邮件　" + i + "　发送时间:　" + mailService.getSentDate());
        	System.out.println("邮件　" + i + "　是否需要回复:　" + mailService.getReplySign());
        	System.out.println("邮件　" + i + "　是否包含附件:　" + mailService.isContainAttach((Part)msg));
        	System.out.println("邮件　" + i + "　发送人地址:　" + mailService.getFrom());
        	System.out.println("邮件　" + i + "　收信人地址:　" + mailService.getMailAddress("to"));
        	System.out.println("邮件　" + i + "　抄送:　" + mailService.getMailAddress("cc"));
        	System.out.println("邮件　" + i + "　暗抄:　" + mailService.getMailAddress("bcc"));
        	System.out.println("邮件　" + i + "　发送时间:　" + mailService.getSentDate());
        	System.out.println("邮件　" + i + "　邮件ID:　" + mailService.getMessageId());
        	mailService.getMailContent((Part)msg);
        	System.out.println("邮件　" + i + "　正文内容:　\r\n" + mailService.getBodyText());
        	mailService.setAttachPath("E:\\");
        	mailService.saveAttachMent((Part)msg);
        }
        
        folder.close(false); 
        store.close(); 
	}
}
