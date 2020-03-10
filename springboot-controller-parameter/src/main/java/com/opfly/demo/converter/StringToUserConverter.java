package com.opfly.demo.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.opfly.demo.pojo.User;

@Component
public class StringToUserConverter implements Converter<String, User>{

	@Override
	public User convert(String value) {
		User user = new User();
		String[] strArr = value.split("-");
		String name = strArr[0];
		int age = Integer.parseInt(strArr[1]);
		double score = Double.parseDouble(strArr[2]);
		user.setName(name);
		user.setAge(age);
		user.setScore(score);
		return user;
	}
}
