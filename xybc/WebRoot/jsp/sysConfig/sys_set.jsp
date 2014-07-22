<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.blisscloud.util.*" %>
<%
    String path = request.getContextPath();
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
	<title>系统参数配置</title>
	<link href="<%=path%>/css/style.css" type="text/css" rel="stylesheet" />
	<script language="javascript" type="text/javascript">
		/*Edit data*/
		function edit(seq_id)
		{
			//alert(seq_id);
			form1.optType.value="update";
			form1.action="<%=request.getContextPath()%>/jsp/sysConfig/sys_set_edit.jsp?seq_id="+seq_id;
			form1.submit();
		}
		/*Delete data*/
		function delet(seq_id)
		{
			//alert(seq_id);
			form1.optType.value="delete";
			form1.action="<%=request.getContextPath()%>/jsp/sysConfig/sys_set.jsp?seq_id="+seq_id;
			if(confirm("确定要删除吗?")){
				form1.submit();
			}
		}
		/*Query data*/
		function query()
		{
	       document.form1.curpage.value ="1";
	       document.form1.action="<%=request.getContextPath()%>/jsp/sysConfig/sys_set.jsp";
	       document.form1.method="post";
	       form1.submit();
	     }
	    /*Add new set*/ 
	    function addSet()
	    {
	    	var en_name = document.form1.en_name.value;
	    	//document.form1.optType.value="add";
	    	form1.action="<%=request.getContextPath()%>/jsp/sysConfig/sys_set_edit.jsp?optType=add&param_name="+en_name;
			form1.submit();
	    }
	    /* Go newPage*/ 
	    function  goNewPage()
	    {
	     form1.searchFlag.value="1";
	     document.form1.curpage.value = document.form1.selPage.value;
	     form1.action='<%=request.getContextPath()%>'+'/jsp/sysConfig/sys_set.jsp';
	     document.form1.submit();
	    }
	    /* Go toPage*/
	    function  goToPage(_curpage)
	    {
	     form1.searchFlag.value="1";
	     document.form1.curpage.value = _curpage;
	     form1.action='<%=request.getContextPath()%>'+'/jsp/sysConfig/sys_set.jsp';
	     document.form1.submit();
	   }
	</script>
	</head>
<%
		//1、获取查询条件
		String curpage	=	request.getParameter("curpage");			//当前页
    	String zh_name 	= 	request.getParameter("zh_name");			//中文名称
    	String en_name 	= 	request.getParameter("en_name");			//英文标识
    	String optType	=	request.getParameter("optType");			//optType
    	//新增操作
    	if("add".equals(optType)){
    	 String cn_name1 		= request.getParameter("cn_name");		//中文名称
    	 String param_name1 	= request.getParameter("param_name");	//参数名称
    	 String param_value1 	= request.getParameter("param_value");	//参数值
    	 String memo1 			= request.getParameter("memo");			//备注
    	 String sqla = "insert into tb_ips_sys_set(param_name,param_value,cn_name,memo,seqid) "+
    	 			   "values('"+param_name1+"','"+param_value1+"','"+cn_name1+"','"+memo1+"',nextval('seq_tb_ips_sys_set'))";
		 EZDbUtil.runSql(sqla);
    	}
    	//更新操作
    	if("update".equals(optType)){
    	 String seq_id 					= request.getParameter("seq_id");		//序列ID
    	 String cn_name1 				= request.getParameter("cn_name");		//中文名称
    	 String param_name1 			= request.getParameter("param_name");	//参数名称
    	 String param_value1 			= request.getParameter("param_value");	//参数值
    	 String param_value_camNameList = request.getParameter("param_value_camNameList");	//名单库
    	 String memo1 			= request.getParameter("memo");			//备注
    	 if(!StringUtil.isNullOrEmpty(param_value_camNameList))
    	 {
    		 param_value1 = param_value_camNameList;
 		 }
    	 String sqlb = "update tb_ips_sys_set set "+
    	 			   "param_name='"+param_name1+"'"+
    	 			   ",param_value='"+param_value1+"'"+
    	 			   ",cn_name='"+cn_name1+"'"+
    	 			   ",memo='"+memo1+"'"+
    	 			   " where seqid="+seq_id;
		 EZDbUtil.runSql(sqlb);
    	}
    	//删除操作
    	if("delete".equals(optType)){
    		String seq_id=request.getParameter("seq_id");			
    		String tmpsql="delete from tb_ips_sys_set t where t.seqid="+seq_id;
    		EZDbUtil.runSql(tmpsql);
    	}
    	//2、过滤查询条件
    	if(StringUtil.isNullOrEmpty(zh_name)){
			zh_name="";
		}
		if(StringUtil.isNullOrEmpty(en_name)){
			en_name="";
		}
    	//3、查询数据
		String sql="select t.cn_name,t.param_name,t.param_value,t.memo,t.seqid from tb_ips_sys_set t where 1=1";
		if(!StringUtil.isNullOrEmpty(zh_name)){
			sql += "and t.cn_name like '%"+zh_name+"%'";
		}
		if(!StringUtil.isNullOrEmpty(en_name)){
			sql += "and t.param_name like '%"+en_name+"%'";
		}
		sql +=" order by t.seqid desc";
		//out.println("sql--->"+sql);
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
<body topmargin="0">
	<table width="100%" border="0">
	<tr><td>
		<table align="center"  width="100%"  border="0" cellspacing="1" cellpadding="2" class="lab2">
		 <form  name="form1"  method="post" action="">
		 <input name="curpage" type="hidden" value="<%=curpage%>"/>
		 <input type="hidden" name="searchFlag" value="1">
		 <input type="hidden" name="cn_name" value=""/> 
		 <input type="hidden" name="param_name" value=""/> 
		 <input type="hidden" name="param_value" value=""/> 
		 <input type="hidden" name="memo" value=""/>
		 <input type="hidden" name="optType" value=""/>  		
			  <tr class="lab2">
				<td  colspan="4">
					<div align="center" class="font2">
						系统参数配置
					</div>
				</td>
			  </tr>
			  <tr class="lab3">
			  	<td  nowrap="nowrap" align="right">中文名称：</td>
			  	<td  nowrap="nowrap">
			  		<input name="zh_name" value="<%=zh_name%>" class="box01" >
			  	</td>
			  	<td  nowrap="nowrap" align="right">英文标识：</td>
			  	<td  nowrap="nowrap">
			  		<input name="en_name" value="<%=en_name%>" class="box01" >
			  	</td>
			  </tr>
			  <tr class="lab3">
			     <td colspan="6" align="right"> 
			     	<input onclick="javascript:query();" type="button" value="查询" style="width: 80px;">
			     	&nbsp;&nbsp;&nbsp;&nbsp;
			     	<input onclick="javascript:addSet();" type="button" value="新增" style="width: 80px;">
			     </td>
			  </tr>
			  </form>
			</table>
			</td>
			</tr>
			</table>
		
		 <%if(list!=null){%>
		 <br>
		 <table align="center"  width="100%" border="0" cellpadding="2" cellspacing="1" class="lab2">
		  <tr>
			<td width="10%" nowrap="nowrap"><div align="center">序号</div></td>
			<td width="30%" nowrap="nowrap"><div align="center">中文名称</div></td>
			<td width="30%" nowrap="nowrap"><div align="center">英文标识</div></td>
			<td width="20%" nowrap="nowrap"><div align="center">数值</div></td>
			<td width="10%" nowrap="nowrap"><div align="center">操作</div></td>
		  </tr>
		 <%
			for(int i=0;i<list.size();i++){
			Object[] s =(Object[])list.get(i);
			if(s!=null){%>
			<tr class="lab3">
				<td nowrap="nowrap" align="center"><%=i+1%></td>
				<td nowrap="nowrap"><%=s[0]%></td>
				<td nowrap="nowrap"><%=s[1]!=null?s[1]:""%></td>
				<td nowrap="nowrap"><%=s[2]%></td>
				<td nowrap="nowrap" align="center">
				 <a href="javascript:edit('<%=s[4]%>');">编辑</a>
				<!--  |<a href="javascript:delet('<%=s[4]%>');">删除</a>  -->
				</td>
			</tr>
			<%}
			}%>
			
			<tr class="lab3" align="right">
	  			<td colspan="5"><%=pageBean.getFooter()%></td>
		   </tr>   
		  </table>
		<%}%>    
	
</body>
</html>

