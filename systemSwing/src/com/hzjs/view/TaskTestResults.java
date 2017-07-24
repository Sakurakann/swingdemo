package com.hzjs.view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.List;
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
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import utils.excel.ExcelExportUtil;
import utils.page.PagePOJO;
import utils.page.QueryPOJO;

import com.hzjs.dao.impl.TestCodeDao;
import com.hzjs.dao.impl.TestResultDao;
import com.hzjs.dao.impl.TestTypeDao;
import com.hzjs.domain.TaskQuery;
import com.hzjs.domain.TestCode;
import com.hzjs.domain.TestType;

public class TaskTestResults extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable resultTable;
	private JTextField taskSIdField;
	private JFormattedTextField taskName;
	private JComboBox testCode;
	private JButton nextPage;
	private JButton previousPage;
	private JLabel foot;
	private JButton goToBtn;
	private JButton exportBtn;

	private int taskSIdNum;
	private Integer currPage = 1;
	private Font font = new Font("宋体", Font.PLAIN, 14);

	private TestTypeDao testTypeDao = new TestTypeDao();
	private TestCodeDao testCodeDao = new TestCodeDao();
	private TestResultDao testResultDao = new TestResultDao();

	private List<TaskQuery> results;
	private List<TestType> testTypes;
	private List<Integer> codes;
	private PagePOJO<TaskQuery> pagePOJO = new PagePOJO<TaskQuery>();

	private DefaultTableModel tableModel = new DefaultTableModel();
	private DefaultComboBoxModel codeModel = new DefaultComboBoxModel();
	private String[] tableTitle = new String[] { "\u4EFB\u52A1ID",
			"\u4EFB\u52A1\u540D\u79F0", "\u6210\u529F\u6B21\u6570",
			"\u6210\u529F\u7387", "\u5931\u8D25\u6B21\u6570",
			"\u5931\u8D25\u7387" };
	private JTextField pageSizeField;
	private JTextField goToPage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskTestResults frame = new TaskTestResults();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TaskTestResults() {
		setResizable(false);
		initialize();
	}

	public TaskTestResults(int taskSID) {
		this.taskSIdNum = taskSID;
		initialize();
	}

	/**
	 * Create the frame.
	 */
	public void initialize() {
		// 加载时就获取所有的测试类别
		testTypes = testTypeDao.findAllTestTypes1();
		// getInitCodeModel(testTypes);

		setTitle("系统检测");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 870, 435);
		contentPane = new JPanel();
		setContentPane(contentPane);

		// setLocationRelativeTo(null);//设置窗体居中
		// 第二种方法
		int windowWidth = this.getWidth(); // 获得窗口宽
		int windowHeight = this.getHeight(); // 获得窗口高
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width; // 获取屏幕的宽
		int screenHeight = screenSize.height; // 获取屏幕的高
		this.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2
				- windowHeight / 2);// 设置窗口居中显示

		contentPane.setLayout(null);

		JLabel label = new JLabel("任务测试结果");
		label.setFont(font);
		label.setBounds(383, 10, 84, 16);
		contentPane.add(label);

		JLabel label_1 = new JLabel("测试类别");
		label_1.setBounds(10, 39, 55, 23);
		contentPane.add(label_1);

		Vector<Object> testTypeVector = new Vector<Object>();
		testTypeVector.addElement("---请选择测试类别---");
		for (TestType Type : testTypes) {
			testTypeVector.addElement(Type.getTestTypeName());
		}
		JComboBox testType = new JComboBox(testTypeVector);
		testType.setBounds(68, 39, 195, 23);
		testType.setFont(font);
		testType.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox thisType = (JComboBox) e.getSource();
				String testTypeName = (String) thisType.getSelectedItem();
				if (testTypeName.contains("---请选择测试类别---")) {
					codeModel.removeAllElements();
					return;

				}
				codes = testTypeDao.findTestCodeByName(testTypeName);
				List<TestCode> testCodes = testCodeDao
						.findTestCodesByTestType(codes);
				codeModel.removeAllElements();
				// TODO 选择大类后 将小类默认值设为空
				codeModel.addElement("--请选择测试类型--");
				for (TestCode code : testCodes) {
					codeModel.addElement(code.getTestName());
				}
			}
		});
		contentPane.add(testType);

		JLabel label_2 = new JLabel("测试类型");
		label_2.setBounds(279, 39, 54, 23);
		contentPane.add(label_2);

		// 使用模型创建选择
		testCode = new JComboBox(codeModel);
		testCode.setBounds(336, 39, 156, 23);
		testCode.setFont(font);
		contentPane.add(testCode);

		JLabel lblNewLabel = new JLabel("任务名称");
		lblNewLabel.setBounds(506, 39, 55, 23);
		contentPane.add(lblNewLabel);

		taskName = new JFormattedTextField();
		lblNewLabel.setLabelFor(taskName);
		taskName.setBounds(563, 39, 132, 23);
		contentPane.add(taskName);

		JLabel label_3 = new JLabel("任务组ID");
		label_3.setBounds(711, 39, 55, 23);
		contentPane.add(label_3);

		taskSIdField = new JTextField();
		taskSIdField.setText("" + taskSIdNum);
		taskSIdField.setEditable(true);
		taskSIdField.setBounds(766, 39, 86, 23);
		contentPane.add(taskSIdField);

		JButton queryButton = new JButton("查询结果");
		queryButton.setBounds(642, 361, 100, 28);
		contentPane.add(queryButton);
		queryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currPage = 1;
				queryForResult(e, currPage);

				btnEnableJudge();
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 72, 842, 277);
		contentPane.add(scrollPane);

		resultTable = new JTable();
		scrollPane.setViewportView(resultTable);
		DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
		cr.setHorizontalAlignment(JLabel.CENTER);
		resultTable.setDefaultRenderer(Object.class, cr);
		// 设置不可移动
		resultTable.getTableHeader().setReorderingAllowed(false);
		resultTable.setModel(tableModel);

		previousPage = new JButton("上一页");
		previousPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currPage = pagePOJO.getPreviousPage();
				queryForResult(e, currPage);
				btnEnableJudge();
			}
		});
		previousPage.setBounds(10, 361, 75, 28);
		previousPage.setEnabled(false);
		contentPane.add(previousPage);

		nextPage = new JButton("下一页");
		nextPage.setBounds(95, 361, 75, 28);
		contentPane.add(nextPage);
		nextPage.setEnabled(false);
		nextPage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currPage = pagePOJO.getNextPage();
				// System.out.println(pagePOJO.getPageNum() + " pageNum");
				btnEnableJudge();
				queryForResult(e, currPage);
			}
		});

		foot = new JLabel();
		foot.setBounds(180, 364, 217, 23);
		contentPane.add(foot);

		exportBtn = new JButton("导出结果");
		exportBtn.setBounds(752, 361, 100, 28);
		contentPane.add(exportBtn);
		exportBtn.setEnabled(false);
		exportBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ExcelExportUtil<TaskQuery> exportUtil = new ExcelExportUtil<TaskQuery>();
				String[] title = new String[] { "taskId", "taskName",
						"successNum", "successRate", "failureNum",
						"failureRate" };

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
					if (results == null) {
						JOptionPane.showMessageDialog(null, "结果集为空,请查询后操作");
						return;
					}
					exportUtil.exportExceptionExcel(file, title, results);
					JOptionPane.showMessageDialog(null, "导出成功");
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "导出失败");
					e1.printStackTrace();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "导出失败");
					e2.printStackTrace();
				}
			}
		});

		JLabel label_4 = new JLabel("每页");
		label_4.setBounds(403, 367, 31, 16);
		contentPane.add(label_4);

		pageSizeField = new JTextField("500");
		pageSizeField.setBounds(431, 365, 42, 21);
		contentPane.add(pageSizeField);
		pageSizeField.setColumns(10);

		JLabel label_5 = new JLabel("条");
		label_5.setBounds(477, 367, 31, 16);
		contentPane.add(label_5);

		goToBtn = new JButton("转到");
		goToBtn.setBounds(506, 364, 65, 23);
		contentPane.add(goToBtn);
		goToBtn.setEnabled(false);
		goToBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					currPage = Integer.parseInt(goToPage.getText());
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

		goToPage = new JTextField();
		goToPage.setBounds(573, 365, 31, 21);
		contentPane.add(goToPage);
		goToPage.setColumns(10);

		JLabel label_6 = new JLabel("页");
		label_6.setBounds(609, 368, 23, 15);
		contentPane.add(label_6);

		/*
		 * JButton taskSimulation = new JButton("模拟测试");
		 * taskSimulation.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { taskSimulationRet(e); } });
		 * taskSimulation.setBounds(622, 359, 100, 28);
		 * taskSimulation.setFont(font); contentPane.add(taskSimulation);
		 */

	}

	/*
	 * private void taskSimulationRet(ActionEvent e){ this.dispose();
	 * TaskSimulationTest.main(null);; }
	 */

	@SuppressWarnings("unused")
	private DefaultComboBoxModel getInitCodeModel(
			List<TestType> testTypes) {
		TestType type = testTypes.get(0);
		List<Integer> codes = testTypeDao.findTestCodeByName(type
				.getTestTypeName());
		List<TestCode> testCodes = testCodeDao.findTestCodesByTestType(codes);
		codeModel.removeAllElements();
		for (TestCode code : testCodes) {
			codeModel.addElement(code.getTestName());
		}
		return codeModel;
	}

	/**
	 * 查询结果 分页
	 * 
	 * @param e
	 */
	private void queryForResult(ActionEvent e, int currPage) {
		tableModel.setColumnCount(0);
		exportBtn.setEnabled(false);
		foot.setVisible(false);

		int taskSIDVal = taskSIdNum;
		int pageSizeVal = 0;

		try {
			pageSizeVal = Integer.parseInt(pageSizeField.getText());
			if (pageSizeVal <= 0) {
				JOptionPane.showMessageDialog(null, "每页条数应为正整数");
				return;
			}
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, "每页条数应为有效数字");
			return;
		}

		try {
			taskSIDVal = Integer.parseInt(taskSIdField.getText());
		} catch (Exception e2) {
			e2.printStackTrace();
			JOptionPane.showMessageDialog(null, "任务组ID应为有效数字");
			return;
		}

		String testName = (String) testCode.getSelectedItem();

		if (testName != null && testName.contains("--请选择测试类型--") && codes != null) {

			String taskNameval = taskName.getText();

			QueryPOJO queryPOJO = new QueryPOJO();
			queryPOJO.setPageSize(pageSizeVal);
			queryPOJO.setCurrPage(currPage);
			queryPOJO.setTaskName(taskNameval);
			queryPOJO.setTaskSId(taskSIDVal);
			
			pagePOJO = testResultDao.findTestResults1(queryPOJO, codes);

		} else {
			
			int codeInt = -1;
			
			if (testName != null && !testName.contains("--请选择测试类型--")) {
				codeInt = testCodeDao.findCodeIntByName(testName);
			}
			String taskNameval = taskName.getText();

			QueryPOJO queryPOJO = new QueryPOJO();
			queryPOJO.setPageSize(pageSizeVal);
			queryPOJO.setCurrPage(currPage);
			queryPOJO.setTestCode(codeInt);
			queryPOJO.setTaskName(taskNameval);
			queryPOJO.setTaskSId(taskSIDVal);

			pagePOJO = testResultDao.findTestResults1(queryPOJO);

		}

		if (pagePOJO.getTotleResult() == 0) {
			JOptionPane.showMessageDialog(null, "没有记录");
			return;
		}
		foot.setVisible(true);
		exportBtn.setEnabled(true);
		foot.setText("第 " + pagePOJO.getCurrPage() + " 页 , 共 "
				+ pagePOJO.getPageNum() + " 页 , " + pagePOJO.getTotleResult()
				+ " 条");

		goToPage.setText("" + pagePOJO.getCurrPage());

		results = pagePOJO.getBeanList();

		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		for (TaskQuery task : results) {
			Vector<Object> rowVector = new Vector<Object>();
			DecimalFormat df = new DecimalFormat("0%");

			rowVector.addElement(task.getTaskId());
			rowVector.addElement(task.getTaskName());
			rowVector.addElement(task.getSuccessNum());
			rowVector.addElement(df.format(task.getSuccessRate()));
			rowVector.addElement(task.getFailureNum());
			rowVector.addElement(df.format(task.getFailureRate()));

			data.add(rowVector);
		}
		Vector<String> columnName = new Vector<String>();
		for (String str : tableTitle) {
			columnName.addElement(str);
		}
		tableModel.setDataVector(data, columnName);
	}

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
			goToBtn.setEnabled(false);
		} else {
			goToBtn.setEnabled(true);
		}
	}
}
