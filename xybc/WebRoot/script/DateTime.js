document.write("<div id=meizzCalendarLayer1 style='position: absolute; z-index: 9999; width: 180; height: 211; display: none'>");
document.write("<iframe name=meizzCalendarIframe1 scrolling=no frameborder=0 width=100% height=100%></iframe></div>");

document.write("<div id=meizzCalendarLayer2 style='position: absolute; z-index: 9999; width: 180; height: 44; display: none'>");
document.write("<iframe name=meizzCalendarIframe2 scrolling=no frameborder=0 width=100% height=44></iframe></div>");

var varDateTime = new DateTime();
var varDate		= new DateTime();
var varTime		= new DateTime();
var varUsed		= null;

var varFontFamily		="??";
var varFontSize			="12";
var varTipSelectYear	="????????";
var varTipSelectMonth	="????????";
var varSunday			="?";
var varMonday			="?";
var varTuesday			="?";
var varWednesday		="?";
var varThirsday			="?";
var varFriday			="?";
var varSaturday			="?";
var varYear				="?";
var varMonth			="?";
var varClear			="??";
var varOK				="??";
var varClose			="??";

/*
varFontFamily		="Arial";
varFontSize			="12";
varTipSelectYear	="Click to select year";
varTipSelectMonth	="Click to select month";
varSunday			="Sun";
varMonday			="Mon";
varTuesday			="Tue";
varWednesday		="Wed";
varThirsday			="Thi";
varFriday			="Fri";
varSaturday			="Sat";
varYear				="Year";
varMonth			="Mon";
varClear			="Clear";
varOK				="OK";
varClose			="Close";
*/



function writeIframe(varCalendar)
{
	//Header???
	var strIframe = "<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'>";
	
	strIframe += "<style>*{font-size: "+varFontSize+"px; font-family: "+varFontFamily+"}";
	strIframe += ".bg{  color: "+ varCalendar.lightColor +"; cursor: default; background-color: "+ varCalendar.darkColor +";}";
	strIframe += "table#tableMain{ width: 100%; height: 10;}";
	strIframe += "table#tableWeek td{ color: "+ varCalendar.lightColor +";}";
	strIframe += "table#tableDay  td{ font-weight: bold;}";
	strIframe += "table#tableTime  td{ font-weight: bold;color: "+ varCalendar.lightColor +";}";
	strIframe +="td#meizzYearHead, td#meizzYearMonth{color: "+ varCalendar.wordColor +"}";
	
	strIframe +=".out { text-align: center; border-top: 1px solid "+ varCalendar.DarkBorder +"; border-left: 1px solid "+ varCalendar.DarkBorder +";";
	strIframe +="border-right: 1px solid "+ varCalendar.lightColor +"; border-bottom: 1px solid "+ varCalendar.lightColor +";}"+
		".over{ text-align: center; border-top: 1px solid #FFFFFF; border-left: 1px solid #FFFFFF;"+
		"border-bottom: 1px solid "+ varCalendar.DarkBorder +"; border-right: 1px solid "+ varCalendar.DarkBorder +"}"+
		"input{ border: 1px solid "+ varCalendar.darkColor +"; padding-top: 1px; height: 18; cursor: hand;"+
		"       color:"+ varCalendar.wordColor +"; background-color: "+ varCalendar.btnBgColor +"}";

	strIframe +="</style>";

	strIframe +="</head><body onkeydown='return false' onselectstart='return false' style='margin: 0px' oncontextmenu='return false'><form name=meizz>";

	//?????
	strIframe += "<table id=tableMain class=bg border=0 cellspacing=2 cellpadding=0>";

	if(varCalendar==varDateTime || varCalendar==varDate)
	{
		var nLittleWidth=19;
		var nBigWidth	=19;
		var nYearWidth	=75;
		var nMonthWidth	=61;
		
		//????--------------------------------------
		//?
		strIframe += "<select name=tmpYearSelect  onblur='parent.hiddenSelect(this)' style='z-index:1;position:absolute;top:3;left:"+(3+nLittleWidth)+";width:"+nYearWidth+";display:none'";
		if(varCalendar==varDateTime)
			strIframe +=" onchange='parent.varDateTime.thisYear =this.value; parent.hiddenSelect(this); parent.InitCalendar(parent.varUsed);'></select>";
		else
			strIframe +=" onchange='parent.varDate.thisYear =this.value; parent.hiddenSelect(this); parent.InitCalendar(parent.varUsed);'></select>";
		
		//?
		strIframe +="<select name=tmpMonthSelect onblur='parent.hiddenSelect(this)' style='z-index:1; position:absolute;top:3;left:"+(3+nLittleWidth+nYearWidth)+";width:"+nMonthWidth+";display:none'";
		if(varCalendar==varDateTime)
			strIframe +=" onchange='parent.varDateTime.thisMonth=this.value; parent.hiddenSelect(this); parent.InitCalendar(parent.varUsed);'></select>";
		else
			strIframe +=" onchange='parent.varDate.thisMonth=this.value; parent.hiddenSelect(this); parent.InitCalendar(parent.varUsed);'></select>";
		
		if(varCalendar==varDateTime)
		{
			strIframe +="<tr><td width=100% height=19 bgcolor='"+ varCalendar.lightColor +"'>"+
				"    <table width=100% height=100% id=tableHead border=0 cellspacing=1 cellpadding=0><tr align=center height=100%>"+
				"    <td width=11% class=bg style='cursor: hand' onclick='parent.prevMonth(parent.varDateTime)'><b>&lt;</b></td>"+
				"    <td width=43% id=meizzYearHead  title='"+varTipSelectYear+"' onclick='parent.funYearSelect(parent.varDateTime)'";
			strIframe +=" onmouseover='this.bgColor=parent.varDateTime.selDayColor; this.style.color=parent.varDateTime.lightColor' onmouseout='this.bgColor=parent.varDateTime.lightColor; this.style.color=parent.varDateTime.wordColor'>";
			strIframe +="</td>";
			strIframe +="<td width=35% id=meizzYearMonth title='"+varTipSelectMonth+"' onclick='parent.funMonthSelect(parent.varDateTime)'";
			strIframe +=" onmouseover='this.bgColor=parent.varDateTime.selDayColor; this.style.color=parent.varDateTime.lightColor' onmouseout='this.bgColor=parent.varDateTime.lightColor; this.style.color=parent.varDateTime.wordColor'>";
			strIframe +="</td>";
			strIframe +="<td width=11 class=bg onclick='parent.nextMonth(parent.varDateTime)' style='cursor: hand'><b>&gt;</b></td></tr>";
			strIframe += "</table></td></tr>";
		}
		else
		{
			strIframe += "<tr><td width=100% height=19 bgcolor='"+ varCalendar.lightColor +"'>"+
				"    <table width=100% height=100% id=tableHead border=0 cellspacing=1 cellpadding=0><tr align=center height=100%>"+
				"    <td width=15 class=bg style='cursor: hand' onclick='parent.prevMonth(parent.varDate)'><b>&lt;</b></td>"+
				"    <td width=60 id=meizzYearHead  title='"+varTipSelectYear+"' onclick='parent.funYearSelect(parent.varDate)'";

			strIframe +="        onmouseover='this.bgColor=parent.varDate.selDayColor; this.style.color=parent.varDate.lightColor'";
			strIframe +="        onmouseout='this.bgColor=parent.varDate.lightColor; this.style.color=parent.varDate.wordColor'></td>"+
				"    <td width=50 id=meizzYearMonth title='"+varTipSelectMonth+"' onclick='parent.funMonthSelect(parent.varDate)'";
			strIframe +="        onmouseover='this.bgColor=parent.varDate.selDayColor; this.style.color=parent.varDate.lightColor'";
			strIframe +="        onmouseout='this.bgColor=parent.varDate.lightColor; this.style.color=parent.varDate.wordColor'></td>"+
				"    <td width=15 class=bg onclick='parent.nextMonth(parent.varDate)' style='cursor: hand'><b>&gt;</b></td></tr></table>";
			strIframe += "</td></tr>";
		}

		strIframe +="<tr width=100%><td height=20><table id=tableWeek border=1 width=100% cellpadding=0 cellspacing=0 borderColorLight='"+ varCalendar.darkColor +"' borderColorDark='"+ varCalendar.lightColor +"'>"+
		"    <tr align=center><td height=20>"+varSunday+"</td><td>"+varMonday+"</td><td>"+varTuesday+"</td><td>"+varWednesday+"</td><td>"+varThirsday+"</td><td>"+varFriday+"</td><td>"+varSaturday+"</td></tr></table>"+
		"</td></tr><tr><td valign=top width=100% bgcolor='"+ varCalendar.lightColor +"'>"+
		"    <table id=tableDay height=120 width=100% border=0 cellspacing=1 cellpadding=0>";
		
		for(var x=0; x<5; x++)
		{
			strIframe += "<tr>";
			for(var y=0; y<7; y++)
			strIframe += "<td class=out id='meizzDay"+ (x*7+y) +"'></td>";
			strIframe += "</tr>";
		}
		strIframe += "<tr>";
		for(var x=35; x<39; x++)
			strIframe += "<td class=out id='meizzDay"+ x +"'></td>";
		strIframe +="<td colspan=3 class=out>";
		strIframe +="&nbsp;";
		strIframe +="</td></tr>";
		
		strIframe +="</table></td></tr>"
	}
	
		
	if(varCalendar==varDateTime || varCalendar==varTime)
	{
		//????--------------------------------------
		strIframe +="<tr><td height=20 width=100% bgcolor='"+ varCalendar.lightColor +"'>";
		if(varCalendar==varDateTime)
		{
			strIframe +="<table id='tableTime' border=0 cellpadding=0 cellspacing=0 width=100% bgcolor='"+varCalendar.dayBgColor+"'><tr>";
			strIframe +="<td align='center'><select name=tmpHourSelect onchange='parent.varDateTime.thisHour =this.value;'></select></td>";
			strIframe +="<td align='center'><font color='"+varCalendar.wordColor+"'><b>:</b></font></td>";
			strIframe +="<td align='center'><select name=tmpMinuteSelect onchange='parent.varDateTime.thisMinute =this.value;'></select></td>";
			if(varCalendar.secondFlag!=0)
			{
				strIframe +="<td align='center'><font color='"+varCalendar.wordColor+"'><b>:</b></font></td>";
				strIframe +="<td align='center'><select name=tmpSecondSelect onchange='parent.varDateTime.thisSecond =this.value;'></select></td>";
			}
			strIframe +="</tr></table>";
		}
		else
		{
			strIframe +="<table id='tableTime' border=0 cellpadding=0 cellspacing=0 width=100%><tr>";
			strIframe +="<td align='center'><select name=tmpHourSelect onchange='parent.varTime.thisHour =this.value;'></select></td>";
			strIframe +="<td align='center'><font color='"+varCalendar.wordColor+"'><b>:</b></font></td>";
			strIframe +="<td align='center'><select name=tmpMinuteSelect onchange='parent.varTime.thisMinute =this.value;'></select></td>";
			if(varCalendar.secondFlag!=0)
			{
				strIframe +="<td align='center'><font color='"+varCalendar.wordColor+"'><b>:</b></font></td>";
				strIframe +="<td align='center'><select name=tmpSecondSelect onchange='parent.varTime.thisSecond =this.value;'></select></td>";
			}
			strIframe +="</tr></table>";
		}
		strIframe+="</td></tr>";
	}
	
	//????--------------------------------------
	strIframe +="<tr><td height=18 width=100% bgcolor='"+ varCalendar.lightColor +"'>";
	strIframe +="<table border=0 cellpadding=0 cellspacing=1 width=100% height=100% borderColorLight='"+ varCalendar.darkColor +"' borderColorDark='"+ varCalendar.lightColor +"'>";
	strIframe +="<tr><td width=33% align='center'  onmouseover='this.bgColor=parent.varDateTime.selDayColor; this.style.color=parent.varDateTime.lightColor' onmouseout='this.bgColor=parent.varDateTime.lightColor; this.style.color=parent.varDateTime.wordColor' onclick='parent.onClear()'>";
	strIframe +=varClear;
	strIframe +="</td>";
	strIframe +="<td width=33% align='center'  onmouseover='this.bgColor=parent.varDateTime.selDayColor; this.style.color=parent.varDateTime.lightColor' onmouseout='this.bgColor=parent.varDateTime.lightColor; this.style.color=parent.varDateTime.wordColor' onclick='parent.onOK()'>";
	strIframe +=varOK;
	strIframe +="</td>";
	strIframe +="<td width=33%  align='center' onmouseover='this.bgColor=parent.varDateTime.selDayColor; this.style.color=parent.varDateTime.lightColor' onmouseout='this.bgColor=parent.varDateTime.lightColor; this.style.color=parent.varDateTime.wordColor' onclick='parent.hiddenCalendar()'>";
	strIframe +=varClose;
	strIframe +="</td></tr>";
	strIframe +="</table>";
	strIframe+="</td></tr>";
	
	strIframe+="</td></tr>";
	strIframe+="<table>";
	
	strIframe+="</form></body></html>";

	with(varCalendar.iframe)
	{
		document.writeln(strIframe); document.close();

		if(varCalendar==varDateTime || varCalendar==varDate)
		{
			//????--------------------------------------
			for(var i=0; i<39; i++)
			{
				varCalendar.dayObj[i] = eval("meizzDay"+ i);
				varCalendar.dayObj[i].onmouseover = dayMouseOver;
				varCalendar.dayObj[i].onmouseout  = dayMouseOut;
				varCalendar.dayObj[i].onclick     = dayMouseClick;
			}
		}
	}
}

function DateTime() //????????
{
    this.daysMonth  = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
    this.day        = new Array(39);            //??????????
    this.dayObj     = new Array(39);            //??????????
    this.dateStyle  = null;                     //??????????
    this.objExport  = null;                     //?????????
    this.eventSrc   = null;                     //?????????
    this.inputDate  = null;                     //?????????(d/m/yyyy)
    this.thisYear   = new Date().getFullYear(); //??????????
    this.thisMonth  = new Date().getMonth()+ 1; //??????????
    this.thisDay    = new Date().getDate();     //??????????
    this.thisHour   = 0;						//??????????
    this.thisMinute = 0;						//??????????
    this.thisSecond = 0;						//??????????
    this.today      = this.thisDay +"/"+ this.thisMonth +"/"+ this.thisYear;   //??(d/m/yyyy)
    this.iframe     = null; //??? iframe ??
//    this.iframe     = window.frames("meizzCalendarIframe1"); //??? iframe ??
//    this.calendar   = getObjectById("meizzCalendarLayer1");  //????
    this.calendar   = null;  //????
    this.dateReg    = "";           //??????????
    this.yearFall   = 50;           //??????????
    this.format     = "yyyy-mm-dd"; //???????
    this.timeShow   = false;        //??????
    this.type		= 0;			//0-???1-?????2-??
    this.drag       = true;         //??????
    this.darkColor  = "#8DB9DF";    //?????
    this.lightColor = "#FFFFFF";    //?????
    this.btnBgColor = "#F5F5F5";    //????????
    this.wordColor  = "#000000";    //???????
    this.wordDark   = "#DCDCDC";    //????????
    this.dayBgColor = "#F0F0F0";    //???????
    this.todayBgColor="#FF8080";    //????????????
    this.DarkBorder = "#D4D0C8";    //??????????
    this.selDayColor= "#8DB9DF";	//?????????
    this.selectedDayObj= null;			//???????
    this.returnValue= "";			//??????
    this.selectedDate	="";		//???????2005-01-01
    this.secondFlag		=1;
}

function ShowCalendar(varCalendarId,varSecondFlag) //????
{
	hiddenCalendar();

	var varCalendar=null;
	
	if(varCalendarId==0)
	{
		varCalendar=varDateTime;
	}
	else if(varCalendarId==1)
	{
		varCalendar=varDate;
	}
	else
	{
		varCalendar=varTime;
	}
	varUsed		=varCalendar;
	
	if(varUsed==varDateTime || varUsed==varDate)
	{//????
    	varUsed.iframe     = window.frames("meizzCalendarIframe1"); //??? iframe ??
		varUsed.calendar   = getObjectById("meizzCalendarLayer1");  //????
	}
	else
	{//????
    	varUsed.iframe     = window.frames("meizzCalendarIframe2"); //??? iframe ??
		varUsed.calendar   = getObjectById("meizzCalendarLayer2");  //????
	}
	
	if(varSecondFlag!=null)
	{
		varUsed.secondFlag=varSecondFlag;
	}
	var e = window.event.srcElement;

	writeIframe(varCalendar);
	
	// ?????????
	if (varCalendar==varDateTime || varCalendar==varTime)
	{
		funHourSelect(varCalendar);
		funMinuteSelect(varCalendar);
		if(varUsed.secondFlag!=0)
		{
			funSecondSelect(varCalendar);
		}
	}

	var o = varCalendar.calendar.style;
	varCalendar.eventSrc = e;
	varCalendar.objExport = e;
		
	if(varCalendar==varDateTime || varCalendar==varDate)
	{
		varCalendar.iframe.tableWeek.style.cursor = "default";
	}

	if(varCalendar==varDateTime)
	{
		o.height=211;
	}
	else if(varCalendar==varDate)
	{
		o.height=189;
	}
	else
	{
		o.height=44;
	}
	
	
	var t = e.offsetTop,  h = e.clientHeight, l = e.offsetLeft, p = e.type;
	while (e = e.offsetParent)
	{
		t += e.offsetTop; l += e.offsetLeft;
	}
	o.display = "";
	varCalendar.iframe.document.body.focus();
	var cw = varCalendar.calendar.clientWidth, ch = varCalendar.calendar.clientHeight;
	var dw = document.body.clientWidth, dl = document.body.scrollLeft, dt = document.body.scrollTop;

//alert(ch);
	if (document.body.clientHeight + dt - t - h >= ch)
	{
		o.top=t+h;
	}
	else
	{
		if(t>=ch)
			o.top = t -ch;
		else
			o.top=t+h;
	}

	if (dw + dl - l >= cw)
		o.left = l;
	else
		o.left = (dw >= cw) ? dw - cw + dl : dl;

	if(varCalendar==varDateTime)
	{
		if(varCalendar.secondFlag!=0)
		{
			varCalendar.dateReg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
		}
		else
		{
			varCalendar.dateReg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2})$/;
		}
	}
	else if(varCalendar==varDate)
	{
		varCalendar.dateReg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;
	}
	else
	{
		if(varCalendar.secondFlag!=0)
		{
			varCalendar.dateReg = /^(\d{1,2}):(\d{1,2}):(\d{1,2})$/;
		}
		else
		{
			varCalendar.dateReg = /^(\d{1,2}):(\d{1,2})$/;
		}
	}

	try
	{
		if (varCalendar.objExport.value.trim() != "")
		{//??????????????
			varCalendar.dateStyle = varCalendar.objExport.value.trim().match(varCalendar.dateReg);

			if(varCalendar==varDateTime || varCalendar==varDate)
			{
				varCalendar.thisYear   = parseInt(varCalendar.dateStyle[1], 10);
				varCalendar.thisMonth  = parseInt(varCalendar.dateStyle[3], 10);
				varCalendar.thisDay    = parseInt(varCalendar.dateStyle[4], 10);
	
				varCalendar.inputDate  = parseInt(varCalendar.thisDay, 10) +"/"+ parseInt(varCalendar.thisMonth, 10) +"/"+parseInt(varCalendar.thisYear, 10);
			}
			if(varCalendar==varDateTime)
			{
				varCalendar.thisHour    =parseInt(varCalendar.dateStyle[5], 10);
				varCalendar.thisMinute  =parseInt(varCalendar.dateStyle[6], 10);
				if(varCalendar.secondFlag!=0)
				{
					varCalendar.thisSecond  =parseInt(varCalendar.dateStyle[7], 10);
				}
			}
			else if(varCalendar==varTime)
			{
				varCalendar.thisHour    =parseInt(varCalendar.dateStyle[1], 10);
				varCalendar.thisMinute  =parseInt(varCalendar.dateStyle[2], 10);
				if(varCalendar.secondFlag!=0)
				{
					varCalendar.thisSecond  =parseInt(varCalendar.dateStyle[3], 10);
				}
			}
		}
	}
	catch(e)
	{
	}
	
	InitCalendar(varCalendar);
}

function InitCalendar(varCalendar)
{
	if(varCalendar==varDateTime)
	{
		varCalendar.iframe.height=10;
	}
	
	if(varCalendar==varDateTime || varCalendar==varDate)
	{
	    var y = varCalendar.thisYear;
	    var m = varCalendar.thisMonth;
	    var d = varCalendar.thisDay;
	    varCalendar.daysMonth[1] = (0==y%4 && (y%100!=0 || y%400==0)) ? 29 : 28;
	    if (!(y<=9999 && y >= 1000 && parseInt(m, 10)>0 && parseInt(m, 10)<13 && parseInt(d, 10)>0))
	    {
	        varCalendar.thisYear   = new Date().getFullYear();
	        varCalendar.thisMonth  = new Date().getMonth()+ 1;
	        varCalendar.thisDay    = new Date().getDate(); }
	    y = varCalendar.thisYear;
	    m = varCalendar.thisMonth;
	    d = varCalendar.thisDay;
	    varCalendar.iframe.meizzYearHead.innerText  = y +' '+varYear;
	    varCalendar.iframe.meizzYearMonth.innerText = parseInt(m, 10) +' '+varMonth;
	    varCalendar.daysMonth[1] = (0==y%4 && (y%100!=0 || y%400==0)) ? 29 : 28; //?????29?
	    var w = new Date(y, m-1, 1).getDay();
	    var prevDays = m==1  ? varCalendar.daysMonth[11] : varCalendar.daysMonth[m-2];
	    for(var i=(w-1); i>=0; i--) //??? for ???????????? varCalendar.day???? d/m/yyyy
	    {
	        varCalendar.day[i] = prevDays +"/"+ (parseInt(m, 10)-1) +"/"+ y;
	        if(m==1) varCalendar.day[i] = prevDays +"/"+ 12 +"/"+ (parseInt(y, 10)-1);
	        prevDays--;
	    }
	    for(var i=1; i<=varCalendar.daysMonth[m-1]; i++) varCalendar.day[i+w-1] = i +"/"+ m +"/"+ y;
	    for(var i=1; i<39-w-varCalendar.daysMonth[m-1]+1; i++)
	    {
	        varCalendar.day[varCalendar.daysMonth[m-1]+w-1+i] = i +"/"+ (parseInt(m, 10)+1) +"/"+ y;
	        if(m==12) varCalendar.day[varCalendar.daysMonth[m-1]+w-1+i] = i +"/"+ 1 +"/"+ (parseInt(y, 10)+1);
	    }

        if(varCalendar.selectedDayObj==null)
        {//????
		    for(var i=0; i<39; i++)    //?????????????????
		    {
		        var a = varCalendar.day[i].split("/");
				varCalendar.dayObj[i].className="out";
		        varCalendar.dayObj[i].innerText    = a[0];
		        varCalendar.dayObj[i].title        = a[2] +"-"+ appendZero(a[1]) +"-"+ appendZero(a[0]);
		        varCalendar.dayObj[i].style.backgroundColor      = varCalendar.dayBgColor;
		        varCalendar.dayObj[i].style.color  = varCalendar.wordColor;
		        if ((i<10 && parseInt(varCalendar.day[i], 10)>20) || (i>27 && parseInt(varCalendar.day[i], 10)<12))
		        {
		            varCalendar.dayObj[i].style.color = varCalendar.wordDark;
				}
		        
		        if (varCalendar.inputDate==varCalendar.day[i])
		        {//?????
					varCalendar.selectedDayObj=varCalendar.dayObj[i];
					varCalendar.selectedDayObj.className="click";
					varCalendar.selectedDate=getSelectedDate();
		        }
	
		        if (varCalendar.day[i] == varCalendar.today && varCalendar.inputDate!=varCalendar.day[i])      //???????????????
		        {
					varCalendar.dayObj[i].style.backgroundColor = varCalendar.todayBgColor;
					varCalendar.dayObj[i].style.color = varCalendar.lightColor;
					if(varCalendar.selectedDayObj==null)
					{
						varCalendar.selectedDayObj=varCalendar.dayObj[i];
						varCalendar.selectedDate=getSelectedDate();
					}
		        }
		        else if(varCalendar.dayObj[i].title==varCalendar.selectedDate)
		        {//???????
		        	varCalendar.dayObj[i].style.backgroundColor = varCalendar.selDayColor;
		        	varCalendar.dayObj[i].style.color = varCalendar.lightColor;
		        }
		    }
	    }
		else
		{
		    for(var i=0; i<39; i++)    //?????????????????
		    {
				varCalendar.dayObj[i].className="out";
				varCalendar.dayObj[i].style.removeAttribute('backgroundColor');

		        var a = varCalendar.day[i].split("/");
		        varCalendar.dayObj[i].innerText    = a[0];
		        varCalendar.dayObj[i].title        = a[2] +"-"+ appendZero(a[1]) +"-"+ appendZero(a[0]);
		        varCalendar.dayObj[i].style.backgroundColor      = varCalendar.dayBgColor;
		        varCalendar.dayObj[i].style.color  = varCalendar.wordColor;
		        if ((i<10 && parseInt(varCalendar.day[i], 10)>20) || (i>27 && parseInt(varCalendar.day[i], 10)<12))
		        {
		            varCalendar.dayObj[i].style.color = varCalendar.wordDark;
				}
		        
		        if (varCalendar.day[i] == varCalendar.today)      //???????????????
		        {
					varCalendar.dayObj[i].style.backgroundColor = varCalendar.todayBgColor;
					varCalendar.dayObj[i].style.color = varCalendar.lightColor;
					if(varCalendar.selectedDayObj==null)
					{
						varCalendar.selectedDayObj=varCalendar.dayObj[i];
						varCalendar.selectedDate=getSelectedDate();
					}
		        }
		        
		        if(varCalendar.dayObj[i].title==varCalendar.selectedDate)
		        {//???????
		        	varCalendar.dayObj[i].style.backgroundColor = varCalendar.selDayColor;
		        	varCalendar.dayObj[i].style.color = varCalendar.lightColor;
		        	varCalendar.dayObj[i].className		="click";
		        }
		    }
		}
    }

 	if(varCalendar==varDateTime || varCalendar==varTime)
	{
		if(varCalendar.secondFlag!=0)
		{
		  	var hour = varCalendar.iframe.document.forms[0].tmpHourSelect;
			var min = varCalendar.iframe.document.forms[0].tmpMinuteSelect;
			var sec = varCalendar.iframe.document.forms[0].tmpSecondSelect;
			
			hour.value	=varCalendar.thisHour;
			min.value	=varCalendar.thisMinute;
			sec.value	=varCalendar.thisSecond;
		}
		else
		{
		  	var hour = varCalendar.iframe.document.forms[0].tmpHourSelect;
			var min = varCalendar.iframe.document.forms[0].tmpMinuteSelect;
			
			hour.value	=varCalendar.thisHour;
			min.value	=varCalendar.thisMinute;
		}
	}
}

function funMonthSelect(varCalendar) //??????
{
    var m = isNaN(parseInt(varCalendar.thisMonth, 10)) ? new Date().getMonth() + 1 : parseInt(varCalendar.thisMonth);
    var e = varCalendar.iframe.document.forms[0].tmpMonthSelect;
    for (var i=1; i<13; i++) e.options.add(new Option(i +' '+varMonth, i));
    e.style.display = ""; e.value = m; e.focus(); window.status = e.style.top;
}

function funYearSelect(varCalendar) //??????
{
    var n = varCalendar.yearFall;
    var e = varCalendar.iframe.document.forms[0].tmpYearSelect;
    var y = isNaN(parseInt(varCalendar.thisYear, 10)) ? new Date().getFullYear() : parseInt(varCalendar.thisYear);
        y = (y <= 1000)? 1000 : ((y >= 9999)? 9999 : y);
    var min = (y - n >= 1000) ? y - n : 1000;
    var max = (y + n <= 9999) ? y + n : 9999;
        min = (max == 9999) ? max-n*2 : min;
        max = (min == 1000) ? min+n*2 : max;
    for (var i=min; i<=max; i++) e.options.add(new Option(i +' '+varYear, i));
    e.style.display = ""; e.value = y; e.focus();
}

function funHourSelect(varCalendar) //?????
{
    var e = varCalendar.iframe.document.forms[0].tmpHourSelect;
    for (var i=0; i<24; i++) 
    {
    	var hour = i.toString();
    	
    	// ???????
    	if(hour.length==1)
    	{// ???????0
    		hour = '0' + hour;
    	}
    	
    	e.options.add(new Option(hour, i));
    }
    
    e.value = 0;
}

function funMinuteSelect(varCalendar) //?????
{
    var e = varCalendar.iframe.document.forms[0].tmpMinuteSelect;
    for (var i=0; i<60; i++)
    {
    	var min = i.toString();
    	
    	// ???????
    	if(min.length==1)
    	{// ???????0
    		min = '0' + min;
    	}
    	
    	e.options.add(new Option(min, i));
    }
    
    e.value = 0;
}

function funSecondSelect(varCalendar) //?????
{
    var e = varCalendar.iframe.document.forms[0].tmpSecondSelect;
    for (var i=0; i<60; i++)
    {
    	var sec = i.toString();
    	
    	// ???????
    	if(sec.length==1)
    	{// ???????0
    		sec = '0' + sec;
    	}
    	
    	e.options.add(new Option(sec, i));
    }
    e.value = 0;
}

function prevMonth(varCalendar)  //?????
{
    varCalendar.thisDay = 1;
    if (varCalendar.thisMonth==1)
    {
        varCalendar.thisYear--;
        varCalendar.thisMonth=13;
    }
    varCalendar.thisMonth--;
    InitCalendar(varCalendar);
}

function nextMonth(varCalendar)  //?????
{
    varCalendar.thisDay = 1;
    if (varCalendar.thisMonth==12)
    {
        varCalendar.thisYear++;
        varCalendar.thisMonth=0;
    }
    varCalendar.thisMonth++;
    InitCalendar(varCalendar);
}

function hiddenSelect(e)
{
	for(var i=e.options.length; i>-1; i--)
		e.options.remove(i);
	e.style.display="none";
}

function getObjectById(id)
{
	if(document.all)
		return(eval("document.all."+ id));
	return(eval(id));
}

function hiddenCalendar()
{
	if(varUsed==null)
		return;

	if(varUsed==varDateTime || varUsed==varDate)
	{//????
		getObjectById("meizzCalendarLayer1").style.display = "none";
	}
	else
	{//????
		getObjectById("meizzCalendarLayer2").style.display = "none";
	}
	
	varUsed.selectedDayObj	=null;
	varUsed.selectedDate	="";
}

function appendZero(n){return(("00"+ n).substr(("00"+ n).length-2));}//????????
//function String.prototype.trim(){return this.replace(/(^\s*)|(\s*$)/g,"");} this statement is modified as following statement by jeton.dong 20071228 
String.prototype.trim=function(){return this.replace(/(^\s*)|(\s*$)/g,"");}

function dayMouseOver()
{
	if(this.className=="click")
		return;

	this.className = "over";
	this.style.removeAttribute('backgroundColor');

	this.style.backgroundColor = varUsed.selDayColor;
	if(varUsed.day[this.id.substr(8)].split("/")[1] == varUsed.thisMonth)
		this.style.color = varUsed.lightColor;
}
function dayMouseOut()
{
	if(this.className=="click")
	{
		return;
	}
	
    this.className = "out";
    var d = varUsed.day[this.id.substr(8)], a = d.split("/");
    this.style.removeAttribute('backgroundColor');
    if(a[1] == varUsed.thisMonth && d != varUsed.today)
    {
       	this.style.backgroundColor = varUsed.dayBgColor;
        this.style.color = varUsed.wordColor;
    }
	else if(a[1] == varUsed.thisMonth && d == varUsed.today)
	{
	    this.style.removeAttribute('backgroundColor');
       	this.style.backgroundColor = varUsed.todayBgColor;
	}
	else
	{
       	this.style.backgroundColor = varUsed.dayBgColor;
	}
}

function dayMouseClick()
{
	var d = varUsed.day[this.id.substr(8)], a = d.split("/");

	if(varUsed.day[this.id.substr(8)].split("/")[1] != varUsed.thisMonth)
	{//??????
		return;
	}

	if(varUsed.selectedDayObj!=null)
	{
		//?????????
		varUsed.selectedDayObj.className="out";
		varUsed.selectedDayObj.style.removeAttribute('backgroundColor');
		if(varUsed.day[varUsed.selectedDayObj.id.substr(8)]!=varUsed.today)
		{//?????????
			varUsed.selectedDayObj.style.backgroundColor = varUsed.dayBgColor;
			varUsed.selectedDayObj.style.color = varUsed.wordColor;
		}
		else
		{//????????
			varUsed.selectedDayObj.style.backgroundColor = varUsed.todayBgColor;
			varUsed.selectedDayObj.style.color = varUsed.lightColor;
		}
	}

	this.className = "click";
	this.style.removeAttribute('backgroundColor');
	this.style.backgroundColor = varUsed.selDayColor;
	this.style.color = varUsed.lightColor;
	
	varUsed.selectedDayObj=this;
	varUsed.selectedDate=getSelectedDate();
	
	if(varUsed==varDate)
	{//??
		onOK();
	}
}

function getSelectedDate() //????????????????
{
	var returnValue="";
	
	if(varUsed==varDateTime || varUsed==varDate)
	{
		var a = (arguments.length==0) ? varUsed.day[varUsed.selectedDayObj.id.substr(8)].split("/") : arguments[0].split("/");
		var d = varUsed.format.match(/^(\w{4})(-|\/)(\w{1,2})\2(\w{1,2})$/);

		if(d==null)
		{
			return false;
		}
		var flag = d[3].length==2 || d[4].length==2; //??????????????
		returnValue = flag ? a[2] +d[2]+ appendZero(a[1]) +d[2]+ appendZero(a[0]) : a[2] +d[2]+ a[1] +d[2]+ a[0];
	}
	
	return returnValue;
}

function returnValue() //??????????????????
{
	if(varUsed.objExport)
	{
		var returnValue="";
		
		if(varUsed==varDateTime || varUsed==varDate)
		{
			returnValue =varUsed.selectedDate;
		}
		
		if(varUsed==varDateTime || varUsed==varTime)
		{
			if(varUsed.secondFlag!=0)
			{
				var hour = varUsed.iframe.document.forms[0].tmpHourSelect;
				var min = varUsed.iframe.document.forms[0].tmpMinuteSelect;
				var sec = varUsed.iframe.document.forms[0].tmpSecondSelect;
	
				var h = hour.value, m = min.value, s = sec.value;
				if(varUsed==varDateTime)
				{
					returnValue += " "+ appendZero(h) +":"+ appendZero(m) +":"+ appendZero(s);
				}
				else
				{
					returnValue += appendZero(h) +":"+ appendZero(m) +":"+ appendZero(s);
				}
			}
			else
			{
				var hour = varUsed.iframe.document.forms[0].tmpHourSelect;
				var min = varUsed.iframe.document.forms[0].tmpMinuteSelect;
	
				var h = hour.value, m = min.value;
				if(varUsed==varDateTime)
				{
					returnValue += " "+ appendZero(h) +":"+ appendZero(m);
				}
				else
				{
					returnValue += appendZero(h) +":"+ appendZero(m);
				}
			}
		}
		
		varUsed.returnValue=returnValue;
	}
}

function onClear()
{
	varUsed.objExport.value="";
	hiddenCalendar();
}

function onOK()
{
	returnValue();

	varUsed.objExport.value = varUsed.returnValue;
	hiddenCalendar();
}

//function document.onclick() this statement has been modified as following statement by jeton.dong 20071228
document.onclick = function()
{
    if(varDateTime.eventSrc != window.event.srcElement && varDate.eventSrc!= window.event.srcElement && varTime.eventSrc!= window.event.srcElement)
    {
    	hiddenCalendar();
    }
}
