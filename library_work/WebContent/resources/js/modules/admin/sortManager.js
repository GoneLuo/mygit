layui.use(['layer', 'element'], function(){
	var layer = layui.layer
	,element = layui.element;
	
	var zTreeObj,
	treeSetting = {
		treeId:"ztreeDemo",
		data:{
			simpleData: {
				enable: true,
				idKey:"id",
				pIdKey:"parentId",
				rootPId:null
			}
		},
		view: {
			showIcon: true,
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			selectedMulti: false
		},
		callback: {
			onClick: clickSort,
			beforeRemove: beforeRemoveSort,
			onRemove: onRemoveSort,
			onRename: onRenameSort
		},
		edit: {
			enable: true,
			showRemoveBtn: true,
			removeTitle: "删除节点",
			renameTitle: "节点重命名"
		}

	};
	var log, className = "dark";

	$(document).ready(function(sortId){
		/* 初始化分类树 */
		$.ajax({
			type: 'post',
			url: basePath + "/sort/getSortTree",
			data: {},
			dataType:'JSON',
			success: function(data){
				zTreeObj = $.fn.zTree.init($("#ztreeDemo"), treeSetting, data);
			},
			error: function(){
				layer.msg('error');
			}
		});/* ajax end */
	});
	
	//节点点击事件
	function clickSort(event, treeId, treeNode){
		zTreeObj.expandNode(treeNode, true, false, true);
	}
		
	//删除节点前
	function beforeRemoveSort(treeId, treeNode){
		className = (className === "dark" ? "":"dark");
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		zTree.selectNode(treeNode);
		
		//只能删除无子分类
		if(treeNode.isParent){
			layer.msg('只能删除无子分类');
			return false;
		}
		
		return confirm('确定要删除该图书分类吗？'); 
	}
	//删除节点后，数据库删除分类
	function onRemoveSort(e, treeId, treeNode){
		var sortId = treeNode.id;
		
		$.ajax({
			url: basePath + '/sort/admin/delSort',
			type: 'post',
			data: {
				'id': sortId
			},
			dataType: 'json',
			success: function(res){
				layer.msg(res.msg);
			},
			error: function(){
				layer.msg('error');
			}
		}); /* ajax end */
	}
	
	//修改节点名称后，数据库重命名分类
	function onRenameSort(e, treeId, treeNode){
		var sortId = treeNode.id,
		sortName = treeNode.name;
		
		$.ajax({
			url: basePath + '/sort/admin/editName',
			type: 'post',
			data: {
				'id': sortId,
				'name': sortName
			},
			dataType: 'json',
			success: function(res){
				layer.msg(res.msg);
			},
			error: function(){
				layer.msg('error');
			}
		}); /* ajax end */
	}
	
	var newCount = 1;
	/* 用于在节点上固定显示用户自定义控件 */
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='增加子分类' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) btn.bind("click", function(){
			var newName = "new node" + (newCount++),
			newId = 100 + newCount,
			parentId = treeNode.id;
			zTreeObj.addNodes(treeNode, {id:newId, parentId:parentId, name:newName});
			
			var newNode = zTreeObj.getNodesByParam('id', newId, treeNode)[0];
			addNode(parentId, newName, newNode);
			return false;
		});
	};
	/* 用于当鼠标移出节点时，隐藏用户自定义控件 */
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_"+treeNode.tId).unbind().remove();
	};
	
	/* 数据库新增分类 */
	function addNode(parentId, sortName, newNode){
		$.ajax({
			url: basePath + '/sort/admin/addSort',
			type: 'post',
			data: {
				'parentId': parentId,
				'sortName': sortName
			},
			dataType: 'json',
			success: function(res){
				if(res.status){
					//不刷新页面，将当前页面新增的节点id和数据库新增的分类id统一
					newNode.id = res.data['id'];
					zTreeObj.updateNode(newNode);
					
					/* 数据库新增分类后，验证其名称是否合法 ，即父分类下名称不重复*/
					valiNameBeforeAdd(sortName, parentId, newNode);
				} else {
					layer.msg(res.msg);
				}
			},
			error: function(){
				layer.msg('error');
			}
		}); /* ajax end */
	}
	
	/* 数据库新增分类后，验证其名称是否合法 ，即父分类下名称不重复*/
	function valiNameBeforeAdd(sortName, parentId, newNode){
		$.ajax({
			url: basePath + '/sort/admin/valiNameAfterAdd',
			type: 'post',
			data: {
				"sortName": sortName,
				"parentId": parentId,
				"id": newNode.id
			},
			dataType: 'json',
			success: function(res){
				if(!res.status){
					zTreeObj.editName(newNode);
					newNode.rename;
				}else{
					layer.msg('增加成功');
				}
			},
			error: function(){
				layer.msg('error');
			}
		})/* ajax end */
	}
	
});