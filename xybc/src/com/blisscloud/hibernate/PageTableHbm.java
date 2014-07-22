package com.blisscloud.hibernate;

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;



/** 
 * @author peter.chen
 * 时间 2006-10-23
 * 在gcf给的代码的基础上Peter.chen作了些修改
 * 传HQL语句，返回数据记录的集合，并能作分页处理
 * 详见Class类里的说明
 * 另在jsp网页中，要将page4hibernate.js，src进来
 * 
 */
public class PageTableHbm implements java.io.Serializable
{
        private int curPage =1; //当前是第几页
        private int maxPage=1 ; //一共有多少页
        private int maxRowCount=7; //一共有多少行
        private int rowsPerPage=3 ;//每页多少行
        private String baseUrl=null; //jsp page翻页列表对应的URL，modify by peter
        private String HQL=null;        // modify by peter  
        //private List data = new ArrayList(); //modify by peter,实时从数据库内取回数据
        private boolean isEnd = false;
        private boolean isStart = false;
        private java.util.Vector columnList;
        private String pageURL;
        private HttpServletRequest myRequest;
        private JspWriter myOut;
        private String imagepath="";

        public PageTableHbm(){
            
        }
                
        /**
         *  分页构造方法
         * @param _baseUrl    列表对应的url
         * @param _imagepath  图片相对路程,注：最后带路径符 "/"
         * @param _curPage    当前页数
         * @param _perRowPage 每页的条数
         * @param _hql        HQL语句
         */
        
        public PageTableHbm(String _baseUrl,String _imagepath,String  _curPage,int _perRowPage,String _hql){
            
            int Count=getRecordCount(_hql); // 得到记录的总条数
            this.setBaseUrl(_baseUrl);      // modify by peter Chen
            //this.setData()                // setData方法，这里不需要再用，因为getCurPageList方法里，实时去从数据库内取回数据 
            this.setHQL(_hql);   
            
            this.setImagepath(_imagepath);
            this.setRowsPerPage(_perRowPage);
            this.setCurPage(this.strToInt(_curPage));
            this.setMaxRowCount(Count);
            this.countMaxPage(); 
            
        }
        
public PageTableHbm(String _baseUrl,String _imagepath,String  _curPage,int _perRowPage,String _hql,String ss){
            
            int Count=getRecordCounts(_hql); // 得到记录的总条数
            this.setBaseUrl(_baseUrl);      // modify by peter Chen
            //this.setData()                // setData方法，这里不需要再用，因为getCurPageList方法里，实时去从数据库内取回数据 
            this.setHQL(_hql);   
            
            this.setImagepath(_imagepath);
            this.setRowsPerPage(_perRowPage);
            this.setCurPage(this.strToInt(_curPage));
            this.setMaxRowCount(Count);
            this.countMaxPage(); 
            
        }
        
        /** 
         *  分页构造方法
         * @param _baseUrl    列表对应的url
         * @param _curPage    当前页数
         * @param _perRowPage 每页的条数
         * @param _hql        HQL语句
         */
        
//        public PageTableHbm(String _baseUrl,String  _curPage,int _perRowPage,String _hql){
//            
//            int Count=getRecordCount(_hql); // 得到记录的总条数
//            this.setBaseUrl(_baseUrl);      // modify by peter Chen
//            //this.setData()                // setData方法，这里不需要再用，因为getCurPageList方法里，实时去从数据库内取回数据 
//            this.setHQL(_hql);           
//            this.setRowsPerPage(_perRowPage);
//            this.setCurPage(this.strToInt(_curPage));
//            this.setMaxRowCount(Count);
//            this.countMaxPage(); 
//            
//        }

        /**
         * 从HQL语句内得到记录的总条数
         * @param _hql HQL语句
         * @return
         */
        private int getRecordCount(String _hql)
        {
            // 不用初始化集合就可以得到其大小：
            //( (Integer) session.iterate("select count(*) from ....").next() ).intValue();
            int Count=0;
            Session  session =null;
            try {
            	session = HibernateUtil.currentSession();
            	Count= session.createQuery(_hql).list().size();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                HibernateUtil.closeSession();
            }
            
            return Count; 
             
        }
        
        private int getRecordCounts(String _hql)
        {
            // 不用初始化集合就可以得到其大小：
            //( (Integer) session.iterate("select count(*) from ....").next() ).intValue();
            int Count=0;
            Session  session =null;
            try {
            	session = HibernateUtil.currentSession();
            	Count= session.createSQLQuery(_hql).list().size();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                HibernateUtil.closeSession();
            }
            return Count; 
             
        }

        
        
        /**
         * @deprecated  因为传list，将所有数据带入，
         *              数据量太大，这样效率将不会很高
         * @param _curPage
         * @param list
         */
        public PageTableHbm(String  _curPage,List list){
          this.setCurPage(this.strToInt(_curPage));
          // this.setData(list);
          this.setMaxRowCount(list.size());
          this.countMaxPage();
        }
        /**
         * @deprecated  因为传list，将所有数据带入，
         *              数据量太大，这样效率将不会很高
         * @param _perRowPage
         * @param _curPage
         * @param list
         */
        public PageTableHbm(int _perRowPage,String  _curPage,List list){
          this.setRowsPerPage(_perRowPage);
          this.setCurPage(this.strToInt(_curPage));
          //this.setData(list);
          if(list!=null){
             this.setMaxRowCount(list.size());
          }
         this.countMaxPage();
        }
        
 
        /**
         * @deprecated modify by peter.chen
         * @param _perRowPage
         * @param _curPage
         * @param _iCount
         */  
        public PageTableHbm(int _perRowPage,String  _curPage,int _iCount){
          this.setRowsPerPage(_perRowPage);
          this.setCurPage(this.strToInt(_curPage));
         this.setMaxRowCount(_iCount);
         this.countMaxPage();
        }
        
     /**
      * @deprecated modify by peter Chen
      * @param _perRowsPage
      * @param allData
      * @param colList
      * @param _pageURL
      * @param _request
      * @param _myOut
      */

        public PageTableHbm(int _perRowsPage,List allData,Vector colList,String _pageURL,HttpServletRequest _request,JspWriter _myOut){
          this.setRowsPerPage(_perRowsPage);
          //this.setData(allData);
          this.setColumnList(colList);
          this.setPageURL(_pageURL);
          this.setMyRequest(_request);
          this.setMyOut(_myOut);
          String cur=_request.getParameter("pageNo");
          this.setCurPage(this.strToInt(cur));
          this.countMaxPage();
       }


      public List getCurPageList() {
          
          Session ses=null;
          int start=0;
          List list=null;
          list=new ArrayList();
          
             ses = HibernateUtil.currentSession();
              //HQL的判断                
              if (this.HQL==null)
              {
                  try {
                    throw new Exception("HQL comment 不能为空!");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                HibernateUtil.closeSession();
                return null; //没有查到数据
              }
              
            Query query = ses.createQuery(this.HQL);
            // query.setCharacter("sex", 'F');              
              //从第几条记录开始取数据
               start=(this.curPage-1)*(this.rowsPerPage); //新一页的首记录 \
              // 从第'firstrecord'条开始取出'rowsPerPage'条记录
                query.setFirstResult(start);
                query.setMaxResults(this.rowsPerPage);
              
                
                list = query.list();
                /*
              for (Iterator it = query.iterate(); it.hasNext();) {
                  Object obj = (Object) it.next();
                  list.add(obj);
                
              } */ 
                HibernateUtil.closeSession();
              return list;
  }
        
      
public List getCurPageLists() {
          
          Session ses=null;
          Transaction tx=null;
          int start=0;
          List list=null;
          list=new ArrayList();
          
             ses = HibernateUtil.currentSession();
              tx = ses.beginTransaction();
              
              //HQL的判断                
              if (this.HQL==null)
              {
                  try {
                    throw new Exception("HQL comment 不能为空!");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                HibernateUtil.closeSession();
                return null; //没有查到数据
              }
              
            Query query = ses.createSQLQuery(this.HQL);
            // query.setCharacter("sex", 'F');              
              //从第几条记录开始取数据
               start=(this.curPage-1)*(this.rowsPerPage); //新一页的首记录 \
              // 从第'firstrecord'条开始取出'rowsPerPage'条记录
                query.setFirstResult(start);
                query.setMaxResults(this.rowsPerPage);
              
                
                list = query.list();
                /*
              for (Iterator it = query.iterate(); it.hasNext();) {
                  Object obj = (Object) it.next();
                  list.add(obj);
                
              } */ 
                HibernateUtil.closeSession();
              return list;
  }


      
    
        private int strToInt(String s) {
          int icurPage = 0;
          try {
            icurPage = Integer.parseInt(s);
          }
          catch (Exception e) {
            icurPage = 1;
          }
          return icurPage;
        }
        public  boolean  isOnlyOnepage(){
          return  this .getMaxPage()<2;
        }
        public  boolean  isFirstPage(){
          return  this.curPage==1;
        }
        public  boolean  isEndPage(){
          return  this.curPage==this.maxPage;
        }
        public  boolean  isMiddlePage(){
        return  this. curPage>1&&this.curPage<this.maxPage;
         }

        public void countMaxPage() { //根据总行数计算总页数
          if (this.maxRowCount % this.rowsPerPage == 0) {
            this.maxPage = this.maxRowCount / this.rowsPerPage;
          }
          else {
            this.maxPage = this.maxRowCount / this.rowsPerPage + 1;
          }
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
  /**
   * 判断页面是否到头了,如果当前页面数小于最大页面数，
   * 则说明还没有到头，返回true,否则,返回false；
   * @return
   */
  public boolean  isEnd(){
   if(this.curPage<this.maxPage){
      isEnd=false;
    }else {
      isEnd=true;
    }
    return isEnd;
  }
  /**
   * 判断页面是否是首页
   * @return
   */
  public  boolean  isStart(){
   if(this.curPage==1){
     this.isStart=true;
    }else{
      this.isStart=false;
    }
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

  
  
  public void setCurPage(int curPage) {
    this.curPage = curPage;
  }
 
  
  public int prePage(){
     int intpage=this.curPage-1;
     if (intpage<=0)
     {
         intpage=1;
         
     }
             
    return intpage ;
  }
  public int nextPage(){
    return this.curPage+1;
  }
  public int getNowPageNumber(){
    String curPage = (String) myRequest.getParameter("pageNo");
    int h = this.strToInt(curPage);
    return h;
  }
  public java.util.Vector getColumnList() {
    return columnList;
  }
  public void setColumnList(java.util.Vector columnList) {
    this.columnList = columnList;
  }
  
private String getFooter1(){
        StringBuffer buf=new StringBuffer();
        buf.append("<TABLE cellSpacing=0 cellPadding=0 align=center border=0>")
        .append("<TBODY><TR><TD align=middle>")
        .append("共<SPAN class=red>"+getMaxRowCount()+"</SPAN>条记录，共<SPAN class=red>"+getMaxPage()+"</SPAN>页，每页<SPAN class=red>"+getRowsPerPage()+"</SPAN>记录，当前页：<SPAN class=red>"+this.curPage+"</SPAN>")
        .append("</TD>");
        return buf.toString();
   }
   public String getFooter2(){    
       
      //结合page.js 文件进行结合        
      //另在jsp网页中，要将page4hibernate.js，src进来
       
//  curpage 当前页的参数       

     StringBuffer buf=new StringBuffer();
     buf.append(" <TD align=middle>");
    if(this.isOnlyOnepage()){
     buf.append("\n<a><IMG height=10 hspace=3 src='"+this.getImagepath()+"images/arow_fore2.gif' width=22 border=0 alt='第一页'></a>");
     buf.append("\n<a><IMG height=10 hspace=3 src='"+this.getImagepath()+"images/arow_back2.gif' width=22 border=0 alt='上一页'></a>");
     buf.append("\n<a><IMG height=10 hspace=3 src='"+this.getImagepath()+"images/arow_next2.gif' width=22 border=0 alt='下一页'></a>");
     buf.append("\n<a><IMG height=10 hspace=3 src='"+this.getImagepath()+"images/arow_last2.gif' width=22 border=0 alt='最后一页'></a>");
   }else if(this.isFirstPage()){
      buf.append("\n<a >	<IMG height=10 hspace=3 src='"+this.getImagepath()+"images/arow_fore2.gif' width=22 border=0 alt='第一页'></a>");
      buf.append("\n<a>	<IMG height=10 hspace=3 src='"+this.getImagepath()+"images/arow_back2.gif' width=22 border=0 alt='上一页'></a>");
//      buf.append("<a  href='javascript:goToPage("+this.nextPage()+")';>	<IMG height=10 hspace=3 src=images/arow_next.gif width=22 border=0 alt='下一页'></a>");
//      buf.append("<a  href='javascript:goToPage("+this.getMaxPage()+")';>	<IMG height=10 hspace=3 src=images/arow_last.gif width=22 border=0 alt='最后一页'></a>");
      buf.append("\n<a  href=\"javascript:goToPage('"+this.baseUrl+"',"+this.nextPage()+");\"> " +
                 "\n<IMG height=10 hspace=3 src='"+this.getImagepath()+"images/arow_next.gif' width=22 border=0 alt='下一页'></a>");
      buf.append("\n<a  href=\"javascript:goToPage('"+this.baseUrl+"',"+this.getMaxPage()+");\";>   " +
                 "\n<IMG height=10 hspace=3 src='"+this.getImagepath()+"images/arow_last.gif' width=22 border=0 alt='最后一页'></a>");

   }else if(isEndPage()){
     buf.append("\n<a href=\"javascript:goToPage('"+this.baseUrl+"',"+"1);\">	" +
                "\n <IMG height=10 hspace=3 src='"+this.getImagepath()+"images/arow_fore.gif'  width=22 border=0 border=0 alt='第一页'></a>");
     buf.append("\n<a href=\"javascript:goToPage('"+this.baseUrl+"',"+this.prePage()+");\">	" +
                "\n <IMG height=10 hspace=3 src='"+this.getImagepath()+"images/arow_back.gif' width=22 border=0 alt='上一页'></a>");
     buf.append("\n<a>	<IMG height=10 hspace=3 src='"+this.getImagepath()+"images/arow_next2.gif' width=22 border=0 alt='下一页'></a>");
     buf.append("\n<a>	<IMG height=10 hspace=3 src='"+this.getImagepath()+"images/arow_last2.gif'  width=22 border=0 alt='最后一页'></a>");
   }else{
    buf.append("\n<a  href=\"javascript:goToPage('"+this.baseUrl+"',"+"1);\">	<IMG height=10 hspace=3 src='"+this.getImagepath()+"images/arow_fore.gif' width=22 border=0 alt='第一页'></a>");
    buf.append("\n<a  href=\"javascript:goToPage('"+this.baseUrl+"',"+this.prePage()+");\">	 " +
            "\n<IMG  height=10  hspace=3  src='"+this.getImagepath()+"images/arow_back.gif'   width=22 border=0 alt='上一页'></a>");
    buf.append("\n<a  href=\"javascript:goToPage('"+this.baseUrl+"',"+this.nextPage()+");\">	 <IMG  height=10  hspace=3  src='"+this.getImagepath()+"images/arow_next.gif'   width=22 border=0 alt='下一页'></a>");
    buf.append("\n<a  href=\"javascript:goToPage('"+this.baseUrl+"',"+this.getMaxPage()+");\"> <IMG  height=10  hspace=3  src='"+this.getImagepath()+"images/arow_last.gif'   width=22 border=0 alt='最后一页'></a>");
   }
     return buf.toString();
   }

  private String getFooter3(){
        StringBuffer buf=new StringBuffer();
        buf.append("</TD><TD align=middle>第：<select name=selPage class=bot onChange=\"javascript:goToPage('"+this.baseUrl+"',this.value)\">");
        for(int n=1;n<getMaxPage()+1;n++){
        String s="";if(n==this.curPage){ s="selected";}
        buf.append("<option value="+n+"  "+s +" >"+n+" </option>");
        }
      buf.append(" </select>页</TD><TD align=middle>&nbsp;</TD></TR></TBODY></TABLE>");
     return buf.toString();
  }
public String getFooter(){
    return this.getFooter1()+" "+this.getFooter2()+" "+this.getFooter3();
  }
  public static void  main(String[] s){
    PageTableHbm b=new PageTableHbm();
    b.setPageURL("list.jsp");
 }
 public void outMyFooter(){
  try {
    myOut.println(this.getFooter());
  }
  catch (IOException ex) {
    ex.printStackTrace();
  }
 }

 public String  getTableHeader(){
   StringBuffer buffer = new StringBuffer();
   buffer.append("<SCRIPT  LANGUAGE=javascript> \n");
      buffer.append("function  goNewPage(){ \n");
       buffer.append("document.form1.curpage.value = document.form1.selPage.value; \n");
       buffer.append("document.form1.action=" + this.getPageURL() + "; \n");
       buffer.append(" document.form1.submit(); \n");
       buffer.append("} \n");
       buffer.append(" function  goToPage("+this.baseUrl+","+"_curpage){ \n");
       buffer.append("document.form1.curpage.value = _curpage; \n");
       buffer.append("document.form1.action=" + this.getPageURL() + "; \n");
       buffer.append(" document.form1.submit(); \n");
       buffer.append("} \n");
       buffer.append("</SCRIPT>");
       return buffer.toString();
  }
  public static    String getTable(String url){
     StringBuffer buffer = new StringBuffer();
     buffer.append("<SCRIPT  LANGUAGE=javascript> \n");
      buffer.append("function  goNewPage(){ \n");
       buffer.append("document.form1.curpage.value = document.form1.selPage.value; \n");
       buffer.append("document.form1.action="+url+ "; \n");
       buffer.append(" document.form1.submit(); \n");
        buffer.append("} \n");
       buffer.append(" function  goToPage(_curpage){ \n");
       buffer.append("document.form1.curpage.value = _curpage; \n");
       buffer.append("document.form1.action="+url+ "; \n");
       buffer.append(" document.form1.submit(); \n");
       buffer.append("} \n");
       buffer.append("</SCRIPT>");

      return buffer.toString();
  }
 public  String getColWidth(){
   float k=1/columnList.size();
   int kk=(int)k*100;
   String colWith="width="+kk+"%";
   return colWith;
 }
 private  String getAllColTitle(){
   StringBuffer buffer=new StringBuffer();

   buffer.append("<tr align=center bgcolor=#D7DCF7> \n");
   for(int g=0;g<columnList.size();g++){
   String colName=columnList.get(g).toString();
   buffer.append("<td "+getColWidth()+">"+colName+"</td> \n");
   }
   buffer.append("</tr>");
  return buffer.toString();
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
  public void  outTableHeader(){
    try {
      myOut.println(this.getTableHeader());

    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  }

public String getBaseUrl() {
    return baseUrl;
}

public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
}

public String getHQL() {
    return HQL;
}

public void setHQL(String hql) {
    HQL = hql;
}

public String getImagepath() {
    return imagepath;
}

public void setImagepath(String imagepath) {
    this.imagepath = imagepath;
}
}






