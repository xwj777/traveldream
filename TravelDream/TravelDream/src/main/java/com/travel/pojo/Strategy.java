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
@TableName("t_cms_strategy")
public class Strategy {

  @TableId(value = "id",type = IdType.UUID)
  private String id;

  private String addUserId;
  private String addTime;
  private Long deleteStatus;
  private String modifyUserId;
  private String modifyTime;
  private String imgUrl;
  private String title;
  private Long rating;
  private String summary;
  private String introUrl;
  private Long state;
}
