//package com.gwm.one.user.controller;
//
//import RedisUtils;
//import SysUser;
//import SysUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.context.request.ServletWebRequest;
//
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.util.List;
//import java.util.Random;
//
//@RestController
//@RefreshScope  // 需要更新的配置类上加@RefreshScope注解，@RefreshScope必须加，否则客户端会收到服务端的更新消息，但是更新不了，因为不知道更新哪里的
//public class Test {
//
//    @Value("${spring.datasource.username}")
//    private String name;
//    @Autowired
//    private RedisUtils redisUtils;
//    @Autowired
//    private SysUserService sysUserService;
//    @GetMapping("/users-anon/test")
//    public String test() {
//        redisUtils.set("333", "3333");
//        String value = (String) redisUtils.get("333");
//        try {
//            List<SysUser> list = sysUserService.list();
//            list.forEach(System.out::println);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return value;
//
//    }
//
//    /**
//     * 生成图形验证码
//     * @param request
//     * @return
//     */
//    private ImageCode generate(ServletWebRequest request) {
//        int width = 64;
//        int height = 32;
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//
//        Graphics g = image.getGraphics();
//
//        Random random = new Random();
//
//        g.setColor(getRandColor(200, 250));
//        g.fillRect(0, 0, width, height);
//        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
//        g.setColor(getRandColor(160, 200));
//        for (int i = 0; i < 155; i++) {
//            int x = random.nextInt(width);
//            int y = random.nextInt(height);
//            int xl = random.nextInt(12);
//            int yl = random.nextInt(12);
//            g.drawLine(x, y, x + xl, y + yl);
//        }
//
//        String sRand = "";
//        for (int i = 0; i < 4; i++) {
//            String rand = String.valueOf(random.nextInt(10));
//            sRand += rand;
//            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
//            g.drawString(rand, 13 * i + 6, 16);
//        }
//
//        g.dispose();
//
//        return new ImageCode(image, sRand, 60);
//
//    }
//
//    /**
//     * 生成随机背景条纹
//     *
//     * @param fc
//     * @param bc
//     * @return
//     */
//    private Color getRandColor(int fc, int bc) {
//        Random random = new Random();
//        if (fc > 255) {
//            fc = 255;
//        }
//        if (bc > 255) {
//            bc = 255;
//        }
//        int r = fc + random.nextInt(bc - fc);
//        int g = fc + random.nextInt(bc - fc);
//        int b = fc + random.nextInt(bc - fc);
//        return new Color(r, g, b);
//    }
//}
//
