<%@ page language="java" pageEncoding="gbk"%>
<%@ page import="java.util.*" %>
<%@page import="com.blisscloud.util.StringUtil"%>
<%@page import="com.blisscloud.util.EZDbUtil"%>
<%
      	//1.获取productId
      	String productId = request.getParameter("productId");
      	//System.out.println("222222--->"+productId);
      	if(StringUtil.isNullOrEmpty(productId))
      	{
      		out.println("-1");
      		return ;
      	}
      	//2.根据获取的productId,得到其小结路径
      	String myProductName = EZDbUtil.getProductPathById(productId);
        //3.将查询到的小结路径信息输出
       	String outVal = "";
        if(!StringUtil.isNullOrEmpty(myProductName))outVal = myProductName;
       	out.println(outVal);
       	//System.out.println(outVal);
%>