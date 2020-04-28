package com.budbreak.pan.controller.rest.pan;

import com.budbreak.pan.service.pan.shiro.JwtUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 主要页面映射
 *
 * @author Sandeepin
 */
@RestController
public class IndexController {


    /**
     * 管理页面
     *
     * @return 页面
     */

    @GetMapping("/old")
    public ModelAndView admin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies!=null) {
            List<Cookie> collect = Arrays.stream(cookies).filter(cookie -> cookie.getName()
                    .equals("x-auth-token"))
                    .collect(Collectors.toList());
            String username = JwtUtils.getUsername(collect.get(0).getValue());
            ModelAndView modelAndView = new ModelAndView("old");
            modelAndView.addObject("author", username);
            return modelAndView;
        }
        return null;
    }

    @GetMapping("/")
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }
//    /**
//     * onlineplayer页面
//     *
//     * @return 页面
//     */
//    @GetMapping("/onlineplayer")
//    public ModelAndView onlineplayer(HttpServletRequest request, String fileName, String filePath) {
//        User user = (User) request.getSession().getAttribute("user");
//        String userName = (user==null)?"null":user.getUserName();
//        ModelAndView modelAndView = new ModelAndView("onlineplayer");
//        modelAndView.addObject("author", userName);
//        modelAndView.addObject("fileName", fileName);
//        modelAndView.addObject("filePath", filePath);
//        return modelAndView;
//    }

}
