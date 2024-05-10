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
@TableName("t_cms_travel_route")
public class TravelRoute {

  @TableId(value = "id",type = IdType.UUID)
  private String id;

  private String addUserId;
  private String addTime;
  private long deleteStatus=0;
  private String modifyUserId;
  private String modifyTime;
  private String title;
  private String startSite;
  private String endSite;
  private String endTime;
  private String startTime;
  private long day;
  private String productCode;
  private double price;
  private long state;
  private String imgUrl;
  private String intro;

}
