package utils.page;

public class QueryPOJO {

	public QueryPOJO() {
	}
	
	/**
	 * 每页显示500条 提供修改入口
	 */
	private int pageSize = 500;
	
	/**
	 * 当前页 初始化为第一页 后面会根据页数变化修改
	 */
	private int currPage;
	
	/**
	 * 最大值
	 */
	private int endRow;
	
	/**
	 * 最小值
	 */
	private int startRow;
	
	
	private String taskName;
	
	private int testCode;
	
	private int taskSId;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getEndRow() {
		this.endRow = this.currPage*this.pageSize;
		return endRow;
	}

	public int getStartRow() {
		this.startRow = (this.currPage-1)*this.pageSize + 1;
		return startRow;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getTestCode() {
		return testCode;
	}

	public void setTestCode(int testCode) {
		this.testCode = testCode;
	}

	public int getTaskSId() {
		return taskSId;
	}

	public void setTaskSId(int taskSId) {
		this.taskSId = taskSId;
	}
	

}
