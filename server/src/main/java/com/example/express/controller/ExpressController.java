package com.example.express.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.express.bean.ResponseBean;
import com.example.express.entity.express.ExpressEntity;
import com.example.express.entity.user.UserEntity;
import com.example.express.mapper.express.ExpressMapper;
import com.example.express.mapper.user.UserMapper;
import com.example.express.utils.TokenUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * .
 *
 * @author 985892345
 * 2022/12/12 15:10
 */
@RestController
@RequestMapping(value = "/express")
public class ExpressController {
  
  private final ExpressMapper expressMapper;
  private final UserMapper userMapper;
  
  public ExpressController(ExpressMapper expressMapper, UserMapper userMapper) {
    this.expressMapper = expressMapper;
    this.userMapper = userMapper;
  }
  
  @GetMapping("/all")
  public ResponseBean<Page<ExpressEntity>> getAll(
          @RequestParam int current,
          @RequestParam int size,
          @RequestParam(value = "Authorization") String token
  ) {
    // 这里正常情况需要检查 token，是否是管理员，但检查操作交给了拦截器
    //检查是否为管理员
    String username = TokenUtil.getUsernameByToken(token);
    QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("username", username);
    UserEntity user = userMapper.selectOne(queryWrapper);
    //如果是管理员
    Page<ExpressEntity> page = new Page<>(current,size);
    if(user.isAdmin()){
      Page<ExpressEntity> result = expressMapper.selectPage(page,null);
      return ResponseBean.success(result);
    }
    //不是管理员
    QueryWrapper<ExpressEntity> queryWrapper2 = new QueryWrapper<>();
    queryWrapper2.eq("user_id", user.getUserId());
    Page<ExpressEntity> result = expressMapper.selectPage(page,queryWrapper2);
    return ResponseBean.success(result);
  }
  
  @GetMapping("/find")
  public ResponseBean<ExpressEntity> find(
    @RequestParam int expressId,
    @RequestParam(value = "Authorization") String token
  ) {
    String username = TokenUtil.getUsernameByToken(token);
    //是否管理员
    QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("username", username);
    UserEntity user = userMapper.selectOne(queryWrapper);
    if (user.isAdmin()){
      return ResponseBean.success(expressMapper.selectById(expressId));
    }
    //不是管理员
    QueryWrapper<ExpressEntity> queryWrapper2 = new QueryWrapper<>();
    queryWrapper2.eq("user_id", user.getUserId());
    //queryWrapper2.eq("express_id", expressId);
    ExpressEntity expressEntity = expressMapper.selectById(expressId);
    //得到用户的全部快递
    List<ExpressEntity> expressEntities = expressMapper.selectList(queryWrapper2);
    //判断用户的输入的快递id是否在自己的快递列表里
    if(expressEntities.contains(expressEntity)){
      return ResponseBean.success(expressMapper.selectById(expressId));
    }
    else return ResponseBean.success(null);
  }

 /* @GetMapping("/get")
  //得到对应用户id的全部快递
  public ResponseBean<Page<ExpressEntity>> get(
          @RequestParam int userId,
          @RequestParam(value = "Authorization") String token
  ){
    String username = TokenUtil.getUsernameByToken(token);
    //是否管理员
    QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("username", username);
    UserEntity user = userMapper.selectOne(queryWrapper);
    if(user.isAdmin()){
      return
    }
  }*/

}
