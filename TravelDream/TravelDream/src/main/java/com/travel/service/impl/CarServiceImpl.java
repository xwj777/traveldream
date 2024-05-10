package com.travel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.mapper.CarMapper;
import com.travel.pojo.Car;
import com.travel.service.CarService;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.service.impl
 * @CreateTime: 2021-05-22 15:49
 * @Description: TODO
 */
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements CarService {
}
