package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.service.PersonServiceImpl;
import com.example.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PersonServiceImpl personService;

   /* @Autowired
    private UserServiceImpl userService;
    @RequestMapping(value = "/userLogin/submit", method = RequestMethod.POST)
    public String userLogin(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (userService.findByNameAndPassword(username, password) == null) {
            return "error";
        }
        return "success";
    }
    @RequestMapping(value = "/userRegister/submit", method = RequestMethod.POST)
    public String userRegister(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User u = new User(username, password);
        return userService.create(u);
    }*/
    @RequestMapping("/")
    public String index(){
        return  "index.html";
    }

    @RequestMapping(value = "/login")
    public ModelAndView login(){
        return new ModelAndView("/login.html");
    }
    @PostMapping(value = "/loginn")
    @ResponseBody
    public String login(String username,String password){
        System.out.println(username);
        User user=userService.findByNameAndPassword(username,password);

        if(user.getUsername()==null||user.getPassword()==null){
            return "error";
        }else {
            return "success";
        }
    }
    @RequestMapping(value = "/registry")
//    @ResponseBody
   public ModelAndView register(){
        return new ModelAndView("/registry.html");
    }
    @PostMapping(value = "/registrys")
    @ResponseBody
    public String  register(String username,String password,String id){
        System.out.println("duan dian 1");
        User user =userService.findByName(username);
        System.out.println(user);
        if(user.getUsername() == null){
            personService.register(id);
            userService.insertUser(username,password);
            return "Y";
        }
        return "N";
    }
    //去注册页面
  /*  @GetMapping("/register")
    public String toRegister(){
        return "register";
    }*/

    @RequestMapping("/demo")
    public String demo1(){return "/demo.html";}

    //去登陆页面
//    @GetMapping("/logins")





















































//    public String toLogin(){
//        return "/login";
//    }

}
