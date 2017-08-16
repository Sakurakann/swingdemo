package com.hzjs.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * 每个页面的菜单栏
 * @author Administrator
 *
 */
public class GotoOtherMenu extends JFrame{

	private static final long serialVersionUID = 1L;

	private GotoOtherMenu(){}
	
	private static GotoOtherMenu gotoOtherMenu = new GotoOtherMenu();
	
	public static GotoOtherMenu instance(){
		return gotoOtherMenu;
	}
	
	/**
	 * 加载菜单栏
	 * @param menuBar
	 */
	public void loadMenu(JMenuBar menuBar){
		JMenu menu = new JMenu("转到其他");
		menuBar.add(menu);
		
		JMenuItem mnConfigDB = new JMenuItem("数据库配置");
		menu.add(mnConfigDB);
		mnConfigDB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mnConfigDB(e);
			}
		});
		
		JMenuItem mnImportTask = new JMenuItem("任务导入");
		menu.add(mnImportTask);
		mnImportTask.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mnImportTask(e);
			}
		});
		
		JMenuItem mnQueryTask = new JMenuItem("任务查询");
		menu.add(mnQueryTask);
		mnQueryTask.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mnQueryTask(e);
			}
		});
		
		JMenuItem mnTestResult = new JMenuItem("测试结果查询");
		menu.add(mnTestResult);
		mnTestResult.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mnTestResult(e);
			}
		});
	}
	
	private void mnImportTask(ActionEvent event){
		ImportTask.main(null);
		this.setVisible(false);;
	}
	private void mnConfigDB(ActionEvent event){
		ConfigDB.main(null);
		this.dispose();
	}
	private void mnQueryTask(ActionEvent event){
		QueryTask.main(null);
		this.dispose();
	}
	private void mnTestResult(ActionEvent event){
		TaskTestResults.main(null);
		this.dispose();
	}
	

}
