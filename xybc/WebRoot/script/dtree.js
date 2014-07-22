
// Node object
function Node(id, pid, name, value, url, title, target, icon, iconOpen, open, isChecked, isType, isDisabled) {
	this.id = id;
	this.pid = pid;
	this.name = name;
	this.url = url || null;
	this.title = title || null;
	this.target = target || null;
	this.icon = icon || null;
	this.iconOpen = iconOpen || null;
	this._io = open || false;
	this._is = false;	// 是否展开
	this._ls = false;	// 在兄弟中是否为最后一个
	this._hc = false;	// 是否有子节点
	this._ai = 0;		// 绝对id编号
	this._p;			// 存储父节点对象
	
	this.isChecked = isChecked || false;	// 节点是否被用户选中
	this.isType = isType || false;			// 节点是否为类型
	this.isDisabled = isDisabled || false;	// 节点控件是否不可以被操作
	this.value = value;				// 保存节点的实际值，如数据库id
};

// Tree object
function dTree(objName) {
	this.config = {
		target					: null,
		folderLinks				: true,				   // 选择节点的同时是否收缩展开或收缩节点 
		useSelection			: true,				   // 选择某个节点是是否高亮显示
		useCookies				: false,
		useLines				: false,
		useIcons				: true,
		useStatusText			: false,
		closeSameLevel			: false,
		inOrder					: false,
		
		useControl				: true,					// 是否使用选择控件 
		controlType				: 'radio',				// 选择控件的类型input控件的type类型列表默认为checkbox
		isRootUseControl		: false,					// 根节点是否使用控件 
		isDisplay				: true,						// 树是否显示，默认为显示
		typeIdArrayName			: 'typeIdArray',		// 封装类型选择id列表变量名
		nodeIdArrayName			: 'nodeIdArray',		// 封装普通节点选择id列表变量名
		cssLink 				: '<link href=../../css/dtree.css type=text/css rel=StyleSheet>',	// dTree所使用的css文件
		strSplitNameSeparator	: ';',					// 用户选择的结点名称的分割符号
		strSplitIdSeparator		: ',',					// 用户选择的database id列的分割符号
		isCorrelateSelect		: true,				// 正向、向下级联选 如果控件的类型为checkbox，当选中类型时是否级联选择其下类型及节点，默认为false 不级联选择
		isReverseCorrelateSelect		: true,			// 反向、向上级联选 如果控件的类型为checkbox，当选中类型或节点时是否级联选择其上类型直至根，默认为false 不向上级级联选择
		isSingleCorrelateLayer			: false,			// 是否为单层级联选择。既只选择该类型下一级的类型及节点。默认为false无穷级级联选择
		isSingleReverseCorrelateLayer	: false,			// 反向、向上级联选时，是否为单层级联选择。既只选择该类型或节点的上一级的类型。默认为false无穷级级联选择直至根节点
		correlateSelectType				: 'all',			// 当选中类型节点时级联选择的类型。默认为all类型和节点一起选择。type只选择类型。node只选择节点。
		isEventOnOk						: false,			// 用户选择确定后是否触发事件
		isLockDisabledNodeChecked		: false				// 锁定被设置为isDisabled==true结点控件的isChecked选择状态。false不锁定。true锁定。默认为false

	}
	this.icon = {
		root				: '../../images/tree/icon_yxza.gif',
		folder			: '../../images/tree/folder.gif',
		folderOpen	: '../../images/tree/folderopen.gif',
		node				: '../../images/tree/null.gif',
		empty				: '../../images/tree/empty.gif',
		line				: '../../images/tree/empty.gif',
		join				: '../../images/tree/empty.gif',
		joinBottom	: '../../images/tree/empty.gif',
		plus				: '../../images/tree/folder_sign_close.gif',
		plusBottom	: '../../images/tree/folder_sign_close.gif',
		minus				: '../../images/tree/folder_sign_open.gif',
		minusBottom	: '../../images/tree/folder_sign_open.gif',
		nlPlus			: '../../images/tree/nolines_plus.gif',
		nlMinus			: '../../images/tree/nolines_minus.gif'
	};
	this.obj = objName;
	this.aNodes = [];
	this.aIndent = [];
	this.root = new Node(-1);
	this.selectedNode = null;
	this.selectedFound = false;
	this.completed = false;
};

// Adds a new node to the node array
dTree.prototype.add = function(id, pid, name, isType, isChecked, isDisabled, url, title, target, icon, iconOpen, open) {
	
	// 转换字符型为boolean型，便于统一，也防止jsp程序输出到也面的为''产生js脚本错误
	if(isChecked=='true' || isChecked==true)
	{
		isChecked = true;
	}
	else
	{
		isChecked = false;
	}
	
	if(isType=='true' || isType==true)
	{
		isType = true;
	}
	else
	{
		isType = false;
	}
	
	if(isDisabled=='true' || isDisabled==true)
	{
		isDisabled = true;
	}
	else
	{
		isDisabled = false;
	}
	
	var valueTemp = id;
	
	// 为id,pid添加特制字头
	if(isType==true)
	{
		id = 't' + id;
		if((pid!='-1') && (pid!=-1))
		{
			pid = 't' + pid;
		}
	}
	else
	{
		id = 'n' + id;
		if((pid!='-1') && (pid!=-1))
		{
			pid = 't' + pid;
		}
	}
	
	this.aNodes[this.aNodes.length] = new Node(id, pid, name, valueTemp, url, title, target, icon, iconOpen, open, isChecked, isType, isDisabled);
};

// Open/close all nodes
dTree.prototype.openAll = function() {
	this.oAll(true);
};
dTree.prototype.closeAll = function() {
	this.oAll(false);
};

// 生成树html
dTree.prototype.toString = function() {
	// shield useCookies
	this.config.useCookies = false;
	
	var str = '<div class="dtree"';
	if(this.config.isDisplay==false)
	{
		str += 'style="display:none"';
	}
	str += '>\n';
	if (document.getElementById) {
		if (this.config.useCookies) this.selectedNode = this.getSelected();
		str += this.addNode(this.root);
	} else str += 'Browser not supported.';
	str += '</div>';
	if (!this.selectedFound) this.selectedNode = null;
	this.completed = true;
	return str;
};


// Creates the tree structure
// 添加pNode下所有子节点结构。（一层）
dTree.prototype.addNode = function(pNode) {
	var str = '';
	var n=0;
	if (this.config.inOrder) n = pNode._ai;
	for (n; n<this.aNodes.length; n++) {
		if (this.aNodes[n].pid == pNode.id) {
			var cn = this.aNodes[n];
			cn._p = pNode;
			cn._ai = n;
			this.setCS(cn);
			if (!cn.target && this.config.target) cn.target = this.config.target;
			if (cn._hc && !cn._io && this.config.useCookies) cn._io = this.isOpen(cn.id);
			if (!this.config.folderLinks && cn._hc) cn.url = null;
			if (this.config.useSelection && cn.id == this.selectedNode && !this.selectedFound) {
					cn._is = true;
					this.selectedNode = n;
					this.selectedFound = true;
			}
			str += this.node(cn, n);
			if (cn._ls) break;
		}
	}
	return str;
};

// Creates the node icon, url and text
dTree.prototype.node = function(node, nodeId) {
	var str = '<div class="dTreeNode">' + this.indent(node, nodeId);
	// 判断是否使用控件
	if(this.config.useControl==true)
	{
		if(this.root.id!=node.pid || this.config.isRootUseControl==true)
		{// 不为根节点或设置了根节点使用控件生成控件
			// 使用控件，生成html代码
			str += '<input id="ctrl' + this.obj + nodeId + '" name="';
			
			// 根据类型生成控件名称
			if(node.isType==true)
			{
				str += this.config.typeIdArrayName;
			}
			else
			{
				str += this.config.nodeIdArrayName;
			}
			
			str += '" type="' + this.config.controlType + '" value="' + node.value + '" ';
			
			// 根据操作表示设置控件可操作状态
			if(node.isDisabled==true)
			{// 控件不可见
				str += ' style="display:none" ';
			}
	
			// 判断是否为checkbox 或 radio
			if(this.config.controlType=='checkbox' || this.config.controlType=='radio')
			{
				// 是选择类型控件根据节点的选择状态设置控件状态
				if(node.isChecked==true)
				{
					str += ' checked="checked" ';
				}
				
				// 用户改变页面控件选择状态时同时改变相应节点选择状态
				if(node.isDisabled==false)
				{
					str += ' onClick="javascript: ' + this.obj + '.setChecked(' + nodeId + ', this.checked);" ';
				}
			}
			str += ' />';
		}
	}
	
	if (this.config.useIcons) {
		if (!node.icon) node.icon = (this.root.id == node.pid) ? this.icon.root : ((node._hc || node.isType) ? this.icon.folder : this.icon.node);
		if (!node.iconOpen) node.iconOpen = (node._hc || node.isType) ? this.icon.folderOpen : this.icon.node;
		if (this.root.id == node.pid) {
			node.icon = this.icon.root;
			node.iconOpen = this.icon.root;
		}
		str += '<img id="i' + this.obj + nodeId + '" src="' + ((node._io) ? node.iconOpen : node.icon) + '" alt="" />';
	}
	if (node.url) {
		str += '<a id="s' + this.obj + nodeId + '" class="' + ((this.config.useSelection) ? ((node._is ? 'nodeSel' : 'node')) : 'node') + '" href="' + node.url + '"';
		if (node.title) str += ' title="' + node.title + '"';
		if (node.target) str += ' target="' + node.target + '"';
		if (this.config.useStatusText) str += ' onmouseover="window.status=\'' + node.name + '\';return true;" onmouseout="window.status=\'\';return true;" ';
		if (this.config.useSelection && ((node._hc && this.config.folderLinks) || !node._hc))
			str += ' onclick="javascript: ' + this.obj + '.s(' + nodeId + ');"';
		str += '>';
	}
	else if ((!this.config.folderLinks || !node.url) && node._hc && node.pid != this.root.id)
	{
		str += '<a href="#" onClick="javascript: ' + this.obj + '.o(' + nodeId + ');" class="node">';
	}
	
	str += node.name;
	
	if (node.url || ((!this.config.folderLinks || !node.url) && node._hc)) str += '</a>';
	str += '</div>';
	if (node._hc) {
		str += '<div id="d' + this.obj + nodeId + '" class="clip" style="display:' + ((this.root.id == node.pid || node._io) ? 'block' : 'none') + ';">';
		str += this.addNode(node);
		str += '</div>';
	}
	this.aIndent.pop();
	return str;
};

// Adds the empty and line icons
dTree.prototype.indent = function(node, nodeId) {
	var str = '';
	if (this.root.id != node.pid) {
		for (var n=0; n<this.aIndent.length; n++)
			str += '<img src="' + ( (this.aIndent[n] == 1 && this.config.useLines) ? this.icon.line : this.icon.empty ) + '" alt="" />';
		(node._ls) ? this.aIndent.push(0) : this.aIndent.push(1);
		if (node._hc) {
			str += '<img id="j' + this.obj + nodeId + '" style="CURSOR:hand" onClick="javascript: ' + this.obj + '.o(' + nodeId + ');" src="';
			if (!this.config.useLines) str += (node._io) ? this.icon.nlMinus : this.icon.nlPlus;
			else str += ( (node._io) ? ((node._ls && this.config.useLines) ? this.icon.minusBottom : this.icon.minus) : ((node._ls && this.config.useLines) ? this.icon.plusBottom : this.icon.plus ) );
			str += '" alt="" />';
		} else str += '<img src="' + ( (this.config.useLines) ? ((node._ls) ? this.icon.joinBottom : this.icon.join ) : this.icon.empty) + '" alt="" />';
	}
	return str;
};

// Checks if a node has any children and if it is the last sibling
dTree.prototype.setCS = function(node) {
	var lastId;
	for (var n=0; n<this.aNodes.length; n++) {
		if (this.aNodes[n].pid == node.id) node._hc = true;
		if (this.aNodes[n].pid == node.pid) lastId = this.aNodes[n].id;
	}
	if (lastId==node.id) node._ls = true;
};

// Returns the selected node
dTree.prototype.getSelected = function() {
	var sn = this.getCookie('cs' + this.obj);
	return (sn) ? sn : null;
};

// Highlights the selected node
dTree.prototype.s = function(id) {
	if (!this.config.useSelection) return;
	var cn = this.aNodes[id];
	if (cn._hc && !this.config.folderLinks) return;
	if (this.selectedNode != id) {
		if (this.selectedNode || this.selectedNode==0) {
			eOld = document.getElementById("s" + this.obj + this.selectedNode);
			eOld.className = "node";
		}
		eNew = document.getElementById("s" + this.obj + id);
		eNew.className = "nodeSel";
		this.selectedNode = id;
		if (this.config.useCookies) this.setCookie('cs' + this.obj, cn.id);
	}
};

// Toggle Open or close
dTree.prototype.o = function(id) {
	var cn = this.aNodes[id];
	this.nodeStatus(!cn._io, id, cn._ls);
	cn._io = !cn._io;
	if (this.config.closeSameLevel) this.closeLevel(cn);
	if (this.config.useCookies) this.updateCookie();
};

// Open or close all nodes
dTree.prototype.oAll = function(status) {
	for (var n=0; n<this.aNodes.length; n++) {
		if (this.aNodes[n]._hc && this.aNodes[n].pid != this.root.id) {
			this.nodeStatus(status, n, this.aNodes[n]._ls)
			this.aNodes[n]._io = status;
		}
	}
	if (this.config.useCookies) this.updateCookie();
};

// Opens the tree to a specific node
dTree.prototype.openTo = function(nId, bSelect, bFirst) {
	if (!bFirst) {
		for (var n=0; n<this.aNodes.length; n++) {
			if (this.aNodes[n].id == nId) {
				nId=n;
				break;
			}
		}
	}
	var cn=this.aNodes[nId];
	if (cn.pid==this.root.id || !cn._p) return;
	cn._io = true;
	cn._is = bSelect;
	if (this.completed && cn._hc) this.nodeStatus(true, cn._ai, cn._ls);
	if (this.completed && bSelect) this.s(cn._ai);
	else if (bSelect) this._sn=cn._ai;
	this.openTo(cn._p._ai, false, true);
};

// Opens the tree to a specific node by logic id
dTree.prototype.openToAndHighlightSelectedNode = function(nId, isType, bSelect) {
	for(var i = 0; i < this.aNodes.length; i++)
	{
		if(this.aNodes[i].value==nId && this.aNodes[i].isType==isType)
		{
			break;
		}
	}
	
	if(i < this.aNodes.length)
	{
		this.openTo(this.aNodes[i]._ai, bSelect, true);
	}
};

// Closes all nodes on the same level as certain node
dTree.prototype.closeLevel = function(node) {
	for (var n=0; n<this.aNodes.length; n++) {
		if (this.aNodes[n].pid == node.pid && this.aNodes[n].id != node.id && this.aNodes[n]._hc) {
			this.nodeStatus(false, n, this.aNodes[n]._ls);
			this.aNodes[n]._io = false;
			this.closeAllChildren(this.aNodes[n]);
		}
	}
}

// Closes all children of a node
dTree.prototype.closeAllChildren = function(node) {
	for (var n=0; n<this.aNodes.length; n++) {
		if (this.aNodes[n].pid == node.id && this.aNodes[n]._hc) {
			if (this.aNodes[n]._io) this.nodeStatus(false, n, this.aNodes[n]._ls);
			this.aNodes[n]._io = false;
			this.closeAllChildren(this.aNodes[n]);		
		}
	}
}

// Change the status of a node(open or closed)
dTree.prototype.nodeStatus = function(status, id, bottom) {
	eDiv	= document.getElementById('d' + this.obj + id);
	eJoin	= document.getElementById('j' + this.obj + id);
	if (this.config.useIcons) {
		eIcon	= document.getElementById('i' + this.obj + id);
		eIcon.src = (status) ? this.aNodes[id].iconOpen : this.aNodes[id].icon;
	}
	eJoin.src = (this.config.useLines)?
	((status)?((bottom)?this.icon.minusBottom:this.icon.minus):((bottom)?this.icon.plusBottom:this.icon.plus)):
	((status)?this.icon.nlMinus:this.icon.nlPlus);
	eDiv.style.display = (status) ? 'block': 'none';
};


// [Cookie] Clears a cookie
dTree.prototype.clearCookie = function() {
	var now = new Date();
	var yesterday = new Date(now.getTime() - 1000 * 60 * 60 * 24);
	this.setCookie('co'+this.obj, 'cookieValue', yesterday);
	this.setCookie('cs'+this.obj, 'cookieValue', yesterday);
};

// [Cookie] Sets value in a cookie
dTree.prototype.setCookie = function(cookieName, cookieValue, expires, path, domain, secure) {
	document.cookie =
		escape(cookieName) + '=' + escape(cookieValue)
		+ (expires ? '; expires=' + expires.toGMTString() : '')
		+ (path ? '; path=' + path : '')
		+ (domain ? '; domain=' + domain : '')
		+ (secure ? '; secure' : '');
};

// [Cookie] Gets a value from a cookie
dTree.prototype.getCookie = function(cookieName) {
	var cookieValue = '';
	var posName = document.cookie.indexOf(escape(cookieName) + '=');
	if (posName != -1) {
		var posValue = posName + (escape(cookieName) + '=').length;
		var endPos = document.cookie.indexOf(';', posValue);
		if (endPos != -1) cookieValue = unescape(document.cookie.substring(posValue, endPos));
		else cookieValue = unescape(document.cookie.substring(posValue));
	}
	return (cookieValue);
};

// [Cookie] Returns ids of open nodes as a string
dTree.prototype.updateCookie = function() {
	var str = '';
	for (var n=0; n<this.aNodes.length; n++) {
		if (this.aNodes[n]._io && this.aNodes[n].pid != this.root.id) {
			if (str) str += '.';
			str += this.aNodes[n].id;
		}
	}
	this.setCookie('co' + this.obj, str);
};

// [Cookie] Checks if a node id is in a cookie
dTree.prototype.isOpen = function(id) {
	var aOpen = this.getCookie('co' + this.obj).split('.');
	for (var n=0; n<aOpen.length; n++)
		if (aOpen[n] == id) return true;
	return false;
};

// If Push and pop is not implemented by the browser
if (!Array.prototype.push) {
	Array.prototype.push = function array_push() {
		for(var i=0;i<arguments.length;i++)
			this[this.length]=arguments[i];
		return this.length;
	}
};
if (!Array.prototype.pop) {
	Array.prototype.pop = function array_pop() {
		lastElement = this[this.length-1];
		this.length = Math.max(this.length-1,0);
		return lastElement;
	}
};

// 根据数组中id绝对号及选择状态，设置节点对象及页面当前选择状态
dTree.prototype.setTreeNodeChecked = function(id, checked)
{
		this.aNodes[id].isChecked = checked;
		var elementTemp = document.getElementById('ctrl' + this.obj + id);
		
		if(elementTemp!=null)
		{
			elementTemp.checked = checked;
		}
};


// 以数组中id绝对号的节点为根，如果是类型节点深度优先，如果是节点广度优先。级联选择类型及节点
dTree.prototype.setCorrelateSelect = function(id, checked)
{
	// 设置当前类型节点选择状态
	this.setTreeNodeChecked(id, checked);
	
	// 级联选择其下类型及节点选择状态
	for(var i=0; i < this.aNodes.length; i++)
	{// 循环级联选择类型及节点
		if(this.aNodes[i].pid==this.aNodes[id].id)
		{// 循环当前节点为根节点下一级子类型或节点
			if(this.aNodes[i].isType==false)
			{// 是节点
				if(this.config.correlateSelectType=='all' || this.config.correlateSelectType=='node')
				{
					this.setTreeNodeChecked(this.aNodes[i]._ai, checked);
				}
			}
			else
			{// 类型节点
				// 判断级联层次
				if(this.config.isSingleCorrelateLayer==false)
				{// 无穷级联选择
					this.setCorrelateSelect(this.aNodes[i]._ai, checked);
				}
				else
				{// 一层
					if(this.config.correlateSelectType=='all' || this.config.correlateSelectType=='type')
					{
						this.setTreeNodeChecked(this.aNodes[i]._ai, checked);
					}
				}
			}
		}
	}
};

// 根据数组中id绝对号和目标，设置节点当前选择状态
dTree.prototype.setChecked = function(id, checked)
{
	// 控件为radio类型,清除所有节点控件选择状态
	if(this.config.controlType=='radio')
	{
		for(var i=0; i < this.aNodes.length; i++)
		{
				this.aNodes[i].isChecked = false;
		}
		
		this.setTreeNodeChecked(id, checked);
	}
	else
	{// 为checkbox控件
		// 判断是否使用级联选择
		if(this.config.isCorrelateSelect==true)
		{// 级联选择
			if(this.aNodes[id].isType==true)
			{
				this.setCorrelateSelect(id, checked);
			}
			else
			{
				this.setTreeNodeChecked(id, checked);
			}
		}
		else
		{
			this.setTreeNodeChecked(id, checked);
		}
		
		// 判断当前节点是否是选中状态，只有选中才进行反向级联选
		if(checked==true)
		{
			// 判断是否使用反向级联选
			if(this.config.isReverseCorrelateSelect==true)
			{// 反向级联选
				// 得到当前类型或节点的父亲节点对象，初始化
				var parentNodeTemp = this.aNodes[id]._p;
				
				while(parentNodeTemp.pid!=undefined)
				{// 非根节点选中该类型节点
					if(parentNodeTemp.pid!=-1 || this.config.isRootUseControl==true)
					{
						this.setTreeNodeChecked(parentNodeTemp._ai, true);
					}
					
					parentNodeTemp = parentNodeTemp._p;
				}
			}
		}
	}
		
};

// 根据控件id值设置选择状态
dTree.prototype.setCheckedCtrlId = function(id, checked) 
{
	var str = '';
	
	for(var i=0; i<id.length; i++)
	{
		var char = id.substr(i, 1);
		
		if(char=='0' || char=='1' || char=='2' || char=='3' || char=='4' || char=='5' || char=='6' || char=='7' || char=='8' || char=='9')
		{
			str += char;
		}
	}
	
	if(str!='')
	{
		this.aNodes[str].isChecked = checked;
	}
};

function showModalDialogTree(tree, inputCtrlId)
{
    
    var array = new Array(3);
    array[0] = tree;
    array[1] = inputCtrlId;
    array[2] = window;
    
	window.showModalDialog("../jsp/commonTreeIframe.jsp", array, 'dialogWidth=300px;dialogHeight=520px;status:no;resizable:no;help:no;scroll:no');
}

function printTreeToNewWindow(tree, inputCtrlId)
{
	showModalDialogTree(tree, inputCtrlId);
}

function replaceAll(strPrimalString, strOldSubString, strNewSubString)
{
	var str = strPrimalString;
	
	while(str.indexOf(strOldSubString) >= 0)
	{
		str = str.replace(strOldSubString, strNewSubString);
	}
	
	return str;
}

function replaceSpecialCharacter(str)
{
	var str1 = replaceAll(str, '&amp;', '&');
	
	var str2 = replaceAll(str1, '&lt;', '<');
	
	var str3 = replaceAll(str2, '&gt;', '>');
	
	var str4 = replaceAll(str3, '&#039;', '\'');
	
	var str5 = replaceAll(str4, '&#034;', '\"');

	return str5;
}

// 得到用户选择的节点名称，并以分号";"分割,如需要其它分割符请在config中定义
dTree.prototype.getSelectedNameList = function()
{
	if(this.config.useControl==true)
	{// 使用控件
		// 判断用户使用控件类型
		if(this.config.controlType=='radio')
		{
			var str = '';
			
			for(var i=0; i < this.aNodes.length; i++)
			{
				if(this.aNodes[i].isChecked==true)
				{
					str += this.aNodes[i].name + this.config.strSplitNameSeparator + ' ';
					
					break;
				}
			}
		}
		else if(this.config.controlType=='checkbox')
		{
			var str = '';
			
			for(var i=1; i < this.aNodes.length; i++)
			{
				if(this.aNodes[i].isChecked==true)
				{
					if(this.aNodes[i].isType==true)
					{// 类型
						if(this.config.correlateSelectType=='all' || this.config.correlateSelectType=='type')
						{
							str += this.aNodes[i].name + this.config.strSplitNameSeparator + ' ';
						}
					}
					else
					{// 节点
						if(this.config.correlateSelectType=='all' || this.config.correlateSelectType=='node')
						{
							str += this.aNodes[i].name + this.config.strSplitNameSeparator + ' ';
						}
					}
				}
			}
		}
		
		// str = replaceSpecialCharacter(str);
		
		if(str.substring(str.length - 2, str.length)==this.config.strSplitNameSeparator + ' ')
		{
			return str.substring(0, str.length - 2);
		}
		else
		{
			return str;
		}
	}
};

// 得到用户选择的节点数据库id，并以分号","分割,如需要其它分割符请在config中定义
dTree.prototype.getSelectedDatabaseIdList = function()
{
	if(this.config.useControl==true)
	{// 使用控件
		// 判断用户使用控件类型
		var str = '';
		
		if(this.config.controlType=='radio')
		{
			for(var i=0; i < this.aNodes.length; i++)
			{
				if(this.aNodes[i].isChecked==true)
				{
					str += this.aNodes[i].value + this.config.strSplitIdSeparator;
					
					break;
				}
			}
		}
		else if(this.config.controlType=='checkbox')
		{
			for(var i=1; i < this.aNodes.length; i++)
			{
				if(this.aNodes[i].isChecked==true)
				{
					if(this.aNodes[i].isType==true)
					{// 类型
						if(this.config.correlateSelectType=='all' || this.config.correlateSelectType=='type')
						{
							str += this.aNodes[i].value + this.config.strSplitIdSeparator;
						}
					}
					else
					{// 节点
						if(this.config.correlateSelectType=='all' || this.config.correlateSelectType=='node')
						{
							str += this.aNodes[i].value + this.config.strSplitIdSeparator;
						}
					}
				}
			}
		}
		
		if(str.substring(str.length - 1, str.length)==this.config.strSplitIdSeparator)
		{
			return str.substring(0, str.length - 1);
		}
		else
		{
			return str;
		}
	}
};

// 根据节点数据库ID和类型标志高亮显示相应单个节点
dTree.prototype.highlightSelectedNode = function(value, isType) {
	if(value=="")
	{
		value = 0;
	}
	
	if(isType=="true" || isType==true)
	{
		isType = true;
	}
	else
	{
		isType = false;
	}

	for(var i=0; i < this.aNodes.length; i++)
	{// 遍历这个数节点
		// 判断是否存在与value和isType值相等的节点
		if(this.aNodes[i].value==value && this.aNodes[i].isType==isType)
		{
			// 将相应节点加亮显示
			this.s(this.aNodes[i]._ai);
			
			break;
		}
	}
};

// 根据数组中id绝对号及可操作状态，设置对应控件当前可操作状态
dTree.prototype.setNodeControlDisabled = function(id, disabled)
{
	var elementTemp = document.getElementById('ctrl' + this.obj + id);
	
	if(elementTemp!=null)
	{
		elementTemp.disabled = disabled;
	}
};

// 将所有树中的控件设置成disabled状态
dTree.prototype.setAllControlDisabled = function(disabled)
{
	if(this.config.useControl==true)
	{// 使用控件
		for(var i=0; i < this.aNodes.length; i++)
		{// 遍历这个树节点
			// 设置状态
			this.setNodeControlDisabled(this.aNodes[i]._ai, disabled);
		}
	}
};

// 根据节点数据库ID和类型标志设置选中状态
dTree.prototype.setCheckedNode = function(id, isType, checked) {
	if(isType=="true" || isType==true)
	{
		isType = true;
	}
	else
	{
		isType = false;
	}

	for(var i=0; i < this.aNodes.length; i++)
	{// 遍历这个数节点
		// 判断是否存在与value和isType值相等的节点
		if(this.aNodes[i].value==id && this.aNodes[i].isType==isType)
		{
			// 将相应节点加亮显示
			this.setChecked(this.aNodes[i]._ai, checked);
			
			break;
		}
	}
};

dTree.prototype.reset = function()
{
	if(this.config.useControl==true)
	{// 只有使用控件才设置选择状态
		// 判断使用控件类型
		if(this.config.controlType=='radio')
		{
			// 清除数对象选择状态
			for(var i=0; i < this.aNodes.length; i++)
			{
				this.aNodes[i].isChecked = false;
			}
			
			// 从页面控件复制选择状态
			for(var i=0; i < this.aNodes.length; i++)
			{
				var elementTemp = document.getElementById('ctrl' + this.obj + this.aNodes[i]._ai);
				if(elementTemp!=null)
				{
					if(elementTemp.checked==true)
					{
						// 设置选择状态
						this.aNodes[i].isChecked = true;
						
						break;
					}
				}
			}
		}
		else if(this.config.controlType=='checkbox')
		{
			// 复制选择状态
			for(var i=0; i < this.aNodes.length; i++)
			{
				var elementTemp = document.getElementById('ctrl' + this.obj + this.aNodes[i]._ai);
				if(elementTemp!=null)
				{
					// 设置选择状态
					this.aNodes[i].isChecked = elementTemp.checked;
				}
			}
		}
	}
};

// 根据节点数据库ID和类型标志设置选中状态
dTree.prototype.getNodeName = function(id, isType) {
	if(isType=="true" || isType==true)
	{
		isType = true;
	}
	else
	{
		isType = false;
	}

	var strName = "";
	
	for(var i=0; i < this.aNodes.length; i++)
	{// 遍历这个数节点
		// 判断是否存在与value和isType值相等的节点
		if(this.aNodes[i].value==id && this.aNodes[i].isType==isType)
		{
			strName = this.aNodes[i].name;
			
			break;
		}
	}
	
	return strName;
};

//add by jeton.dong 20110805
		dTree.prototype.getMyNodeId = function(dbid) {
			var jetonTreeId = "";
			for(var i=0; i < this.aNodes.length; i++)
			{// 遍历这个数节点
				// 判断是否存在与value和isType值相等的节点
				if(this.aNodes[i].value==dbid)
				{
					jetonTreeId = i;
					
					break;
				}
			}
			return jetonTreeId;
		};