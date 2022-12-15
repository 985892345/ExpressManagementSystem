package com.example.express.utils;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.express.entity.express.ExpressEntity;
import com.example.express.entity.user.UserEntity;
import com.example.express.mapper.user.UserMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
//使用该工具检查用户是否拥有管理员权限
public class CheckAdminUtil {
    private String token;
    private final UserMapper userMapper;
    public UserEntity check(){
        String username = TokenUtil.getUsernameByToken(token);
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UserEntity user = userMapper.selectOne(queryWrapper);
        return user;
    }
}
