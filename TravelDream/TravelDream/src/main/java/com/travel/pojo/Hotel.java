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
@TableName("t_cms_hotel")
public class Hotel {

  @TableId(value = "id",type = IdType.UUID)
  private String id;
  private String addUserId;
  private String addTime;
  private Long deleteStatus;
  private String modifyUserId;
  private String modifyTime;
  private String hotelName;
  private String hotelIntro;
  private Long hotelStar;
  private String linkPhone;
  private String address;
  private Long state;
  private String imgUrl;
  private Double price;

}
