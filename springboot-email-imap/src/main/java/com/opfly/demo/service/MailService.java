package com.opfly.demo.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import com.opfly.demo.utils.StringUtil;

public class MailService {
	private MimeMessage mimeMessage = null;
    private String saveAttachPath = ""; // 附件下载后的存放目录
    private StringBuffer bodyText = new StringBuffer(); // 存放邮件内容的StringBuffer对象
    private String dateFormat = "yyyy-MM-dd HH:mm:ss"; // 默认的日前显示格式
 
    /**
     * 	构造函数,初始化一个MimeMessage对象
     */
    public MailService() {
    }
 
    public MailService(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }
 
    public void setMimeMessage(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }
 
    /**
     * 	获得发件人的地址和姓名 　
     * @throws MessagingException 
     */
    public String getFrom() throws MessagingException  {
        InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
        String from = StringUtil.DealNullString(address[0].getAddress());
        String personal = StringUtil.DealNullString(address[0].getPersonal());
        String fromAddr = personal + "<" + from + ">";
        return fromAddr;
    }
 
    /**
     * 	获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同
     * "to"----收件人　"cc"---抄送人地址　"bcc"---密送人地址 　
     * @throws MessagingException 
     */
    public String getMailAddress(String type) throws MessagingException {
        String mailAddr = "";
        String addType = type.toUpperCase();
 
        InternetAddress[] address = null;
        switch (addType) {
		case "TO":
			address = (InternetAddress[]) mimeMessage
            .getRecipients(Message.RecipientType.TO);
			break;
		case "CC":
			address = (InternetAddress[]) mimeMessage
            .getRecipients(Message.RecipientType.CC);
			break;
		case "BCC":
			address = (InternetAddress[]) mimeMessage
            .getRecipients(Message.RecipientType.BCC);
			break;
		default:
			
			break;
		}
        
        if (address != null) {
            for (int i = 0; i < address.length; i++) {
                String emailAddr = StringUtil.DealNullString(address[i].getAddress());
                //emailAddr = MimeUtility.decodeText(emailAddr);
                String personal = StringUtil.DealNullString(address[i].getPersonal());
                String compositeto = personal + "<" + emailAddr + ">";
                mailAddr += "," + compositeto;
            }
            //去除第一个 ","
            mailAddr = mailAddr.substring(1);
        }
        return mailAddr;
    }
 
    /**
     *	获得邮件主题
     * @return
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public String getSubject() throws MessagingException, UnsupportedEncodingException {
        String subject = MimeUtility.decodeText(mimeMessage.getSubject());
        return subject;
    }
 
    /**
     *	获得邮件发送日期 　
     * @throws MessagingException 
     */
    public String getSentDate() throws MessagingException {
        Date sentDate = mimeMessage.getSentDate();
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        String strSentDate = format.format(sentDate);
        return strSentDate;
    }
 
    /**
     * 	 获得邮件正文内容 　
     */
    public String getBodyText() {
        return bodyText.toString();
    }
 
    /**
     * 	解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件
     *	主要是根据MimeType类型的不同执行不同的操作，一步一步的解析 　　
     */
    public void getMailContent(Part part) throws Exception {
        String contentType = part.getContentType();
        // 获得邮件的MimeType类型
        int nameIndex = contentType.indexOf("name");
        boolean nameExist = (nameIndex != -1) ? true : false;

        if (part.isMimeType("text/plain") && nameExist == false) {
            bodyText.append((String) part.getContent());
        } 
        else if (part.isMimeType("text/html") && nameExist == false) {
            bodyText.append((String) part.getContent());
        } 
        else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int counts = multipart.getCount();
            for (int i = 0; i < counts; i++) {
                getMailContent(multipart.getBodyPart(i));
            }
        }
        else if (part.isMimeType("message/rfc822")) {
            getMailContent((Part) part.getContent());
        } 
        else {
        }
    }
 
    /**
     * 	判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false" 　
     */
    public boolean getReplySign() throws MessagingException {
        boolean replySign = false;
        String needReply[] = mimeMessage.getHeader("Disposition-Notification-To");
        if (needReply != null) {
            replySign = true;
        }
        return replySign;
    }
 
    /**
     *	获得邮件的MessageID 　　
     */
    public String getMessageId() throws MessagingException {
        String messageID = mimeMessage.getMessageID();
        return messageID;
    }
 
    /**
     *	 判断此邮件是否包含附件
     */
    public boolean isContainAttach(Part part) throws Exception {
        boolean attachFlag = false;
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                
                String disposition = bodyPart.getDisposition();
                if ((disposition != null) 
                		&& ((disposition.equals(Part.ATTACHMENT)) 
                				|| (disposition.equals(Part.INLINE)))) {
                	attachFlag = true;
                }  
                else if (bodyPart.isMimeType("multipart/*")) {
                    attachFlag = isContainAttach((Part) bodyPart);
                } 
                else {
                    String contentType = bodyPart.getContentType().toLowerCase();
                    if ((contentType.indexOf("application") != -1)
                    		|| (contentType.toLowerCase().indexOf("name") != -1)) {
                    	attachFlag = true;
                    }
                }
            }
        }
        else if (part.isMimeType("message/rfc822")) {
            attachFlag = isContainAttach((Part) part.getContent());
        }
        
        return attachFlag;
    }
 
    /**
     *	保存附件 　
     * @throws Exception 
     * @throws IOException 
     * @throws MessagingException 
     * @throws UnsupportedEncodingException 
     */
    public void saveAttachMent(Part part) throws UnsupportedEncodingException, MessagingException, IOException, Exception {
        String fileName = "";
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                
                String disposition = bodyPart.getDisposition();
                if ((disposition != null)
                        && ((disposition.equals(Part.ATTACHMENT)) 
                        		|| (disposition.equals(Part.INLINE)))) {
                    fileName = bodyPart.getFileName();
                    
                    if (fileName.toLowerCase().indexOf("gb2312") != -1) {
                        fileName = MimeUtility.decodeText(fileName);
                    }
                    saveFile(fileName, bodyPart.getInputStream());
                }
                else if (bodyPart.isMimeType("multipart/*")) {
                    saveAttachMent(bodyPart);
                }
                else {
                    fileName = bodyPart.getFileName();
                    if ((fileName != null)
                            && (fileName.toLowerCase().indexOf("GB2312") != -1)) {
                        fileName = MimeUtility.decodeText(fileName);
                        saveFile(fileName, bodyPart.getInputStream());
                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            saveAttachMent((Part) part.getContent());
        }
    }
 
    /**
     *	设置附件存放路径
     */
    public void setAttachPath(String attachPath) {
        this.saveAttachPath = attachPath;
    }
 
    /**
     *	获得附件存放路径 　
     */
    public String getAttachPath() {
        return saveAttachPath;
    }
 
    /**
     * 	真正的保存附件到指定目录里 　
     */
    private void saveFile(String fileName, InputStream in) throws Exception {
        String storeDir = getAttachPath();
        File storeFile = new File(storeDir + fileName);
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
 
        try {
            bos = new BufferedOutputStream(new FileOutputStream(storeFile));
            bis = new BufferedInputStream(in);
            int c;
            while ((c = bis.read()) != -1) {
                bos.write(c);
                bos.flush();
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
        } finally {
            bos.close();
            bis.close();
        }
    }
}
