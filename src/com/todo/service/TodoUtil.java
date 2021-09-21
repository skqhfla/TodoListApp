package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	private List<TodoItem> list;

	public TodoUtil() {
		this.list = new ArrayList<TodoItem>();
	}
		
	public void saveList(TodoList l, String filename) {
		int count = 0;
		StringBuffer text = new StringBuffer("");
		 try {
		      FileWriter myWriter = new FileWriter(filename);
		      for (TodoItem item : l.getList()) {
		    	  myWriter.write(item.toString());
				}
		     // myWriter.write(list.get(1).getTitle());
		    //  for(int i = 0; i<list.size(); i++)
		    //	  myWriter.write("[" + list.get(i).getTitle() + "] " + list.get(i).getDesc() + " - " + list.get(i).getCurrent_date() + "\n");
		      myWriter.flush();
		      myWriter.close();
		      
		    } 
		    catch (IOException e) {
		      System.out.println("������ �߻��߽��ϴ�.");
		      e.printStackTrace();
		    }
	}
	
	public void loadList(TodoList l, String filename) {
		int index = 0;
		String title, desc;
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader in = new BufferedReader(fr);	
			String line = "";
			try {
				for(int i = 1; (line = in.readLine()) != null; i++) {
					System.out.println(line);
					StringTokenizer st = new StringTokenizer(line, "##");
					title = st.nextToken();
					desc = st.nextToken();
					TodoItem t = new TodoItem(title, desc);
					l.addItem(t);
				}
			} catch (IOException e) {
				System.out.println("������ �߻��߽��ϴ�.");
			}

		} catch (FileNotFoundException e) {
			System.out.println("������ �����ϴ�.");
		}


		
	}

	public void createItem(TodoList list) {

		String title, desc;
		Scanner sc = new Scanner(System.in);

		System.out.println("\n" + "========== �׸� �߰�\n" + "������ �Է��ϼ���.\n");

		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.println("���� �ߺ�");
			return;
		}

		sc.nextLine();
		System.out.println("������ �Է��� �ּ���.");
		desc = sc.nextLine().trim();

		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("�߰��Ǿ����ϴ�.");
	}

	public void deleteItem(TodoList l) {

		Scanner sc = new Scanner(System.in);

		System.out.println("\n" + "========== �׸� ����\n" + "������ �׸��� �Է����ּ���\n" + "\n");
		String title = sc.next();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("�����Ǿ����ϴ�.");
				break;
			}
		}
	}

	public void updateItem(TodoList l) {

		Scanner sc = new Scanner(System.in);

		System.out.println(
				"\n" + "========== �׸� ����\n" + "�����ϰ� ���� �׸��� �Է����ּ���\n" + "\n");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("�׸��� �������� �ʽ��ϴ�");
			return;
		}

		System.out.println("���ο� �׸��� �Է����ּ���");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("�̹� �����ϴ� �׸��Դϴ�.");
			return;
		}
		sc.nextLine();
		System.out.println("������ �Է����ּ���");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("����Ǿ����ϴ�.");
			}
		}

	}

	public void listAll(TodoList l) {
		System.out.println("[��ü �׸�]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
}
