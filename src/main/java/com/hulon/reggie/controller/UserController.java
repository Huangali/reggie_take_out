package com.hulon.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hulon.reggie.common.R;
import com.hulon.reggie.entity.User;
import com.hulon.reggie.service.UserService;
import com.hulon.reggie.utils.SMSUtils;
import com.hulon.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author Hulon
 * @date 2023/5/27
 * @className UserController
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 发送验证码
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user,
                             HttpSession session){
        //获取手机号
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)){
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);

            //调用阿里云提供短信API完成发送验证码
            //SMSUtils.sendMessage("签名","模板代码",phone,code);
            //需要将生成的验证码保存到Session
            session.setAttribute(phone,code);

            return R.success("手机验证码发送成功");

        }

        return R.error("手机验证码发送失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map,
                           HttpSession session){
        log.info(map.toString());

        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();
        //从Session中获取验证码
        String code2 = session.getAttribute(phone).toString();
        //进行验证码比对（页面提交的验证码和Session中保存的验证码）
        if (code2 != null && code.equals(code2)){
            //如果比对成功，登陆成功
            LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
            lqw.eq(User::getPhone,phone);
            User one = userService.getOne(lqw);

            if (one == null){
                //判断当前是手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                one = new User();
                one.setPhone(phone);
                one.setStatus(1);
                userService.save(one);
            }
            session.setAttribute("user",one.getId());

            return R.success(one);
        }



        return R.error("登录失败");

    }



}
