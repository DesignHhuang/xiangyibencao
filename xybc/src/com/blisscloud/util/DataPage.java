// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 2007-6-21 13:12:20
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   DataPage.java

package com.blisscloud.util;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;

public class DataPage {

	public DataPage() {
		currentPageNumber = 0;
		eachPageRowLimit = 0;
		rowLimit = 0;
		pageNumberLimit = 0;
	}

	public List getPageDataList(SQLQuery countSQLQuery,
			SQLQuery selectSQLQuery, int currentPageNumber, int eachPageRowLimit) {
		this.currentPageNumber = currentPageNumber;
		this.eachPageRowLimit = eachPageRowLimit;
		if (eachPageRowLimit == 0)
			return new ArrayList();
		rowLimit = Integer.parseInt(countSQLQuery.uniqueResult().toString());
		pageNumberLimit = getRowLimit() % eachPageRowLimit != 0 ? getRowLimit()
				/ eachPageRowLimit + 1 : getRowLimit() / eachPageRowLimit;
		selectSQLQuery.setFirstResult((currentPageNumber - 1)
				* eachPageRowLimit);
		selectSQLQuery.setMaxResults(eachPageRowLimit);
		List list = selectSQLQuery.list();
		return list;
	}

	public List getPageDataList(int count, SQLQuery selectSQLQuery,
			int currentPageNumber, int eachPageRowLimit) {
		this.currentPageNumber = currentPageNumber;
		this.eachPageRowLimit = eachPageRowLimit;
		rowLimit = count;
		int total = eachPageRowLimit;
		if (count < eachPageRowLimit)
			total = count;
		pageNumberLimit = getRowLimit() % eachPageRowLimit != 0 ? getRowLimit()
				/ eachPageRowLimit + 1 : getRowLimit() / eachPageRowLimit;
		selectSQLQuery.setFirstResult((currentPageNumber - 1)
				* eachPageRowLimit);
		selectSQLQuery.setMaxResults(total);
		return selectSQLQuery.list();
	}

	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	public int getEachPageRowLimit() {
		return eachPageRowLimit;
	}

	public int getRowLimit() {
		return rowLimit;
	}

	public int getPageNumberLimit() {
		return pageNumberLimit;
	}

	private int currentPageNumber;
	private int eachPageRowLimit;
	private int rowLimit;
	private int pageNumberLimit;
}