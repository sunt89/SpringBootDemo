package com.opfly.demo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.opfly.demo.annotation.Password;

public class PasswordValidator implements ConstraintValidator<Password, Object>{

	private final static String SPECIAL_CHAR = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return checkContainSpecialChar(value.toString());
	}

	 /**
     * @brief   检测密码中是否包含特殊符号
     * @param[in] password 密码字符串
     * @return  包含特殊符号 返回true
     */
    private boolean checkContainSpecialChar(String password) {
        char[] chPass = password.toCharArray();
        for (int i = 0; i < chPass.length; i++) {
            if (SPECIAL_CHAR.indexOf(chPass[i]) != -1) {
                return true;
            }
        }
        return false;
    }
}
