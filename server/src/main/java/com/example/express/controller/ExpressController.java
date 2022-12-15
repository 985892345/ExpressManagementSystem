package com.example.express.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.express.bean.ResponseBean;
import com.example.express.entity.express.ExpressEntity;
import com.example.express.entity.user.UserEntity;
import com.example.express.mapper.express.ExpressMapper;
import com.example.express.mapper.user.UserMapper;
import com.example.express.utils.CheckAdminUtil;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *
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
  
  //查找所有快递
  @GetMapping("/all")
  public ResponseBean<Page<ExpressEntity>> getAll(
    @RequestParam int current,
    @RequestParam int size,
    @RequestHeader(value = "Authorization") String token
  ) {
    // 这里正常情况需要检查 token，是否是管理员，但检查操作交给了拦截器
    //检查是否为管理员
    UserEntity user = new CheckAdminUtil(token, userMapper).check();
    //如果是管理员
    Page<ExpressEntity> page = new Page<>(current, size);
    if (user.isAdmin()) {
      Page<ExpressEntity> result = expressMapper.selectPage(page, null);
      return ResponseBean.success(result);
    }
    //不是管理员
    QueryWrapper<ExpressEntity> queryWrapper2 = new QueryWrapper<>();
    queryWrapper2.eq("receive_id", user.getUserId())
      .or().eq("sent_id", user.getUserId());
    Page<ExpressEntity> result = expressMapper.selectPage(page, queryWrapper2);
    return ResponseBean.success(result);
  }
  
  //查找单个快递
  @GetMapping("/find")
  public ResponseBean<ExpressEntity> find(
    @RequestParam int expressId,
    @RequestHeader(value = "Authorization") String token
  ) {
    UserEntity user = new CheckAdminUtil(token, userMapper).check();//为检查是否管理员做准备
    if (user.isAdmin()) {
      return ResponseBean.success(expressMapper.selectById(expressId));
    }
    //不是管理员
    QueryWrapper<ExpressEntity> queryWrapper2 = new QueryWrapper<>();
    queryWrapper2.eq("receive_id", user.getUserId());
    //queryWrapper2.eq("express_id", expressId);
    ExpressEntity expressEntity = expressMapper.selectById(expressId);
    //得到用户的全部快递
    List<ExpressEntity> expressEntities = expressMapper.selectList(queryWrapper2);
    //判断用户的输入的快递id是否在自己的快递列表里
    if (expressEntities.contains(expressEntity)) {
      return ResponseBean.success(expressMapper.selectById(expressId));
    } else return ResponseBean.success(null);
  }
  
  @GetMapping("/get")
  //得到对应用户id的全部快递
  public ResponseBean<Page<ExpressEntity>> get(
    @RequestParam int current,
    @RequestParam int size,
    @RequestParam int userId,
    @RequestHeader(value = "Authorization") String token
  ) {
    UserEntity user = new CheckAdminUtil(token, userMapper).check();//为检查是否管理员做准备
    if (user.isAdmin()) {
      QueryWrapper<ExpressEntity> queryWrapper2 = new QueryWrapper<>();
      queryWrapper2.eq("receive_id", userId);//得到与收件人id匹配的快递
      Page<ExpressEntity> page = new Page<>(current, size);
      Page<ExpressEntity> result = expressMapper.selectPage(page, queryWrapper2);
      return ResponseBean.success(result);
    }
    return ResponseBean.error(10010, "权限错误");
  }
  
  //删除指定的数据
  @PostMapping("/delete")
  public ResponseBean<String> delete(
    @RequestParam int expressId,
    @RequestHeader(value = "Authorization") String token
  ) {
    UserEntity user = new CheckAdminUtil(token, userMapper).check();//为检查是否管理员做准备
    if (user.isAdmin()) {
      QueryWrapper<ExpressEntity> queryWrapper2 = new QueryWrapper<>();
      queryWrapper2.eq("express_id", expressId);
      int delete = expressMapper.delete(queryWrapper2);
      return ResponseBean.success("成功删除" + delete + "条数据");
    }
    return ResponseBean.error(10010, "权限不足，删除失败");
  }
  
  //更新快递信息
  @PostMapping("/update")
  public ResponseBean<String> update(
    @RequestBody ExpressEntity express,
    @RequestHeader(value = "Authorization") String token
  ) {
    UserEntity user = new CheckAdminUtil(token, userMapper).check();//为检查是否管理员做准备
    if (user.isAdmin()) {
      QueryWrapper<ExpressEntity> queryWrapper2 = new QueryWrapper<>();
      queryWrapper2.eq("express_id", express.getExpressId());
      int update = expressMapper.update(express, queryWrapper2);//逗号前四传入的实体
      if (update == 0) {
        return ResponseBean.success("该数据不存在");
      }
      return ResponseBean.success("修改成功");
    }
    return ResponseBean.success("权限不足，修改失败");
  }
  
  //添加快递信息
  @PostMapping("/add")
  public ResponseBean<String> add(
    @RequestBody ExpressEntity express,
    @RequestHeader(value = "Authorization") String token
  ) {
    UserEntity user = new CheckAdminUtil(token, userMapper).check();//为检查是否管理员做准备
    if (user.isAdmin()) {
      ExpressEntity expressEntity1 = expressMapper.selectById(express.getExpressId());
      if (expressEntity1 != null) {
        return ResponseBean.error(10020, "该条记录已存在");
      }
      expressMapper.insert(express);//快递实体
      return ResponseBean.success("增加信息成功");
    }
    return ResponseBean.error(10010, "权限不足，增加信息失败");
  }
}
