/*Js get curent time*/
function getCurrentTime()
{
	var d = new Date();    
	var year = d.getYear();    
	var month = d.getMonth()+1;    
	var ddate = d.getDate();    
	var hours = d.getHours();    
	var minutes = d.getMinutes();    
	var seconds = d.getSeconds();    
	var curDateTime= year;   
	if(month>9)   
	curDateTime = curDateTime +"-"+month;   
	else   
	curDateTime = curDateTime +"-0"+month;   
	if(ddate>9)   
	curDateTime = curDateTime +"-"+ddate;   
	else   
	curDateTime = curDateTime +"-0"+ddate;   
	if(hours>9)   
	curDateTime = curDateTime +" "+hours;   
	else   
	curDateTime = curDateTime +" 0"+hours;   
	if(minutes>9)   
	curDateTime = curDateTime +":"+minutes;   
	else   
	curDateTime = curDateTime +":0"+minutes;   
	if(seconds>9)   
	curDateTime = curDateTime +":"+seconds;   
	else   
	curDateTime = curDateTime +":0"+seconds;   
	return curDateTime;
}