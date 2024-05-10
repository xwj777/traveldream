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
@TableName("t_cms_car")
public class Car {

  @TableId(value = "id",type = IdType.UUID)
  private String id;
  private String addUserId;
  private String addTime;
  private Long deleteStatus;
  private String modifyUserId;
  private String modifyTime;
  private String title;
  private String startPlace;
  private String endPlace;
  private String startDateAndTime;
  private Double needTime;
  private String gatherPlace;
  private Long type;
  private String imgUrl;
  private Long state;
  private String remark;
  private Double price;

}
