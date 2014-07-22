package com.blisscloud.util;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.blisscloud.hibernate.EZHibernateUtil;

public class EZSimpleDataPageBean {

	public EZSimpleDataPageBean() {
		dataPage = new DataPage();
		currentPageNumber = 1;
		eachPageRowLimit = 10;
		rowLimit = 0;
		pageNumberLimit = 0;
		collection = null;
	}

	public void execute(int count, SQLQuery selectSQLQuery) {
		execute(count, selectSQLQuery, currentPageNumber, eachPageRowLimit);
	}

	public void execute(int count, SQLQuery selectSQLQuery,
			int currentPageNumber, int eachPageRowLimit) {
		collection = dataPage.getPageDataList(count, selectSQLQuery,
				currentPageNumber, eachPageRowLimit);
		pageNumberLimit = dataPage.getPageNumberLimit();
	}

	public void execute(SQLQuery countSQLQuery, SQLQuery selectSQLQuery) {
		execute(countSQLQuery, selectSQLQuery, currentPageNumber,
				eachPageRowLimit);
	}

	public void execute(SQLQuery countSQLQuery, SQLQuery selectSQLQuery,
			int currentPageNumber, int eachPageRowLimit) {
		collection = dataPage.getPageDataList(countSQLQuery, selectSQLQuery,
				currentPageNumber, eachPageRowLimit);
		rowLimit = dataPage.getRowLimit();
		pageNumberLimit = dataPage.getPageNumberLimit();
	}

	public void execute(String countSql, String selectSql,
			int currentPageNumber, int eachPageRowLimit) {
		try {
			Session session = EZHibernateUtil.currentSession();
			SQLQuery selectSQLQuery = session.createSQLQuery(selectSql);
			SQLQuery countSQLQuery = session.createSQLQuery(countSql);
			collection = dataPage.getPageDataList(countSQLQuery,
					selectSQLQuery, currentPageNumber, eachPageRowLimit);
			rowLimit = dataPage.getRowLimit();
			pageNumberLimit = dataPage.getPageNumberLimit();
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("Hibernate statement error....");
		} finally {
			EZHibernateUtil.closeSession();
		}

	}

	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}

	public int getEachPageRowLimit() {
		return eachPageRowLimit;
	}

	public void setEachPageRowLimit(int eachPageRowLimit) {
		this.eachPageRowLimit = eachPageRowLimit;
	}

	public int getRowLimit() {
		return rowLimit;
	}

	public void setRowLimit(int rowLimit) {
		this.rowLimit = rowLimit;
	}

	public int getPageNumberLimit() {
		return pageNumberLimit;
	}

	public void setPageNumberLimit(int pageNumberLimit) {
		this.pageNumberLimit = pageNumberLimit;
	}

	public List getCollection() {
		return collection;
	}

	public void setCollection(List collection) {
		this.collection = collection;
	}

	public String getFooter() {
		return this.footer;
	}

	private int prePage() {
		return currentPageNumber - 1;
	}

	private int getMaxPage() {
		return pageNumberLimit;
	}

	private int nextPage() {
		return currentPageNumber + 1;
	}

	private boolean isEndPage() {
		return currentPageNumber == pageNumberLimit;
	}

	private boolean isFirstPage() {
		return currentPageNumber == 1;
	}

	private boolean isOnlyOnepage() {
		return getMaxPage() < 2;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public void setFooter() {
		StringBuffer buf = new StringBuffer();
		buf.append("<TABLE cellSpacing=0 cellPadding=0 align=right border=0>")
				.append("<TBODY><TR><TD align=middle>")
				.append((new StringBuilder("\u5171<SPAN class=red>"))
						.append(rowLimit)
						.append("</SPAN>\u6761\u8BB0\u5F55\uFF0C\u5171<SPAN class=red>")
						.append(pageNumberLimit)
						.append("</SPAN>\u9875\uFF0C\u6BCF\u9875<SPAN class=red>")
						.append(eachPageRowLimit)
						.append("</SPAN>条记录，当前页：<SPAN class=red>")
						.append(getCurrentPageNumber()).append("</SPAN>")
						.toString()).append("</TD>")
				.append(" <TD align=middle>");
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
		buf.append("</TD><TD align=middle>\u7B2C\uFF1A<select name=selPage class=bot onChange=javascript:goNewPage();>");
		for (int n = 1; n < pageNumberLimit + 1; n++) {
			String s = "";
			if (n == currentPageNumber)
				s = "selected";
			buf.append((new StringBuilder("<option value=")).append(n)
					.append("  ").append(s).append(" >").append(n)
					.append(" </option>").toString());
		}

		buf.append(" </select>\u9875</TD><TD align=middle>&nbsp;</TD></TR></TBODY></TABLE>");
		this.footer = buf.toString();
	}

	private DataPage dataPage;
	private int currentPageNumber;// ；当前页面
	private int eachPageRowLimit;// 每页几条数据
	private int rowLimit;// 总共多少行；
	private int pageNumberLimit;// 总共多少页面
	private List collection;
	private String picPath;
	private String footer;

}