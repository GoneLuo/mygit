package com.luoy.library.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 配合前端使用z-tree的工具类
 * @author ying luo
 * @createDate 2018年4月10日
 */
public class TreeNodeVo implements Serializable {
	
	private static final long serialVersionUID = -68063028880464121L;

	private String id; // 节点ID
	private String parentId;
	private String name = "z-tree node"; // 节点名称
	private boolean isParent; // 是否含有子节点，含有子节点为true，否则false
	private boolean checked = false; // 是否选中
	private boolean open = false; // 节点是否展开
	private List<TreeNodeVo> children = null; // 子节点
	private boolean nocheck;//设置节点是否隐藏 checkbox

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public TreeNodeVo() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}


	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}


	public List<TreeNodeVo> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNodeVo> children) {
		this.children = children;
	}

}

