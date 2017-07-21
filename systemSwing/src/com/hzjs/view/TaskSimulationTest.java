package com.hzjs.view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import utils.excel.ExcelExportUtil;
import utils.excel.ExcelImportUtil;
import utils.page.PagePOJO;
import utils.page.QueryPOJO;
import utils.table.MyTableModel;

import com.eltima.components.ui.DatePicker;
import com.gs.biz.BizApplication;
import com.gs.framework.ModuleConstructException;
import com.hzjs.dao.impl.TaskInfoDao;
import com.hzjs.dao.impl.TestCodeDao;
import com.hzjs.dao.impl.TestTypeDao;
import com.hzjs.domain.TaskInfo;
import com.hzjs.domain.TaskQuery;
import com.hzjs.domain.TestCode;
import com.hzjs.domain.TestType;
import com.jeesoon.tnits.client.web.WebClient;

public class TaskSimulationTest {

	private JFrame frame;
	private JTextField executeNum;
	private JTextField cycleInput;
	private JPanel contentPane;
	private JTable resultTable;
	private JButton taskTestResult;
	private JLabel foot;
	private JComboBox<Object> testCode;
	private JFormattedTextField taskname;
	private JButton previousPage;
	private JButton nextPage;
	private JButton goToPage;

	private TaskInfoDao taskInfoDao = new TaskInfoDao();
	private TestCodeDao testCodeDao = new TestCodeDao();
	private TestTypeDao testTypeDao = new TestTypeDao();
	private List<List<Object>> listobj = null;
	private List<TestType> testTypes = null;
	private PagePOJO<TaskQuery> pagePOJO = new PagePOJO<>();
	private JTextField goTOPageField;
	private JTextField pageSizeField;

	private DatePicker datePicker;

	private int taskSIDNum;
	private int currPage;
	private List<Integer> codes;

	private DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>();
	private DefaultTableModel tableModel = new MyTableModel();
	private Vector<String> columnName = new Vector<>();
	protected final String[] title = new String[] { "\u4efb\u52a1ID",
			"\u4efb\u52a1\u540d\u79f0", "\u6267\u884c\u65b9\u5f0f",
			"\u5faa\u73af\u5468\u671f", "\u6267\u884c\u72b6\u6001",
			"\u8ba1\u5212\u6267\u884c\u65f6\u95f4" };
	private JTextField taskSId;
	private JTextField durationField;
	private JTextField interValField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskSimulationTest window = new TaskSimulationTest();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TaskSimulationTest() {
		// 在启动时加载所有的测试类别
		testTypes = testTypeDao.findAllTestTypes1();
		// getInitCodeModel(testTypes);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// TODO 修改rmi接口参数

		try {
			new BizApplication("server.properties.xml").run();
			WebClient.login("test", "test");
		} catch (ModuleConstructException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}

		contentPane = new JPanel();

		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("系统检测");
		frame.setBounds(100, 100, 860, 564);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("任务模拟测试系统");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(368, 10, 112, 16);
		frame.getContentPane().add(lblNewLabel);

		final JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "任务导入", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(0, 36, 852, 161);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel("执行方式");
		label.setBounds(20, 30, 52, 16);
		panel.add(label);
		label.setFont(new Font("宋体", Font.PLAIN, 13));
		label.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel label_1 = new JLabel("执行次数");
		label_1.setBounds(199, 30, 55, 16);
		panel.add(label_1);
		label_1.setFont(new Font("宋体", Font.PLAIN, 13));

		JLabel label_2 = new JLabel("循环周期");
		label_2.setBounds(354, 30, 52, 16);
		panel.add(label_2);
		label_2.setFont(new Font("宋体", Font.PLAIN, 13));

		JLabel label_3 = new JLabel("计划时间");
		label_3.setBounds(563, 30, 52, 16);
		panel.add(label_3);
		label_3.setFont(new Font("宋体", Font.PLAIN, 13));

		JLabel label_4 = new JLabel("模板文件");
		label_4.setBounds(58, 79, 52, 16);
		panel.add(label_4);
		label_4.setFont(new Font("宋体", Font.PLAIN, 13));

		final JLabel fileName = new JLabel("");
		fileName.setBounds(217, 78, 212, 17);
		panel.add(fileName);

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
						JOptionPane.showMessageDialog(null, "请选择模板文件");
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
					}
				}
			}
		});
		btnNewButton.setBounds(120, 75, 87, 23);
		panel.add(btnNewButton);

		// 执行方式
		final JComboBox<Object> executeMode = new JComboBox<>();
		executeMode.setFont(new Font("宋体", Font.PLAIN, 13));
		executeMode.setModel(new DefaultComboBoxModel<Object>(new String[] {
				"定时", "循环", "立即" }));
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
		executeNum.setBounds(257, 27, 44, 23);
		panel.add(executeNum);
		executeNum.setText("");
		executeNum.setColumns(10);

		/*
		 * planTime = new JTextField(); planTime.setBounds(684, 28, 87, 23);
		 * panel.add(planTime); planTime.setText("1971-01-01 00:00:00");
		 * planTime.setColumns(10);
		 */
		datePicker = getDatePicker();
		datePicker.setBounds(618, 28, 200, 23);
		panel.add(datePicker);

		final JComboBox<Object> cycleUnit = new JComboBox<>();
		cycleUnit.setFont(new Font("宋体", Font.PLAIN, 13));
		cycleUnit.setModel(new DefaultComboBoxModel<Object>(new String[] {
				"不循环", "分钟", "小时", "天", "周", "月" }));
		cycleUnit.setBounds(456, 27, 67, 23);
		panel.add(cycleUnit);

		cycleInput = new JTextField();
		cycleInput.setBounds(409, 27, 44, 23);
		panel.add(cycleInput);
		cycleInput.setText("");
		cycleInput.setColumns(10);

		JButton importBtn = new JButton("导入任务");
		importBtn.setBounds(615, 128, 87, 23);
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

				switch (executeModeStr) {
				case "立即":
					executeModeChar = '1';
					break;
				case "定时":
					executeModeChar = '2';
					break;
				case "循环":
					executeModeChar = '3';
					break;
				default:
					break;
				}
				switch (cycleUnitStr) {
				case "不循环":
					cycleUnitVal = 0;
					break;
				case "分钟":
					cycleUnitVal = 1;
					break;
				case "小时":
					cycleUnitVal = 2;
					break;
				case "天":
					cycleUnitVal = 3;
					break;
				case "周":
					cycleUnitVal = 4;
					break;
				case "月":
					cycleUnitVal = 5;
					break;
				default:
					break;
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
							JOptionPane.showMessageDialog(null, "循环次数应为有效数字");
							return;
						}
					}
					if (cycleInputVal <= 0 || cycleUnitVal == 0) {
						JOptionPane.showMessageDialog(null, "循环周期应大于0");
						return;
					}
				} else {
					if (!executeNum.getText().equals("")) {
						try {
							executeNumVal = Integer.parseInt(executeNum
									.getText());
						} catch (Exception e2) {
							e2.printStackTrace();
							JOptionPane.showMessageDialog(null, "执行次数应为有效数字");
							return;
						}
					}
					if (executeNumVal <= 0) {
						JOptionPane.showMessageDialog(null, "执行次数应大于0");
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
							JOptionPane.showMessageDialog(null, "通话时长应为有效数字");
							return;
						}
					}
					if (durationval <= 0) {
						JOptionPane.showMessageDialog(null, "通话时长应大于0");
						return;
					}

					if (!interValField.getText().equals("")) {
						try {
							interVal = Integer.parseInt(interValField.getText());
						} catch (Exception e2) {
							e2.printStackTrace();
							JOptionPane.showMessageDialog(null, "呼叫间隔应为有效数字");
							return;
						}
					}
					if (interVal <= 0) {
						JOptionPane.showMessageDialog(null, "呼叫间隔应大于0");
						return;
					}
				}

				// 定时
				if (executeModeStr == "定时") {
					if (planTimeVal.before(new Date())) {
						JOptionPane.showMessageDialog(null, "定时任务应当大于当前时间");
						return;
					}
				}

				if (listobj == null) {
					JOptionPane.showMessageDialog(null, "请选择模板文件");
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

				try {
					resultInt = taskInfoDao.addTasks(listobj, taskQuery);
				} catch (Exception e2) {
					// 判断主叫格式
					JOptionPane.showMessageDialog(null, e2.getMessage());
					return;
				}
				taskSIDNum = taskInfoDao.findTaskSID();
				taskSId.setText("" + taskSIDNum);

				List<Integer> idList = taskInfoDao.findTaskIdBySId(taskSIDNum);

				// TODO 将id传入rmi接口
				for (Integer id : idList) {
					try {
						WebClient.taskExecute(id);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}

				if (resultInt != 0) {
					JOptionPane.showMessageDialog(null, "任务导入成功");
				} else {
					JOptionPane.showMessageDialog(null, "任务导入失败");
					return;
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
		taskTestResult.setBounds(731, 128, 87, 23);
		panel.add(taskTestResult);

		JButton button = new JButton("检出模板");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				List<TaskInfo> results = taskInfoDao.getTemplate();

				if (results.size() <= 0) {
					JOptionPane.showMessageDialog(contentPane, "模板为空,仍然导出?");
				}
				ExcelExportUtil<TaskInfo> exportUtil = new ExcelExportUtil<>();
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
						JOptionPane.showMessageDialog(contentPane, "请输入文件名");
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
		button.setBounds(55, 128, 87, 23);
		panel.add(button);

		JLabel lblNewLabel_1 = new JLabel("通话时长");
		lblNewLabel_1.setBounds(450, 79, 54, 15);
		panel.add(lblNewLabel_1);

		durationField = new JTextField();
		durationField.setEditable(false);
		durationField.setBounds(512, 77, 66, 21);
		panel.add(durationField);
		durationField.setColumns(10);

		JLabel label_11 = new JLabel("呼叫间隔");
		label_11.setBounds(618, 79, 54, 15);
		panel.add(label_11);

		interValField = new JTextField();
		interValField.setEditable(false);
		interValField.setBounds(682, 77, 66, 21);
		panel.add(interValField);
		interValField.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "任务列表", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_1.setBounds(0, 218, 852, 308);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel label_5 = new JLabel("测试类别");
		label_5.setBounds(20, 31, 52, 16);
		panel_1.add(label_5);
		label_5.setHorizontalAlignment(SwingConstants.LEFT);
		label_5.setFont(new Font("宋体", Font.PLAIN, 13));

		/**
		 * 测试类别选择
		 */
		Vector<Object> vector = new Vector<>();
		vector.addElement("---请选择测试类别---");
		for (TestType type : testTypes) {
			vector.add(type.getTestTypeName());
		}
		final JComboBox<Object> testType = new JComboBox<Object>(vector);
		testType.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String testTypeName = (String) testType.getSelectedItem();
				if (testTypeName.contains("---请选择测试类别---")) {
					model.removeAllElements();
					return;
				}
				codes = testTypeDao
						.findTestCodeByName(testTypeName);
				List<TestCode> testCodes = testCodeDao
						.findTestCodesByTestType(codes);
				// 清除以前的选项
				model.removeAllElements();
				model.addElement("--请选择测试类型--");
				for (TestCode code : testCodes) {
					model.addElement(code.getTestName());
				}
			};
			/*
			 * @Override public void itemStateChanged(ItemEvent e) { 如果有父级选项变化
			 * 则清楚其所有的选项后 添加新的选项
			 * 
			 * 
			 * if (e.getStateChange() == ItemEvent.SELECTED) {
			 * 
			 * } }
			 */
		});
		testType.setBounds(77, 28, 179, 23);
		panel_1.add(testType);
		testType.setFont(new Font("宋体", Font.PLAIN, 13));

		JLabel label_6 = new JLabel("测试类型");
		label_6.setBounds(277, 31, 52, 16);
		panel_1.add(label_6);
		label_6.setHorizontalAlignment(SwingConstants.LEFT);
		label_6.setFont(new Font("宋体", Font.PLAIN, 13));

		/**
		 * 测试类型选择 二级联动根据测试类别变化
		 */
		testCode = new JComboBox<Object>(model);
		testCode.setBounds(333, 28, 141, 23);
		panel_1.add(testCode);
		testCode.setFont(new Font("宋体", Font.PLAIN, 13));

		JLabel label_7 = new JLabel("任务名称");
		label_7.setBounds(498, 31, 52, 16);
		panel_1.add(label_7);
		label_7.setHorizontalAlignment(SwingConstants.LEFT);
		label_7.setFont(new Font("宋体", Font.PLAIN, 13));

		taskname = new JFormattedTextField();
		taskname.setBounds(553, 29, 85, 21);
		panel_1.add(taskname);

		taskSId = new JTextField();
		taskSId.setBounds(684, 29, 38, 21);
		panel_1.add(taskSId);
		taskSId.setColumns(10);

		/**
		 * 查询符合条件的任务信息
		 */
		JButton queryBtn = new JButton("查询");
		queryBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// JComboBox<?> source = (JComboBox<?>) e.getSource();
				// String testName = (String) source.getSelectedItem();

				currPage = 1;
				queryForResult(e, currPage);
				btnEnableJudge();
			}
		});
		queryBtn.setBounds(751, 27, 67, 23);
		panel_1.add(queryBtn);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 61, 832, 203);
		panel_1.add(scrollPane);

		resultTable = new JTable();
		// 设置表中数据居中
		DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
		cr.setHorizontalAlignment(JLabel.CENTER);
		resultTable.setDefaultRenderer(Object.class, cr);
		// 设置不可移动
		resultTable.getTableHeader().setReorderingAllowed(false);
		resultTable.setModel(tableModel);

		scrollPane.setViewportView(resultTable);

		// 上一页
		previousPage = new JButton("上一页");
		previousPage.setBounds(20, 274, 75, 23);
		panel_1.add(previousPage);
		previousPage.setEnabled(false);
		previousPage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currPage = pagePOJO.getPreviousPage();
				btnEnableJudge();
				queryForResult(e, currPage);
			}
		});

		// 下一页
		nextPage = new JButton("下一页");
		nextPage.setBounds(105, 274, 75, 23);
		panel_1.add(nextPage);
		nextPage.setEnabled(false);
		nextPage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currPage = pagePOJO.getNextPage();
				btnEnableJudge();
				queryForResult(e, currPage);
			}
		});

		foot = new JLabel();
		foot.setBounds(196, 274, 237, 23);
		panel_1.add(foot);

		goToPage = new JButton("转到");
		goToPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					currPage = Integer.parseInt(goTOPageField.getText());
				} catch (Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "请输入正确页码");
					return;
				}
				if (currPage > pagePOJO.getPageNum() || currPage <= 0) {
					JOptionPane.showMessageDialog(null, "请输入正确页码");
					return;
				}
				queryForResult(e, currPage);
				btnEnableJudge();
			}
		});
		goToPage.setBounds(675, 274, 75, 23);
		goToPage.setEnabled(false);
		panel_1.add(goToPage);

		goTOPageField = new JTextField();
		goTOPageField.setColumns(10);
		goTOPageField.setBounds(751, 274, 44, 23);
		panel_1.add(goTOPageField);

		JLabel label_8 = new JLabel("每页");
		label_8.setBounds(553, 274, 28, 21);
		panel_1.add(label_8);

		pageSizeField = new JTextField("500");
		pageSizeField.setBounds(585, 274, 44, 21);
		panel_1.add(pageSizeField);
		pageSizeField.setColumns(10);

		JLabel label_10 = new JLabel("条");
		label_10.setBounds(633, 274, 26, 21);
		panel_1.add(label_10);

		JLabel label_9 = new JLabel("页");
		label_9.setBounds(798, 278, 20, 15);
		panel_1.add(label_9);

		JLabel lblSid = new JLabel("SID");
		lblSid.setBounds(661, 32, 26, 16);
		panel_1.add(lblSid);

	}

	private void taskTestResultAct(ActionEvent e) {
		// 将前一个页面销毁
		// this.frame.dispose();
		new TaskTestResults(taskSIDNum).setVisible(true);
	}

	@SuppressWarnings("unused")
	// 为了能够使testcode的值为null或默认值 这里不再加载
	/**
	 * 加载时 自动获取测试类别下第一个所属的类型
	 * @param testTypes
	 * @return
	 */
	private DefaultComboBoxModel<Object> getInitCodeModel(
			List<TestType> testTypes) {
		TestType type = testTypes.get(0);
		List<Integer> codes = testTypeDao.findTestCodeByName(type
				.getTestTypeName());
		List<TestCode> testCodes = testCodeDao.findTestCodesByTestType(codes);
		model.removeAllElements();
		for (TestCode code : testCodes) {
			model.addElement(code.getTestName());
		}
		return model;
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

	/**
	 * 分页
	 */
	// 分页方法
	private void queryForResult(ActionEvent e, int currPage) {
		// 清除所有的table数据
		tableModel.setColumnCount(0);
		foot.setVisible(false);

		int pageSizeVal = 0;
		int taskSIdVal = -2;
		try {
			if (taskSId.getText().equals("")) {
				taskSIdVal = -2;
			} else {
				taskSIdVal = Integer.parseInt(taskSId.getText());
			}
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, "请输入正确SID");
			return;
		}

		try {
			pageSizeVal = Integer.parseInt(pageSizeField.getText());
			if (pageSizeVal <= 0) {
				JOptionPane.showMessageDialog(null, "每页条数应大于0");
				return;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
			JOptionPane.showMessageDialog(null, "每页条数应为正确数字");
			return;
		}

		String testName = (String) testCode.getSelectedItem();

		//
		if (testName != null && testName.contains("--请选择测试类型--")
				&& codes != null) {

			String taskNameval = taskname.getText();

			QueryPOJO queryPOJO = new QueryPOJO();
			queryPOJO.setPageSize(pageSizeVal);
			queryPOJO.setCurrPage(currPage);
			queryPOJO.setTaskName(taskNameval);
			queryPOJO.setTaskSId(taskSIdVal);

			pagePOJO = taskInfoDao.findTaskInfo1(queryPOJO, codes);

		} else {

			int codeInt = -1;

			if (testName != null && !testName.contains("--请选择测试类型--")) {
				codeInt = testCodeDao.findCodeIntByName(testName);
			}
			String taskNameval = taskname.getText();

			QueryPOJO queryPOJO = new QueryPOJO();
			queryPOJO.setPageSize(pageSizeVal);
			queryPOJO.setCurrPage(currPage);
			queryPOJO.setTestCode(codeInt);
			queryPOJO.setTaskName(taskNameval);
			queryPOJO.setTaskSId(taskSIdVal);

			pagePOJO = taskInfoDao.findTaskInfo1(queryPOJO);

		}

		if (pagePOJO.getTotleResult() == 0) {
			JOptionPane.showMessageDialog(null, "没有记录");
			return;
		}

		foot.setVisible(true);
		foot.setText("第 " + pagePOJO.getCurrPage() + " 页 , 共 "
				+ pagePOJO.getPageNum() + " 页 , " + pagePOJO.getTotleResult()
				+ " 条");
		goTOPageField.setText("" + currPage);
		pageSizeField.setText("" + pagePOJO.getPageSize());

		Vector<Vector<Object>> data = new Vector<>();
		String executeModeStr = null;
		String taskStatusStr = null;
		List<TaskQuery> taskQueries = pagePOJO.getBeanList();

		for (TaskQuery tQuery : taskQueries) {
			Vector<Object> rowVector = new Vector<>();
			rowVector.addElement(tQuery.getTaskId());
			rowVector.addElement(tQuery.getTaskName());
			switch (tQuery.getExecuteMode()) {
			case '1':
				executeModeStr = "立即";
				break;
			case '2':
				executeModeStr = "定时";
				break;
			case '3':
				executeModeStr = "循环";
				break;
			default:
				executeModeStr = "Unknow";
				break;
			}
			rowVector.addElement(executeModeStr);

			rowVector.addElement(tQuery.getCycleValue() + "分钟");

			// 任务状态 0 无效；1 等待执行；2 正在执行；3 完成；4 失败；5 暂停；6 中止；7 恢复执行；8 挂起；
			switch (tQuery.getTaskStatus()) {
			case '0':
				taskStatusStr = "无效";
				break;
			case '1':
				taskStatusStr = "等待执行";
				break;
			case '2':
				taskStatusStr = "正在执行";
				break;
			case '3':
				taskStatusStr = "完成";
				break;
			case '4':
				taskStatusStr = "失败";
				break;
			case '5':
				taskStatusStr = "暂停";
				break;
			case '6':
				taskStatusStr = "中止";
				break;
			case '7':
				taskStatusStr = "恢复执行";
				break;
			case '8':
				taskStatusStr = "挂起";
				break;
			default:
				taskStatusStr = "Unknow";
				break;
			}
			rowVector.addElement(taskStatusStr);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			rowVector.addElement(sdf.format(tQuery.getPlanTime()));

			data.add(rowVector);
		}
		for (String str : title) {
			columnName.addElement(str);
		}
		tableModel.setDataVector(data, columnName);
	}

	/**
	 * 分页按钮判断
	 */
	private void btnEnableJudge() {
		if (currPage <= 1) {
			previousPage.setEnabled(false);
		} else {
			previousPage.setEnabled(true);
		}
		if (currPage >= pagePOJO.getPageNum()) {
			nextPage.setEnabled(false);
		} else {
			nextPage.setEnabled(true);
		}
		if (pagePOJO.getPageNum() <= 1) {
			goToPage.setEnabled(false);
		} else {
			goToPage.setEnabled(true);
		}
	}
}