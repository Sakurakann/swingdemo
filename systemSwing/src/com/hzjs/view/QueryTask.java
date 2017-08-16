package com.hzjs.view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import utils.page.PagePOJO;
import utils.page.QueryPOJO;
import utils.table.MyTableModel;

import com.hzjs.dao.impl.TaskInfoDao;
import com.hzjs.dao.impl.TestCodeDao;
import com.hzjs.dao.impl.TestTypeDao;
import com.hzjs.domain.TaskQuery;
import com.hzjs.domain.TestCode;
import com.hzjs.domain.TestType;
import com.hzjs.domain.Trans;

public class QueryTask extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable resultTable;
	private JLabel foot;
	private JComboBox testCode;
	private JFormattedTextField taskname;
	private JButton previousPage;
	private JButton nextPage;
	private JButton goToPage;

	private TaskInfoDao taskInfoDao = new TaskInfoDao();
	private TestCodeDao testCodeDao = new TestCodeDao();
	private TestTypeDao testTypeDao = new TestTypeDao();
	private List<TestType> testTypes = null;
	private PagePOJO<TaskQuery> pagePOJO = new PagePOJO<TaskQuery>();
	private JTextField goTOPageField;
	private JTextField pageSizeField;

	private int currPage;
	private List<Integer> codes;

	private DefaultComboBoxModel model = new DefaultComboBoxModel();
	private DefaultTableModel tableModel = new MyTableModel();
	private Vector<String> columnName = new Vector<String>();
	protected final String[] title = new String[] { "\u4efb\u52a1ID",
			"\u4efb\u52a1\u540d\u79f0", "\u6267\u884c\u65b9\u5f0f",
			"\u5faa\u73af\u5468\u671f", "\u6267\u884c\u72b6\u6001",
			"\u8ba1\u5212\u6267\u884c\u65f6\u95f4" };
	private JTextField taskSId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QueryTask frame = new QueryTask();
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
	public QueryTask() {

		testTypes = testTypeDao.findAllTestTypes1();

		setResizable(false);
		setTitle("系统检测");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 855, 397);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		loadMenu(menuBar);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "任务列表", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 23, 829, 315);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel label_5 = new JLabel("测试类别");
		label_5.setBounds(20, 31, 52, 16);
		panel_1.add(label_5);
		label_5.setHorizontalAlignment(SwingConstants.LEFT);
		label_5.setFont(new Font("宋体", Font.PLAIN, 13));

		/**
		 * 测试类别选择
		 */
		Vector<Object> vector = new Vector<Object>();
		vector.addElement("---请选择测试类别---");
		for (TestType type : testTypes) {
			vector.add(type.getTestTypeName());
		}
		final JComboBox testType = new JComboBox(vector);
		testType.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String testTypeName = (String) testType.getSelectedItem();
				if (testTypeName.contains("---请选择测试类别---")) {
					model.removeAllElements();
					return;
				}
				codes = testTypeDao.findTestCodeByName(testTypeName);
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
		testCode = new JComboBox(model);
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

		taskSId = new JTextField(Trans.getInstance().getSID());
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
		scrollPane.setBounds(10, 61, 808, 203);
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

		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		String executeModeStr = null;
		String taskStatusStr = null;
		List<TaskQuery> taskQueries = pagePOJO.getBeanList();

		for (TaskQuery tQuery : taskQueries) {
			Vector<Object> rowVector = new Vector<Object>();
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

	/**
	 * 加载菜单栏
	 * 
	 * @param menuBar
	 */
	private void loadMenu(JMenuBar menuBar) {
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

	private void mnImportTask(ActionEvent event) {
		ImportTask.main(null);
		this.dispose();
	}

	private void mnConfigDB(ActionEvent event) {
		ConfigDB.main(null);
		this.dispose();
	}

	private void mnQueryTask(ActionEvent event) {
		QueryTask.main(null);
		this.dispose();
	}

	private void mnTestResult(ActionEvent event) {
		TaskTestResults.main(null);
		this.dispose();
	}

}
