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
import java.util.Objects;

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
  
  //查找所有快递
  @GetMapping("/all")
  public ResponseBean<Page<ExpressEntity>> getAll(
    @RequestParam int current,
    @RequestParam int size,
    @RequestHeader(value = "Authorization") String token
  ) {
    // 这里正常情况需要检查 token，是否是管理员，但检查操作交给了拦截器
    //检查是否为管理员
    UserEntity user = CheckAdminUtil.check(token, userMapper);
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
    ExpressEntity expressEntity = expressMapper.selectById(expressId);
    if (expressEntity == null) {
      return ResponseBean.error(10020, "未找到该快递");
    }
    UserEntity user = CheckAdminUtil.check(token, userMapper);//为检查是否管理员做准备
    if (user.isAdmin()) {
      return ResponseBean.success(expressEntity);
    }
    //不是管理员
    if (Objects.equals(expressEntity.getSentId(), user.getUserId())
      || Objects.equals(expressEntity.getReceiveId(), user.getUserId())
    ) {
      return ResponseBean.success(expressEntity);
    } else {
      return ResponseBean.error(10010, "权限不足");
    }
  }
  
  //删除指定的数据
  @PostMapping("/delete")
  public ResponseBean<String> delete(
    @RequestParam int expressId,
    @RequestHeader(value = "Authorization") String token
  ) {
    UserEntity user = CheckAdminUtil.check(token, userMapper);//为检查是否管理员做准备
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
    @RequestBody ExpressEntity expressEntity,
    @RequestHeader(value = "Authorization") String token
  ) {
    UserEntity user = CheckAdminUtil.check(token, userMapper);//为检查是否管理员做准备
    if (user.isAdmin()) {
      QueryWrapper<ExpressEntity> queryWrapper2 = new QueryWrapper<>();
      queryWrapper2.eq("express_id", expressEntity.getExpressId());
      int update = expressMapper.update(expressEntity, queryWrapper2);//逗号前四传入的实体
      if (update == 0) {
        return ResponseBean.error(10020, "该数据不存在");
      }
      return ResponseBean.success("修改成功");
    }
    return ResponseBean.error(10010, "权限不足，修改失败");
  }
  
  //添加快递信息
  @PostMapping("/add")
  public ResponseBean<String> add(
    @RequestBody ExpressEntity expressEntity,
    @RequestHeader(value = "Authorization") String token
  ) {
    UserEntity user = CheckAdminUtil.check(token, userMapper);//为检查是否管理员做准备
    if (user.isAdmin()) {
      ExpressEntity expressEntity1 = expressMapper.selectById(expressEntity.getExpressId());
      if (expressEntity1 != null) {
        return ResponseBean.error(10030, "该条记录已存在");
      }
      expressMapper.insert(expressEntity);//快递实体
      return ResponseBean.success("增加信息成功");
    }
    return ResponseBean.error(10010, "权限不足，增加信息失败");
  }
  
}
