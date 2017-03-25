package com.example.beautyphone.network;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface MyBeanTag {
	public String tag();
	public String description();
}
