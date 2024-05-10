package com.travel.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: SpringBoot-001
 * @BelongsPackage: com.bruce.utils
 * @CreateTime: 2021-05-14 15:51
 * @Description: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult {

    private Integer code;
    private String message;
    private Object data;

    public CommonResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
