<%@ page contentType="text/html;charset=GBK" %>
<%@page import="java.util.*"%>
<%@page import="com.blisscloud.util.*"%>
<%
    String rootPath = request.getContextPath();

	String optType	 	  = request.getParameter("optType");	//optType
	String optID 	  	  = request.getParameter("optID");		//optID
	String mainType 	  = request.getParameter("mainType");	//mainType
	String typeName   	  = request.getParameter("typeName");	//typeName
	String typeValue 	  = request.getParameter("typeValue");	//typevalue
	String orderNum  	  = request.getParameter("orderNum");	//ordernum
	String isUse 		  = request.getParameter("isUse");		//isuse
	String remark 		  = request.getParameter("remark");		//remark
	
	if(StringUtil.isNullOrEmpty(mainType))mainType="";
	if(StringUtil.isNullOrEmpty(typeName))typeName="";
	if(StringUtil.isNullOrEmpty(typeValue))typeValue="";
	if(StringUtil.isNullOrEmpty(orderNum))orderNum="";
	if(StringUtil.isNullOrEmpty(isUse))isUse="";
	if(StringUtil.isNullOrEmpty(remark))remark="";
	//add Dict
	if(!StringUtil.isNullOrEmpty(optType) &&
		"add".equals(optType)  
	  )
	{
		String insSQL = "INSERT into tb_ips_dictionary ( "+
								"SEQID,MAINTYPE,TYPEVALUE,TYPENAME,ordernum,isuse,remark) "+
					   "VALUES(nextval('Seq_TB_IPS_DICTIONARY'),"+
							   "'"+mainType+"',"+
							   "'"+typeValue+"',"+
							   "'"+typeName+"',"+
							   "'"+orderNum+"',"+
							   "'"+isUse+"',"+
							   "'"+remark+"' "+
							   ")";
		//out.println("insSQL-->"+insSQL);
		boolean okFlag = EZDbUtil.runSql(insSQL);
	}
	//modify Dict
	if(!StringUtil.isNullOrEmpty(optType) &&
		"modi".equals(optType) &&
		!StringUtil.isNullOrEmpty(optID) 
	  )
	{
		String mySQL = "UPDATE tb_ips_dictionary "+
					   "SET maintype='"+mainType+"',"+
					   		"typename='"+typeName+"',"+
					   		"typevalue='"+typeValue+"',"+
					   		"ordernum='"+orderNum+"',"+
					   		"isuse='"+isUse+"',"+
					   		"remark='"+remark+"' "+
					   "WHERE seqid="+optID;
		//out.println("mySQL-->"+mySQL);
		boolean okFlag = EZDbUtil.runSql(mySQL);
	}
%>
<html>
	<head>
		<title>字典表管理</title>
		<link href="<%=rootPath%>/css/chrome.css" rel="stylesheet" type="text/css">
		<script src="<%=rootPath%>/script/EvanGrid.js"></script>
		<script language="javascript" type="text/javascript">
		/*open Detail*/ 
	    function  openDetail(optType,optID,maintypeName)
	    {
		    var width = screen.width-700;
			var height = screen.height-480;
			var x = 400;
			var y = 200;
			var openURL = "code_edit.jsp?optType="+optType+
										  "&optID="+optID+
										  "&maintype_name="+maintypeName;
			parent.window.open(openURL,"open_detail","width="+width+"',height="+height+
					                      ",left="+x+",top="+y+
					                      ",scrollbars=yes,menubar=no,statusbar=no,");
	    }
		/*Go new page*/
		function  goNewPage()
		{
			 document.form1.curpage.value = document.form1.selPage.value;
		     form1.action="<%=request.getContextPath()%>/jsp/sysConfig/code_list.jsp";
		     document.form1.submit();
		}
		/*Go to Page*/
	    function  goToPage(_curpage)
	    {
	     document.form1.curpage.value = _curpage;
	     form1.action="<%=request.getContextPath()%>/jsp/sysConfig/code_list.jsp";
	     document.form1.submit();
	   }
		</script>
		<%
		String maintype_name	= request.getParameter("maintype_name");
		String curpage			= request.getParameter("curpage");
		String flag				= request.getParameter("flag");
		String sql = "";
		
		StringBuffer buffer=new StringBuffer();
		buffer.append("SELECT seqid, parentid, maintype, typename, typevalue, ordernum ");
		buffer.append("FROM tb_ips_dictionary ");
		buffer.append("WHERE 1=1 ");
  		if(!StringUtil.isNullOrEmpty(maintype_name) 
  				&&!"-1".equals(maintype_name))
  		{
  			buffer.append(" and maintype='"+maintype_name+"'");  
  		}
  	 	buffer.append(" order by maintype,ordernum"); 
   		sql=buffer.toString();
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
		</head>
<body leftmargin="0" topmargin="0" rightmargin="0" style="overflow: hidden;"> 
 		<form  name="form1"  method="post" action="code_list.jsp">
		 <input type="hidden" name="seqId" value=""/>
		 <input type="hidden" name="flag" value=""/>
		 <input type="hidden" name="maintype_name" value="<%=maintype_name%>"/>
		 <input name="curpage" type="hidden" value="<%=curpage%>"/>
	 	 <div id="dataTable">
			<table  width="100%" border="0" cellspacing="0" cellpadding="0" id="theObjTable" style="table-layout:fixed;">
			  <tbody id="dataArea">
			 	<%
				 if(list!=null)
					{
						for(int i=0;i<list.size();i++){
						Object[] s =(Object[])list.get(i);
						if(s!=null)
						{
							String seqid 	 		=	String.valueOf(s[0]);		//seqid
							String parentid			=	String.valueOf(s[1]);		//parentid
							String maintype			=  	String.valueOf(s[2]);		//maintype
							String typename			=  	String.valueOf(s[3]);		//typename
							String typevalue		=  	String.valueOf(s[4]);		//typevalue
							String ordernum 		=	String.valueOf(s[5]);		//ordernum
							
							if(StringUtil.isNullOrEmpty(seqid))seqid="";
							if(StringUtil.isNullOrEmpty(parentid))parentid="";
							if(StringUtil.isNullOrEmpty(maintype))maintype="";
							if(StringUtil.isNullOrEmpty(typename))typename="";
							if(StringUtil.isNullOrEmpty(typevalue))typevalue="";
							if(StringUtil.isNullOrEmpty(ordernum))ordernum="";
					%>
						<TR>
							<td align="center" nowrap="nowrap"><%=i+1%></td>
							<td nowrap="nowrap"><%=maintype%></td>
							<td nowrap="nowrap"><%=typename%></td>
							<td nowrap="nowrap"><%=typevalue%></td>
							<td nowrap="nowrap"><%=ordernum%></td>
							<td nowrap="nowrap">
							 <a href="javascript:openDetail('modi','<%=seqid%>','<%=maintype_name%>');">编辑</a>|
							 <a href="javascript:openDetail('add','<%=maintype%>','<%=maintype_name%>');">添加</a>
							</td>
						</tr>
						<%}}}%>
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
	["编码",150],	
	["代码名称",80],
	["代码值",120],
	["排列序号",120],
	["操作",]
	];
	initGrid(fields,"<%=rootPath%>/css/EvanGrid.css","<%=rootPath%>/images/evanGrid");
</script>


