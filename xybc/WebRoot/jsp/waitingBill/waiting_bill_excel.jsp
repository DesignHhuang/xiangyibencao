<%@ page contentType="application/vnd.ms-excel;charset=gbk" pageEncoding="gbk" language="java"%>
<%@page import="com.blisscloud.util.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%
    String rootPath = request.getContextPath();
	String myTextStyle = "style='mso-number-format:\"\\@\";'";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<% 
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
			String agentID		  	  = request.getParameter("agentID");			//agentID
			String callType			  = request.getParameter("callType");			//呼叫类型
			String showOtherVal		  = request.getParameter("showOtherVal");		//是否显示其他人工单
			if(StringUtil.isNullOrEmpty(agentID))agentID = "10000";
			//###########begin############工单报表###########begin############
			String sql = "SELECT 												      "+
						 "		 bill.receiveid,                                      "+     
						 "       to_char(bill.receivetime,'yyyy-mm-dd hh24:mi:ss') as receivetime,"+
						 "       to_char(bill.endtime,'yyyy-mm-dd hh24:mi:ss') as endtime,    "+
						 "       dic2.TYPENAME   as dealstate,                        "+
						 "       bill.eventno,                                        "+
						 "       dic3.TYPENAME   as receivemethod,                    "+
						 
						 "       bill.EVENTTYPE   as eventtype,                       "+
						 
						 "       dic4.TYPENAME   as eventfrom,                        "+
						 "       dic1.TYPENAME   as calltype,                         "+
						 "       percus.strname   as IPCCCusname,                     "+
						 "       case when percus.ntitle = '1' then '先生'            	  "+
						 "            when percus.ntitle = '2' then '小姐'               "+
						 "            when percus.ntitle = '3' then '女士'               "+
						 "       end as IPCCCustSex,                                  "+
						 "       percus.strmobilephone   as IPCCCusTel,               "+
						 "       bill.contactname,                                    "+
						 "       bill.contacttel,                                     "+
						 
						 "       bill.CONTACTPERSONMEMO,                              "+
						 
						 "       bill.backupcontactname,                              "+
						 "       bill.backupcontacttel,                               "+
						 
						 "       bill.BACKUPPERSONMEMO,                               "+
						 
						 "       case when bill.isreserve = '1' then '是'             "+
						 "            when bill.isreserve = '2' then '否'             "+
						 "       end as isreserve,                                    "+
						 "       to_char(bill.reserveTime,'yyyy-mm-dd hh24:mi:ss') as reserveTime, "+
						 "       percus.strofficephone,                               "+
						 "       percus.strhomephone,                                 "+
						 "       percus.strmobilephone,                               "+
						 "       percus.strcorporation,                               "+
						 "       percus.straddress1,                                  "+
						 "       percus.strpostcode1,                                 "+
						 "       percus.straddress2,                                  "+
						 "       percus.strpostcode2,                                 "+
						 "       percus.stremail,                                     "+
						 "       bill.idnumber,                                       "+
						 "       bill.area,                                           "+
						 "       product.strname,                                     "+
						 
						 
						 "       product.strextend1,                                  "+
						 "       product.strextend2,                                  "+
						 "       product.strstandard,                                  "+
						 
						 
						 "       billproduct.productbatno,                            "+
						 "       billproduct.productlimittime,                        "+
						 "       product.strextend4,                                  "+
						 "       product.strmanufacturer,                             "+
						 
						 "       billproduct.avoidwrongno,                            "+
						 "       bill.interactionid,                                  "+
						 "       bill.eventmark,                                      "+
						 "       bill.contactmark,                                    "+
						 "       bill.tracemark,                                      "+
						 
						 "       bill.billMemo,                                       "+
						 
						 "       dic5.TYPENAME   as channel,                          "+
						 "       bill.customername,                                   "+
						 
						 "       case when bill.ISAUTHORIZATION  = '1' then '是'         "+
						 "       	  when bill.ISAUTHORIZATION  = '2' then '否'         "+
						 "       end as isauthorization,                              "+
						 
						 "       bill.businesspersonname,                             "+
						 "       bill.contacttelphone,                                "+
						 "       dic6.TYPENAME   as skinstate,                        "+
						 "       bill.usecycle,                                       "+
						 "       bill.usemethod,                                      "+
						 "       bill.symptommark,                                    "+
						 "       case when bill.isfirstuseproduct = 1 then '是'       "+
						 "            when bill.isfirstuseproduct = 2 then '否'       "+
						 "       end as isfirstuseproduct,                            "+
						 "       bill.beforeproduct,                                  "+
						 "       bill.othercontactresourse,                           "+
						 "       bill.illhistory,                                     "+
						 "       bill.age,                                            "+
						 "       bill.job,                                            "+
						 "       case when bill.canreceivetel = 1 then '是'           "+
						 "            when bill.canreceivetel = 2 then '否'           "+
						 "       end as canreceivetel,                                "+
						 "       bill.billsummarypath                                 "+
						 "  FROM tb_ips_bill as bill                                  "+
						 "  LEFT JOIN TB_IPS_DICTIONARY dic1                          "+
						 "       on bill.calltype = dic1.TYPEVALUE                    "+
						 "       and dic1.MAINTYPE = 'callType'                       "+
						 "  LEFT JOIN TB_IPS_DICTIONARY dic2                          "+
						 "       on bill.dealstate = dic2.TYPEVALUE                   "+
						 "       and dic2.MAINTYPE = 'dealState'                      "+
						 "  LEFT JOIN TB_IPS_DICTIONARY dic3                          "+
						 "       on bill.receivemethod = dic3.TYPEVALUE               "+
						 "       and dic3.MAINTYPE = 'receiveMethod'                  "+
						 "  LEFT JOIN TB_IPS_DICTIONARY dic4                          "+
						 "       on bill.eventfrom = dic4.TYPEVALUE                   "+
						 "       and dic4.MAINTYPE = 'eventFrom'                      "+
						 "  LEFT JOIN TB_IPS_DICTIONARY dic5                          "+
						 "       on bill.channel = dic5.TYPEVALUE                     "+
						 "       and dic5.MAINTYPE = 'channel'                        "+
						 "  LEFT JOIN TB_IPS_DICTIONARY dic6                          "+
						 "       on bill.skinstate = dic6.TYPEVALUE                   "+
						 "       and dic6.MAINTYPE = 'skinState'                      "+
						 "  LEFT JOIN tb000000personalcustomer percus                 "+
						 "	     on percus.lid=bill.customerid                        "+
						 "  LEFT JOIN tb_ips_bill_product billproduct                 "+
						 "	     on billproduct.billid=bill.eventno                   "+
						 "  LEFT JOIN tb000000product product                         "+
						 "	     on product.lid=billproduct.productid                 "+
						 
						 "  LEFT JOIN tb000000employee emp on bill.agentid = CAST(emp.lid AS varchar(10)) "+
				         "  WHERE 1=1 ";
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
		//	System.out.println("sql--->"+sql);
			List billReportList = EZDbUtil.getStringArrayList(sql);
			//###########end############工单报表###########end############
		%>
	</head>

	<body leftmargin="0" topmargin="10" rightmargin="0" >
		<table  width="100%" border="1" cellspacing="1" cellpadding="2">
			<tr>
		    	<td nowrap="nowrap" align="center">事件编号</td>
		    	<td nowrap="nowrap" align="center">创建人</td>
				<td nowrap="nowrap" align="center">创建日期</td>
				<td nowrap="nowrap" align="center">结案日期</td>
				<td nowrap="nowrap" align="center">处理状态</td>
				<td nowrap="nowrap" align="center">接收方式</td>
				
				<td nowrap="nowrap" align="center">获知渠道</td>
				
				<td nowrap="nowrap" align="center">事件来源</td>
				<td nowrap="nowrap" align="center">呼叫类型</td>
				<td nowrap="nowrap" align="center">消费者姓名</td>
				<td nowrap="nowrap" align="center">称谓</td>
				 
				<td nowrap="nowrap" align="center">联系人(一)</td>
				<td nowrap="nowrap" align="center">联系电话(一)</td>
				
				<td nowrap="nowrap" align="center">联系人备注(一)</td>
				
				<td nowrap="nowrap" align="center">联系人(二)</td>
				<td nowrap="nowrap" align="center">联系电话(二)</td>
				
				<td nowrap="nowrap" align="center">联系人备注(二)</td>
				
				<td nowrap="nowrap" align="center">是否预约</td>
				<td nowrap="nowrap" align="center">预约时间</td>
				<td nowrap="nowrap" align="center">公司电话</td>
				<td nowrap="nowrap" align="center">家庭电话</td>
				<td nowrap="nowrap" align="center">手机</td>
				<td nowrap="nowrap" align="center">公司名称</td>
				<td nowrap="nowrap" align="center">公司地址</td>
				<td nowrap="nowrap" align="center">邮编</td>
				<td nowrap="nowrap" align="center">家庭地址</td>
				<td nowrap="nowrap" align="center">邮编</td>
				<td nowrap="nowrap" align="center">电子邮箱</td>
				<td nowrap="nowrap" align="center">身份证号</td>
				<td nowrap="nowrap" align="center">所在地区</td>
				<td nowrap="nowrap" align="center">产品名称</td>
				
				<td nowrap="nowrap" align="center">品牌</td>
				<td nowrap="nowrap" align="center">系列</td>
				<td nowrap="nowrap" align="center">规格</td>
				
				<td nowrap="nowrap" align="center">生产批次</td>
				<td nowrap="nowrap" align="center">限用日期</td>
				<td nowrap="nowrap" align="center">商品条码</td>
				<td nowrap="nowrap" align="center">工厂</td>
				<td nowrap="nowrap" align="center">防串货码</td>
				<td nowrap="nowrap" align="center">反馈明细</td>
				<td nowrap="nowrap" align="center">事件描述</td>
				<td nowrap="nowrap" align="center">沟通情况</td>
				<td nowrap="nowrap" align="center">跟踪情况</td>
				
				<td nowrap="nowrap" align="center">工单备注</td>
				
				<td nowrap="nowrap" align="center">渠道</td>
				<td nowrap="nowrap" align="center">客户名称</td>
				
				<td nowrap="nowrap" align="center">是否授权</td>
				
				<td nowrap="nowrap" align="center">业务员名称</td>
				<td nowrap="nowrap" align="center">业务员联系电话</td>
				<td nowrap="nowrap" align="center">皮肤状态</td>
				<td nowrap="nowrap" align="center">使用周期</td>
				<td nowrap="nowrap" align="center">使用方法</td>
				<td nowrap="nowrap" align="center">症状描述</td>
				<td nowrap="nowrap" align="center">是否首次使用</td>
				<td nowrap="nowrap" align="center">其他接触源</td>
				<td nowrap="nowrap" align="center">之前使用过的其他化妆品</td>
				<td nowrap="nowrap" align="center">病史</td>
				<td nowrap="nowrap" align="center">年龄</td>
				<td nowrap="nowrap" align="center">职业</td>
				<td nowrap="nowrap" align="center">愿意接受电话访问</td>
		  	</tr>
		  	<%
		  	if(billReportList!=null){
				for(int i=0;i<billReportList.size();i++){
					Object[] s =(Object[])billReportList.get(i);
					if(s!=null){
						String receiveid		=	String.valueOf(s[0]);
				        String receivetime		=	String.valueOf(s[1]);
				        String endtime			=	String.valueOf(s[2]);
				        String dealstate		=	String.valueOf(s[3]);
				        String eventno			=	String.valueOf(s[4]);
				        String receivemethod	=	String.valueOf(s[5]);
				        
				        String eventtype        =   String.valueOf(s[6]);
				        
				        String eventfrom		=	String.valueOf(s[7]);
				        
				        
				        
				        String calltype			=	String.valueOf(s[8]);
				        String IPCCCustName		= 	String.valueOf(s[9]);
				        String IPCCCustSex		= 	String.valueOf(s[10]);
				        String IPCCCustTel		= 	String.valueOf(s[11]);
				        String contactname		=	String.valueOf(s[12]);
				        String contacttel		=	String.valueOf(s[13]);
				        
				        String contactpersonmemo		=	String.valueOf(s[14]);
				        String backupcontactname=	String.valueOf(s[15]);
				        String backupcontacttel	=	String.valueOf(s[16]);
				        
				        String backuppersonmemo	=	String.valueOf(s[17]);
				        String isreserve		=	String.valueOf(s[18]);
				        String reservetime      =   String.valueOf(s[19]);
				        String strofficephone	=	String.valueOf(s[20]);
				        String strhomephone		=	String.valueOf(s[21]); 
				        String strmobilephone	=	String.valueOf(s[22]);
				        String strcorporation	=	String.valueOf(s[23]);
				        String straddress1		=	String.valueOf(s[24]);
				        String strpostcode1		=	String.valueOf(s[25]);
				        String straddress2		=	String.valueOf(s[26]);
				        String strpostcode2		=	String.valueOf(s[27]);
				        String stremail			=	String.valueOf(s[28]);
				        String idnumber			=	String.valueOf(s[29]);
				        String area				=	String.valueOf(s[30]);
				        String productname		=	String.valueOf(s[31]);
				        
				        String pinpai		    =	String.valueOf(s[32]);
				        String xilie		    =	String.valueOf(s[33]);
				        String guige		    =	String.valueOf(s[34]);
				        
				        String prpductbatno		=	String.valueOf(s[35]);
				        String limittime		=	String.valueOf(s[36]);
				        String productbarcode	=	String.valueOf(s[37]);
				        String factory			=	String.valueOf(s[38]);
				        String preventfalseno	=	String.valueOf(s[39]);
				        Object interactionid	=	s[40];
				        String eventmark		=	String.valueOf(s[41]);
				        String contactmark		=	String.valueOf(s[42]);
				        String tracemark		=	String.valueOf(s[43]);
				        
				        String billmemo		 	=	String.valueOf(s[44]);
				        
				        String channel			=	String.valueOf(s[45]);
				        String customername		=	String.valueOf(s[46]);
				        
				        String isauthorization		=	String.valueOf(s[47]);
				        
				        String businesspersonname=	String.valueOf(s[48]);
				        String contacttelphone	=	String.valueOf(s[49]);
				        String skinstate		=	String.valueOf(s[50]);
				        String usecycle			=	String.valueOf(s[51]);
				        String usemethod		=	String.valueOf(s[52]);
				        String symptommark		=	String.valueOf(s[53]);
				        String isfirstuseproduct=	String.valueOf(s[54]);
				        String beforeproduct	=	String.valueOf(s[55]);
				        String othercontactresourse=String.valueOf(s[56]);
				        String illhistory		=	String.valueOf(s[57]);
				        String age				=	String.valueOf(s[58]);
				        String job				=	String.valueOf(s[59]);
				        String canreceivetel	=	String.valueOf(s[60]);
				        String billsummarypath  =	String.valueOf(s[61]);
				        
				        if(StringUtil.isNullOrEmpty(receiveid))receiveid="";
				        if(StringUtil.isNullOrEmpty(receivetime))receivetime="";
				        if(StringUtil.isNullOrEmpty(endtime) || "0001-01-01 00:00:00".equals(endtime))endtime="";
				        if(StringUtil.isNullOrEmpty(dealstate))dealstate="";
				        if(StringUtil.isNullOrEmpty(eventno))eventno="";
				        if(StringUtil.isNullOrEmpty(receivemethod))receivemethod="";
				        
				        if(StringUtil.isNullOrEmpty(eventtype))eventtype="";
				        
				        if(StringUtil.isNullOrEmpty(eventfrom))eventfrom="";
				        if(StringUtil.isNullOrEmpty(calltype))calltype="";
				        if(StringUtil.isNullOrEmpty(IPCCCustName))IPCCCustName="";
				        if(StringUtil.isNullOrEmpty(IPCCCustSex))IPCCCustSex="";
				        if(StringUtil.isNullOrEmpty(IPCCCustTel))IPCCCustTel="";
				        if(StringUtil.isNullOrEmpty(contactname))contactname="";
				        if(StringUtil.isNullOrEmpty(contacttel))contacttel="";
				        
				        if(StringUtil.isNullOrEmpty(contactpersonmemo))contactpersonmemo="";
				        
				        
				        if(StringUtil.isNullOrEmpty(backupcontactname))backupcontactname="";
				        if(StringUtil.isNullOrEmpty(backupcontacttel))backupcontacttel="";
				        
				        if(StringUtil.isNullOrEmpty(backuppersonmemo))backuppersonmemo="";
				        
				        if(StringUtil.isNullOrEmpty(isreserve))isreserve="";
				        if(StringUtil.isNullOrEmpty(reservetime) || "0001-01-01 00:00:00".equals(reservetime))reservetime="";
				        if(StringUtil.isNullOrEmpty(strofficephone))strofficephone="";
				        if(StringUtil.isNullOrEmpty(strhomephone))strhomephone="";
				        if(StringUtil.isNullOrEmpty(strmobilephone))strmobilephone="";
				        if(StringUtil.isNullOrEmpty(strcorporation))strcorporation="";
				        if(StringUtil.isNullOrEmpty(straddress1))straddress1="";
				        if(StringUtil.isNullOrEmpty(strpostcode1))strpostcode1="";
				        if(StringUtil.isNullOrEmpty(straddress2))straddress2="";
				        if(StringUtil.isNullOrEmpty(strpostcode2))strpostcode2="";
				        if(StringUtil.isNullOrEmpty(stremail))stremail="";
				        if(StringUtil.isNullOrEmpty(idnumber))idnumber="";
				        if(StringUtil.isNullOrEmpty(area))area="";
				        if(StringUtil.isNullOrEmpty(productname))productname="";
				        
				        if(StringUtil.isNullOrEmpty(pinpai))pinpai="";
				        if(StringUtil.isNullOrEmpty(xilie))xilie="";
				        if(StringUtil.isNullOrEmpty(guige))guige="";
				        
				        
				        if(StringUtil.isNullOrEmpty(prpductbatno))prpductbatno="";
				        if(StringUtil.isNullOrEmpty(limittime) || "0001-01-01 00:00:00 BC".equals(limittime))limittime="";
				        if(StringUtil.isNullOrEmpty(productbarcode))productbarcode="";
				        if(StringUtil.isNullOrEmpty(factory))factory="";
				        if(StringUtil.isNullOrEmpty(preventfalseno))preventfalseno="";
				        if(StringUtil.isNullOrEmpty(eventmark))eventmark="";
				        if(StringUtil.isNullOrEmpty(contactmark))contactmark="";
				        if(StringUtil.isNullOrEmpty(tracemark))tracemark="";
				        
				        if(StringUtil.isNullOrEmpty(billmemo))billmemo="";
				        if(StringUtil.isNullOrEmpty(channel))channel="";
				        if(StringUtil.isNullOrEmpty(customername))customername="";
				        
				        if(StringUtil.isNullOrEmpty(isauthorization))isauthorization="";
				        if(StringUtil.isNullOrEmpty(businesspersonname))businesspersonname="";
				        if(StringUtil.isNullOrEmpty(contacttelphone))contacttelphone="";
				        if(StringUtil.isNullOrEmpty(skinstate))skinstate="";
				        if(StringUtil.isNullOrEmpty(usecycle))usecycle="";
				        if(StringUtil.isNullOrEmpty(usemethod))usemethod="";
				        if(StringUtil.isNullOrEmpty(symptommark))symptommark="";
				        if(StringUtil.isNullOrEmpty(isfirstuseproduct))isfirstuseproduct="";
				        if(StringUtil.isNullOrEmpty(beforeproduct))beforeproduct="";
				        if(StringUtil.isNullOrEmpty(othercontactresourse))othercontactresourse="";
				        if(StringUtil.isNullOrEmpty(illhistory))illhistory="";
				        if(StringUtil.isNullOrEmpty(age))age="";
				        if(StringUtil.isNullOrEmpty(job))job="";
				        if(StringUtil.isNullOrEmpty(canreceivetel))canreceivetel="";
						if(StringUtil.isNullOrEmpty(billsummarypath))billsummarypath="";
				        
				        
				        //电话小结
				        String mySumPath = "";
				        mySumPath = billsummarypath;
				        //if(interactionid != null)mySumPath = EZDbUtil.getSumPath(interactionid);
			%>
					  	<tr>
		    				   <td><%=eventno              %></td>
		    				   <td><%
								    String qryAgentNameSQL = "select strname from tb000000employee where lid ="+receiveid;
									String agentName = EZDbUtil.getOnlyStringValue(qryAgentNameSQL );
											          		out.println(agentName);
									%>
								</td>            
						       <td style="vnd.ms-excel.numberformat:yyyy-mm-dd hh:mm:ss"><%=receivetime          %></td>     
						       <td style="vnd.ms-excel.numberformat:yyyy-mm-dd hh:mm:ss"><%=endtime              %></td>     
						       <td><%=dealstate            %></td>     
						       <td><%=receivemethod        %></td>  
						       
						       <td>
						         <%
					            	String eventTypeSQL =  "SELECT  typename "+
										            	   "FROM tb_ips_dictionary "+
										            	   "WHERE maintype='eventType' and isuse='1' and typevalue= '"+eventtype+"'";  
									String eventTypeName = EZDbUtil.getOnlyStringValue(eventTypeSQL ); 
					          		out.println(eventTypeName);
					              %>
						       </td>    
						       <td><%=eventfrom            %></td>     
						       <td><%=calltype             %></td> 
						       <td><%=IPCCCustName         %></td> 
						       <td><%=IPCCCustSex          %></td> 
						         
						       <td><%=contactname          %></td>     
						       <td><%=contacttel           %></td>  
						       
						       <td><%=contactpersonmemo    %></td>  
						             
						       <td><%=backupcontactname    %></td>     
						       <td><%=backupcontacttel     %></td>    
						       
						       <td><%=backuppersonmemo    %></td>  
						       
						        
						       <td><%=isreserve            %></td> 
						       <td style="vnd.ms-excel.numberformat:yyyy-mm-dd hh:mm:ss"><%=reservetime%></td> 
						       <td><%=strofficephone       %></td>     
						       <td style="vnd.ms-excel.numberformat:@"><%=strhomephone         %></td>     
						       <td><%=strmobilephone       %></td>     
						       <td><%=strcorporation       %></td>     
						       <td><%=straddress1          %></td>     
						       <td><%=strpostcode1         %></td>     
						       <td><%=straddress2          %></td>     
						       <td><%=strpostcode2         %></td>     
						       <td><%=stremail             %></td>     
						       <td><%=idnumber             %></td>     
						       <td><%=area                 %></td>     
						       <td><%=productname          %></td>  
						       
						       <td><%=pinpai        	   %></td> 
						       <td><%=xilie                %></td> 
						       <td><%=guige                %></td> 
						          
						       <td><%=prpductbatno         %></td>     
						       <td style="vnd.ms-excel.numberformat:yyyy-mm-dd hh:mm:ss"><%=limittime%></td>     
						       <td style="vnd.ms-excel.numberformat:@"><%=productbarcode%></td>     
						       <td><%=factory              %></td>     
						       <td><%=preventfalseno       %></td>     
						       <td><%=mySumPath            %></td>     
						       <td><%=eventmark            %></td>     
						       <td><%=contactmark          %></td>     
						       <td><%=tracemark            %></td> 
						       
						       <td><%=billmemo            %></td>
						           
						       <td><%=channel              %></td>     
						       <td><%=customername         %></td>  
						         
						        <td><%=isauthorization         %></td>
						        
						       <td><%=businesspersonname   %></td>     
						       <td><%=contacttelphone      %></td>     
						       <td><%=skinstate            %></td>     
						       <td><%=usecycle             %></td>     
						       <td><%=usemethod            %></td>     
						       <td><%=symptommark          %></td>     
						       <td><%=isfirstuseproduct    %></td>     
						       <td><%=beforeproduct        %></td>     
						       <td><%=othercontactresourse %></td>     
						       <td><%=illhistory           %></td>     
						       <td><%=age                  %></td>     
						       <td><%=job                  %></td>     
						       <td><%=canreceivetel        %></td>     
					  	</tr>
		  	<%}}}%>
		</table>
	</body>
</html>
