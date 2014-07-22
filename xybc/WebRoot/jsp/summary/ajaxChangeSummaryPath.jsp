<%@ page language="java" pageEncoding="gbk"%>
<%@ page import="java.util.*" %>
<%@page import="com.blisscloud.util.StringUtil"%>
<%@page import="com.blisscloud.util.EZDbUtil"%>
<%
      	//1.获取summaryId
      	String summaryId = request.getParameter("summaryId");
      	//System.out.println("222222--->"+summaryId);
      	if(StringUtil.isNullOrEmpty(summaryId))
      	{
      		out.println("-1");
      		return ;
      	}
      	//2.根据获取的summaryId,得到其小结路径
      	String mySummPath = EZDbUtil.getSumPathById(summaryId);
        //3.将查询到的小结路径信息输出
       	String outVal = "";
        if(!StringUtil.isNullOrEmpty(mySummPath))outVal = mySummPath;
       	out.println(outVal);
       	//System.out.println(outVal);
%>