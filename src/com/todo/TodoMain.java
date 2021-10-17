package com.todo;

import java.text.ParseException;
import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {

	public static void start() throws ParseException {
		
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		boolean menu = true;
		int d = 0;
		//System.out.printf("txt파일에 저장된 데이터를 추가하시겠습니까?: ");
		//String imp;
		//imp = sc.nextLine();
		//if(imp.equals("y"))
			//TodoUtil.importData(l, "TodoListApp.txt");
		do {
			if(menu==false) {
				Menu.prompt();
				d=0;
			}
			else {
			Menu.displaymenu();
			menu = false;
			d=1;}
			
			isList = false;
			String choice = sc.next();
			
			if(d==1) {System.out.println("");}
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name":
				TodoUtil.listAll(l,"title",1);
				break;

			case "ls_name_desc":
				TodoUtil.listAll(l,"title",0);
				break;
				
			case "ls_date":
				TodoUtil.listAll(l,"due_date",1);
				break;
			
			case "ls_date_desc":
				TodoUtil.listAll(l,"due_date",0);
				break;

			case "exit":
				quit = true;
				System.out.println("프로그램을 종료합니다.");
				break;
				
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
			
			case "help":
				menu = true;
				break;
			
			case "find":
				String keyword = sc.nextLine().trim();
				TodoUtil.find(l,keyword);
				break;
			
			case "find_cate":
				String cate;
				cate = sc.nextLine().trim();
				TodoUtil.findCate(l,cate);
				break;
			
			case "comp":
				String input;
				input = sc.nextLine();
				TodoUtil.complete(l, input,0);
				break;
				
			case "re_comp":
				String input2;
				input2 = sc.nextLine();
				TodoUtil.complete(l,input2,1);
				break;
				
			case "ls_comp":
				TodoUtil.listAllCompleted(l);
				break;
				

			default:
				System.out.println("\n존재하지 않는 명령입니다. 도움말 - help\n");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
	}

}
