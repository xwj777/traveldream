package com.travel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.mapper.UserOrderMapper;
import com.travel.pojo.OrderData;
import com.travel.pojo.UserOrder;
import com.travel.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.service.impl
 * @CreateTime: 2021-05-21 14:37
 * @Description: TODO
 */
@Service
public class UserOrderServiceImpl extends ServiceImpl<UserOrderMapper, UserOrder> implements UserOrderService {

    @Resource
    UserOrderMapper userOrderMapper;

    @Override
    public List<OrderData> getOrderDatas() {
        return userOrderMapper.getOrderDatas();
    }
}
