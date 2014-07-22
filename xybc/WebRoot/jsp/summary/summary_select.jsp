<%@ page contentType="text/html;charset=GBK" %>
<%@page import="java.util.*"%>
<%@page import="com.blisscloud.util.*"%>
<%
    String rootPath		= request.getContextPath();
	String summaryID 	= request.getParameter("summaryID");//summaryID 
	if(StringUtil.isNullOrEmpty(summaryID))summaryID = "-1";
%>
<html>
	<head>
		<title>反馈明细</title>
		<link href="<%=rootPath%>/css/chrome.css" rel="stylesheet" type="text/css">
		<script language="javascript" type="text/javascript">
		/*Summary Select*/
		function summarySelect() 
		{
			//if(confirm("确定要选择该小结吗?"))
			//{
				var mySelectSum = document.frames['jetonSummaryTree'].jetonSummaryTree.getSelectedDatabaseIdList();
				//alert("mySelectSum---->"+mySelectSum);
				this.opener.form1.summaryID.value = mySelectSum;
				var sumId = mySelectSum.substring(4);
				//alert("sumId---->"+sumId);
				this.opener.ajaxGetSummaryPath(sumId);
				window.close();
			//}
			
		}
		</script>
	</head>
	<body topmargin="0" leftmargin="0" rightmargin="0" style="overflow: hidden;"> 
		<table border="1" cellspacing="0" cellpadding="3" width="100%" class="opbg">
			<tr valign="top">
				<td align="left" valign="middle">
           			<div id="Title">
           				<img src='<%=rootPath%>/images/ipcc_icon_setup.gif' align='absmiddle' width='16' height='16' border='0'/>
           				<strong class="titleMiddle">反馈明细</strong>
           			</div>              			
           		</td>
           	</tr>
        </table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr height="94%">
		    <td colspan="6" align="center" nowrap="nowrap">
				<iframe id="jetonSummaryTree" name="jetonSummaryTree" src="summary_tree.jsp?summaryID=<%=summaryID%>" width="100%" height="100%">
				</iframe>
			</td>	
		  </tr>
		  <tr>
		    <td colspan="6" align="center" nowrap="nowrap" height="20px">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input value="确定" type="button" style="width:80px;"  onclick="summarySelect();" class="button">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input value="关闭" type="button" style="width:80px;" onclick="window.close();" class="button">
			</td>	
		  </tr>
		</table>
	</body>	
</html>
