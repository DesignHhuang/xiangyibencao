<%@ page contentType="text/html; charset=gbk"%>
<%
    String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
		<title>工单报表</title>
		<link href="<%=path%>/css/chrome.css" type="text/css" rel="stylesheet" />
		<script src="<%=path%>/script/DateTime.js" language="JavaScript" type="text/javascript"></script>
		<script language="javascript"><%@include file="/script/DateTimeVariable.js"%></script>
		<script language="JavaScript" type="text/javascript">
		/*Data check*/
		function dataCheck()
		{
			var startTime = form1.startTime.value;	//开始时间
			var endTime   = form1.endTime.value;	//结束时间
			if(startTime==null||startTime=="")
			{
				alert("请输入开始时间!");
				form1.startTime.focus();
				return false;
			}
			if(checkDateEarlier(startTime,endTime) == false)
			{
				alert("结束时间不能晚于开始时间!");
				form1.endTime.focus();
				return false;
			}
			return true;
		}
	   /*queryBill*/
		function queryBill()
		{
			if(dataCheck())
			{
				//var expButton = document.getElementById('exportExcel');
	            //expButton.disabled=false;
	            form1.action="bill_report_list.jsp";
	            form1.target = "myBillFrame";
				form1.submit();
			}
		}
	   /*Date check*/
	   function checkDateEarlier(strStart,strEnd)
		{
		    if (( strStart == "" ) || ( strEnd == "" ))
		        return true;
		    var arr1 = strStart.split("-");
		    var arr2 = strEnd.split("-");
		    //var date1 = new Date(arr1[0],parseInt(arr1[1].replace(/^0/,""),10) - 1,arr1[2]);
		    //var date2 = new Date(arr2[0],parseInt(arr2[1].replace(/^0/,""),10) - 1,arr2[2]);
		    if(arr1[1].length == 1)
		        arr1[1] = "0" + arr1[1];
		    if(arr1[2].length == 1)
		        arr1[2] = "0" + arr1[2];
		    if(arr2[1].length == 1)
		        arr2[1] = "0" + arr2[1];
		    if(arr2[2].length == 1)
		        arr2[2]="0" + arr2[2];
		    var d1 = arr1[0] + arr1[1] + arr1[2];
		    var d2 = arr2[0] + arr2[1] + arr2[2];
		    if(parseInt(d1,10) > parseInt(d2,10))
		       return false;
		    else
		       return true;
		}
	   /*export Bill ToExcel*/
		function exportBillToExcel()
		{
			if(dataCheck())
			{
				//var expButton = document.getElementById('exportExcel');
	            //expButton.disabled=true;
	            form1.action="bill_report_excel.jsp";
	            form1.target = "_blank";
	            form1.submit();
			}
	    }
	</script>
	</head>
	<body topmargin="0" style="overflow: hidden;">
		<table width="100%" border="0">
			<tr height="10%"> 
				<td> 
					<table width="100%" border="0" width="100%" cellspacing="0" cellpadding="0" class="opbg">
					   <form name="form1" method="post" action="bill_report_list.jsp" target="myBillFrame">
						<tr>
							<td colspan="5">
								<table border="0" cellspacing="0" cellpadding="3" width="100%" class="opbg">
									<tr valign="top">
										<td align="left" valign="middle">
						           			<div id="Title">
						           				<img src='<%=path%>/images/ipcc_icon_report.gif' align='absmiddle' width='16' height='16' border='0'/>
						           				<strong class="titleMiddle">工单报表</strong>
						           			</div>              			
						           		</td>
						           	</tr>
						        </table>
						       </td>
						  </tr>
						  <tr height="30px">
							<td width="" align="right" nowrap="nowrap">开始时间：</td>
							<td width="" align="left">
		 						<input name="startTime" type="text" class="box01" value="" size="20" onFocus="ShowCalendar(0)">
							</td>
							<td width="" align="right" nowrap="nowrap">结束时间：</td>
							<td width="" align="left">
		 						<input name="endTime" type="text" class="box01" value="" size="20" onFocus="ShowCalendar(0)">
							</td>
							<td align="right" nowrap="nowrap">
								<input type="button" value="查询" style="width:40px" onclick="queryBill();" class="button">
								 
			  					<input value="汇出" type="button" style="width:40px;" id="exportExcel" onclick="exportBillToExcel();" class="button">
			  				</td>
						</tr>
						</form>	
					</table>
				</td>
			</tr>
			<tr height="90%">
				<td>
					<iframe src="" name="myBillFrame" id="myBillFrame" 
							framespacing="0" frameborder="no" border="0" scrolling="no" 
							width="100%" height="100%">
					</iframe>
				</td>
			</tr>
	</table>
</body>
</html>
		
