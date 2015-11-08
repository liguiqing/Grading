/**
 * <p><b></b></p>
 * 
 **/

package com.easytnt.commons.entity.cqrs;

import java.util.List;
import java.util.Map;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年9月23日
 * @version 1.0
 **/
public class QueryBuilder {

	public <T> Query<T> newQuery(final int page,final int size,final Map<String, String[]> params) {
		Query<T> query = new Query<T>() {

			private int startRow;

			private int curPage = page;

			private int pageSize = size;

			private int totalPage;

			private int totalRows;
			
			private boolean next;
			
			private boolean prev;
			
			private List<T> list;
			
			private Map<String, String[]> parameters = params;
			
			@Override
			public void parametersWith(Map<String, String[]> parameters) {
				this.parameters = parameters;
			}
			
			public <E> E parameterOf(String name,Class<E> clazz){
				//等实现
				return null;
			}

			@Override
			public void result(List<T> list) {
				this.list = list;
			}
			
			@Override
			public void totalRows(int totalRows) {
				this.totalRows = totalRows;
				this.totalPage = this.totalRows / this.getPageSize();
				
				if(totalRows <= this.pageSize) {
					this.curPage = 1;
				}
				
				if (totalRows % this.pageSize > 0) {
					this.totalPage = this.totalPage + 1;
				}
				
				if (this.curPage > this.totalPage) {
					this.curPage = this.totalPage;
				}
				
				if (this.totalPage == 0) {
					this.totalPage = 1;
				}

				if (getCurPage() < this.totalPage) {
					this.next = true;
				}

				if (getCurPage() > 1 && this.totalPage > 1) {
					this.prev = true;
				}
			}

			@Override
			public boolean hasNext() {
				return this.next;
			}

			@Override
			public boolean hasPrev() {
				return this.prev;
			}

			@Override
			public void first() {
				this.curPage = 1;
				this.startRow = 0;
			}

			@Override
			public void prev() {
				this.curPage--;
				this.startRow = (this.curPage - 1) * this.pageSize;
				check();
			}

			@Override
			public void next() {
				if (this.curPage < this.totalPage) {
					this.curPage++;
				}
				this.startRow = (this.curPage - 1) * this.pageSize;
				check();
			}

			@Override
			public void last() {
				this.curPage = this.totalPage;
				this.startRow = (this.curPage - 1) * this.pageSize;
				check();
			}

			@Override
			public int getCurPage() {
				return this.curPage;
			}

			@Override
			public int getPageSize() {
				if(this.pageSize == 0) {
					this.pageSize = 10;
				}
				return this.pageSize;
			}

			@Override
			public int getTotalRows() {
				return this.totalRows;
			}

			@Override
			public int getTotalPage() {
				return this.totalPage;
			}

			@Override
			public int getStartRow() {
				this.startRow = (this.curPage - 1) * this.pageSize;
				if (this.startRow < 0) {
					this.startRow = 0;
				}
				return startRow;
			}

			@Override
			public Map<String,String[]> getParameters() {
				return this.parameters;
			}

			@Override
			public List<T> getResults() {
				return this.list;
			}
			
			private void check() {
				if (this.curPage <= 1) {
					this.curPage = 1;
					this.startRow = 0;
				}
			}
			
		};
		
		return query;
	}
}

