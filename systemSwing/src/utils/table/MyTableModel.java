package utils.table;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel{
	
	private static final long serialVersionUID = 1L;

	public MyTableModel() {
		super();
	}
	
	public MyTableModel(Vector<?> data, Vector<?> columnNames){
		super(data, columnNames);
	}
	
	/**
	 * 重写方法 使jtable的cell不可编辑
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
