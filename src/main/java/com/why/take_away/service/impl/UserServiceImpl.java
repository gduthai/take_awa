package com.why.take_away.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.take_away.entitty.User;
import com.why.take_away.mapper.UserMapper;
import com.why.take_away.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
