package com.blisscloud.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import org.hibernate.SQLQuery;

public class PageTableBean implements Serializable {
	private int curPage;
	private int maxPage;
	private int maxRowCount;
	private int rowsPerPage;
	private List data;
	private boolean isEnd;
	private boolean isStart;
	private String picPath;
	private Vector columnList;
	private String pageURL;
	private HttpServletRequest myRequest;
	private JspWriter myOut;

	public PageTableBean() {
		data = new ArrayList();
		isEnd = false;
		isStart = false;
		picPath = "";
	}

	public void getPageDataList(SQLQuery countSQLQuery,
			SQLQuery selectSQLQuery, int currentPageNumber, int eachPageRowLimit) {
		curPage = currentPageNumber;
		rowsPerPage = eachPageRowLimit;
		maxRowCount = Integer.parseInt(countSQLQuery.uniqueResult().toString());
		maxPage = maxRowCount % rowsPerPage != 0 ? maxRowCount / rowsPerPage
				+ 1 : maxRowCount / rowsPerPage;
		selectSQLQuery.setFirstResult((currentPageNumber - 1)
				* eachPageRowLimit);
		selectSQLQuery.setMaxResults(eachPageRowLimit);
		data = selectSQLQuery.list();
	}

	public PageTableBean(String _curPage, List list) {
		data = new ArrayList();
		isEnd = false;
		isStart = false;
		picPath = "";
		setCurPage(strToInt(_curPage));
		setData(list);
		setMaxRowCount(list.size());
		countMaxPage();
	}

	public PageTableBean(int _perRowPage, String _curPage, List list) {
		data = new ArrayList();
		isEnd = false;
		isStart = false;
		picPath = "";
		setRowsPerPage(_perRowPage);
		setCurPage(strToInt(_curPage));
		setData(list);
		if (list != null)
			setMaxRowCount(list.size());
		countMaxPage();
	}

	public PageTableBean(int _perRowPage, String _curPage, int _iCount) {
		data = new ArrayList();
		isEnd = false;
		isStart = false;
		picPath = "";
		setRowsPerPage(_perRowPage);
		setCurPage(strToInt(_curPage));
		setMaxRowCount(_iCount);
		countMaxPage();
	}

	public PageTableBean(int _perRowsPage, List allData, Vector colList,
			String _pageURL, HttpServletRequest _request, JspWriter _myOut) {
		data = new ArrayList();
		isEnd = false;
		isStart = false;
		picPath = "";
		setRowsPerPage(_perRowsPage);
		setData(allData);
		setColumnList(colList);
		setPageURL(_pageURL);
		setMyRequest(_request);
		setMyOut(_myOut);
		String cur = _request.getParameter("pageNo");
		setCurPage(strToInt(cur));
		countMaxPage();
	}

	public List getCurPageList() {
		ArrayList array = new ArrayList();
		if (data == null)
			return null;
		if (data.isEmpty())
			return array;
		if (curPage == 1 && maxPage == 1)
			return data;
		if (curPage < maxPage) {
			int start = (curPage - 1) * rowsPerPage;
			int end = curPage * rowsPerPage;
			for (int k = start; k < end; k++)
				array.add(data.get(k));

		} else if (curPage == maxPage) {
			int start = (curPage - 1) * rowsPerPage;
			int end = data.size();
			for (int k = start; k < end; k++)
				array.add(data.get(k));

		}
		return array;
	}

	public List getLocalPageList() {
		return data;
	}

	private int strToInt(String s) {
		int icurPage = 0;
		try {
			icurPage = Integer.parseInt(s);
		} catch (Exception e) {
			icurPage = 1;
		}
		return icurPage;
	}

	public boolean isOnlyOnepage() {
		return getMaxPage() < 2;
	}

	public boolean isFirstPage() {
		return curPage == 1;
	}

	public boolean isEndPage() {
		return curPage == maxPage;
	}

	public boolean isMiddlePage() {
		return curPage > 1 && curPage < maxPage;
	}

	public void countMaxPage() {
		if (maxRowCount % rowsPerPage == 0)
			maxPage = maxRowCount / rowsPerPage;
		else
			maxPage = maxRowCount / rowsPerPage + 1;
	}

	public int getCurPage() {
		return curPage;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public int getMaxRowCount() {
		return maxRowCount;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public boolean isEnd() {
		if (curPage < maxPage)
			isEnd = false;
		else
			isEnd = true;
		return isEnd;
	}

	public boolean isStart() {
		if (curPage == 1)
			isStart = true;
		else
			isStart = false;
		return isStart;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public void setMaxRowCount(int maxRowCount) {
		this.maxRowCount = maxRowCount;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public void setData(List data) {
		this.data = data;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public List getData() {
		return data;
	}

	public int prePage() {
		return curPage - 1;
	}

	public int nextPage() {
		return curPage + 1;
	}

	public int getNowPageNumber() {
		String curPage = myRequest.getParameter("pageNo");
		int h = strToInt(curPage);
		return h;
	}

	public Vector getColumnList() {
		return columnList;
	}

	public void setColumnList(Vector columnList) {
		this.columnList = columnList;
	}

	private String getFooter1() {
		StringBuffer buf = new StringBuffer();
		buf.append("<TABLE cellSpacing=0 cellPadding=0 align=center border=0>")
				.append("<TBODY><TR><TD align=middle>")
				.append((new StringBuilder("��<SPAN class=red>"))
						.append(getMaxRowCount())
						.append("</SPAN>����¼ <SPAN class=red>")
						.append(getMaxPage())
						.append("</SPAN>ҳ  ÿҳ<SPAN class=red>")
						.append(getRowsPerPage())
						.append("</SPAN>����¼<SPAN class=red> ��ǰ��")
						.append(curPage).append("ҳ</SPAN>").toString())
				.append("</TD>");
		return buf.toString();
	}

	public String getFooter2() {
		StringBuffer buf = new StringBuffer();
		buf.append(" <TD align=middle>");
		if (isOnlyOnepage()) {
			buf.append((new StringBuilder("<a><IMG height=10 hspace=3 src="))
					.append(picPath)
					.append("/images/arow_fore2.gif width=22 border=0 alt='\u7B2C\u4E00\u9875'></a>")
					.toString());
			buf.append((new StringBuilder("<a><IMG height=10 hspace=3 src="))
					.append(picPath)
					.append("/images/arow_back2.gif width=22 border=0 alt='\u4E0A\u4E00\u9875'></a>")
					.toString());
			buf.append((new StringBuilder("<a><IMG height=10 hspace=3 src="))
					.append(picPath)
					.append("/images/arow_next2.gif width=22 border=0 alt='\u4E0B\u4E00\u9875'></a>")
					.toString());
			buf.append((new StringBuilder("<a><IMG height=10 hspace=3 src="))
					.append(picPath)
					.append("/images/arow_last2.gif width=22 border=0 alt='\u6700\u540E\u4E00\u9875'></a>")
					.toString());
		} else if (isFirstPage()) {
			buf.append((new StringBuilder("<a >\t<IMG height=10 hspace=3 src="))
					.append(picPath)
					.append("/images/arow_fore2.gif width=22 border=0 alt='\u7B2C\u4E00\u9875'></a>")
					.toString());
			buf.append((new StringBuilder("<a>\t<IMG height=10 hspace=3 src="))
					.append(picPath)
					.append("/images/arow_back2.gif width=22 border=0 alt='\u4E0A\u4E00\u9875'></a>")
					.toString());
			buf.append((new StringBuilder("<a  href='javascript:goToPage("))
					.append(nextPage())
					.append(")';>\t<IMG height=10 hspace=3 src=")
					.append(picPath)
					.append("/images/arow_next.gif width=22 border=0 alt='\u4E0B\u4E00\u9875'></a>")
					.toString());
			buf.append((new StringBuilder("<a  href='javascript:goToPage("))
					.append(getMaxPage())
					.append(")';>\t<IMG height=10 hspace=3 src=")
					.append(picPath)
					.append("/images/arow_last.gif width=22 border=0 alt='\u6700\u540E\u4E00\u9875'></a>")
					.toString());
		} else if (isEndPage()) {
			buf.append((new StringBuilder(
					"<a href=javascript:goToPage(1);>\t<IMG height=10 hspace=3 src="))
					.append(picPath)
					.append("/images/arow_fore.gif  width=22 border=0 border=0 alt='\u7B2C\u4E00\u9875'></a>")
					.toString());
			buf.append((new StringBuilder("<a href=javascript:goToPage("))
					.append(prePage())
					.append(");>\t<IMG height=10 hspace=3 src=")
					.append(picPath)
					.append("/images/arow_back.gif width=22 border=0 alt='\u4E0A\u4E00\u9875'></a>")
					.toString());
			buf.append((new StringBuilder("<a>\t<IMG height=10 hspace=3 src="))
					.append(picPath)
					.append("/images/arow_next2.gif width=22 border=0 alt='\u4E0B\u4E00\u9875'></a>")
					.toString());
			buf.append((new StringBuilder("<a>\t<IMG height=10 hspace=3 src="))
					.append(picPath)
					.append("/images/arow_last2.gif  width=22 border=0 alt='\u6700\u540E\u4E00\u9875'></a>")
					.toString());
		} else {
			buf.append((new StringBuilder(
					"<a  href=javascript:goToPage(1);>\t<IMG height=10 hspace=3 src="))
					.append(picPath)
					.append("/images/arow_fore.gif width=22 border=0 alt='\u7B2C\u4E00\u9875'></a>")
					.toString());
			buf.append((new StringBuilder("<a  href=javascript:goToPage("))
					.append(prePage())
					.append(");>\t<IMG  height=10  hspace=3  src=")
					.append(picPath)
					.append("/images/arow_back.gif   width=22 border=0 alt='\u4E0A\u4E00\u9875'></a>")
					.toString());
			buf.append((new StringBuilder("<a  href=javascript:goToPage("))
					.append(nextPage())
					.append(");>\t<IMG  height=10  hspace=3  src=")
					.append(picPath)
					.append("/images/arow_next.gif   width=22 border=0 alt='\u4E0B\u4E00\u9875'></a>")
					.toString());
			buf.append((new StringBuilder("<a  href=javascript:goToPage("))
					.append(getMaxPage())
					.append(");>\t<IMG  height=10  hspace=3  src=")
					.append(picPath)
					.append("/images/arow_last.gif   width=22 border=0 alt='\u6700\u540E\u4E00\u9875'></a>")
					.toString());
		}
		return buf.toString();
	}

	private String getFooter3() {
		StringBuffer buf = new StringBuffer();
		buf.append("</TD><TD align=middle>\u7B2C\uFF1A<select name=selPage class=bot onChange=javascript:goNewPage();>");
		for (int n = 1; n < getMaxPage() + 1; n++) {
			String s = "";
			if (n == curPage)
				s = "selected";
			buf.append((new StringBuilder("<option value=")).append(n)
					.append("  ").append(s).append(" >").append(n)
					.append(" </option>").toString());
		}

		buf.append(" </select>\u9875</TD><TD align=middle>&nbsp;</TD></TR></TBODY></TABLE>");
		return buf.toString();
	}

	public String getFooter() {
		return (new StringBuilder(String.valueOf(getFooter1()))).append(" ")
				.append(getFooter2()).append(" ").append(getFooter3())
				.toString();
	}

	public static void main(String s[]) {
		PageTableBean b = new PageTableBean();
		b.setPageURL("list.jsp");
	}

	public void outMyFooter() {
		try {
			myOut.println(getFooter());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public String getTableHeader() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<SCRIPT  LANGUAGE=javascript> \n");
		buffer.append("function  goNewPage(){ \n");
		buffer.append("document.form1.curpage.value = document.form1.selPage.value; \n");
		buffer.append((new StringBuilder("document.form1.action="))
				.append(getPageURL()).append("; \n").toString());
		buffer.append(" document.form1.submit(); \n");
		buffer.append("} \n");
		buffer.append(" function  goToPage(_curpage){ \n");
		buffer.append("document.form1.curpage.value = _curpage; \n");
		buffer.append((new StringBuilder("document.form1.action="))
				.append(getPageURL()).append("; \n").toString());
		buffer.append(" document.form1.submit(); \n");
		buffer.append("} \n");
		buffer.append("</SCRIPT>");
		return buffer.toString();
	}

	public static String getTable(String url) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<SCRIPT  LANGUAGE=javascript> \n");
		buffer.append("function  goNewPage(){ \n");
		buffer.append("document.form1.curpage.value = document.form1.selPage.value; \n");
		buffer.append((new StringBuilder("document.form1.action=")).append(url)
				.append("; \n").toString());
		buffer.append(" document.form1.submit(); \n");
		buffer.append("} \n");
		buffer.append(" function  goToPage(_curpage){ \n");
		buffer.append("document.form1.curpage.value = _curpage; \n");
		buffer.append((new StringBuilder("document.form1.action=")).append(url)
				.append("; \n").toString());
		buffer.append(" document.form1.submit(); \n");
		buffer.append("} \n");
		buffer.append("</SCRIPT>");
		return buffer.toString();
	}

	public String getColWidth() {
		float k = 1 / columnList.size();
		int kk = (int) k * 100;
		String colWith = (new StringBuilder("width=")).append(kk).append("%")
				.toString();
		return colWith;
	}

	public String getPageURL() {
		return pageURL;
	}

	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}

	public HttpServletRequest getMyRequest() {
		return myRequest;
	}

	public void setMyRequest(HttpServletRequest myRequest) {
		this.myRequest = myRequest;
	}

	public JspWriter getMyOut() {
		return myOut;
	}

	public void setMyOut(JspWriter myOut) {
		this.myOut = myOut;
	}

	public void outTableHeader() {
		try {
			myOut.println(getTableHeader());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

}