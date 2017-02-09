package com.fbksoft.djinn.ui.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

import com.fbksoft.djinn.db.data.JavaItem;

public class JavaItemTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<JavaItem> list;
	
	public JavaItemTableModel() {
		this.list = new ArrayList<JavaItem>();
	}

	public JavaItemTableModel(Set<JavaItem> value) {		
		this.list = new ArrayList<JavaItem>(value);		
	}
	
	public void setList(List<JavaItem> list) {
		this.list = list;
		fireTableDataChanged();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		JavaItem javaItem = list.get(rowIndex);		
		switch(columnIndex) {
			case 0: return javaItem.getSmallIcon();
			case 1: return javaItem.getLabel();
			case 2: return javaItem.getParent().toString();			
		}
		return null;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {	
		switch(columnIndex) {
			case 0: return ImageIcon.class;
			case 1: return String.class;
			case 2: return String.class;
		}
		return null;
	}
	
	@Override
	public String getColumnName(int column) {
		switch(column) {
		case 0: return "";
		case 1: return "Name";
		case 2: return "Contained by";
	}
	return null;
	}
}
