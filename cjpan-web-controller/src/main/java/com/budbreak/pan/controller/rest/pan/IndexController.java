package com.budbreak.pan.controller.rest.pan;

import com.budbreak.pan.common.EncryptUtil;
import com.budbreak.pan.mapper.pan.UserMapper;
import com.budbreak.pan.service.WebUtil;
import com.budbreak.pan.service.pan.shiro.JwtUtils;
import com.budbreak.pan.vo.pan.UserVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${key}")
    private String key;

    @Autowired
    private UserMapper userMapper;

    @ApiOperation("定位到分享页面")
    @RequestMapping("/share")
    public ModelAndView share(String link, HttpServletRequest request) {
        EncryptUtil des = null;
        String linkDecoder = "";
        try {
            des = new EncryptUtil(key, "utf-8");
            linkDecoder = des.decode(link);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView("share");
        modelAndView.addObject("link", link);
        modelAndView.addObject("linkDecoder", linkDecoder);
        String userName = WebUtil.getUserNameByRequest(request);
        if (userName == null){
            userName = "游客";
        }
        modelAndView.addObject("author", userName);
        return modelAndView;
    }

    @ApiOperation("定位到管理页面")
    @GetMapping("/old")
    public ModelAndView admin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            List<Cookie> collect = Arrays.stream(cookies).filter(cookie -> cookie.getName()
                    .equals("x-auth-token"))
                    .collect(Collectors.toList());
            String username = "";
            if (collect.size() > 0) {
                username = JwtUtils.getUsername(collect.get(0).getValue());
            }
            UserVO userVO = userMapper.selectUserByUserName(username);
            if (userVO != null && userVO.getLevel().equals("0")) {
                ModelAndView modelAndView = new ModelAndView("old");
                modelAndView.addObject("author", username);
                return modelAndView;
            } else if (userVO != null && userVO.getLevel().equals("1")){
                ModelAndView modelAndView = new ModelAndView("manager");
                modelAndView.addObject("author", username);
                return modelAndView;
            }else {
                ModelAndView modelAndView = new ModelAndView("errorPage");
                return modelAndView;
            }
        }
        return null;
    }

    @ApiOperation("定位到首页")
    @GetMapping("/")
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }

//    @ApiOperation("定位到管理页面")
//    @GetMapping("/manager")
////    @RequiresPermissions("1")
//    public ModelAndView manager() {
//        ModelAndView modelAndView = new ModelAndView("manager");
//        return modelAndView;
//    }

    @ApiOperation("定位到播放页面")
    @GetMapping("/onlineplayer")
    public ModelAndView onlineplayer(HttpServletRequest request, String fileName, String filePath) {
        String userNameByRequest = WebUtil.getUserNameByRequest(request);
        String userName = (userNameByRequest == null) ? "null" : userNameByRequest;
        ModelAndView modelAndView = new ModelAndView("onlineplayer");
        modelAndView.addObject("author", userName);
        modelAndView.addObject("fileName", fileName);
        modelAndView.addObject("filePath", filePath);
        return modelAndView;
    }


    @RequestMapping("/errorPage")
    public ModelAndView errorPage(String message) {
        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject("message", message);
        return modelAndView;

    }


}
