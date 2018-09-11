package com.hhly.cms.bo;

/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2017-1-5 上午10:01:57
 * @Desc 树形子节点Buttons
 */
public class FunctionBO{


    private String name;
    
    private String action;
    
    private boolean checked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name; 
    }

    

    public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	
}
