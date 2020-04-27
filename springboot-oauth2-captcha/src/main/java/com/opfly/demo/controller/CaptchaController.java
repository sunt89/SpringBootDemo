package com.opfly.demo.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.gimpy.FishEyeGimpyRenderer;

@RestController
@RequestMapping("api/captcha")
public class CaptchaController {
	private static int captchaExpires = 3*60; //超时时间3min
	private static int captchaW = 200;
	private static int captchaH = 60;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@GetMapping
	public void getCaptcha(HttpServletResponse response) throws IOException
	{
		//生成验证码
		String uuid = UUID.randomUUID().toString();
		Captcha captcha = new Captcha.Builder(captchaW, captchaH)
                .addText().addBackground(new GradiatedBackgroundProducer())
                .gimp(new FishEyeGimpyRenderer())
                .build();
		
		//将验证码以<key,value>形式缓存到redis
		stringRedisTemplate.opsForValue().set(uuid, captcha.getAnswer(), captchaExpires, TimeUnit.SECONDS);
		
		//将验证码key，及验证码的图片返回
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=\"captcha.png\"");
        response.setHeader("captcha_id", uuid);
        response.setContentType("image/png");
        //ByteArrayOutputStream bao = new ByteArrayOutputStream();
        OutputStream outputStream = response.getOutputStream();
		ImageIO.write(captcha.getImage(), "png", outputStream);
	}
}
