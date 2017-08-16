package com.hzjs.view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import utils.excel.ExcelImportUtil;
import utils.xml.XMLUtil;

import com.eltima.components.ui.DatePicker;
import com.gs.biz.BizApplication;
import com.hzjs.dao.impl.TaskInfoDao;
import com.hzjs.domain.TaskQuery;
import com.hzjs.domain.Trans;
import com.jeesoon.tnits.client.web.WebClient;

public class ImportTask extends JFrame {
	private static final long serialVersionUID = 1L;

	private TaskInfoDao taskInfoDao = new TaskInfoDao();

	private DatePicker datePicker;

	private int taskSIDNum;
	private List<List<Object>> listobj = null;

	private String IPPattern = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\."
			+ "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\."
			+ "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\."
			+ "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
	private String portPatten = "[0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-5]{2}[0-3][0-5]";
	protected final String[] title = new String[] { "\u4efb\u52a1ID",
			"\u4efb\u52a1\u540d\u79f0", "\u6267\u884c\u65b9\u5f0f",
			"\u5faa\u73af\u5468\u671f", "\u6267\u884c\u72b6\u6001",
			"\u8ba1\u5212\u6267\u884c\u65f6\u95f4" };
	// TODO
	private JTextField durationField;
	private JTextField interValField;
	private JTextField executeNum;
	private JTextField cycleInput;
	private JPanel contentPane;
	private JButton taskTestResult;
	private JLabel fileName;
	private JComboBox executeMode;
	private JComboBox cycleUnit;
	private JTextField IPField;
	private JTextField portField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImportTask frame = new ImportTask();
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
	public ImportTask() {
		
		setResizable(false);
		setTitle("系统检测");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 726, 473);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		loadMenu(menuBar);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "任务导入", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(10, 162, 697, 221);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel("执行方式");
		label.setBounds(20, 30, 52, 16);
		panel.add(label);
		label.setFont(new Font("宋体", Font.PLAIN, 13));
		label.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel label_1 = new JLabel("执行次数");
		label_1.setBounds(237, 30, 55, 16);
		panel.add(label_1);
		label_1.setFont(new Font("宋体", Font.PLAIN, 13));

		JLabel label_2 = new JLabel("循环周期");
		label_2.setBounds(415, 30, 52, 16);
		panel.add(label_2);
		label_2.setFont(new Font("宋体", Font.PLAIN, 13));

		JLabel label_3 = new JLabel("计划时间");
		label_3.setBounds(20, 82, 52, 16);
		panel.add(label_3);
		label_3.setFont(new Font("宋体", Font.PLAIN, 13));

		JLabel label_4 = new JLabel("模板文件");
		label_4.setBounds(20, 143, 52, 16);
		panel.add(label_4);
		label_4.setFont(new Font("宋体", Font.PLAIN, 13));

		fileName = new JLabel("");
		fileName.setBounds(178, 142, 212, 17);
		panel.add(fileName);

		//TODO error: Unable to read entire header; 0 bytes read; expected 512 bytes
		JButton btnNewButton = new JButton("选择文件");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						".xlsx,.xls", "xlsx", "xls");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(contentPane);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					// chooser.getSelectedFile() 导入的文件
					// 完成excel数据解析
					InputStream iStream = null;
					File file = chooser.getSelectedFile();
					if (file == null) {
						JOptionPane.showMessageDialog(contentPane, "请选择模板文件");
						return;
					} else {
						fileName.setText(file.getName());
					}
					try {
						iStream = new FileInputStream(file);
						listobj = new ExcelImportUtil().getBankListByExcel(
								iStream, file.getName());
					} catch (Exception exception) {
						exception.printStackTrace();
						JOptionPane.showMessageDialog(contentPane, "模板文件解析错误,请确认文件格式");
						return;
					}
				}
			}
		});
		btnNewButton.setBounds(82, 139, 87, 23);
		panel.add(btnNewButton);

		// 执行方式
		executeMode = new JComboBox();
		executeMode.setFont(new Font("宋体", Font.PLAIN, 13));
		executeMode.setModel(new DefaultComboBoxModel(new String[] { "定时",
				"循环", "立即" }));
		executeMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if ("立即" == (String) executeMode.getSelectedItem()) {
					durationField.setEditable(true);
					interValField.setEditable(true);
				} else {
					durationField.setEditable(false);
					interValField.setEditable(false);
				}
			}
		});
		executeMode.setBounds(75, 28, 67, 23);
		panel.add(executeMode);

		executeNum = new JTextField();
		executeNum.setBounds(292, 27, 44, 23);
		panel.add(executeNum);
		executeNum.setText("");
		executeNum.setColumns(10);

		/*
		 * planTime = new JTextField(); planTime.setBounds(684, 28, 87, 23);
		 * panel.add(planTime); planTime.setText("1971-01-01 00:00:00");
		 * planTime.setColumns(10);
		 */
		datePicker = getDatePicker();
		datePicker.setBounds(75, 80, 200, 23);
		panel.add(datePicker);

		cycleUnit = new JComboBox();
		cycleUnit.setFont(new Font("宋体", Font.PLAIN, 13));
		cycleUnit.setModel(new DefaultComboBoxModel(new String[] { "不循环", "分钟",
				"小时", "天", "周", "月" }));
		cycleUnit.setBounds(524, 27, 67, 23);
		panel.add(cycleUnit);

		cycleInput = new JTextField();
		cycleInput.setBounds(470, 27, 44, 23);
		panel.add(cycleInput);
		cycleInput.setText("");
		cycleInput.setColumns(10);

		JButton importBtn = new JButton("导入任务");
		importBtn.setBounds(396, 139, 87, 23);
		importBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 获取executeMode的选择值

				TaskQuery taskQuery = new TaskQuery();
				char executeModeChar = '1';
				int executeNumVal = 0;
				int cycleInputVal = 0;
				int cycleUnitVal = 0;

				int durationval = 0;
				int interVal = 0;
				String remark1 = "H";

				Date planTimeVal = (Date) datePicker.getValue();
				String executeModeStr = (String) executeMode.getSelectedItem();
				String cycleUnitStr = (String) cycleUnit.getSelectedItem();

				if (executeModeStr.equals("立即")) {
					executeModeChar = '1';
				} else if (executeModeStr.equals("定时")) {
					executeModeChar = '2';
				} else if (executeModeStr.equals("循环")) {
					executeModeChar = '3';
				}
				if (cycleUnitStr.equals("不循环")) {
					cycleUnitVal = 0;
				} else if (cycleUnitStr.equals("分钟")) {
					cycleUnitVal = 1;
				} else if (cycleUnitStr.equals("小时")) {
					cycleUnitVal = 2;
				} else if (cycleUnitStr.equals("天")) {
					cycleUnitVal = 3;
				} else if (cycleUnitStr.equals("周")) {
					cycleUnitVal = 4;
				} else if (cycleUnitStr.equals("月")) {
					cycleUnitVal = 5;
				}
				// 当执行方式为循环时 可以没有执行次数
				// 循环
				if (executeModeStr == "循环") {

					if (!cycleInput.getText().equals("")) {
						try {
							cycleInputVal = Integer.parseInt(cycleInput
									.getText());
						} catch (Exception e2) {
							e2.printStackTrace();
							JOptionPane.showMessageDialog(contentPane, "循环次数应为有效数字");
							return;
						}
					}
					if (cycleInputVal <= 0 || cycleUnitVal == 0) {
						JOptionPane.showMessageDialog(contentPane, "循环周期应大于0");
						return;
					}
				} else {
					if (!executeNum.getText().equals("")) {
						try {
							executeNumVal = Integer.parseInt(executeNum
									.getText());
						} catch (Exception e2) {
							e2.printStackTrace();
							JOptionPane.showMessageDialog(contentPane, "执行次数应为有效数字");
							return;
						}
					}
					if (executeNumVal <= 0) {
						JOptionPane.showMessageDialog(contentPane, "执行次数应大于0");
						return;
					}
				}

				// 当执行方式为立即时 提供测试时长和测试间隔 单位 s
				// 如果是立即执行 则设置plantime为当前时间
				// 立即
				if (executeModeStr == "立即") {
					planTimeVal = new Date();
					if (!durationField.getText().equals("")) {
						try {
							durationval = Integer.parseInt(durationField
									.getText());
						} catch (Exception e2) {
							e2.printStackTrace();
							JOptionPane.showMessageDialog(contentPane, "通话时长应为有效数字");
							return;
						}
					}
					if (durationval <= 0) {
						JOptionPane.showMessageDialog(contentPane, "通话时长应大于0");
						return;
					}

					if (!interValField.getText().equals("")) {
						try {
							interVal = Integer.parseInt(interValField.getText());
						} catch (Exception e2) {
							e2.printStackTrace();
							JOptionPane.showMessageDialog(contentPane, "呼叫间隔应为有效数字");
							return;
						}
					}
					if (interVal <= 0) {
						JOptionPane.showMessageDialog(contentPane, "呼叫间隔应大于0");
						return;
					}
				}

				// 定时
				if (executeModeStr == "定时") {
					if (planTimeVal.before(new Date())) {
						JOptionPane.showMessageDialog(contentPane, "定时任务应当大于当前时间");
						return;
					}
				}

				if (listobj == null) {
					JOptionPane.showMessageDialog(contentPane, "请选择模板文件");
					return;
				}

				taskQuery.setExecuteMode(executeModeChar);
				taskQuery.setExecuteNum(executeNumVal);
				taskQuery.setCycleInput(cycleInputVal);
				taskQuery.setCycleUnit(cycleUnitVal);
				taskQuery.setPlanTime(planTimeVal);

				taskQuery.setRemark1(remark1);
				taskQuery.setDuration(durationval);
				taskQuery.setInterVal(interVal);

				int resultInt = 0;

				if(!LoginedWebClient()){
					JOptionPane.showMessageDialog(contentPane, "任务未导入,请检查RMI配置是否正确");
					return;
				}
				try {
					resultInt = taskInfoDao.addTasks(listobj, taskQuery);
				} catch (Exception e2) {
					// 判断主叫格式
					JOptionPane.showMessageDialog(contentPane, e2.getMessage());
					return;
				}
				
				if (resultInt != 0) {
					JOptionPane.showMessageDialog(contentPane, "任务导入成功");
				} else {
					JOptionPane.showMessageDialog(contentPane, "任务导入失败");
					return;
				}
				
				taskSIDNum = taskInfoDao.findTaskSID();
				Trans.getInstance().setSID(Integer.toString(taskSIDNum));
				List<Integer> idList = taskInfoDao.findTaskIdBySId(taskSIDNum);
				
				for (Integer id : idList) {
					try {
						WebClient.taskExecute(id);
					} catch (Exception e2) {
						e2.printStackTrace();
						JOptionPane.showMessageDialog(contentPane, "任务执行失败");
					}
				}

			}
		});
		panel.add(importBtn);

		taskTestResult = new JButton("任务结果");
		taskTestResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				taskTestResultAct(e);
			}
		});
		taskTestResult.setBounds(580, 188, 87, 23);
		panel.add(taskTestResult);

		JLabel lblNewLabel_1 = new JLabel("通话时长");
		lblNewLabel_1.setBounds(369, 84, 54, 15);
		panel.add(lblNewLabel_1);

		durationField = new JTextField();
		durationField.setEditable(false);
		durationField.setBounds(431, 82, 66, 21);
		panel.add(durationField);
		durationField.setColumns(10);

		JLabel label_11 = new JLabel("呼叫间隔");
		label_11.setBounds(537, 84, 54, 15);
		panel.add(label_11);

		interValField = new JTextField();
		interValField.setEditable(false);
		interValField.setBounds(601, 82, 66, 21);
		panel.add(interValField);
		interValField.setColumns(10);
		
		JButton button = new JButton("查询任务");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.setBounds(524, 140, 87, 23);
		panel.add(button);

		JPanel rMIConfigJPanel = new JPanel();
		rMIConfigJPanel.setLayout(null);
		rMIConfigJPanel.setBorder(new TitledBorder(null, "RMI配置",
				TitledBorder.LEADING,

				TitledBorder.TOP, null, null));
		rMIConfigJPanel.setBounds(45, 25, 624, 127);
		contentPane.add(rMIConfigJPanel);

		JLabel IPLable = new JLabel("IP");
		IPLable.setBounds(120, 23, 17, 15);
		rMIConfigJPanel.add(IPLable);

		IPField = new JTextField();
		IPField.setColumns(10);
		IPField.setBounds(137, 20, 76, 21);
		rMIConfigJPanel.add(IPField);

		JLabel portLable = new JLabel("端口");
		portLable.setBounds(364, 23, 37, 15);
		rMIConfigJPanel.add(portLable);

		portField = new JTextField();
		portField.setColumns(10);
		portField.setBounds(397, 20, 66, 21);
		rMIConfigJPanel.add(portField);

		JButton RMIDefaultBtn = new JButton("默认配置");
		RMIDefaultBtn.setBounds(120, 66, 93, 23);
		rMIConfigJPanel.add(RMIDefaultBtn);
		RMIDefaultBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					XMLUtil.RMIConn();
					JOptionPane.showMessageDialog(contentPane, "RMI 配置可用");
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane, e2.getMessage());
					return;
				}

			}
		});

		JButton RMIModifyBtn = new JButton("更新配置");
		RMIModifyBtn.setBounds(370, 66, 93, 23);
		rMIConfigJPanel.add(RMIModifyBtn);
		RMIModifyBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String rmiIP = IPField.getText();
				if (!Pattern.matches(IPPattern, rmiIP)) {
					JOptionPane.showMessageDialog(contentPane, "RMI IP输入错误");
					return;
				}
				String rmiPort = portField.getText();
				if (!Pattern.matches(portPatten, rmiPort)) {
					JOptionPane.showMessageDialog(contentPane, "RMI 端口输入错误");
					return;
				}
				try {
					XMLUtil.ModifyRMIUrl(rmiIP, rmiPort);
					JOptionPane.showMessageDialog(contentPane, "RMI配置更新成功");
					try {
						XMLUtil.RMIConn();
						JOptionPane.showMessageDialog(contentPane, "RMI 配置可用");
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(contentPane, e2.getMessage());
						return;
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane, "RMI配置更新错误");
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

	// 获取时间选择器
	private DatePicker getDatePicker() {
		final DatePicker datePicker;
		// 显示格式
		String defaultFormt = "yyyy-MM-dd HH:mm:ss";
		// 初始化时间
		Date date = new Date();
		// 字体 宋体加粗 14号字
		Font font = new Font("宋体", Font.BOLD, 14);
		// 控件大小
		Dimension dimension = new Dimension(200, 20);
		// 构造方法 初始时间,显示格式,字体,控件大小
		datePicker = new DatePicker(date, defaultFormt, font, dimension);
		// 设置起始位置
		datePicker.setLocation(150, 150);
		// 设置显示语言
		datePicker.setLocale(Locale.US);
		// 设置可见
		datePicker.setTimePanleVisible(true);
		return datePicker;
	}
	
	private boolean LoginedWebClient() {
		try {
			new BizApplication("server.properties.xml").run();
			WebClient.login("test", "test");
			return true;
		} catch (Throwable e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(contentPane, "RMI配置可能出现错误,任务执行错误");
			return false;
		}
	}

	private void taskTestResultAct(ActionEvent e) {
		// 将前一个页面销毁
		this.dispose();
		new TaskTestResults(taskSIDNum).setVisible(true);
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