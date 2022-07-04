package com.example.controller;

import com.example.common.R;
import com.example.service.UserService;
import com.example.status.UserStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 增
     *
     * @return
     */
    @GetMapping
    public R save() {
        System.out.println(UserStatus.DELETE.ordinal());
//        boolean b = userService.save(user);
        return R.success("查询成功", null);
    }

}
