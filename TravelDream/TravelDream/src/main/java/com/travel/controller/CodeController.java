package com.travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @BelongsProject: ssm2021
 * @BelongsPackage: com.ssm.web
 * @CreateTime: 2021-05-12 09:14
 * @Description: 负责生成一张随机的验证码
 */
@Controller
public class CodeController {

    /**
     * 生成随机的验证码
     */
    @RequestMapping("/createImage")
    public void createImage(HttpServletResponse response, HttpSession session) {
        try {
            int height = 38;
            int width = 120;

            //准备一个绘图工具类 画布  RGB( Red  Green  Blue 三原色)
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //画笔
            Graphics g = bufferedImage.getGraphics();
            //把画布填充颜色 白色填充矩形
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);

            //随机生成字符串
            String words = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789";
            char[] chars = words.toCharArray();
            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= 4; i++) {
                //随机一个下标
                int index = random.nextInt(chars.length);
                char c = chars[index];
                sb.append(c);
                Font font = new Font("Arial", Font.BOLD, 25);
                //随机颜色
                int red = random.nextInt(256);
                int green = random.nextInt(256);
                int blue = random.nextInt(256);
                Color color = new Color(red, green, blue);
                //绘制验证码
                g.setColor(color);
                g.setFont(font);
                g.drawString(String.valueOf(c), (width / 5) * i, 30);
            }

            //绘制验证干扰线
            for (int i = 1; i <= 10; i++) {
                //随机颜色
                int red = random.nextInt(256);
                int green = random.nextInt(256);
                int blue = random.nextInt(256);
                Color color = new Color(red, green, blue);

                g.setColor(color);
                int x1 = random.nextInt(width);
                int y1 = random.nextInt(height);
                int x2 = random.nextInt(width);
                int y2 = random.nextInt(height);
                g.drawLine(x1, y1, x2, y2);

            }

            System.out.println("系统正确随机的验证码是:" + sb.toString());
            session.setAttribute("sysCode",sb.toString());

            //最后输出图片
            response.setContentType("image/jpg");//浏览器的响应类型是图片
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
