package com.todo.dao;
import com.todo.service.TodoConnection;
import com.todo.dao.TodoItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.ParseException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;

public class TodoList {
	Connection con;
	
	private List<TodoItem> list;
	
	public TodoList() {
		this.con = TodoConnection.getConnection();
		this.list = new ArrayList<TodoItem>();
	}
	
	public int addCate(String category) {
		String sql1 = "Select * from category where cate_title = '"+category+"'";
		String sql2 = "insert into category (cate_title)"+" values('"+category+"')";
		Statement stmt;
		int i=0;
		int id=0;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql1);
			
			while(rs.next()) {
				i++;
			}
			if(i==0) {
				stmt.executeUpdate(sql2);
			}
			ResultSet rs2 = stmt.executeQuery(sql1);
			while(rs2.next()) {
				id = rs2.getInt("id");
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		 return id;
	}
	public boolean existCate(String category) {
		String sql = "Select * from category where cate_title = '"+category+"'";
		Statement stmt;
		int count=0;
		try {
			stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				count++;
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		if (count==0) return false;
		else return true;
	}
	public int addItem(TodoItem t) {
		String sql = "insert into lists(title,memo,current_date,due_date,today,D_day,cate_id)"+" values(?,?,?,?,?,?,?)";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getMemo());
			pstmt.setString(3, t.getCurrent_date());
			pstmt.setString(4, t.getDuedate());
			pstmt.setString(5, t.getToday());
			pstmt.setInt(6, t.getDday());
			pstmt.setInt(7, t.getCateId());
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(String input) {
		StringTokenizer st = new StringTokenizer(input,",");
		ArrayList<String> s = new ArrayList<String>();
		int i=0;
		
		while(st.hasMoreTokens()) {
			s.add(st.nextToken());
			i++;
		}
		String ip="'"+s.get(0)+"'";
		for(int j=1;j<i;j++) {
			ip+=",'"+s.get(j)+"'";
		}
		String sql = "delete from lists Where id in ("+ip+")";
		Statement stmt;
		int count = 0;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int editItem(TodoItem updated, int id, int cate_id) {
		String sql = "Update lists set title=?, memo=?, current_date=?, due_date=?, today=?, D_day=?,cate_id=?"+" where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,updated.getTitle());
			pstmt.setString(2,updated.getMemo());
			pstmt.setString(3,updated.getCurrent_date());
			pstmt.setString(4,updated.getDuedate());
			pstmt.setString(5,updated.getToday());
			pstmt.setInt(6,updated.getDday());
			pstmt.setInt(7, cate_id);
			pstmt.setInt(8,id);
			pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) throws ParseException {
		ArrayList<TodoItem> list =  new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "Select * from Lists order by "+ orderby;
			if(ordering == 0)
				sql+=" desc";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				int cate_id = rs.getInt("cate_id");
				int is_completed = rs.getInt("is_completed");
				String category = getCate(cate_id);
				TodoItem t = new TodoItem(title,memo);
				t.setID(id);
				t.setCategory(category);
				t.setCurrent_date(current_date);
				t.setDuedate(due_date);
				t.setDday();
				t.setCateId(cate_id);
				t.setIs_completed(is_completed);
				list.add(t);
			}
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from category");
			while(rs.next()) {
				list.add(rs.getString("cate_title"));
			}
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return list;
		}
	
	public ArrayList<TodoItem> getCategoryList(String cate) throws ParseException{
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		if(!existCate(cate)) return list;
		int cate_id = addCate(cate);
		String sql = "Select * from lists where cate_id = '"+cate_id+"'";
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				int is_completed = rs.getInt("is_completed");
				String category = getCate(cate_id);
				TodoItem t = new TodoItem(title,memo);
				t.setID(id);
				t.setCategory(category);
				t.setCurrent_date(current_date);
				t.setDuedate(due_date);
				t.setDday();
				t.setCateId(cate_id);
				t.setIs_completed(is_completed);
				list.add(t); 
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public String getCate(int cate_id){
		String category="";
		String sql = "select * from category where id = "+cate_id;
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				category = rs.getString("cate_title");
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return category;
	}

	public ArrayList<TodoItem> listAll() throws ParseException {
		String sql = "Select * from lists";
		Statement stmt;
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		TodoItem k = new TodoItem("title","memo");
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(sql);
			while(rs1.next()) {
				int id = rs1.getInt("id");
				String title = rs1.getString("title");
				String memo = rs1.getString("memo");
				String current_date = rs1.getString("current_date");
				String due_date = rs1.getString("due_date");
				int cate_id = rs1.getInt("cate_id");
				int is_completed = rs1.getInt("is_completed");
				String category = getCate(cate_id);
				TodoItem item = new TodoItem(title,memo);
				item.setID(id);
				item.setCurrent_date(current_date);
				item.setDuedate(due_date);
				item.setDday();
				item.setCateId(cate_id);
				item.setIs_completed(is_completed);
				item.setCategory(category);
				list.add(item);
			}
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int getCount() {
		String sql = "Select count(id) from Lists";
		Statement stmt;
		int i=0;
		try {
			
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
				i = rs.getInt(1);
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return i;
		
	}
	
	public ArrayList<TodoItem> getList(String keyword) throws ParseException{
		ArrayList<TodoItem> l = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%"+keyword+"%";
		String sql = "Select * from Lists where title like ? or memo like ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				int cate_id = rs.getInt("cate_id");
				String category = getCate(cate_id);
				int is_completed = rs.getInt("is_completed");
				TodoItem item = new TodoItem(title,memo);
				item.setCategory(category);
				item.setCurrent_date(current_date);
				item.setDuedate(due_date);
				item.setDday();
				item.setID(id);
				item.setIs_completed(is_completed);
				l.add(item);
			}
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return l;
	}

	public boolean isDuplicate(String title, String memo) {
		
		String sql = "Select * from Lists where title = ?";
		PreparedStatement pstmt;
		int i=0;
		boolean same;
		ArrayList<String> tm = new ArrayList<String>();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,title);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String title2 = rs.getString("title");
				String memo2 = rs.getString("memo");
				tm.add(title2+memo2);
				i++;
			}
			int j=0;
		if(i!=0) {
			while(j<i) {
				if((title+memo).equals(tm.get(j)))
					return true;
				j++;
				}
			}
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;		
	}
	
	public int comp(String input, int reverse) {
		StringTokenizer st = new StringTokenizer(input,",");
		ArrayList<String> s = new ArrayList<String>();
		int i=0;
		
		while(st.hasMoreTokens()) {
			s.add(st.nextToken());
			i++;
		}
		String ip="'"+s.get(0)+"'";
		for(int j=1;j<i;j++) {
			ip+=",'"+s.get(j)+"'";
		}
		String sql;
		if (reverse==0)
		sql = "Update lists set is_completed = 1"+" where id in ("+ip+")";
		else
		sql = "Update lists set is_completed = 0"+" where id in ("+ip+")";
		Statement stmt;
		int count = 0;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	public ArrayList<TodoItem> listComp() throws ParseException{
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		String sql = "Select * from Lists where is_completed = ?";
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, 1);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				int cate_id = rs.getInt("cate_id");
				String category = getCate(cate_id);
				int is_completed = rs.getInt("is_completed");
				TodoItem item = new TodoItem(title,memo);
				item.setCategory(category);
				item.setCurrent_date(current_date);
				item.setDuedate(due_date);
				item.setID(id);
				item.setDday();
				item.setCateId(cate_id);
				item.setIs_completed(is_completed);
				list.add(item);
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
