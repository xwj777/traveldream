package com.travel.test;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.App;
import com.travel.pojo.UserMessage;
import com.travel.service.UserMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.test
 * @CreateTime: 2021-05-21 10:25
 * @Description: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TestMyBatis1 {

    @Autowired
    UserMessageService userMessageService;

    @Test
    public void test1(){
        IPage<UserMessage> page = userMessageService.page(new Page<>(1, 4));
        List<UserMessage> records = page.getRecords();
        for (UserMessage userMessage : records) {
            System.out.println(userMessage.getReplyContent());
        }
    }
}
