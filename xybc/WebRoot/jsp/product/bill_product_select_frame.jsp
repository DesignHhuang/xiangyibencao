<%@ page contentType="text/html;charset=GBK" %>
<%@ page errorPage="/sys_error.jsp" %>
<%@page import="com.blisscloud.util.*"%>
<%
	String billID = request.getParameter("billID");//工单ID
	
	if(StringUtil.isNullOrEmpty(billID))billID="-1";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>工单产品选择</title>
</head>
	<frameset cols="*" rows="45%,55%" frameborder="yes" border="1" framespacing="1" bordercolor="gray">
	  <frame src="product_view.jsp?billID=<%=billID%>" name="productViewFrame" scrolling="no" id="productViewFrame" title="productViewFrame" frameborder="0"/>
	  <frameset cols="75%,25%" rows="*" framespacing="1" frameborder="yes" border="1" bordercolor="gray">
	    <frame src="product_select.jsp" name="productSelectFrame" scrolling="no" id="productSelectFrame" title="productSelectFrame" frameborder="0"/>
	    <frame src="add_product_info.jsp?billID=<%=billID%>" name="addProductToBillFrame" id="addProductToBillFrame" title="addProductToBillFrame" frameborder="0"/>
	  </frameset>
	</frameset>
</html>
