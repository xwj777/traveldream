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
@TableName("t_cms_insurance")
public class Insurance {

  @TableId(value = "id",type = IdType.UUID)
  private String id;

  private String addUserId;
  private String addTime;
  private Long deleteStatus;
  private String modifyUserId;
  private String modifyTime;
  private String title;
  private Long insuranceCompany;
  private double price;
  private Long type;
  private String resume;
  private Long state;
  private String imgUrl;

}
