package com.travel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.mapper.AdminUserMapper;
import com.travel.pojo.AdminUser;
import com.travel.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.service.impl
 * @CreateTime: 2021-05-17 14:57
 * @Description: TODO
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper,AdminUser> implements AdminUserService {

    @Resource
    AdminUserMapper adminUserMapper;

    @Override
    public AdminUser login(AdminUser adminUser) {
        return adminUserMapper.login(adminUser);
    }
}
