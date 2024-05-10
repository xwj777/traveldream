package com.travel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.mapper.HotelMapper;
import com.travel.pojo.Hotel;
import com.travel.service.HotelService;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.service.impl
 * @CreateTime: 2021-05-22 14:20
 * @Description: TODO
 */
@Service
public class HotelServiceImpl extends ServiceImpl<HotelMapper, Hotel> implements HotelService {
}
