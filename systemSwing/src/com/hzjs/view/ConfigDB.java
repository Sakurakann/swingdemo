package com.hzjs.view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;

import utils.excel.ExcelExportUtil;
import utils.prop.EditProp;
import utils.prop.JDBCConn;

import com.hzjs.dao.impl.TaskInfoDao;
import com.hzjs.domain.TaskInfo;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class ConfigDB extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField userNameField;
	private JTextField IPField;
	private JTextField portField;
	private JTextField SIDField;
	private JButton editProrBtn;
	private JButton importTaskBtn;
	private JButton exportBtn;
	// TODO
	private TaskInfoDao taskInfoDao = new TaskInfoDao();

	private String IPPattern = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\."
			+ "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\."
			+ "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\."
			+ "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
	private String portPatten = "[0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-5]{2}[0-3][0-5]";
	private String SIDPattern = "[a-zA-z_\\-0-9]+";
	private String passWordPattern = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?]){1,20}$";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigDB frame = new ConfigDB();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConfigDB() {
		setResizable(false);
		setTitle("系统检测");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 695, 465);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		loadMenu(menuBar);
		//GotoOtherMenu.instance().loadMenu(menuBar);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		// 设置绝对坐标
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "数据库配置", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(27, 65, 624, 194);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblIp = new JLabel("IP");
		lblIp.setBounds(62, 32, 12, 15);
		panel.add(lblIp);

		JLabel label = new JLabel("端口");
		label.setBounds(265, 32, 31, 15);
		panel.add(label);

		JLabel label_1 = new JLabel("服务名");
		label_1.setBounds(429, 32, 43, 15);
		panel.add(label_1);

		JLabel label_2 = new JLabel("登录名");
		label_2.setBounds(40, 79, 41, 15);
		panel.add(label_2);

		JLabel label_3 = new JLabel("登录密码");
		label_3.setBounds(241, 79, 55, 15);
		panel.add(label_3);

		passwordField = new JPasswordField();
		passwordField.setBounds(296, 76, 120, 21);
		panel.add(passwordField);

		userNameField = new JTextField();
		userNameField.setBounds(82, 76, 120, 21);
		panel.add(userNameField);
		userNameField.setColumns(10);

		IPField = new JTextField();
		IPField.setBounds(82, 29, 93, 21);
		panel.add(IPField);
		IPField.setColumns(10);

		portField = new JTextField();
		portField.setBounds(295, 29, 72, 21);
		panel.add(portField);
		portField.setColumns(10);

		SIDField = new JTextField();
		SIDField.setBounds(471, 29, 83, 21);
		panel.add(SIDField);
		SIDField.setColumns(10);

		JButton defaultPropBtn = new JButton("默认配置");
		defaultPropBtn.setBounds(85, 141, 93, 23);
		panel.add(defaultPropBtn);
		defaultPropBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (JDBCConn.connTest()) {
						JOptionPane.showMessageDialog(contentPane, "数据库连接成功!");
						setBtnEnable();
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane,
							"默认数据库连接错误,请重新配置");
					return;
				}
			}
		});

		JButton editTestBtn = new JButton("测试连接");
		editTestBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String IPVal = IPField.getText();
				String portVal = portField.getText();
				String SIDVal = SIDField.getText();
				String userNameVal = userNameField.getText();
				String passWordVal = String.valueOf(passwordField.getPassword());
				if (!btnJudge(IPVal,portVal,SIDVal,userNameVal,passWordVal)) {
					return;
				}
				try {
					boolean flag = JDBCConn.connTest(IPVal, portVal, SIDVal,
							userNameVal, passWordVal);
					if (flag) {
						JOptionPane.showMessageDialog(contentPane, "数据库可连接");
						editProrBtn.setEnabled(true);
					} else {
						JOptionPane.showMessageDialog(contentPane, "数据库配置有误");
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane, "数据库配置有误");
					return;
				}
			}
		});
		editTestBtn.setBounds(274, 141, 93, 23);
		panel.add(editTestBtn);

		editProrBtn = new JButton("更新配置");
		editProrBtn.setEnabled(false);
		editProrBtn.setBounds(452, 141, 93, 23);
		panel.add(editProrBtn);
		editProrBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String IPVal = IPField.getText();
				String portVal = portField.getText();
				String SIDVal = SIDField.getText();
				String userNameVal = userNameField.getText();
				String passWordVal = String.valueOf(passwordField.getPassword());
				if (!btnJudge(IPVal,portVal,SIDVal,userNameVal,passWordVal)) {
					return;
				}
				try {
					if (JDBCConn.connTest(IPVal, portVal, SIDVal, userNameVal,
							passWordVal)) {
						try {
							EditProp.editJDBCProp(IPVal, portVal, SIDVal,
									userNameVal, passWordVal);
							setBtnEnable();
							JOptionPane.showMessageDialog(contentPane,
									"修改数据库配置成功");
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(contentPane,
									"修改数据库配置出错");
							return;
						}
					} else {
						JOptionPane.showMessageDialog(contentPane, "数据库配置有误");
						return;
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane, "数据库配置有误");
					return;
				}
			}
		});

		importTaskBtn = new JButton("导入任务");
		importTaskBtn.setEnabled(false);
		importTaskBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToImportTask(e);
			}
		});
		importTaskBtn.setBounds(486, 338, 93, 23);
		contentPane.add(importTaskBtn);

		exportBtn = new JButton("导出模板");
		exportBtn.setBounds(114, 338, 93, 23);
		contentPane.add(exportBtn);
		exportBtn.setEnabled(false);
		exportBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				List<TaskInfo> results = null;
				try {
					results = taskInfoDao.getTemplate();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane, e2.getMessage());
					return;
				}

				if (results.size() <= 0) {
					JOptionPane.showMessageDialog(contentPane, "模板为空,仍然导出?");
				}
				ExcelExportUtil<TaskInfo> exportUtil = new ExcelExportUtil<TaskInfo>();
				String[] title = new String[] { "taskName", "testCode",
						"caller", "called", "router", "textInfo", "gateWay",
						"toneCode1", "toneCode3", "toneName1" };

				JFileChooser chooser = new JFileChooser();

				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				// FileNameExtensionFilter filter = new FileNameExtensionFilter(
				// ".xls","xls");
				// chooser.setFileFilter(filter);

				// 添加文件过滤器 与上方类似 上面是添加一系列的过滤 该方法逐条添加
				chooser.addChoosableFileFilter(new FileFilter() {

					@Override
					public boolean accept(File f) {
						if (f.getName().endsWith("xls") || f.isDirectory()) {
							return true;
						} else {
							return false;
						}
					}

					@Override
					public String getDescription() {
						return "Excel文件(*.xls)";
					}

				});
				chooser.setDialogTitle("另存为");

				int result = chooser.showOpenDialog(contentPane);
				File file = null;
				if (result == JFileChooser.APPROVE_OPTION) {
					File fileIn = chooser.getSelectedFile();

					// 给文件名添加固定的后缀 .xls
					if (!fileIn.getName().endsWith(".xls")) {
						file = new File(fileIn.getAbsolutePath() + ".xls");
					}
					if (fileIn.exists()) {
						JOptionPane.showMessageDialog(contentPane, "确认覆盖?");
					}
				}
				try {
					if (file == null) {
						JOptionPane.showMessageDialog(contentPane, "文件名不能为空");
						return;
					}
					exportUtil.exportExceptionExcel(file, title, results);
					JOptionPane.showMessageDialog(contentPane, "导出成功");
					return;
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(contentPane, "导出失败");
					e1.printStackTrace();
					return;
				}
			}
		});

		// 设置窗口居中
		int windowWidth = this.getWidth(); // 获得窗口宽
		int windowHeight = this.getHeight(); // 获得窗口高
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width; // 获取屏幕的宽
		int screenHeight = screenSize.height; // 获取屏幕的高
		this.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2
				- windowHeight / 2);// 设置窗口居中显示

	}

	private boolean btnJudge(String IPVal, String portVal, String SIDVal,
			String userNameVal, String passWordVal) {
		if (!Pattern.matches(IPPattern, IPVal)) {
			JOptionPane.showMessageDialog(contentPane, "IP输入有误");
			setBtnDisable();
			return false;
		}
		if (!Pattern.matches(portPatten, portVal)) {
			JOptionPane.showMessageDialog(contentPane, "端口输入有误");
			setBtnDisable();
			return false;
		}
		if (!Pattern.matches(SIDPattern, SIDVal)) {
			JOptionPane.showMessageDialog(contentPane, "服务名输入有误");
			setBtnDisable();
			return false;
		}
		if (!Pattern.matches("[a-zA-Z_0-9\\-]+", userNameVal)) {
			JOptionPane.showMessageDialog(contentPane, "登录名输入有误");
			setBtnDisable();
			return false;
		}
		if (!Pattern.matches(passWordPattern, passWordVal)) {
			JOptionPane.showMessageDialog(contentPane, "密码输入有误");
			setBtnDisable();
			return false;
		}
		return true;
		
	}

	private void setBtnDisable() {
		exportBtn.setEnabled(false);
		importTaskBtn.setEnabled(false);
	}
	
	private void setBtnEnable(){
		importTaskBtn.setEnabled(true);
		exportBtn.setEnabled(true);
	}

	// 跳转到模拟测试页面
	private void goToImportTask(ActionEvent e) {
		this.dispose();
		ImportTask.main(null);
	}
	
	/**
	 * 加载菜单栏
	 * @param menuBar
	 */
	private void loadMenu(JMenuBar menuBar){
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
		this.dispose();
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
