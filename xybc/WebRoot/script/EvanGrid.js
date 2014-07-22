// JavaScript Document


function flagImage(imagePath){
	var filed;
	var direction;
	var lable; 
	if(document.forms[0].list_order != undefined)
	{
		filed = document.forms[0].list_order.value;
		lable = document.getElementById(filed + "_Flag"); 
		
	}
	if(document.forms[0].list_asc != undefined)
	{
		direction = document.forms[0].list_asc.value;
	}
	if(lable != undefined && lable != null){
			lable.innerHTML = "<img src=\""+imagePath+"/"+ direction +".gif\" border=\"0\" align=\"absmiddle\" style=\"margin-left:2px\">";
	}
	
}

function sort(valueField) {
	var flag = document.forms[0].list_asc.value;
	if (flag == "desc") {
		flag = "asc";
	} else {
		flag = "desc";
	}
	
	document.forms[0].list_order.value = valueField;
	document.forms[0].list_asc.value = flag;
	document.forms[0].submit();
}

function MouseDownToResize(obj){
	obj.mouseDownX=event.clientX;
	obj.pareneTdW=obj.parentElement.offsetWidth;
	obj.pareneTableW=theObjTable.offsetWidth;
	obj.setCapture();
}
function MouseMoveToResize(obj){
		if(!obj.mouseDownX) return false;
			var newWidth=obj.pareneTdW*1+event.clientX*1-obj.mouseDownX;
			if(newWidth>25){
				obj.parentElement.style.width = newWidth;
				theObjTable.style.width=obj.pareneTableW*1+event.clientX*1-obj.mouseDownX;
		}
}
function MouseUpToResize(obj){
	obj.mouseDownX=0;
	obj.releaseCapture();
}

function trim(str){
	return str.replace(/(^\s*|\s*$)/g,"");
}

function setCssFile(pFile){
		var cssFile =document.createElement("LINK");
		document.getElementsByTagName("HEAD")[0].appendChild(cssFile);
		cssFile.rel="stylesheet";
		cssFile.type="text/css";
		cssFile.href = pFile;
}
	
function getWidthValue(field){
	if(field[1] == null){
		return "";
	}else{
		return field[1];
	}
}

function getAlignValue(field){
	if(field[2] == null || trim(field[2]) == ""){
		return "alignCenter";
	}
	switch(field[2]){
		case 'left':
			return "alignLeft";
			break;
		case 'center':
			return "alignCenter";
			break;
		case 'right':
			return "alignRight";
			break;
		default:
			return "alignCenter";
	}
}

function runOrder(value){
	if(!isNullField(value)){
		sort(value);
	}
}

function getFieldOrder(field){
	if(isNullField(field[3])){
		return "";
	}
	return field[3];
}

function getNameValue(field){
	if(isNullField(field[0])){
		return "";
	}
	//if(!isNullField(field[3])){
		//return "<a href='javascript:sort(\""+field[3]+"\");'>"+field[0]+"</a><label id='"+field[3]+"_Flag'></label>";
	//}
	return field[0];
}

function isCheckBox(field){
	var bValue = null;
	var valCheckBox = field[4];

	if(valCheckBox == null || trim(valCheckBox+" ") != "1"){
		bValue = null;
	}else{
		var strCheckBox = field[5];
		if(strCheckBox == null || trim(strCheckBox+" ") == ""){
			bValue = "lId";
		}else{
			bValue = strCheckBox;
		}
	}

	return bValue;
}

function isNullField(field){
	if(field == null || trim(field) == ""){
		return true;
	}else{
		return false;
	}
}

function getThTag(strValue,strWidth){
	var strTh = "<td width="+strWidth+"><font class='alignCenter'>"+strValue+"</font></td>";
	return strTh;
}


function bindTitle1(fieldArray,bodyName,imagePath,isResizeDiv){
	var pField;
	var fieldName = "";
	
	if (document.getElementById("theObjTable").getElementsByTagName("td")[0] == undefined){				
		var strTag = "";
		//var strTemp = "";
		
		for(i=0; i<fieldArray.length; i++)	{		
			pField = fieldArray[i];
			//if(pField[1] != undefined){
			//	strTemp = pField[1];
			//}
			strTag += getThTag(pField[0],pField[1]);	
		}
				
		strTag = "<table width='100%' border='1' cellpadding='0' cellspacing='0' id='theObjTable' style='table-layout:fixed'><tbody id='"+bodyName+"'><tr bgcolor='#CAD9EC' style='background-image:url("+imagePath+"/grid_head.gif)' height='22'>"+strTag+"</tr><tr><td  colspan="+fieldArray.length+" style='background-color:#fcfcfc;height:88px;text-align:center; font-size:14px; font-weight:bold; color:#aaaaaa'>No Data</td></tr></tbody></table>";
		document.getElementById("dataTable").innerHTML = strTag;
		return -1;
	}
	var test=document.getElementById("theObjTable").getElementsByTagName("td")[0].parentNode.cloneNode(true);
	test.className = "tableTitle";
	
	for(h=0;h<test.getElementsByTagName("td").length;h++){
			pField = fieldArray[h];
			fieldName = getFieldOrder(pField);
			
			if(fieldName == null || fieldName == ""){
				test.getElementsByTagName("td")[h].style.cursor = "default";
			}else{
				test.getElementsByTagName("td")[h].style.cursor = "pointer";
			}

			test.getElementsByTagName("td")[h].style.padding = "0px";
			//test.getElementsByTagName("td")[h].style.backgroundColor = "#CAD9EC";
			test.getElementsByTagName("td")[h].style.backgroundImage = "url("+imagePath+"/grid_head.gif)";
			test.getElementsByTagName("td")[h].style.borderBottom = "1px solid #959595";
			test.getElementsByTagName("td")[h].style.borderRight = "1px solid #94a8cd";
			test.getElementsByTagName("td")[h].width = getWidthValue(pField);
			test.getElementsByTagName("td")[h].onclick = "";
			test.getElementsByTagName("td")[h].title = "";
			var selectallValue = isCheckBox(pField);
			if(selectallValue != null){
			test.getElementsByTagName("td")[h].innerHTML="<center><input type='checkbox' name='checkall' style='height:15px; width:15px;' onclick='selectall(\""+selectallValue+"\");'/></center>";
			test.getElementsByTagName("td")[h].style.borderRight = "1px solid #86a1d1";
			}else{
				if(isResizeDiv != undefined && isResizeDiv != null){
					test.getElementsByTagName("td")[h].innerHTML="<font onclick='runOrder(\""+fieldName+"\")' class='"+getAlignValue(pField)+"'>"+getNameValue(pField)+"<label id='"+fieldName+"_Flag'></label></font>";
				}else{
					test.getElementsByTagName("td")[h].innerHTML="<font class='resizeDivClass' onmousedown='MouseDownToResize(this);' onmousemove='MouseMoveToResize(this);' onmouseup='MouseUpToResize(this);'></font><font onclick='runOrder(\""+fieldName+"\")' class='"+getAlignValue(pField)+"'>"+getNameValue(pField)+"<label id='"+fieldName+"_Flag'></label></font>";
				}
			
			}
		}
	if(bodyName == null)
	{
		dataArea.insertAdjacentElement("afterBegin",test);
	}
	else
	{
		eval(bodyName).insertAdjacentElement("afterBegin",test);
	}
	test.style.display="block";
}

function bindTitle(fieldArray,bodyName,n,imagePath,isResizeDiv){
	var pField;
	var fieldName = "";
	var id1="theObjTable"+n;
	var id2="dataTable"+n;

	if (document.getElementById(id1).getElementsByTagName("td")[0] == undefined){
		
		var strTag = "";
		//var strTemp = "";

		for(i=0; i<fieldArray.length; i++)	{		
			pField = fieldArray[i];
			//if(pField[1] != undefined){
			//	strTemp = pField[1];
			//}
			strTag += getThTag(pField[0],pField[1]);

		}		

		strTag = "<table width='100%' border='1' cellpadding='0' cellspacing='0' id='"+id1+"' style='table-layout:fixed;border-collapse:collapse;border:1px solid #959595;table-layout:fixed;'><tbody id='"+bodyName+"'><tr bgcolor='#CAD9EC' style='background-image:url("+imagePath+"/grid_head.gif)' height='22'>"+strTag+"</tr><tr><td  colspan="+fieldArray.length+" style='background-color:#fcfcfc;height:88px;text-align:center; font-size:14px; font-weight:bold; color:#aaaaaa'>No Data</td></tr></tbody></table>";
		
		//document.getElementById(id2).innerHTML = strTag;		
		return 1;
	}
	var test=document.getElementById(id1).getElementsByTagName("td")[0].parentNode.cloneNode(true);
	test.className = "tableTitle";
	
	for(h=0;h<test.getElementsByTagName("td").length;h++){
			pField = fieldArray[h];
			fieldName = getFieldOrder(pField);
			
			if(fieldName == null || fieldName == ""){
				test.getElementsByTagName("td")[h].style.cursor = "default";
			}else{
				test.getElementsByTagName("td")[h].style.cursor = "pointer";
			}

			test.getElementsByTagName("td")[h].style.padding = "0px";
			//test.getElementsByTagName("td")[h].style.backgroundColor = "#CAD9EC";
			test.getElementsByTagName("td")[h].style.backgroundImage = "url("+imagePath+"/grid_head.gif)";
			test.getElementsByTagName("td")[h].style.borderBottom = "1px solid #959595";
			test.getElementsByTagName("td")[h].style.borderRight = "1px solid #94a8cd";
			test.getElementsByTagName("td")[h].width = getWidthValue(pField);
			test.getElementsByTagName("td")[h].onclick = "";
			test.getElementsByTagName("td")[h].title = "";
			var selectallValue = isCheckBox(pField);
			if(selectallValue != null){
			test.getElementsByTagName("td")[h].innerHTML="<center><input type='checkbox' name='checkall' style='height:15px; width:15px;' onclick='selectall(\""+selectallValue+"\");'/></center>";
			test.getElementsByTagName("td")[h].style.borderRight = "1px solid #86a1d1";
			}else{
				if(isResizeDiv != undefined && isResizeDiv != null){
					test.getElementsByTagName("td")[h].innerHTML="<font onclick='runOrder(\""+fieldName+"\")' class='"+getAlignValue(pField)+"'>"+getNameValue(pField)+"<label id='"+fieldName+"_Flag'></label></font>";
				}else{
					test.getElementsByTagName("td")[h].innerHTML="<font class='resizeDivClass' onmousedown='MouseDownToResize(this);' onmousemove='MouseMoveToResize(this);' onmouseup='MouseUpToResize(this);'></font><font onclick='runOrder(\""+fieldName+"\")' class='"+getAlignValue(pField)+"'>"+getNameValue(pField)+"<label id='"+fieldName+"_Flag'></label></font>";
				}
			
			}
		}
	if(bodyName == null)
	{
		dataArea.insertAdjacentElement("afterBegin",test);
	}
	else
	{
		eval(bodyName).insertAdjacentElement("afterBegin",test);
	}
	test.style.display="block";
}

function initGrid1(fieldArray,cssFile,imagePath,isCursor,tBodyName){
var bodyName = "dataArea";
if(tBodyName != null)
{
	bodyName = tBodyName;
}
setCssFile(cssFile);
if (bindTitle1(fieldArray,bodyName,imagePath) == -1){return; }
flagImage(imagePath);

var trl=document.getElementById(bodyName).getElementsByTagName("tr");
var normalColor = ""; 
var normalColor2 = "#EEF5FE"; 
var overColor   = "#B2CFEE"; 
var clickColor  = "#0d4b9e"; 
var normalFontColor  = "#000000"; 
var clickFontColor  = "#ffffff"; 


for (i=1; i<trl.length; i++){
	
	if((i % 2) == 1){trl[i].className="interleaveColor";} 
	
	for(k=0;k<trl[i].cells.length;k++){if(trim(trl[i].cells[k].innerText) != ""){trl[i].cells[k].title = trl[i].cells[k].innerText;}}// title??

	trl[i].onmouseover = function rty1(){	 if (this.style.backgroundColor != clickColor) {this.style.backgroundColor = overColor; if(isCursor == null){this.style.cursor = "pointer";}else{this.style.cursor = isCursor;}}}//onmouseover

	if((i % 2) == 0){	trl[i].onmouseout =  function rty21(){	if (this.style.backgroundColor != clickColor) {this.style.backgroundColor = normalColor;  }}}
	if((i % 2) == 1){	trl[i].onmouseout =  function rty22(){	if (this.style.backgroundColor != clickColor) {this.style.backgroundColor = normalColor2; }}}	
	
	/*trl[i].onclick = function rty3(){	
		if (this.style.backgroundColor == clickColor){ 
			this.style.backgroundRepeat = "no-repeat";
		}
		for (j=0; j<trl.length; j++){
				trl[j].style.backgroundColor = normalColor;
				for(k=0;k<trl[j].cells.length;k++){trl[j].cells[k].style.color = normalFontColor;}
		}
			if (this.style.backgroundRepeat == "no-repeat"){ 
					this.style.backgroundColor = normalColor; 
					this.style.backgroundRepeat = "";
					for(k=0;k<this.cells.length;k++){this.cells[k].style.color = normalFontColor;}
			}
			else{	 this.style.backgroundColor = clickColor; 
					 for(k=0;k<this.cells.length;k++){this.cells[k].style.color = clickFontColor;}
			}
			return true;
	}*/
}
}

function initGrid(fieldArray,cssFile,imagePath,m,isCursor,tBodyName){

if(initGrid.arguments.length!=4)
{
	if(initGrid.arguments.length==5)
	{
		initGrid1(fieldArray,cssFile,imagePath,m,isCursor);
	}
	initGrid1(fieldArray,cssFile,imagePath);
	return;
}

var bodyName = "dataArea";
if(tBodyName != null)
{
	bodyName = tBodyName;
}

setCssFile(cssFile);

for(var i=0;i<m;i++)
{	
	if (bindTitle(fieldArray,bodyName,i,imagePath) == -1){return; }
}	
flagImage(imagePath);

var trl=document.getElementById(bodyName).getElementsByTagName("tr");
var normalColor = ""; 
var normalColor2 = "#EEF5FE"; 
var overColor   = "#B2CFEE"; 
var clickColor  = "#0d4b9e"; 
var normalFontColor  = "#000000"; 
var clickFontColor  = "#ffffff"; 


for (i=1; i<trl.length; i++){
	
	if((i % 2) == 1){trl[i].className="interleaveColor";} 
	
	for(k=0;k<trl[i].cells.length;k++){if(trim(trl[i].cells[k].innerText) != ""){trl[i].cells[k].title = trl[i].cells[k].innerText;}}// title??

	trl[i].onmouseover = function rty1(){	 if (this.style.backgroundColor != clickColor) {this.style.backgroundColor = overColor; if(isCursor == null){this.style.cursor = "pointer";}else{this.style.cursor = isCursor;}}}//onmouseover

	if((i % 2) == 0){	trl[i].onmouseout =  function rty21(){	if (this.style.backgroundColor != clickColor) {this.style.backgroundColor = normalColor;  }}}
	if((i % 2) == 1){	trl[i].onmouseout =  function rty22(){	if (this.style.backgroundColor != clickColor) {this.style.backgroundColor = normalColor2; }}}	
	
	/*trl[i].onclick = function rty3(){	
		if (this.style.backgroundColor == clickColor){ 
			this.style.backgroundRepeat = "no-repeat";
		}
		for (j=0; j<trl.length; j++){
				trl[j].style.backgroundColor = normalColor;
				for(k=0;k<trl[j].cells.length;k++){trl[j].cells[k].style.color = normalFontColor;}
		}
			if (this.style.backgroundRepeat == "no-repeat"){ 
					this.style.backgroundColor = normalColor; 
					this.style.backgroundRepeat = "";
					for(k=0;k<this.cells.length;k++){this.cells[k].style.color = normalFontColor;}
			}
			else{	 this.style.backgroundColor = clickColor; 
					 for(k=0;k<this.cells.length;k++){this.cells[k].style.color = clickFontColor;}
			}
			return true;
	}*/
}
}

function initGridNonOrder(dataAreaDiv,cssFile,imagePath){
	setCssFile(cssFile);
	//if (bindTitle(fieldArray) == -1){return; }
	//flagImage(imagePath);
	
	//alert(dataAreaDiv);
	var dataArea = document.getElementById(dataAreaDiv);
	
	if((dataArea == null) || (dataArea == undefined)){
		return;
	}
	var trl = dataArea.getElementsByTagName("tr");
	var normalColor = ""; 
	var normalColor2 = "#EEF5FE"; 
	var overColor   = "#B2CFEE"; 
	var clickColor  = "#0d4b9e"; 
	var normalFontColor  = "#000000"; 
	var clickFontColor  = "#ffffff"; 

	for (i=0; i<trl.length; i++){
	
		if((i % 2) == 1){trl[i].className="interleaveColor";} 
	
		for(k=0;k<trl[i].cells.length;k++){ if(trl[i].cells[k].innerText.length >=2){trl[i].cells[k].title = trl[i].cells[k].innerText.substring(0,100);}}// title??

		trl[i].onmouseover = function rty1(){	 if (this.style.backgroundColor != clickColor) {this.style.backgroundColor = overColor;this.style.cursor = "pointer";}}//onmouseover

		if((i % 2) == 0){	trl[i].onmouseout =  function rty21(){	if (this.style.backgroundColor != clickColor) {this.style.backgroundColor = normalColor;  }}}
		if((i % 2) == 1){	trl[i].onmouseout =  function rty22(){	if (this.style.backgroundColor != clickColor) {this.style.backgroundColor = normalColor2; }}}	
	
		/*trl[i].onclick = function rty3(){	
			if (this.style.backgroundColor == clickColor){ 
				this.style.backgroundRepeat = "no-repeat";
			}
			for (j=0; j<trl.length; j++){
				trl[j].style.backgroundColor = normalColor;
				for(k=0;k<trl[j].cells.length;k++){trl[j].cells[k].style.color = normalFontColor;}
			}
			if (this.style.backgroundRepeat == "no-repeat"){ 
					this.style.backgroundColor = normalColor; 
					this.style.backgroundRepeat = "";
					for(k=0;k<this.cells.length;k++){this.cells[k].style.color = normalFontColor;}
			}
			else{	 this.style.backgroundColor = clickColor; 
					 for(k=0;k<this.cells.length;k++){this.cells[k].style.color = clickFontColor;}
			}
			return true;
		}*/
	}
}



function initGridNoTitle(fieldArray,cssFile,imagePath,isCursor,tBodyName){
var bodyName = "dataArea";
if(tBodyName != null)
{
	bodyName = tBodyName;
}
setCssFile(cssFile);
if (bindTitle1(fieldArray,bodyName,imagePath,false) == -1){return; }
flagImage(imagePath);

var trl=document.getElementById(bodyName).getElementsByTagName("tr");
var normalColor = ""; 
var normalColor2 = "#EEF5FE"; 
var overColor   = "#B2CFEE"; 
var clickColor  = "#0d4b9e"; 
var normalFontColor  = "#000000"; 
var clickFontColor  = "#ffffff"; 


for (i=1; i<trl.length; i++){
	
	if((i % 2) == 1){trl[i].className="interleaveColor";} 
	
	//for(k=0;k<trl[i].cells.length;k++){if(trim(trl[i].cells[k].innerText) != ""){trl[i].cells[k].title = trl[i].cells[k].innerText;}}// title??

	trl[i].onmouseover = function rty1(){	 if (this.style.backgroundColor != clickColor) {this.style.backgroundColor = overColor; if(isCursor == null){this.style.cursor = "pointer";}else{this.style.cursor = isCursor;}}}//onmouseover

	if((i % 2) == 0){	trl[i].onmouseout =  function rty21(){	if (this.style.backgroundColor != clickColor) {this.style.backgroundColor = normalColor;  }}}
	if((i % 2) == 1){	trl[i].onmouseout =  function rty22(){	if (this.style.backgroundColor != clickColor) {this.style.backgroundColor = normalColor2; }}}	
	
}
}