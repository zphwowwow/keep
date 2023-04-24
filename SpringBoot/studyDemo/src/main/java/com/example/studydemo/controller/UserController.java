package com.example.studydemo.controller;

import com.example.studydemo.entity.User;
import com.example.studydemo.mapper.UserMapper;
import com.example.studydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> index(){
        return userMapper.findAll();
    }

    @PostMapping
    public Integer save(@RequestBody User user){
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Integer id){
        return userMapper.deleteById(id);
    }

    @GetMapping("/page")
    public Map<String,Object> page(@RequestParam Integer pageNum, @RequestParam Integer pageSize,@RequestParam String username){
        pageNum = (pageNum - 1) * pageSize;
        List<User> data = userMapper.selectPage(pageNum,pageSize,username);
        Integer total = userMapper.selectTotal(username);
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }
}
