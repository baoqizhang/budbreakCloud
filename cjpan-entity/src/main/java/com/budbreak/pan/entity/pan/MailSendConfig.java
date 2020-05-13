package com.budbreak.pan.entity.pan;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author baoqi
 * @date 2020/4/7 10:42
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MailSendConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 收件人邮箱*/
    private String sendMail;

    /** 收件人用户名*/
    private String userName;

    /** 邮箱内容*/
    private String content;

    /** 邮件主题*/
    private String topic;

    /** 是否html格式 */
    private boolean isHtml;

}
