package com.travel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.pojo.OrderData;
import com.travel.pojo.UserOrder;

import java.util.List;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.service
 * @CreateTime: 2021-05-21 14:37
 * @Description: TODO
 */
public interface UserOrderService extends IService<UserOrder> {

    List<OrderData> getOrderDatas();
}
