package com.scramcode.djinn.ui.table;

import javax.swing.JTable;


public final class JavaItemTableFactory {
	/**
	 * @wbp.factory
	 */
	public static JTable createJTable() {
		JTable table = new JTable();

		table.setModel(new JavaItemTableModel());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setAutoCreateRowSorter(true);
		resizeTable(table);
		
		return table;
	}
	
	public static void resizeTable(JTable table) {
		table.getColumnModel().getColumn(0).setMaxWidth(20);
		
		TableColumnAdjuster tableColumnAdjuster = new TableColumnAdjuster(table);
		tableColumnAdjuster.setDynamicAdjustment(true);
		tableColumnAdjuster.adjustColumns(); 
	}
}