package com.way.chat.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;

public class HttpUtil {
	public static void http(Map map,HttpServletResponse response ){
		Gson gson=new Gson();
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Cache-Control","no-cache");
        try {
			PrintWriter pw = response.getWriter();
			pw.print(gson.toJson(map).toString());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
