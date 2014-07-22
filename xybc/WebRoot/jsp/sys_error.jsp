<%@ page contentType="text/html;charset=GBK" isErrorPage="true" %>
<html>
<head>
	<script language="javascript">
		function MM_preloadImages() { //v3.0
		  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
		    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
		    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
		}
		function MM_swapImgRestore() { //v3.0
		  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
		}
		function MM_findObj(n, d) { //v4.01
		  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
		    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
		  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
		  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
		  if(!x && d.getElementById) x=d.getElementById(n); return x;
		}
		function MM_swapImage() { //v3.0
		  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
		   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
		}
		 function fanhui(){
		  history.back(-1);
		 }
	</script>

<title>信息提示框</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<p>
<p>
<table width="686" border="0" cellspacing="1" class="lab" bordercolor="#ffffff">
			<tr class="lab2" height="30"> 
			  <td colspan=6">
               <div align="center" > <font size="4"><strong>系统异常</strong></font>
			   </div>               
               </td>  
             </tr>
             
             <tr class="lab3" height="30"> 
                <td colspan=6"><div align="center">系统异常，请联系系统管理员.</div></td>                  
             </tr>
             <tr class="lab3"> 
		      <td colspan="6" align="center">
		        <A href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image12','','<%=request.getContextPath()%>/images/bu_fanhui_02.gif',2)"><IMG src="<%=request.getContextPath()%>/images/bu_fanhui_01.gif" name="Image12" width="59" height="23" border="0" onClick="fanhui()"></A>
                  &nbsp;&nbsp;  
             </tr>
             <%
             String msg="";
             if(exception!=null){
               msg=exception.getMessage();
               
               System.out.println("sysErrorMsg:"+(new java.util.Date()).toString()+"===>"+msg);
             }
             
             %> 
            </table>                  
</body>
</html>
