package com.budbreak.pan.service;


import com.budbreak.pan.service.pan.shiro.JwtUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sandeepin
 * 2018/2/12 0012
 */
public class WebUtil {

    public static String getUserNameByRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null && cookies.length > 0 ) {
            List<Cookie> collect = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("x-auth-token")).collect(Collectors.toList());
            if (collect.size()>0) {
                token = collect.get(0).getValue();
            }
        }
        return JwtUtils.getUsername(token);
    }
}
