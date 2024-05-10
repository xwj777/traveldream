package com.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.pojo.OrderData;
import com.travel.pojo.UserOrder;

import java.util.List;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.mapper
 * @CreateTime: 2021-05-21 14:36
 * @Description: TODO
 */
public interface UserOrderMapper extends BaseMapper<UserOrder> {

    List<OrderData> getOrderDatas();
}
