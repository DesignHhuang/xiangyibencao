<%@ page contentType="text/html;charset=GBK" %>
<%@page import="java.util.*"%>
<%@page import="com.blisscloud.util.*"%>
<%
    String rootPath = request.getContextPath();
%>
<html>
	<head>
		<title>字典表管理</title>
		<link href="<%=rootPath%>/css/chrome.css" rel="stylesheet" type="text/css">
		<script language="javascript" type="text/javascript">
		/*Search*/
		function searchByCondition()
		{
			form1.submit();
		}
		</script>
	</head>
	
	<body topmargin="0" rightmargin="0" leftmargin="0" style="overflow: hidden;"> 
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
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<form  name="form1"  method="post" action="code_list.jsp" target="jetonDictListFrame">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="opbg">
						  <tr height="30px">
							<td align="right">代码类型：</td>
							<td align="left">
							<select name="maintype_name">
								<option value="-1">全部</option>
					            <%
					            	String orderTypeSQL =  "select distinct maintype,maintype as maintype_name from tb_ips_dictionary t";   
					   				String options=EZDmManager.getOptionsBySql(orderTypeSQL,"");
					          		out.println(options);
					            %>
				            </select>
				          </TD>
						  <td align="center">
								<input value="查询" type="button" style="width:40px;"  onclick="javascript:searchByCondition();" class="button">
						  </td>
						</tr>
						</table>
					 </form>
				</td>
			</tr>
			<tr height="90%"> 
				<td>
					<iframe width="100%" height="100%" src="code_list.jsp" 
						    name="jetonDictListFrame" scrolling="no" id="jetonDictListFrame"
							frameborder="0" border="0" framespacing="0">
					</iframe>
				</td>
			</tr>
		</table>
	</body>	
</html>
