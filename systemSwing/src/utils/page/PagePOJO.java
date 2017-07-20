package utils.page;

import java.util.List;

public class PagePOJO <T>{

	public PagePOJO() {
	}
	
	private List<T> beanList;
	
	private int totleResult;
	
	private int pageNum;
	
	private int pageSize = 1;
	
	private int currPage;
	
	private int nextPage;
	
	private int previousPage;

	public List<T> getBeanList() {
		return beanList;
	}

	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}

	public int getTotleResult() {
		return totleResult;
	}

	public void setTotleResult(int totleResult) {
		this.totleResult = totleResult;
	}

	public int getPageNum() {
		if (this.totleResult % this.pageSize == 0) {
			this.pageNum = this.totleResult / this.pageSize;
		} else {
			this.pageNum = this.totleResult / this.pageSize + 1;
		}
		return this.pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getNextPage() {
		nextPage = currPage + 1;
		if (nextPage >= this.getPageNum()) {
			return pageNum;
		}
		return nextPage;
	}

	public int getPreviousPage() {
		this.previousPage = this.currPage - 1;
		if (this.previousPage <= 0) {
			return 1;
		} 
		return this.previousPage;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	
}
