package com.travel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.mapper.InsuranceMapper;
import com.travel.pojo.Insurance;
import com.travel.service.InsuranceService;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.service.impl
 * @CreateTime: 2021-05-24 09:23
 * @Description: TODO
 */
@Service
public class InsuranceServiceImpl extends ServiceImpl<InsuranceMapper, Insurance> implements InsuranceService {
}
