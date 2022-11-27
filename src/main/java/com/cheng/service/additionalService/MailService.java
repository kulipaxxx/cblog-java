package com.cheng.service.additionalService;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cheng.common.dto.LoginDto;
import com.cheng.common.dto.pwdDto;
import com.cheng.entity.User;
import com.cheng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * @Description: MailService
 * @Author cheng
 * @Date: 2022/11/26 22:47
 * @Version 1.0
 */
@Service
@Async
public class MailService {
    //定义邮件发送器
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    //定义邮件发送者
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送html邮件
     *
     * @param to       接收者
     * @param subject  主题
     * @param loginDto 登录dto
     * @throws Exception 异常
     * @Value注解读取配置文件中同名的配置值
     */
    public void sendHtmlMail(String to, String subject, LoginDto loginDto) throws Exception {
        System.out.println(DateUtil.now());
        String content = "<div style=\"background-color:#ECECEC; padding: 35px;\">\n" +
                "    <table cellpadding=\"0\" align=\"center\"\n" +
                "           style=\"width: 600px; margin: 0px auto; text-align: left; position: relative; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 5px; border-bottom-left-radius: 5px; font-size: 14px; font-family:微软雅黑, 黑体; line-height: 1.5; box-shadow: rgb(153, 153, 153) 0px 0px 5px; border-collapse: collapse; background-position: initial initial; background-repeat: initial initial;background:#fff;\">\n" +
                "        <tbody>\n" +
                "        <tr>\n" +
                "            <th valign=\"middle\"\n" +
                "                style=\"height: 25px; line-height: 25px; padding: 15px 35px; border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #42a3d3; background-color: #49bcff; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;\">\n" +
                "                <font face=\"微软雅黑\" size=\"5\" style=\"color: rgb(255, 255, 255); \">注册成功! （CHUB）</font>\n" +
                "            </th>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>\n" +
                "                <div style=\"padding:25px 35px 40px; background-color:#fff;\">\n" +
                "                    <h2 style=\"margin: 5px 0px; \">\n" +
                "                        <font color=\"#333333\" style=\"line-height: 20px; \">\n" +
                "                            <font style=\"line-height: 22px; \" size=\"4\">\n" +
                "                                亲爱的 </font>\n" +
                "                        </font>\n" +
                "                    </h2>\n" +
                "                    <p>首先感谢您加入CHUB！下面是您的账号信息<br>\n" +
                "                        您的账号：<b>"+ loginDto.getUsername() +"</b><br>\n" +
                "                        您的密码：<b>*******</b><br>\n" +
                "                        您的邮箱：<b>" + to + "</b><br>\n" +
                "                        您注册时的日期：<b>"+ DateUtil.now() + "</b><br>\n" +
                "                        当您在使用本网站时，遵守当地法律法规。<br>\n" +
                "                        如果您有什么疑问可以联系管理员，Email: 1845472368@qq.com</p>\n" +
                "                    <div style=\"width:700px;margin:0 auto;\">\n" +
                "                        <div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\">\n" +
                "                            <p>此为系统邮件，请勿回复<br>\n" +
                "                                请保管好您的邮箱，避免账号被他人盗用\n" +
                "                            </p>\n" +
                "                            <p>©***</p>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "</div>\n";
        //创建MIME样式的电子邮件对象
        MimeMessage message = mailSender.createMimeMessage();

        //用于填充MimeMessage的帮助类
        //参数1填入MimeMessage对象
        //参数2表示是否创建支持替代文本、内联元素和附件的多部分消息
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        //参数2表示是否使用默认内容类型为HTML邮件应用内容类型"text/html"
        helper.setText(content, true);
        message.setFrom(from);
        mailSender.send(message);
    }

    public void sendHtmlMail(String to, String subject, pwdDto pwdDto, String pwd) throws Exception {
        System.out.println(DateUtil.now());
        String content = "<div style=\"background-color:#ECECEC; padding: 35px;\">\n" +
                "    <table cellpadding=\"0\" align=\"center\"\n" +
                "           style=\"width: 600px; margin: 0px auto; text-align: left; position: relative; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 5px; border-bottom-left-radius: 5px; font-size: 14px; font-family:微软雅黑, 黑体; line-height: 1.5; box-shadow: rgb(153, 153, 153) 0px 0px 5px; border-collapse: collapse; background-position: initial initial; background-repeat: initial initial;background:#fff;\">\n" +
                "        <tbody>\n" +
                "        <tr>\n" +
                "            <th valign=\"middle\"\n" +
                "                style=\"height: 25px; line-height: 25px; padding: 15px 35px; border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #42a3d3; background-color: #49bcff; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;\">\n" +
                "                <font face=\"微软雅黑\" size=\"5\" style=\"color: rgb(255, 255, 255); \">找回成功! （CHUB）</font>\n" +
                "            </th>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>\n" +
                "                <div style=\"padding:25px 35px 40px; background-color:#fff;\">\n" +
                "                    <h2 style=\"margin: 5px 0px; \">\n" +
                "                        <font color=\"#333333\" style=\"line-height: 20px; \">\n" +
                "                            <font style=\"line-height: 22px; \" size=\"4\">\n" +
                "                                您的用户信息为 </font>\n" +
                "                        </font>\n" +
                "                    </h2>\n" +
                "                    <p>首先感谢您加入CHUB！下面是您的账号信息<br>\n" +
                "                        您的账号：<b>"+ pwdDto.getUsername() +"</b><br>\n" +
                "                        您的密码：<b>"+ pwd +"</b><br>\n" +
                "                        您的邮箱：<b>" + to + "</b><br>\n" +
                "                        您注册时的日期：<b>"+ DateUtil.now() + "</b><br>\n" +
                "                        当您在使用本网站时，遵守当地法律法规。<br>\n" +
                "                        如果您有什么疑问可以联系管理员，Email: 1845472368@qq.com</p>\n" +
                "                    <div style=\"width:700px;margin:0 auto;\">\n" +
                "                        <div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\">\n" +
                "                            <p>此为系统邮件，请勿回复<br>\n" +
                "                                请保管好您的邮箱，避免账号被他人盗用\n" +
                "                            </p>\n" +
                "                            <p>©***</p>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "</div>\n";
        //创建MIME样式的电子邮件对象
        MimeMessage message = mailSender.createMimeMessage();

        //用于填充MimeMessage的帮助类
        //参数1填入MimeMessage对象
        //参数2表示是否创建支持替代文本、内联元素和附件的多部分消息
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        //参数2表示是否使用默认内容类型为HTML邮件应用内容类型"text/html"
        helper.setText(content, true);
        message.setFrom(from);
        mailSender.send(message);
    }

    public void getPassword(pwdDto pwdDto) throws Exception {
        User email = userService.getOne(new QueryWrapper<User>().eq("email", pwdDto.getEmail()));
        Assert.notNull(email,"用户不存在");
        String pwd = SecureUtil.md5(email.getPassword());
        sendHtmlMail(pwdDto.getEmail(),"get",pwdDto, pwd);
    }
}
