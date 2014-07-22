<%@ page contentType="text/html;charset=GBK" %>
<%@page import="java.util.*"%>
<%@page import="com.blisscloud.util.*"%>
<%
    String rootPath = request.getContextPath();
	String myTextSize		 = "size=\"20\"";
%>
<html>
	<head>
		<title>产品查询</title>
		<link href="<%=rootPath%>/css/chrome.css" rel="stylesheet" type="text/css">
		<script src="<%=rootPath%>/script/EvanGrid.js"></script>
		<script src="<%=rootPath%>/script/DateTime.js" language="JavaScript" type="text/javascript"></script>
	    <script language="javascript"><%@include file="/script/DateTimeVariable.js"%></script>
		<script language="javascript" type="text/javascript">
		/*Search*/
		function queryProduct()
		{
			form1.curpage.value = 1;
			form1.submit();
		}
		/*reset Product */
		function resetProduct()
		{
			form1.productName.value = "";
			form1.productBatno.value = "";
			form1.limitTime.value = "";
			form1.productBarcode.value = "";
			form1.factory.value = "";
			form1.preventFalseno.value = "";
		}
		/*Go new page*/
		function  goNewPage()
		{
			 document.form1.curpage.value = document.form1.selPage.value;
		     form1.action="<%=request.getContextPath()%>/jsp/product/product_select.jsp";
		     document.form1.submit();
		}
		/*Go to Page*/
	    function  goToPage(_curpage)
	    {
	     document.form1.curpage.value = _curpage;
	     form1.action="<%=request.getContextPath()%>/jsp/product/product_select.jsp";
	     document.form1.submit();
	    }
	    /*set select productID value*/
	    function setBillProduct(lid)
	    {
			document.form1.selectProductID.value = lid;
		}
		/*get the Selected porductID*/
		function getSelProductID()
		{
			return document.form1.selectProductID.value;
		}
		</script>
	</head>
	<%
		String productName	  = request.getParameter("productName");     //产品名称
		String productBarcode = request.getParameter("productBarcode");  //国际条码
		if(StringUtil.isNullOrEmpty(productName))productName="";
		if(StringUtil.isNullOrEmpty(productBarcode))productBarcode="";
		
		String curpage			= request.getParameter("curpage");
		String flag				= request.getParameter("flag");
		String sql = "";
		StringBuffer buffer=new StringBuffer();
		buffer.append("SELECT lid, ");									//产品ID
		buffer.append("strname, ");										//产品名称
		buffer.append("strextend4, ");									//国际条码
		//buffer.append("strextend2, ");									//防串货码
		buffer.append("strmanufacturer, ");								//工厂
		buffer.append("strextend1 as brand,");								//品牌
		buffer.append("strextend2 as series,");							//系列
		buffer.append("strstandard as form ");								//规格
		buffer.append("FROM tb000000product ");
		buffer.append("WHERE 1=1 ");
  		//产品名称
		if(!StringUtil.isNullOrEmpty(productName))
  		{
  			buffer.append(" and strname like '%"+productName+"%' ");  
  		}
		//商品条码(strextend4----对应国际条码)
		if(!StringUtil.isNullOrEmpty(productBarcode))
  		{
  			buffer.append(" and strextend4 like '%"+productBarcode+"%' ");  
  		}
		buffer.append(" order by lid desc"); 
   		sql=buffer.toString();
   		//out.println("sql-->"+sql);
   		List list=null;
   		int currentPageNumber=StringUtil.getIntValue(curpage,1);
   		EZSimpleDataPageBean pageBean=new EZSimpleDataPageBean();
   		String countSql=EZDbUtil.getCountSql(sql);
   		pageBean.setCurrentPageNumber(currentPageNumber);
   		pageBean.execute(countSql,sql,currentPageNumber,5);
   		list=pageBean.getCollection();
   		String picPath=request.getContextPath();
   		pageBean.setPicPath(picPath);
   		pageBean.setEachPageRowLimit(5);
   		pageBean.setFooter();    
		%>
	
	<body topmargin="5" leftmargin="0" rightmargin="0" style="overflow: hidden;"> 
		<form  name="form1"  method="post" action="product_select.jsp">
		<input name="curpage" type="hidden" value="<%=curpage%>"/>
		<input name="selectProductID" type="hidden" value="-1"/>
		<table border="0" cellspacing="0" cellpadding="3" width="100%" class="opbg">
			<tr valign="top">
				<td align="left" valign="middle">
           			<div id="Title">
           				<img src='<%=rootPath%>/images/ipcc_icon_setup.gif' align='absmiddle' width='16' height='16' border='0'/>
           				<strong class="titleMiddle">产品查询</strong>
           			</div>              			
           		</td>
           	</tr>
        </table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="opbg" style="table-layout:fixed;">
		  <tr>
		    <td align="right" nowrap="nowrap">产品名称：</td>
		    <td><input name="productName" type="text" class="box01" value="<%=productName%>" <%=myTextSize%>></td>
		    <td align="right" nowrap="nowrap">国际条码：</td>
		    <td><input name="productBarcode" type="text" class="box01" value="<%=productBarcode%>" <%=myTextSize%>></td>
		    <td align="left" nowrap="nowrap" height="30px">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input value="查询" type="button" style="width:80px;"  onclick="queryProduct();" class="button">
			</td>	
		  </tr>
		</table>
		
		<div id="dataTable">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" id="theObjTable" style="table-layout:fixed;">
			<tbody id="dataArea">
			 	<%
				 if(list!=null)
					{
						for(int i=0;i<list.size();i++){
						Object[] s =(Object[])list.get(i);
						if(s!=null)
						{
							String lid 	 		   =  String.valueOf(s[0]);	//lid
							String strname	  	   =  String.valueOf(s[1]);	//产品名称
							String strserialnumber =  String.valueOf(s[2]);	//国际条码
						//	String preventfalseno  =  String.valueOf(s[3]);	//防串货码
							String strmanufacturer =  String.valueOf(s[3]);	//工厂
							String brand		   =  String.valueOf(s[4]);	//品牌
							String series		   =  String.valueOf(s[5]);	//系列
							String form			   =  String.valueOf(s[6]); //规格
							
							
							if(StringUtil.isNullOrEmpty(lid))lid="";
							if(StringUtil.isNullOrEmpty(strname))strname="";
							if(StringUtil.isNullOrEmpty(strserialnumber))strserialnumber="";
					//		if(StringUtil.isNullOrEmpty(preventfalseno))preventfalseno="";
							if(StringUtil.isNullOrEmpty(strmanufacturer))strmanufacturer="";
							if(StringUtil.isNullOrEmpty(brand))brand="";	//品牌
							if(StringUtil.isNullOrEmpty(series))series="";	//系列
							if(StringUtil.isNullOrEmpty(series))series="";  //规格
					%>
						<TR>
							<td align="center" nowrap="nowrap">
								<input name="myCheckProduct" type="radio" value="<%=lid%>" 
									   onclick="setBillProduct('<%=lid%>');"> 
					        </td>
					        <td nowrap="nowrap" align="center"><%=i+1%></td>
							<td nowrap="nowrap"><%=brand%></td>
							<td nowrap="nowrap"><%=series%></td>
							<td nowrap="nowrap"><%=strname%></td>
							<td nowrap="nowrap"><%=form%></td>
							<td nowrap="nowrap"><%=strserialnumber%></td>
							<td nowrap="nowrap"><%=strmanufacturer%></td>
						</tr>
						<%}}}%>
			</tbody> 
		 </table>
		 </div>
		 
		 <!-- 页面翻页  -->
		<table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
        	<tr id="trPage">
	    		<td colspan="8">
	    			<table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
						<tr>
	   						<td><%=pageBean.getFooter()%></td>
	   					</tr>
	  				</table>
	  			</td>
	 		</tr> 	
		</table>
	 </form>
	</body>	
</html>

<script language="javascript" type="text/javascript">
	var fields = [
	["选择",30],
	["序号",30],
	["品牌",80],	
	["系列",80],
	["产品名称",120],
	["规格",80],
	["国际条码",90],
	["工厂信息",]
	];
	initGrid(fields,"<%=rootPath%>/css/EvanGrid.css","<%=rootPath%>/images/evanGrid");
</script>
