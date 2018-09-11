package com.hhly.cms.bo;

import java.util.List;
/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2017-1-5 上午10:07:40
 * @Desc 树形菜单数据
 */
public class TreeGridBO {
	
	private String id;
	
	private String name;
	
	private String pid;
	
	private boolean checked;
	
	
    public List<FunctionBO> functions;

    
	

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public List<FunctionBO> getFunctions() {
		return functions;
	}

	public void setFunctions(List<FunctionBO> functions) {
		this.functions = functions;
	}

}
