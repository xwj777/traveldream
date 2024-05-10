package com.travel.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.pojo
 * @CreateTime: 2021-05-24 15:40
 * @Description: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderData {

    private Integer value;
    private String name;
    private Integer type;
}
