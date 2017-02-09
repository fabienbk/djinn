package com.fbksoft.djinn.ui.dialogs;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import com.fbksoft.djinn.db.data.JavaItem;

public class WorkspaceEditorListModel extends AbstractListModel {
	
	private static final long serialVersionUID = 1L;
	
	ArrayList<JavaItem> list = new ArrayList<JavaItem>();

	public JavaItem getElementAt(int index) {
		return list.get(index);		
	}

	public int getSize() {
		return list.size();
	}
	
	public void addJavaItem(JavaItem javaItem) {
		list.add(javaItem);
		fireContentsChanged(javaItem, list.size(), list.size());
	}

	public void remove(JavaItem javaItem) {
		boolean remove = list.remove(javaItem);
		if (remove) {
			fireContentsChanged(javaItem, list.size(), list.size());
		}
	}

	public ArrayList<JavaItem> getList() {
		return list;
	}
}