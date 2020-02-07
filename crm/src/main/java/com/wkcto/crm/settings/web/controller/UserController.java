package com.wkcto.crm.settings.web.controller;

import com.wkcto.crm.exception.LoginException;
import com.wkcto.crm.settings.domain.User;
import com.wkcto.crm.settings.service.UserService;
import com.wkcto.crm.utils.HandleFlag;
import com.wkcto.crm.utils.MD5Util;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 动力节点7777
 */
@Controller
@RequestMapping("/settings/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/login.do")
    @ResponseBody
    public Map<String,Object> login(String loginAct, String loginPwd, String flag, HttpServletRequest request, HttpServletResponse response) throws LoginException {
        loginPwd = MD5Util.getMD5(loginPwd);
        String ip = request.getRemoteAddr();
        User user = userService.login(loginAct, loginPwd, ip);
        request.getSession().setAttribute("user", user);
        if ("a".equals(flag)){
            Cookie cookie1=new Cookie("loginAct", loginAct);
            Cookie cookie2=new Cookie("loginPwd", loginPwd);

            cookie1.setPath("/");
            cookie2.setPath("/");

            cookie1.setMaxAge(60*60*24*10);
            cookie2.setMaxAge(60*60*24*10);

            response.addCookie(cookie1);
            response.addCookie(cookie2);
        }
        return HandleFlag.successTrue();
    }
    @RequestMapping("/toLogin.do")
    public String toLogin(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String loginAct=null;
        String loginPwd=null;
        if (cookies!=null&&cookies.length>0){
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if ("loginAct".equals(name)){
                    loginAct = cookie.getValue();
                    continue;
                }
                if ("loginPwd".equals(name)){
                    loginPwd=cookie.getValue();
                }
            }
            if (loginAct!=null&&loginPwd!=null){
                String ip = request.getRemoteAddr();
                try {
                    User user = userService.login(loginAct, loginPwd, ip);
                    request.getSession().setAttribute("user", user);
                    return "redirect:/workbench/toWorkbenchIndex.do";
                } catch (LoginException e) {
                    e.printStackTrace();
                    return "login";
                }
            }
        }
        return "login";
    }
    @RequestMapping("/loginout.do")
    public String loginOut(HttpSession session,HttpServletResponse response){
        session.invalidate();
        Cookie cookie1=new Cookie("loginAct",null );
        Cookie cookie2=new Cookie("loginPwd",null );
        response.addCookie(cookie1);
        response.addCookie(cookie2);
        return "login";
    }

}
