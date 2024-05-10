package com.travel.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.utils
 * @CreateTime: 2021-05-19 15:15
 * @Description: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KindEditorResult {

    private int error;
    private String url;
    private String message;
}
