<%@ page language="java" pageEncoding="GBK"%>
<%@page import="com.blisscloud.util.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%
    String rootPath = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
		<title>工单报表</title>
		<link href="<%=rootPath%>/css/chrome.css" rel="stylesheet" type="text/css">
		<script src="<%=rootPath%>/script/EvanGrid.js"></script>
		<script language="JavaScript" type="text/javascript">
		 /*Go newPage*/ 
	    function  goNewPage()
	    {
	     form1.searchFlag.value="1";
	     document.form1.curpage.value = document.form1.selPage.value;
	     form1.action='<%=request.getContextPath()%>'+'/jsp/billReport/bill_report_list.jsp';
	     document.form1.submit();
	    }
	    /*Go toPage*/
	    function  goToPage(_curpage)
	    {
	     form1.searchFlag.value="1";
	     document.form1.curpage.value = _curpage;
	     form1.action='<%=request.getContextPath()%>'+'/jsp/billReport/bill_report_list.jsp';
	     document.form1.submit();
	   }
	    /*deal Bill*/ 
	   function  dealBill(optType,optID,agentID)
	   {
		    var width = screen.width-140;
			var height = screen.height-180;
			var x = 400;
			var y = 200;
			var openURL = "../waitingBill/bill_detail.jsp?optType="+optType+
										  "&optID="+optID+
										  "&agentID="+agentID;
			//alert("openURL--->"+openURL);
			var openFea = "width="+width+",height="+height+",left="+x+",top="+y+",scrollbars=yes,menubar=no,statusbar=no";
			parent.window.open(openURL,"bill_detail",openFea);
	  }
	  </script>
	</head>
	<% 
			String curpage		    =   request.getParameter("curpage");	//curpage	
			String startTime  		= 	request.getParameter("startTime");	//开始时间
			String endTime 	  		= 	request.getParameter("endTime");	//结束时间
			String agentID		    = 	request.getParameter("agentID");	//agentID
			
			if(StringUtil.isNullOrEmpty(agentID))agentID = "10000";
			//out.println("agentID--->"+agentID);
			//###########begin############要查询的报表数据###########begin############
			String sql = "SELECT bill.seqid,bill.eventno,dic1.TYPENAME as calltype,bill.receiveid,"+
							  "to_char(bill.reservetime,'yyyy-mm-dd hh24:mi:ss') as reservetime,dic2.TYPENAME as dealstate,p.strname,"+
							  "p.strmobilephone,bill.businesspersonname,bill.customername,dic0.TYPENAME as eventtype "+
					     "FROM  tb_ips_bill bill "+
					     "LEFT JOIN TB_IPS_DICTIONARY dic0  on bill.eventtype = dic0.TYPEVALUE and dic0.MAINTYPE = 'eventType' "+
					     "LEFT JOIN TB_IPS_DICTIONARY dic1  on bill.calltype = dic1.TYPEVALUE and dic1.MAINTYPE = 'callType' "+
					     "LEFT JOIN TB_IPS_DICTIONARY dic2  on bill.dealstate = dic2.TYPEVALUE and dic2.MAINTYPE = 'dealState' "+
					     "LEFT JOIN tb000000personalcustomer p  on p.lid=bill.customerid "+
					     "WHERE 1=1 ";
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
			sql += " ORDER BY receivetime desc ";
			//out.println("sql--->"+sql);
			//###########end############要查询的报表数据###########end############
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

	<body leftmargin="0" topmargin="0" rightmargin="0" >
		<form name="form1" method="post" action="order_list.jsp" >
		<input name="startTime" type="hidden" value="<%=startTime%>"/>
		<input name="endTime" type="hidden" value="<%=endTime%>"/>
		<input name="curpage" type="hidden" value="<%=curpage%>"/>
		<input name="agentID" type="hidden" value="<%=agentID%>"/>
		<input type="hidden" name="searchFlag" value="1">
		<div id="dataTable">	
			<table width="100%" border="0" cellspacing="0" cellpadding="0" id="theObjTable" style="table-layout:fixed;">
			<tbody id="dataArea">
			  <%
			  for(int i=0;i<list.size();i++){
					Object[] s =(Object[])list.get(i);
					if(s!=null){
						String seqid 	 		 =	String.valueOf(s[0]);	    //seqid
						String eventno			 =	String.valueOf(s[1]);		//eventno
						String calltype			 =  String.valueOf(s[2]);		//calltype
						String receiveid		 =  String.valueOf(s[3]);		//receiveid
						String reservetime		 =  String.valueOf(s[4]);		//reservetime
						String dealstate 		 =	String.valueOf(s[5]);		//dealstate
						String IPCCCustomername  =	String.valueOf(s[6]);	//IPCCCustomername
						String IPCCCustomertel	 =	String.valueOf(s[7]);		//IPCCCustomertel
						String businesspersonname=	String.valueOf(s[8]);		//businesspersonname
						String customername		 =	String.valueOf(s[9]);		//customername
						String eventtype          =	String.valueOf(s[10]);		//eventtype
						
						if(StringUtil.isNullOrEmpty(seqid))seqid="";
						if(StringUtil.isNullOrEmpty(eventno))eventno="";
						if(StringUtil.isNullOrEmpty(calltype))calltype="";
						if(StringUtil.isNullOrEmpty(receiveid))receiveid="";
						if(StringUtil.isNullOrEmpty(reservetime)|| "0001-01-01 00:00:00".equals(reservetime))reservetime="";
						if(StringUtil.isNullOrEmpty(dealstate))dealstate="";
						if(StringUtil.isNullOrEmpty(IPCCCustomername))IPCCCustomername="";
						if(StringUtil.isNullOrEmpty(IPCCCustomertel))IPCCCustomertel="";
						if(StringUtil.isNullOrEmpty(businesspersonname))businesspersonname="";
						if(StringUtil.isNullOrEmpty(customername))customername="";
						if(StringUtil.isNullOrEmpty(eventtype))eventtype="";
			  %>
			  <tr>
			    <td nowrap="nowrap" align="center"><%=i+1%></td>
			    <td nowrap="nowrap">
			    	<a style="cursor:hand;" onclick="dealBill('updateBill','<%=seqid%>','<%=agentID%>');"><%=eventno%></a>
			    </td>
			    <td nowrap="nowrap"><%=eventtype%></td>
			    <td nowrap="nowrap"><%=calltype%></td>
			    <td nowrap="nowrap">
			    <%
			    String qryAgentNameSQL = "select strname from tb000000employee where lid ="+receiveid;
				String agentName = EZDbUtil.getOnlyStringValue(qryAgentNameSQL );
						          		out.println(agentName);
				%>
			    
			    </td>
			    <td nowrap="nowrap"><%=reservetime%></td>
			    <td nowrap="nowrap" align="center"><%=dealstate%></td>
			    <td nowrap="nowrap"><%=IPCCCustomername%></td>
			    <td nowrap="nowrap"><%=IPCCCustomertel%></td>
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
	["获知渠道",80],
	["呼叫类型",80],
	["创建人",100],
	["预约日期",120],
	["处理状态",80],
	["顾客姓名",100],	
	["顾客电话",120],
	["业务员姓名",100],
	["客户名称(经销商)",]
	];
	initGrid(fields,"<%=rootPath%>/css/EvanGrid.css","<%=rootPath%>/images/evanGrid");
</script>
