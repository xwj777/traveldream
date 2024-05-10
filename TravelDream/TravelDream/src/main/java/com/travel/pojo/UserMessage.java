package com.travel.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_cms_message")
public class UserMessage {

  @TableId(value = "id",type = IdType.UUID)
  private String id;

  private String addUserId;
  private String addTime;
  private long deleteStatus;
  private String modifyUserId;
  private String modifyTime;
  private String userId;
  private String userName;
  private String name;
  private String title;
  private String content;
  private String replyContent; //留言回复
  private long state;
}
