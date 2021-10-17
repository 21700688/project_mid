package com.todo.menu;

public class Menu {

	public static void displaymenu()
    {
        System.out.println();
        System.out.println("1.  add: 추가할 항목을 입력합니다.");
        System.out.println("2.  del: 추가한 항목을 삭제합니다.");
        System.out.println("3.  edit: 추가한 항목을 수정합니다.");
        System.out.println("4.  ls: 추가한 항목들을 출력하니다.");
        System.out.println("5.  ls_name: 추가한 항목들을 이름순으로 나열하여 출력합니다.");
        System.out.println("6.  ls_name_desc: 추가한 항목들을 이름의 후순으로 나열하여 출력합니다.");
        System.out.println("7.  ls_date: 추가한 항목들을 추가한 날짜 순으로 나열하여 출력합니다.");
        System.out.println("8.  ls_date_desc: 추가한 항목들을 추가한 날짜의 후순으로 나열하여 출력합니다.");
        System.out.println("9.  ls_cate: 현재 입력된 카테고리을 모두 출력합니다.");
        System.out.println("10. find <키워드>: 키워드가 제목 또는 내용에 포함된 항목을 모두 찾아서 출력합니다.");
        System.out.println("11. find_cate <키워드>: 키워드가 카테고리에 포함된 모든 항목을 찾아서 출력합니다.");
        System.out.println("12. comp <번호>: 번호에 해당하는 항목을 완료처리합니다.");
        System.out.println("12. re_comp <번호>: 번호에 해당하는 항목들의 완료처리를 해제합니다.");
        System.out.println("13. ls_comp: 완료처리된 항목들을 모두 출력합니다.");
        System.out.println("14. exit: 프로그램 실행을 종료합니다.");
        System.out.printf("명령어를 입력하세요: ");
    }
    
    public static void prompt(){
    	System.out.println();
    	System.out.printf("명령어를 입력하세요: ");
    }

}
