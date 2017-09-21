package cn.edu.szu.bigdata.controller;

import cn.edu.szu.bigdata.model.User;
import org.apache.catalina.Session;
import org.apache.ibatis.jdbc.Null;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by yangqj on 2017/4/21.
 */
@Controller
public class HomeController {
    @RequestMapping(value="/login",method= RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value="/login",method= RequestMethod.POST)
    public String login(HttpServletRequest request, User user, Model model){
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            request.setAttribute("msg", "用户名或密码不能为空！");
            return "login";
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try {
            subject.login(token);
            return "redirect:/index";
        }catch (LockedAccountException lae) {
            token.clear();
            request.setAttribute("msg", "用户已经被锁定不能登录，请与管理员联系！");
            return "login";
        } catch (AuthenticationException e) {
            token.clear();
            request.setAttribute("msg", "用户或密码不正确！");
            return "login";
        }
    }
    @RequestMapping(value={"/admin/usersPage","admin"})
    public String usersPage(){
        return "user/users";
    }

    @RequestMapping("/admin/rolesPage")
    public String rolesPage(){
        return "role/roles";
    }

    @RequestMapping("/admin/resourcesPage")
    public String resourcesPage(){
        return "resources/resources";
    }

    @RequestMapping("/403")
    public String forbidden(){
        return "403";
    }

    @RequestMapping(value = {"/index",""})
    public String index(HttpServletRequest request,Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        return "index";
    }

    @RequestMapping("/huanping_input")
    public String huanping_input(HttpServletRequest request,Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        return "huanping_input";
    }

    @RequestMapping("/admin/myuser")
    public String myuser(HttpServletRequest request,Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        return "admin/user";
    }

    @RequestMapping("/admin/myrole")
    public String myrole(HttpServletRequest request,Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        return "admin/role";
    }

    @RequestMapping("/admin/myresource")
    public String myresource(HttpServletRequest request,Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        return "admin/resource";
    }
}
