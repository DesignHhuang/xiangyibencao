<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@page import="com.blisscloud.util.EZDbUtil"%>
<%@page import="com.blisscloud.util.StringUtil"%>
<%
	String contextPath = request.getContextPath();
	String summaryID 	= request.getParameter("summaryID");//summaryID 
	if(StringUtil.isNullOrEmpty(summaryID))summaryID = "-1";
	
	//add by jeton.dong 2013-05-24
	String needTreeID = "10002";//现场环境为10002
%>
<html>
  <head>
    <title>电话小结树</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<script type="text/javascript" src="<%=contextPath %>/script/dtree.js"></script>
    <link rel="stylesheet" href="<%=contextPath %>/css/dtree.css" type="text/css"></link>
  </head>
  
  <body topmargin="0" leftmargin="0">
  	<a href="javascript: jetonSummaryTree.openAll();" class="dtree">展开所有节点</a>&nbsp;<a href="javascript: jetonSummaryTree.closeAll();" class="dtree">收缩所有结点</a>
    <script type="text/javascript">
		var jetonSummaryTree = new dTree('jetonSummaryTree');
		jetonSummaryTree.config.controlType='radio';
		jetonSummaryTree.config.useCookies = true;
		jetonSummaryTree.add('2','-1','联络事项','true','','','javascript:gotoUrl(0)');
	    <%
			String sql="select lid,lparentid,strname,'false' as isleaf "+
					   " from tb000000summarytype "+
					   " where lid in "+
					   " ( "+
					   "	SELECT lid FROM connectby('tb000000summarytype', 'lid', 'lparentid', '"+needTreeID+"', 0, '->') "+
					   "	  	AS t(lid bigint, parentid bigint, level int, branch text) "+
					   " ) "+
					   " and lid >1 "+
					   " order by lparentid,lid";
			//System.out.println("sql--->"+sql);
			List list = EZDbUtil.getStringArrayList(sql);
			if(list != null && list.size()>0){	
				for(int k=0;k<list.size();k++){
						String[] info=(String[])list.get(k);
						String ID=String.valueOf(info[0]);				//序列ID
						String parentID=String.valueOf(info[1]);		//父ID
						String nodeName=String.valueOf(info[2]);		//结点名称
						
						if(StringUtil.isNullOrEmpty(ID))ID="";
						if(StringUtil.isNullOrEmpty(parentID))parentID="";
						if(StringUtil.isNullOrEmpty(nodeName))nodeName="";
					%>
						jetonSummaryTree.add('<%=ID%>','<%=parentID%>','<%=nodeName%>','true','false','true','');
					<%
					}
				}
			
			
			String summaryNodeSQL = "SELECT lid, ltypeid as lparentid, strname,'true' as leaf "+
									 " FROM tb000000summary "+
									 " WHERE ltypeid in "+
									 " ("+
									 "	SELECT lid FROM connectby('tb000000summarytype', 'lid', 'lparentid', '"+needTreeID+"', 0, '->') "+
									 " 	AS t(lid bigint, parentid bigint, level int, branch text) "+
									 " ) "+
									" and lid>1 "+
									" order by lparentid";
			List nodeList = EZDbUtil.getStringArrayList(summaryNodeSQL);
			if(nodeList != null && nodeList.size()>0){	
				for(int k = 0;k < nodeList.size();k++){
						String[] info=(String[])nodeList.get(k);
						String ID=String.valueOf(info[0]);				//序列ID
						String parentID=String.valueOf(info[1]);		//父ID
						String nodeName=String.valueOf(info[2]);		//结点名称
						
						if(StringUtil.isNullOrEmpty(ID))ID="";
						if(!StringUtil.isNullOrEmpty(ID))ID="url_"+ID;
						if(StringUtil.isNullOrEmpty(parentID))parentID="";
						if(StringUtil.isNullOrEmpty(nodeName))nodeName="";
					%>
						jetonSummaryTree.add('<%=ID%>','<%=parentID%>','<%=nodeName%>','false','false','false','');
					<%
					}
				}
			
			out.println("document.write(jetonSummaryTree.toString());");
			//默认选中设定节点
			if(!"-1".equals(summaryID))
			{
				out.println("var jetonId = jetonSummaryTree.getMyNodeId('"+summaryID+"');");
				out.println("jetonSummaryTree.setChecked(jetonId,'false');");
				out.println("jetonSummaryTree.openTo(jetonId,false);");
			}else
			{
				//out.println("jetonSummaryTree.openAll();");
				out.println("jetonSummaryTree.closeAll();");

			}
		%>
	</script>   
  </body>
</html>
