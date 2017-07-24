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
import utils.xml.XMLUtil;

import com.hzjs.dao.impl.TaskInfoDao;
import com.hzjs.domain.TaskInfo;

public class Pre extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField userNameField;
	private JTextField IPField;
	private JTextField portField;
	private JTextField SIDField;
	private JButton editProrBtn;
	private JButton taskSimulationBtn;
	private JButton exportBtn;
	private JButton RMIModifyBtn;
	private JButton RMIDefaultBtn;
	// TODO
	private TaskInfoDao taskInfoDao = new TaskInfoDao();

	private String IPPattern = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\."
			+ "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\."
			+ "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\."
			+ "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
	private String SIDPattern = "[a-zA-z_\\-0-9]+";
	private String passWordPattern = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?]){1,20}$";
	private JTextField RMIIPField;
	private JTextField RMIPortField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pre frame = new Pre();
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
	public Pre() {
		setResizable(false);
		setTitle("系统检测");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 695, 465);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		// 设置绝对坐标
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "数据库配置", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(29, 23, 624, 194);
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
						taskSimulationBtn.setEnabled(true);
						exportBtn.setEnabled(true);
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
				if (!Pattern.matches(IPPattern, IPVal)) {
					JOptionPane.showMessageDialog(contentPane, "IP输入有误");
					return;
				}
				String portVal = portField.getText();
				if (!Pattern.matches("[1-6]{0,1}[0-9]{1,4}", portVal)) {
					JOptionPane.showMessageDialog(contentPane, "端口输入有误");
					return;
				}
				String SIDVal = SIDField.getText();
				if (!Pattern.matches(SIDPattern, SIDVal)) {
					JOptionPane.showMessageDialog(contentPane, "服务名输入有误");
					return;
				}

				String userNameVal = userNameField.getText();
				if (!Pattern.matches("[a-zA-Z_0-9\\-]+", userNameVal)) {
					JOptionPane.showMessageDialog(contentPane, "登录名输入有误");
					return;
				}

				String passWordVal = String.valueOf(passwordField.getPassword());
				if (!Pattern.matches(passWordPattern, passWordVal)) {
					JOptionPane.showMessageDialog(contentPane, "密码输入有误");
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
				if (!Pattern.matches(IPPattern, IPVal)) {
					JOptionPane.showMessageDialog(contentPane, "IP输入有误");
					return;
				}
				String portVal = portField.getText();
				if (!Pattern.matches("[1-6]{0,1}[0-9]{1,4}", portVal)) {
					JOptionPane.showMessageDialog(contentPane, "端口输入有误");
					return;
				}
				String SIDVal = SIDField.getText();
				if (!Pattern.matches(SIDPattern, SIDVal)) {
					JOptionPane.showMessageDialog(contentPane, "服务名输入有误");
					return;
				}

				String userNameVal = userNameField.getText();
				if (!Pattern.matches("[a-zA-Z_0-9\\-]+", userNameVal)) {
					JOptionPane.showMessageDialog(contentPane, "登录名输入有误");
					return;
				}

				String passWordVal = String.valueOf(passwordField.getPassword());
				System.out.println(passwordField.getPassword().toString());
				if (!Pattern.matches(passWordPattern, passWordVal)) {
					JOptionPane.showMessageDialog(contentPane, "密码输入有误");
					return;
				}
				try {
					if (JDBCConn.connTest(IPVal, portVal, SIDVal, userNameVal,
							passWordVal)) {
						try {
							EditProp.editJDBCProp(IPVal, portVal, SIDVal,
									userNameVal, passWordVal);
							taskSimulationBtn.setEnabled(true);
							exportBtn.setEnabled(true);
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

		taskSimulationBtn = new JButton("导入模板");
		taskSimulationBtn.setEnabled(false);
		taskSimulationBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToTaskSimulation(e);
			}
		});
		taskSimulationBtn.setBounds(483, 385, 93, 23);
		contentPane.add(taskSimulationBtn);

		exportBtn = new JButton("检出模板");
		exportBtn.setBounds(113, 385, 93, 23);
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
					JOptionPane.showMessageDialog(null, "导出成功");
					return;
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "导出失败");
					e1.printStackTrace();
					return;
				}
			}
		});
		// TODO
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "RMI配置", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_1.setBounds(29, 227, 624, 127);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblIp_1 = new JLabel("IP");
		lblIp_1.setBounds(120, 23, 17, 15);
		panel_1.add(lblIp_1);

		RMIIPField = new JTextField();
		RMIIPField.setBounds(137, 20, 76, 21);
		panel_1.add(RMIIPField);
		RMIIPField.setColumns(10);

		JLabel label_4 = new JLabel("端口");
		label_4.setBounds(364, 23, 37, 15);
		panel_1.add(label_4);

		RMIPortField = new JTextField();
		RMIPortField.setBounds(397, 20, 66, 21);
		panel_1.add(RMIPortField);
		RMIPortField.setColumns(10);

		RMIDefaultBtn = new JButton("默认配置");
		RMIDefaultBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					XMLUtil.RMIConn();
					JOptionPane.showMessageDialog(null, "RMI 配置可用");
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
					return;
				}
				
			}
		});
		RMIDefaultBtn.setBounds(120, 66, 93, 23);
		panel_1.add(RMIDefaultBtn);

		RMIModifyBtn = new JButton("更新配置");
		RMIModifyBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String rmiIP = RMIIPField.getText();
				if (!Pattern.matches(IPPattern, rmiIP)) {
					JOptionPane.showMessageDialog(null, "RMI IP输入错误");
					return;
				}
				String rmiPort = RMIPortField.getText();
				if (!Pattern.matches("[1-6]{0,1}[0-9]{1,4}", rmiPort)) {
					JOptionPane.showMessageDialog(null, "RMI 端口输入错误");
					return;
				}
				try {
					XMLUtil.ModifyRMIUrl(rmiIP, rmiPort);
					JOptionPane.showMessageDialog(null, "RMI配置更新成功");
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "RMI配置更新错误");
					return;
				}
			}
		});
		RMIModifyBtn.setBounds(370, 66, 93, 23);
		panel_1.add(RMIModifyBtn);

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

	// 跳转到模拟测试页面
	private void goToTaskSimulation(ActionEvent e) {
//		this.dispose();
		TaskSimulationTest.main(null);
	}
}
