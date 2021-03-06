package com.artbrain.util;

import javax.servlet.http.HttpServletRequest;

public class GetRealIp {
	public static String getIpAddr(HttpServletRequest request) {
		String fromSource = "X-Real-IP";
	    String ip = request.getHeader("X-Real-IP");
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("X-Forwarded-For");  
	        fromSource = "X-Forwarded-For";  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	        fromSource = "Proxy-Client-IP";  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	        fromSource = "WL-Proxy-Client-IP";  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	        fromSource = "request.getRemoteAddr";  
	    }  
	    System.out.println("App Client IP: "+ip+", fromSource: "+fromSource);
	    return ip;  

	}
}
