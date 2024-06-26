package com.mynewcrud.Resource.pages;


import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class PageRequest {

		@QueryParam("pageNum")
	    @DefaultValue("0")
	    private int pageNum;
	 
	    @QueryParam("pageSize")
	    @DefaultValue("10")
	    private int pageSize;
	    
	    public PageRequest() {
	    }

	    public PageRequest(int pageNum, int pageSize) {
	        this.pageNum = pageNum;
	        this.pageSize = pageSize;
	    }

	    public int getPageNum() {
	        return pageNum;
	    }

	    public int getPageSize() {
	        return pageSize;
	    }
}
