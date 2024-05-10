package com.travel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.pojo.AdminUser;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.service
 * @CreateTime: 2021-05-17 14:56
 * @Description: TODO
 */
public interface AdminUserService extends IService<AdminUser> {

    AdminUser login(AdminUser adminUser);
}
