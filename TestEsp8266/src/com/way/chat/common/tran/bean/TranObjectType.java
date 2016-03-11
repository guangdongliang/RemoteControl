package com.way.chat.common.tran.bean;

/**
 * 传输对象类型
 * 
 * @author way
 * 
 */
public enum TranObjectType {
	MOTOR, //电机
	GREEN, // 绿灯
	RED, // 红灯
	DIODE, // 二极管
	BUZZER, // 蜂鸣器
	LOGININ,//登陆
	REFRESH,//刷新
	LOGOUT,//登出
	REGISTER,//注册
	DOSUCCESS,//成功执行
}
