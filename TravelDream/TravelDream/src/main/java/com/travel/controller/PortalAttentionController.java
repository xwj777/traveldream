package com.travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.controller
 * @CreateTime: 2021-05-24 11:14
 * @Description: TODO
 */
@Controller
public class PortalAttentionController {

    /**
     * 跳转注意事项页面
     * @param model
     * @return
     */
    @GetMapping("portal_attention")
    public String toattention(Model model){
        model.addAttribute("val","portal_attention");
        return "portal/attention";
    }
}
