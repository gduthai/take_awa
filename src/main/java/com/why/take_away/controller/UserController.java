package com.why.take_away.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.why.take_away.common.R;
import com.why.take_away.entitty.User;
import com.why.take_away.service.UserService;
import com.why.take_away.util.SMSUtils;
import com.why.take_away.util.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * send phone msg
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //get ph num
        String phone = user.getPhone();
        if(StringUtils.isNotEmpty(phone)){
            //generate 4 random number
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);
            //diaoyong aliyun msg
            //SMSUtils.sendMessage("why外卖平台","",phone,code);

            //send generated msg to Session

            //将生成的验证吗返回到redis
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);

            return R.success("手机验证码发送成功");
        }
        return R.error("短信发送失败失败");



    }

    /**
     * 移动端用户登录
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        log.info("map={}",map.toString());
        //get number
        String phone = map.get("phone").toString();

        //get msg
        String code = map.get("code").toString();

        //get msg from session
        //Object codeInSession = session.getAttribute(phone);
        //从redis中获取缓存的验证码
        Object codeInSession = redisTemplate.opsForValue().get(phone);

        //conpair msg
        if(codeInSession != null && codeInSession.equals(code)){
            //chengli


            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);

            User user=userService.getOne(queryWrapper);
            if (user==null){
                //if new user
                user=new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);

            }
            session.setAttribute("user",user.getId());
            //如果用户登录成功，
            redisTemplate.delete(phone);
            return R.success(user);
        }
        return R.error("登录失败");
    }
}
