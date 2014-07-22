<%@ page language="java" pageEncoding="GBK"%>
<%@page import="com.blisscloud.util.*"%>
<%@page import="java.util.*"%>
<%
    String rootPath 		= request.getContextPath();
	String agentID	    	= request.getParameter("agentID");		 //agentID
	String customerName 	= request.getParameter("customerName");  //customerName
	String contactPersonTel = request.getParameter("contactPersonTel");//contactPersonTel
	if(StringUtil.isNullOrEmpty(agentID)){
		agentID = "10000";
		}else{
			agentID=new String(agentID.getBytes("ISO-8859-1"),"UTF-8");
		String qryAgentNameSQL = "select lid from tb000000employee where strname ='"+agentID+"'";
		String agent = EZDbUtil.getOnlyStringValue(qryAgentNameSQL );
		if(agent!=null){
			agentID= agent;
		}
		
	}
	if(StringUtil.isNullOrEmpty(customerName))customerName = "";
if(customerName!=""){
		customerName=new String(customerName.getBytes("ISO-8859-1"),"UTF-8");
	}
	if(StringUtil.isNullOrEmpty(contactPersonTel))contactPersonTel = "";
	//out.println("agentID--->"+agentID);
	//out.println("customerName--->"+customerName);
	//out.println("contactPersonTel--->"+contactPersonTel);
	String myTextSize		 = "size=\"28\"";
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
	<title>工单查询</title>
	<link href="<%=rootPath%>/css/chrome.css" rel="stylesheet" type="text/css">
	<script src="<%=rootPath%>/script/DateTime.js" type="text/javascript" type="text/javascript"></script>
	<script type="text/javascript"><%@include file="/script/DateTimeVariable.js"%></script>
	<script type="text/javascript">
		/* query bill*/
		function queryBill()
		{
			//if(dataCheck())
			//{
				//判断是否选定显示其他人工单
				var val = form1.showOther.checked;
				if(val == true)
				{
					form1.showOtherVal.value = "0";
				}else
				{
					form1.showOtherVal.value = "1";
				}
				form1.action="query_bill_list.jsp";
	            form1.target = "jetonQueryBillFrame";
				form1.submit();
			//}
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
		/*Data check*/
		function dataCheck()
		{
			var billNo 				= form1.billNo.value;			 //工单号
			var contactPersonName 	= form1.contactPersonName.value; //联系人姓名
			var contactPersonTel 	= form1.contactPersonTel.value;	 //联系人电话
			//var dealState		 	= form1.dealState.value;		 //处理状态
			var startTime 			= form1.startTime.value;		 //开始时间
			var endTime 			= form1.endTime.value;			 //结束时间
			var businessPersonName	= form1.businessPersonName.value;//业务员姓名
			var customerName		= form1.customerName.value;		 //客户名称
			var receiveID			= form1.receiveID.value;		 //信息接收人
			if(checkDateEarlier(startTime,endTime) == false)
			{
				alert("结束时间不能晚于开始时间!");
				form1.endTime.focus();
				return false;
			}
			if((startTime==null||startTime==''||startTime=="")&&
			   (endTime==null||endTime==''||endTime=="")&&
			   (billNo==null||billNo==''||billNo=="")&&
			   (contactPersonName==null||contactPersonName==''||contactPersonName=="")&&
			   (contactPersonTel==null||contactPersonTel==''||contactPersonTel=="")&&
			   (businessPersonName==null||businessPersonName==''||businessPersonName=="")&&
			   (customerName==null||customerName==''||customerName=="")&&
			   (receiveID==null||receiveID==''||receiveID=="")
			  )
			{
				alert("请至少输入一个查询条件!");
				return false;
			}
			return true;
		}
		/*reset Bill*/
		function resetBill()
		{
			form1.billNo.value = "";
			form1.startTime.value = "";
			form1.endTime.value = "";
			form1.reserveTime.value = "";
			document.getElementById("dealState").selectedIndex=0;
			document.getElementById("callType").selectedIndex=0;
			//清空原有值
			form1.contactPersonTel.value = "";
			form1.contactPersonName.value = "";
			form1.customerName.value = "";
			form1.receiveID.value = "";
		}
		/*Change the option div*/
	    function changeDiv(divName)
	    {
			var myDivName = divName+"_div";
			if(myDivName == 'contactPersonName_div')
			{
				document.getElementById("contactPersonName_div").style.display ="block";
				document.getElementById("contactPersonTel_div").style.display ="none";
				document.getElementById("customerName_div").style.display ="none";
				document.getElementById("receiveID_div").style.display ="none";
				//清空原有值
				form1.contactPersonTel.value = "";
				form1.customerName.value = "";
				form1.receiveID.value = "";
			}
			if(myDivName == 'contactPersonTel_div')
			{
				document.getElementById("contactPersonName_div").style.display ="none";
				document.getElementById("contactPersonTel_div").style.display ="block";
				document.getElementById("customerName_div").style.display ="none";
				document.getElementById("receiveID_div").style.display ="none";
				//清空原有值
				form1.contactPersonName.value = "";
				form1.customerName.value = "";
				form1.receiveID.value = "";
			}
			if(myDivName == 'customerName_div')
			{
				document.getElementById("contactPersonName_div").style.display ="none";
				document.getElementById("contactPersonTel_div").style.display ="none";
				document.getElementById("customerName_div").style.display ="block";
				document.getElementById("receiveID_div").style.display ="none";
				//清空原有值
				form1.contactPersonName.value = "";
				form1.contactPersonTel.value = "";
				form1.receiveID.value = "";
			}
			if(myDivName == 'receiveID_div')
			{
				document.getElementById("contactPersonName_div").style.display ="none";
				document.getElementById("contactPersonTel_div").style.display ="none";
				document.getElementById("customerName_div").style.display ="none";
				document.getElementById("receiveID_div").style.display ="block";
				//清空原有值
				form1.contactPersonName.value = "";
				form1.contactPersonTel.value = "";
				form1.customerName.value = "";
			}
		}
	</script>
</head>
	<body topmargin="0" rightmargin="0" leftmargin="0" style="overflow: hidden;"> 
		<table width="100%" border="0">
			<tr>
				<td>
					<form name="form1" method="post" action="query_bill_list.jsp" target="jetonQueryBillFrame">
					<input name="agentID" type="hidden" value="<%=agentID%>"/>
					<input name="showOtherVal" type="hidden" value=""/>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="opbg"> 
					  <tr>
						<td colspan="6">
							<table border="0" cellspacing="0" cellpadding="3" width="100%" class="opbg">
								<tr valign="top">
									<td align="left" valign="middle">
					           			<div id="Title">
					           				<img src='<%=rootPath%>/images/ipcc_icon_setup.gif' align='absmiddle' width='16' height='16' border='0'/>
					           				<strong class="titleMiddle">工单查询</strong>
					           			</div>              			
					           		</td>
					           	</tr>
					        </table>
						</td>
					  </tr>
					  <tr>
					    <td align="right" nowrap="nowrap">事件编号：</td>
					    <td><input name="billNo" type="text" class="box01" <%=myTextSize%>></td>
					    <td align="right" nowrap="nowrap">处理状态：</td>
					    <td>
						    <select name="dealState" id="dealState">
						      <option value="-1">全部</option>
						      <%
				            	String dealStateSQL =  "SELECT typevalue, typename "+
									            	   "FROM tb_ips_dictionary "+
									            	   "WHERE maintype='dealState' and isuse='1' "+
									            	   "ORDER BY ordernum";   
				            	String  options=EZDmManager.getOptionsBySql(dealStateSQL,"");
				          		out.println(options);
				              %>
						    </select>    
					    </td>
					    <td align="right" nowrap="nowrap">预约时间：</td>
						<td><input name="reserveTime" type="text" class="box01" value="" <%=myTextSize%> onFocus="ShowCalendar(1)"></td>
					  </tr>
					  <tr >
					    <td align="right" nowrap="nowrap">呼叫类型：</td>
					    <td>
						    <select name="callType" id="callType">
						      <option value="-1">全部</option>
						      <%
				            	String callTypeSQL =  "SELECT typevalue, typename "+
									            	   "FROM tb_ips_dictionary "+
									            	   "WHERE maintype='callType' and isuse='1' "+
									            	   "ORDER BY ordernum";   
				            	String  callTypeOptions=EZDmManager.getOptionsBySql(callTypeSQL,"");
				          		out.println(callTypeOptions);
				              %>
						    </select>    
					    </td>
					    <td align="right" nowrap="nowrap">开始时间：</td>
						<td>
							<input name="startTime" type="text" class="box01" value="" <%=myTextSize%> onFocus="ShowCalendar(0)">
						</td>
					    <td align="right" nowrap="nowrap">结束时间：</td>
						<td>
							<input name="endTime" type="text" class="box01" value="" <%=myTextSize%> onFocus="ShowCalendar(0)">
						</td>
					  </tr>
					  <tr >
					    <td align="right" nowrap="nowrap">
						    <select name="callType" id="callType" onchange="changeDiv(this.value);">
						    	<option value="contactPersonName">联系人姓名</option>
						        <option value="contactPersonTel">联系人电话</option>
						        <option value="customerName">经销商</option>
						        <option value="receiveID">创建人</option>
						    </select>  
					    </td>
					     <td>
						     <div style="display: block;" id="contactPersonName_div">
						     	<input name="contactPersonName" type="text" class="box01" <%=myTextSize%>>
						     </div> 
						     <div style="display: none;" id="contactPersonTel_div">
						     	<input name="contactPersonTel" type="text" class="box01"  <%=myTextSize%>>
						     </div>
						     <div style="display: none;" id="customerName_div">
						     	<input name="customerName" type="text" class="box01"  <%=myTextSize%>>
						     </div>
						     <div style="display: none;" id="receiveID_div">
						     	<input name="receiveID" type="text" class="box01" <%=myTextSize%>>
						     </div>
					    </td>					    <td colspan="4">
							<input type="checkbox" name="showOther" id="showOther" value="1" checked/>显示其他人工单
						</td>
						<td colspan="6" align="right" nowrap="nowrap">
							 
							<input value="查询" type="button" style="width:40px;"  onclick="queryBill();" class="button">
							 
							<input value="重置" type="button" style="width:40px;" onclick="resetBill();" class="button">
							 
						</td>
					  </tr>
					  <tr height="30px">
					    
					  </tr>
					</table>
					</form>
				</td>
			</tr>
			<tr height="90%"> 
				<td>
					<iframe width="100%" height="100%" src="" 
						    name="jetonQueryBillFrame" scrolling="no" id="jetonQueryBillFrame"
							frameborder="0" border="0" framespacing="0">
					</iframe>
				</td>
			</tr>
		</table>
	</body>
</html>
<script language="javascript">
<%if(customerName!=""){%>
changeDiv('contactPersonName');
form1.contactPersonName.value = "<%=customerName%>";
				
<%}%>
<%if(contactPersonTel!=""){%>
changeDiv('contactPersonTel');
form1.contactPersonTel.value = <%=contactPersonTel%>;
<%}%>
queryBill();
</script>
