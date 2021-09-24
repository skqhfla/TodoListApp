package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
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
		    	  myWriter.write(item.toSaveString());
				}
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
		String category, title, desc, due_date, time;
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader in = new BufferedReader(fr);	
			String line = "";
			try {
				for(int i = 1; (line = in.readLine()) != null; i++) {
					System.out.println(line);
					StringTokenizer st = new StringTokenizer(line, "##");
					category = st.nextToken();
					title = st.nextToken();
					desc = st.nextToken();
					due_date = st.nextToken();
					time = st.nextToken();
					TodoItem t = new TodoItem(category,title, desc, due_date,time);
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

		String category, title, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n" + "========== �׸� �߰�\n" + "ī������ �Է��ϼ���.\n");
		
		category = sc.next();

		System.out.println("������ �Է��ϼ���.\n");

		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.println("���� �ߺ�");
			return;
		}

		sc.nextLine();
		System.out.println("������ �Է��� �ּ���.");
		desc = sc.nextLine().trim();
		
		System.out.println("������¥�� �Է��� �ּ���.");
		due_date = sc.next();

		TodoItem t = new TodoItem(category, title, desc, due_date);
		list.addItem(t);
		System.out.println("�߰��Ǿ����ϴ�.");
	}

	public void deleteItem(TodoList l) {
		int num;
		Scanner sc = new Scanner(System.in);

		System.out.println("\n" + "========== �׸� ����\n" + "������ �׸��� ��ȣ�� �Է����ּ���\n" + "\n");
		num = sc.nextInt();
		num  = num-1;
		for (TodoItem item : l.getList()) {
			if(num == l.indexOf(item)) {
				l.deleteItem(item);
				System.out.println("�����Ǿ����ϴ�.");
				break;
			}
		}
	}

	public void updateItem(TodoList l) {
		int num;

		Scanner sc = new Scanner(System.in);

		System.out.println(
				"\n" + "========== �׸� ����\n" + "�����ϰ� ���� �׸��� ��ȣ�� �Է����ּ���\n" + "\n");
		num = sc.nextInt();
		if (num>l.getSize()) {
			System.out.println("�׸��� �������� �ʽ��ϴ�");
			return;
		}
		
		System.out.println("���ο� ī������ �Է����ּ���");
		String new_category = sc.next();
		
		System.out.println("�׸��� �Է����ּ���");
		String new_title = sc.next();
		if (l.isDuplicate(new_title)) {
			System.out.println("�̹� �����ϴ� �׸��Դϴ�.");
			return;
		}
		
		sc.nextLine();
		System.out.println("������ �Է����ּ���");
		String new_description = sc.nextLine().trim();
		
		System.out.println("���� ��¥�� �Է����ּ���");
		String new_due_date = sc.next();
		num = num-1;
		for (TodoItem item : l.getList()) {
			if (num == l.indexOf(item)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_category, new_title, new_description, new_due_date);
				l.addItem(t);
				System.out.println("����Ǿ����ϴ�.");
			}
		}

	}

	public void listAll(TodoList l) {
		System.out.println("[��ü ���, �� " + l.getSize() + "��]");
		int i = 1;
		
		for (TodoItem item : l.getList()) {
			System.out.println(i + ". " + item.toString());
			i++;
		}
	}
	
	public void fine(TodoList l) {
		System.out.println("ã���� �ϴ� Ű���带 �Է��ϼ���.");
		Scanner sc = new Scanner(System.in);
		String k = sc.next();
		int count = 0;
		
		for (TodoItem item : l.getList()) {
			if(l.contains(k,item) != -1) {
				count++;
				System.out.println(count + ". " + item.toString());
			}
		}
		
		if(count != 0) {
			System.out.println("�� " + count + "���� �׸��� ã�ҽ��ϴ�.");
			return;
		}
		
		System.out.println("��ġ�ϴ� �׸��� ã�� ���Ͽ����ϴ�.");
		return;
	}
	
	public void fine_cate(TodoList l) {
		System.out.println("ã���� �ϴ� ī�װ��� �Է��ϼ���.");
		Scanner sc = new Scanner(System.in);
		String k = sc.next();
		int count = 0;
		
		for (TodoItem item : l.getList()) {
			if(l.contains_cate(k,item) != -1) {
				count++;
				System.out.println(count + ". " + item.toString());
			}
		}
		
		if(count != 0) {
			System.out.println("�� " + count + "���� �׸��� ã�ҽ��ϴ�.");
			return;
		}
		
		System.out.println("��ġ�ϴ� �׸��� ã�� ���Ͽ����ϴ�.");
		return;
	}
	
	public void ls_cate(TodoList l) {
		Set<String> clist = new HashSet<String>();
		
		for(TodoItem c : l.getList()) {
			clist.add(c.getCategory());
		}
		
		Iterator it = clist.iterator();
		while(it.hasNext()) {
			String s = (String)it.next();
			System.out.print(s);
			if(it.hasNext())
				System.out.print(" / ");
		}
		
		System.out.printf("\n�� %d���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.\n", clist.size());
	}
}
