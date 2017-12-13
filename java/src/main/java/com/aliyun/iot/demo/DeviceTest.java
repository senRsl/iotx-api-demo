/** 
 * Filename:    DeviceTest.java 
 * Description:  
 * Copyright:   Copyright (c)2017 
 * Company:     VCYBER 
 * @author:     senRsl senRsl@163.com 
 * @version:    1.0 
 * Create at:   2017年12月13日 下午3:45:10 
 * 
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2017年12月13日   senRsl      1.0         1.0 Version 
 */  
package com.aliyun.iot.demo;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.iot.model.v20170620.PubRequest;
import com.aliyuncs.iot.model.v20170620.PubResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * @author senrsl
 *
 */
public class DeviceTest {
	
	public static void main(String[] args) {
		try {
			sendMsg();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void sendMsg() throws Exception{
		//初始化
		String accessKey = "LTAIzzLTPglXc0Z7";
		String accessSecret = "tJCecds4N0Pjm1yNUfhC8kZOEFcExs";
		DefaultProfile.addEndpoint("cn-shanghai", "cn-shanghai", "Iot", "iot.cn-shanghai.aliyuncs.com");
		IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", accessKey, accessSecret);
		DefaultAcsClient client = new DefaultAcsClient(profile); //初始化SDK客户端
		
		//发消息
		Runnable sendRunnable = new Runnable() {
			
			@Override
			public void run() {
				try {
					PubRequest request = new PubRequest();
					request.setProductKey("5KsxxwNYH3t");
					request.setMessageContent(Base64.encodeBase64String("hello world".getBytes()));
					request.setTopicFullName("/5KsxxwNYH3t/VCYBER002/get");
					request.setQos(0); //目前支持QoS0和QoS1
					PubResponse response = client.getAcsResponse(request);
					System.out.println(response.getSuccess()+"\t\t\t\t"+response.getErrorMessage());
				} catch (ClientException e) {
					e.printStackTrace();
				}
			}
		};
		
		ScheduledExecutorService sendSch = Executors  
                .newSingleThreadScheduledExecutor();  
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
		sendSch.scheduleAtFixedRate(sendRunnable, 2, 3, TimeUnit.SECONDS);  
	}

}
