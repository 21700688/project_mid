package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.text.ParseException;

	public class TodoItem {
		private int id;
	    private String title;
	    private String memo;
	    private String simpledate;
	    private String category;
	    private String due_date;
	    private int is_completed;
	    private int cate_id;
	    private String today;
	    private int D_day;
	    
	    public TodoItem(String title, String memo){
	    	this.id = 0;
	        this.title=title;
	        this.memo = memo;
	        Date current_date = new Date();
	        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	        this.simpledate = format.format(current_date);
	        this.today = format.format(current_date);
	        this.cate_id = 0;
	        this.D_day = 100000;
	    }
	    
	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public String getMemo() {
	        return memo;
	    }

	    public void setMemo(String memo) {
	        this.memo =memo;
	    }

	    public String getCurrent_date() {
	        return simpledate
	        		;
	    }

	    public void setCurrent_date(String simpledate) {
	    	this.simpledate = simpledate;
	    }
	    
	    public String getCategory() {
	    	return category;
	    }
	    
	    public void setCategory(String category) {
	    	this.category = category;
	    }
	    
	    public 	String getDuedate() {
	    	return due_date;
	    }
	    
	    public void setDuedate(String due_date) {
	    	this.due_date = due_date;
	    }
	    public int getID() {
	    	return id;
	    }
	    public void setID(int id) {
	    	this.id = id;
	    }
	    public int getIs_completed() {
	    	return is_completed;
	    }
	    public void setIs_completed(int is_completed) {
	    	this.is_completed = is_completed;
	    }
	    
	    public String toSaveString() {
	    	return category+"##"+title+"##"+memo+"##"+simpledate+"##"+due_date+"\n";
	    }
	    
	    public String getToday() {
	    	return today;
	    }
	    
	    public void setToday(String today) {
	    	this.today = today;
	    }
	    
	    public int getCateId() {
	    	return cate_id;
	    }
	    
	    public void setCateId(int cate_id) {
	    	this.cate_id = cate_id;
	    }
	    
	    public void setDday() throws ParseException{
	    	SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
	    	Date td = new Date();
	    	
	    	if(!due_date.equals("null")){
	    		Date dd = new SimpleDateFormat("yyyy/MM/dd").parse(due_date);
	    		if((dd.getTime() - td.getTime())/(60*60*1000)<-24) {
	    			this.D_day=-1;
	    			return;
	    		}
	    		long day = (dd.getTime() - td.getTime())/(24*60*60*1000);
	    		this.D_day = (int)day;
	    		this.today = sf.format(td);
	    	}
	    	
	    	this.today = sf.format(td);
	    }
	    
	    public void setDday(int dday) {
	    	this.D_day = dday;
	    }
	    
	    public int getDday() {
	    	return this.D_day;
	    }
	    
	    public String toString() {
	    	String s="";
	    	if(is_completed == 0) {
	    		s = "["+category+"]"+" "+title+" - "+memo+" - "+simpledate+" - "+due_date;
	    		if(D_day==0)s+="-[마감임박] 오늘부터 마감일까지 남은 일수: "+D_day;
	    		else if(D_day<0)s+="[LATE]";
	    		else if(D_day>0&&D_day<100000) s+="- 오늘부터 마감일까지 남은 일수: "+D_day;
	    		else s=s;
	    		return s;}
	    	else
	    		return "["+category+"]"+" "+title+"[V]"+" - "+memo+" - "+simpledate+" - "+due_date;
	
	    }

}
