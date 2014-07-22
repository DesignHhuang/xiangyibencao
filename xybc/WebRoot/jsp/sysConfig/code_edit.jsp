<%@ page contentType="text/html;charset=GBK"%>
<%@page import="com.blisscloud.hibernate.*"%>
<%@page import="com.blisscloud.util.*"%>
<%@page import="java.util.*"%>
<%
    String rootPath = request.getContextPath();

	//1.get request parameter
	String optType	 	  = request.getParameter("optType");		//optType
	String optID 	  	  = request.getParameter("optID");			//optID
	String maintype_name  = request.getParameter("maintype_name");  //maintype_name
	
	String mainType 	= "";//mainType
	String typeName   	= "";//typeName
	String typeValue 	= "";//typevalue
	String orderNum  	= "";//ordernum
	String isUse 		= "";//isuse
	String remark 		= "";//remark
	//add dict
	if(!StringUtil.isNullOrEmpty(optType) &&
			"add".equals(optType) 
		  )
	{
		mainType = optID;
	}
	
	//modify dict
	if(!StringUtil.isNullOrEmpty(optType) &&
			"modi".equals(optType) &&
			!StringUtil.isNullOrEmpty(optID)
		  )
	{

		String dictSQL = "SELECT maintype, typename, typevalue, ordernum,isuse,remark  "+
						 "FROM tb_ips_dictionary "+
						 "WHERE seqid="+optID;
		//out.println(taskSQL);
		List taskList = EZDbUtil.getStringArrayList(dictSQL);
		if(taskList != null)
		{
				Object[] s =(Object[])taskList.get(0);
				if(s!=null)
				{
					String maintype 		=	String.valueOf(s[0]);		//maintype
					String typename			=  	String.valueOf(s[1]);		//typename
					String typevalue 		=  	String.valueOf(s[2]);		//typevalue
					String ordernum			=  	String.valueOf(s[3]);		//ordernum
					String isuse  			=	String.valueOf(s[4]);		//isuse
					String memo  			=	String.valueOf(s[5]);		//remark
					//filter data
					if(StringUtil.isNullOrEmpty(maintype))maintype="";
					if(StringUtil.isNullOrEmpty(typename))typename="";
					if(StringUtil.isNullOrEmpty(typevalue))typevalue="";
					if(StringUtil.isNullOrEmpty(ordernum))ordernum="";
					if(StringUtil.isNullOrEmpty(isuse))isuse="";
					if(StringUtil.isNullOrEmpty(memo))memo="";
					//set value
					mainType = maintype;
					typeName = typename;
					typeValue = typevalue;
					orderNum = ordernum;
					isUse = isuse;
					remark = memo;
			}
		}
	
	}
%>
<html>
	<head>
		<title>数据字典管理</title>
		<link href="<%=rootPath%>/css/chrome.css" rel="stylesheet" type="text/css">
		<script language="javascript" type="text/javascript">
		function isnumber(str)
		{
			var number_chars = "1234567890";
			var i;
			for (i=0;i<str.length;i++){
				if (number_chars.indexOf(str.charAt(i))==-1) return 0;
			}
			return 1;
		}
		function doit() 
		{
	      var name=form1.typeName.value;  //typeName
	      var out_code=form1.typeValue.value; //typeValue
	      var order_num=form1.orderNum.value; //orderNum
	      
	      if(name==""||name==null)
		  {
	        alert("代码名称不能为空");
	        return false;
	      }
	      
	      if(out_code==""||out_code==null)
		  {
	        alert("代码编号不能为空");
	        return false;
	      }
	      if(order_num==""||order_num==null)
		  {
	        alert("排列序号不能为空");
	        return false;
	      }else{
	         if(isnumber(order_num)==0){
	           alert("排列序号必须为数字");
	           return false;
	         }
	      }
	      document.form1.submit();
	      window.close();
		}
		</script>
</head>
<body style="overflow: hidden;" topmargin="0" leftmargin="0" rightmargin="0">
	<form name="form1" method="post" action="code_list.jsp" target="jetonDictListFrame">
	<input name="optType" type="hidden" value="<%=optType%>"/>
	<input name="optID" type="hidden" value="<%=optID%>"/>
	<input name="maintype_name" type="hidden" value="<%=maintype_name%>"/>
	<table border="0" cellspacing="0" cellpadding="3" width="100%" class="opbg">
			<tr valign="top">
				<td align="left" valign="middle">
           			<div id="Title">
           				<img src='<%=rootPath%>/images/ipcc_icon_setup.gif' align='absmiddle' width='16' height='16' border='0'/>
           				<strong class="titleMiddle">数据字典管理</strong>
           			</div>              			
           		</td>
           	</tr>
        </table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="opbg">
	  <tr >
	    <td width="16%" align="right">编码：</td>
	    <td>
	    	<input type="text" name="mainType" size="25" readonly="readonly" value='<%=mainType%>' class="box01"/> 
	    </td>
	    <td align="right">排列序号：</td>
		<td>
			<input type="text" name="orderNum" value='<%=orderNum%>' class="box01"/> 
		</td>
	   </tr>
	  <tr >
	    <td align="right">代码值：</td>
	    <td class="tinttd">
	    	<input type="text" name="typeValue" size="25" value='<%=typeValue%>' class="box01"/> 
	    </td>
	    <td align="right">是否启用：</td>
		<td>
			 <select name="isUse">
				<option value="1" <%if("1".equals(isUse))out.print("selected=\"selected\""); %>>是</option>
				<option value="0" <%if("0".equals(isUse))out.print("selected=\"selected\""); %>>否</option>
			</select>
		</td>
	  </tr>
	  <tr >
	    <td align="right">代码名称：</td>
		<td colspan="3">
			<input type="text" name="typeName" size="70"  value='<%=typeName%>' class="box01"/> 
		</td>
	  </tr>
	    <tr >
	    <td align="right">备注：</td>
	    <td class="tinttd" colspan="3">
	    	<textarea name="remark" rows="4" style="width:87%"><%=remark%></textarea>
	    </td>
	  </tr>
	  <tr  height="130px">
	    <td  align="center" colspan="4">
		<input onclick="javascript:doit();" value="保存" type="button" style="width:80px;" class="button">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input onclick="window.close();" value="关闭" type="button" style="width:80px;" class="button">
	   </td>
	  </tr>
	</table>
	</form>
</body>
</html>
