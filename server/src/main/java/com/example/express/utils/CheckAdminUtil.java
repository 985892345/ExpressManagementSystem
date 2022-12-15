package com.example.express.utils;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.express.entity.express.ExpressEntity;
import com.example.express.entity.user.UserEntity;
import com.example.express.mapper.user.UserMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
//使用该工具检查用户是否拥有管理员权限
public class CheckAdminUtil {
    public static UserEntity check(String token, UserMapper userMapper){
        String username = TokenUtil.getUsernameByToken(token);
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }
}
