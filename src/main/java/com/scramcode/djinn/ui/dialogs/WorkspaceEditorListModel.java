package com.scramcode.djinn.ui.dialogs;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import com.scramcode.djinn.db.data.AbstractJavaItem;

public class WorkspaceEditorListModel extends AbstractListModel<AbstractJavaItem> {
	
	private static final long serialVersionUID = 1L;
	
	ArrayList<AbstractJavaItem> list = new ArrayList<AbstractJavaItem>();

	public AbstractJavaItem getElementAt(int index) {
		return list.get(index);		
	}

	public int getSize() {
		return list.size();
	}
	
	public void addJavaItem(AbstractJavaItem javaItem) {
		list.add(javaItem);
		fireContentsChanged((Object)javaItem, list.size(), list.size());
	}

	public void remove(AbstractJavaItem javaItem) {
		boolean remove = list.remove(javaItem);
		if (remove) {
			fireContentsChanged((Object)javaItem, list.size(), list.size());
		}
	}

	public ArrayList<AbstractJavaItem> getList() {
		return list;
	}
}