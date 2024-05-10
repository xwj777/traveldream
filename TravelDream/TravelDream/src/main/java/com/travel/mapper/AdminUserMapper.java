package com.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.pojo.AdminUser;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.mapper
 * @CreateTime: 2021-05-17 14:51
 * @Description: TODO
 */
public interface AdminUserMapper extends BaseMapper<AdminUser> {

    AdminUser login(AdminUser adminUser);
}
