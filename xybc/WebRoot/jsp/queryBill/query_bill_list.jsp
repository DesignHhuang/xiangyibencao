<%@ page language="java" pageEncoding="GBK"%>
<%@page import="com.blisscloud.util.*"%>
<%@page import="java.util.*"%>
<%
    String rootPath  = request.getContextPath();

	String myTrStyle = "lab3";
	String myToday 	 = StringTools.DateToString(new Date(),"yyyy-MM-dd");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
		<title>工单查询</title>
		<link href="<%=rootPath%>/css/chrome.css" rel="stylesheet" type="text/css">
		<script src="<%=rootPath%>/script/EvanGrid.js"></script>
		<script language="javascript" type="text/javascript">
	    /*Go newPage*/ 
	    function  goNewPage()
	    {
	     form1.searchFlag.value="1";
	     document.form1.curpage.value = document.form1.selPage.value;
	     form1.action='<%=request.getContextPath()%>'+'/jsp/queryBill/query_bill_list.jsp';
	     document.form1.submit();
	    }
	    /*Go toPage*/
	    function  goToPage(_curpage)
	    {
	     form1.searchFlag.value="1";
	     document.form1.curpage.value = _curpage;
	     form1.action='<%=request.getContextPath()%>'+'/jsp/queryBill/query_bill_list.jsp';
	     document.form1.submit();
	   }
	   /*deal Bill*/ 
	   function  dealBill(optType,optID,agentID)
	   {
		    var width = screen.width-160;
			var height = screen.height-180;
			var x = 400;
			var y = 200;
			var openURL = "bill_detail.jsp?optType="+optType+
										  "&optID="+optID+
										  "&agentID="+agentID;
			//alert("openURL--->"+openURL);
			parent.window.open(openURL,"bill_detail","width="+width+"',height="+height+
					                      ",left="+x+",top="+y+
					                      ",scrollbars=yes,menubar=no,statusbar=no,");
	  }
	</script>
	</head>
<%
	//1、获取查询条件;
	String billNo	  		  = request.getParameter("billNo");    			//工单号
	String contactPersonName  = request.getParameter("contactPersonName");  //联系人姓名
	String contactPersonTel	  = request.getParameter("contactPersonTel");   //联系人电话
	String dealState 	  	  = request.getParameter("dealState");		    //处理状态
	String startTime  	 	  = request.getParameter("startTime");	        //开始时间
	String endTime 		  	  = request.getParameter("endTime");	        //结束时间
	String businessPersonName = request.getParameter("businessPersonName");	//业务员姓名
	String customerName		  = request.getParameter("customerName");		//客户名称
	String receiveID		  = request.getParameter("receiveID");			//信息接收人
	String reserveTime		  = request.getParameter("reserveTime");		//预约时间
	String callType			  = request.getParameter("callType");			//呼叫类型
	String optType	 	  	  = request.getParameter("optType");			//optType
	String optID 	  	  	  = request.getParameter("optID");				//optID
	String curpage		  	  = request.getParameter("curpage");			//curpage 
	String agentID		  	  = request.getParameter("agentID");			//agentID
	String showOtherVal		  = request.getParameter("showOtherVal");		//是否显示其他人工单
	
	if(StringUtil.isNullOrEmpty(agentID))agentID = "10000";
	//out.println("agentID--->"+agentID);
	//2、根据查询条件得到待办工单; 
	String sql = "SELECT bill.seqid,bill.eventno,dic0.TYPENAME as eventtype,"+
					  "to_char(bill.reserveTime,'yyyy-mm-dd hh24:mi:ss') as reservetime,dic2.TYPENAME as dealstate,"+
					  "percus.strname,percus.strmobilephone,"+
					  "bill.receiveid,"+
					  "bill.businesspersonname,bill.customername, "+
					  "dic1.TYPENAME as calltype "+
			     "FROM  tb_ips_bill bill "+
			     "LEFT JOIN TB_IPS_DICTIONARY dic0  on bill.eventtype = dic0.TYPEVALUE and dic0.MAINTYPE = 'eventType' "+
			     "LEFT JOIN TB_IPS_DICTIONARY dic1  on bill.calltype = dic1.TYPEVALUE and dic1.MAINTYPE = 'callType' "+
			     "LEFT JOIN TB_IPS_DICTIONARY dic2  on bill.dealstate = dic2.TYPEVALUE and dic2.MAINTYPE = 'dealState' "+
			     "LEFT JOIN tb000000personalcustomer percus on percus.lid=bill.customerid "+
			     "LEFT JOIN tb000000employee emp on bill.agentid = CAST(emp.lid AS varchar(10))"+
			     "WHERE 1=1 ";
	//工单号
	if(!StringUtil.isNullOrEmpty(billNo))
	{
		sql += " and bill.eventno like '%"+billNo.trim()+"%' ";
	}
	//联系人姓名
	if(!StringUtil.isNullOrEmpty(contactPersonName))
	{
		sql += " and ( percus.strname like '%"+contactPersonName.trim()+"%' "+
		       "       or bill.contactname like '%"+contactPersonName.trim()+"%' "+
		       "       or bill.backupcontactname like '%"+contactPersonName.trim()+"%' "+
		       "       or bill.businesspersonname like '%"+contactPersonName.trim()+"%' "+
		       ") ";
	}
	//联系人电话
	if(!StringUtil.isNullOrEmpty(contactPersonTel))
	{
		sql += " and ( percus.strmobilephone like '%"+contactPersonTel.trim()+"%' "+
		 "       or percus.strofficephone like '%"+contactPersonTel.trim()+"%' "+
	       "       or percus.strhomephone like '%"+contactPersonTel.trim()+"%' "+
	       "       or bill.contacttel like '%"+contactPersonTel.trim()+"%' "+
	       "       or bill.backupcontacttel like '%"+contactPersonTel.trim()+"%' "+
	       "       or bill.contacttelphone like '%"+contactPersonTel.trim()+"%' "+

	       ") ";
	}
	//处理状态
	if(!StringUtil.isNullOrEmpty(dealState) 
			&& !"-1".equals(dealState))
	{
		sql += " and bill.dealstate='"+dealState+"' ";
	}
	//开始时间
	if(!StringUtil.isNullOrEmpty(startTime))
	{
		sql += " and bill.receivetime >= to_timestamp('"+startTime+"','yyyy-mm-dd hh24:mi:ss') ";
	}
	//结束时间
	if(!StringUtil.isNullOrEmpty(endTime))
	{
		sql += " and bill.receivetime <= to_timestamp('"+endTime+"','yyyy-mm-dd hh24:mi:ss') ";
	}
	//业务员姓名
	if(!StringUtil.isNullOrEmpty(businessPersonName))
	{
		sql += " and bill.businesspersonname like '%"+businessPersonName.trim()+"%' ";
	}
	//经销商
	if(!StringUtil.isNullOrEmpty(customerName))
	{
		sql += " and bill.customername like '%"+customerName.trim()+"%' ";
	}
	//信息接收人
	if(!StringUtil.isNullOrEmpty(receiveID))
	{
		sql += " and emp.strname like '%"+receiveID.trim()+"%' ";
	}
	//预约时间 
	if(!StringUtil.isNullOrEmpty(reserveTime))
	{
		sql += " and bill.reserveTime >= to_timestamp('"+reserveTime+" 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		sql += " and bill.reserveTime <= to_timestamp('"+reserveTime+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
	}
	//呼叫类型
	if(!StringUtil.isNullOrEmpty(callType) 
			&& !"-1".equals(callType))
	{
		sql += " and bill.calltype='"+callType.trim()+"' ";
	}
	//是否显示其他人工单
	if("1".equals(showOtherVal))
	{
		sql += " and bill.receiveid = '"+agentID+"' ";
	}
	sql += " ORDER BY reserveTime asc,bill.eventno desc ";
	//out.println("sql-->"+sql);
	List list=null;
	int currentPageNumber=StringUtil.getIntValue(curpage,1);
	EZSimpleDataPageBean pageBean=new EZSimpleDataPageBean();
	String countSql=EZDbUtil.getCountSql(sql);
	pageBean.setCurrentPageNumber(currentPageNumber);
	pageBean.execute(countSql,sql,currentPageNumber,10);
	list=pageBean.getCollection();
	String picPath=request.getContextPath();
	pageBean.setPicPath(picPath);
	pageBean.setEachPageRowLimit(10);
	pageBean.setFooter();    			
%>
<body leftmargin="0" topmargin="0" rightmargin="0">
	<form name="form1" method="post" action="query_bill_list.jsp" >
	<input name="billNo" type="hidden" value="<%=billNo%>"/>
	<input name="contactPersonName" type="hidden" value="<%=contactPersonName%>"/>
	<input name="contactPersonTel" type="hidden" value="<%=contactPersonTel%>"/>
	<input name="dealState" type="hidden" value="<%=dealState%>"/>
	<input name="startTime" type="hidden" value="<%=startTime%>"/>
	<input name="endTime" type="hidden" value="<%=endTime%>"/>
	<input name="businessPersonName" type="hidden" value="<%=businessPersonName%>"/>
	<input name="customerName" type="hidden" value="<%=customerName%>"/>
	<input name="receiveID" type="hidden" value="<%=receiveID%>"/>
	<input name="curpage" type="hidden" value="<%=curpage%>"/>
	<input name="agentID" type="hidden" value="<%=agentID%>"/>
	<input name="showOtherVal" type="hidden" value="<%=showOtherVal%>"/>
	<input type="hidden" name="searchFlag" value="1">
	<div id="dataTable">
		<table  width="100%" border="0" cellspacing="0" cellpadding="0" id="theObjTable" style="table-layout:fixed;">
		  <tbody id="dataArea">
		  <%
			  for(int i=0;i<list.size();i++){
					Object[] s =(Object[])list.get(i);
					if(s!=null){
						String seqid 	 		 =	String.valueOf(s[0]);	    //seqid
						String eventno			 =	String.valueOf(s[1]);		//eventno
						String eventtype		 =  String.valueOf(s[2]);		//eventtype
						String reservetime		 =  String.valueOf(s[3]);		//reservetime
						String dealstate		 =  String.valueOf(s[4]);		//dealstate
						String IPCCCustname		 =	String.valueOf(s[5]);		//IPCCCustname
						String IPCCCustTel 		 =	String.valueOf(s[6]);		//IPCCCustTel
						String receiveid		 =	String.valueOf(s[7]);		//receiveid
						String businesspersonname=	String.valueOf(s[8]);		//businesspersonname
						String customername		 =	String.valueOf(s[9]);		//customername
						String calltype          =	String.valueOf(s[10]);		//calltype
						
						if(StringUtil.isNullOrEmpty(seqid))seqid="";
						if(StringUtil.isNullOrEmpty(eventno))eventno="";
						if(StringUtil.isNullOrEmpty(eventtype))eventtype="";
						if(StringUtil.isNullOrEmpty(reservetime) || "0001-01-01 00:00:00".equals(reservetime))reservetime="";
						if(StringUtil.isNullOrEmpty(dealstate))dealstate="";
						if(StringUtil.isNullOrEmpty(IPCCCustname))IPCCCustname="";
						if(StringUtil.isNullOrEmpty(IPCCCustTel))IPCCCustTel="";
						if(StringUtil.isNullOrEmpty(receiveid))receiveid="";
						if(StringUtil.isNullOrEmpty(businesspersonname))businesspersonname="";
						if(StringUtil.isNullOrEmpty(customername))customername="";
						
						if(StringUtil.isNullOrEmpty(calltype))calltype="";
						
						//set tr style
						if(reservetime.equals(myToday))myTrStyle = "lab3_3";
		  %>
		  <tr>
		    <td nowrap="nowrap" align="center"><span class="STYLE1"><%=i+1%></span></td>
		    <td nowrap="nowrap">
		    	<a style="cursor:hand;" onclick="dealBill('updateBill','<%=seqid%>','<%=agentID%>');"><%=eventno%></a>
		    </td>
		    <td nowrap="nowrap"><%=eventtype%></td>
		    <td nowrap="nowrap"><%=calltype%></td>
		    <td nowrap="nowrap"><%=reservetime%></td>
		    <td nowrap="nowrap" align="center"><%=dealstate%></td>
		    <td nowrap="nowrap"><%=IPCCCustname%></td>
		    <td nowrap="nowrap"><%=IPCCCustTel%></td>
		    <td nowrap="nowrap">
			<%
			    String qryAgentNameSQL = "select strname from tb000000employee where lid ="+receiveid;
				String agentName = EZDbUtil.getOnlyStringValue(qryAgentNameSQL );
						          		out.println(agentName);
				%>
			</td>
		    <td nowrap="nowrap"><%=businesspersonname%></td>
		    <td nowrap="nowrap"><%=customername%></td>
		  </tr>
		  <%}} %>
		  </tbody>
		</table>
		</div>
		<!-- 页面翻页  -->
		<table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
	       	<tr id="trPage">
	    		<td colspan="10">
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
	["序号",40],
	["事件编号",150],	
	["事件类型",80],
	["呼叫类型",80],
	["预约日期",120],
	["处理状态",120],
	["消费者姓名",80],
	["消费者电话",100],	
	["创建人",120],
	["业务员姓名",100],
	["客户名称",]
	];
	initGrid(fields,"<%=rootPath%>/css/EvanGrid.css","<%=rootPath%>/images/evanGrid");
</script>
