package com.travel.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.mapper.UserMessageMapper;
import com.travel.pojo.UserMessage;
import com.travel.service.UserMessageService;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.service.impl
 * @CreateTime: 2021-05-20 15:44
 * @Description: TODO
 */
@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage> implements UserMessageService {
}
