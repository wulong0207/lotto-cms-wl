var payment = {
	init : function() {
		mini.parse();
		
		//加载字典初始化
		ComReq.dictionary("1801,1802", function(result){
		    Dic.paytype = result["1801"];
	        Dic.status = result["1802"];
		});

		var json=[{id:'Pc',text:'PC'},{id:'H5',text:'H5'},{id:'Android',text:'ANDROID'},{id:'Ios',text:'IOS'}];
		mini.get("sortType").setData(json);;
		mini.get("sortType").setValue('Pc');
		
		payment.datagrid = mini.get("datagrid");
		payment.changeSortType();
		

	},changeSortType:function(){
		payment.datagrid.load({"sortType":"order_"+mini.get("sortType").getValue()});
	},
	 orderHtml:function(e){
	        var grid = e.sender;
	        var rowIndex = e.rowIndex;

	        var s = '<a class="mini-button"  plain="true" href="javascript:payment.changeOrderNo(\'up\',\''+grid.id+'\','+rowIndex+')" >▲</a>&nbsp;&nbsp;&nbsp;'+
	        '<a class="mini-button"  plain="true" href="javascript:payment.changeOrderNo(\'down\',\''+grid.id+'\','+rowIndex+')" >▼</a>';

	        return s;
	    },
	 changeOrderNo:function(doname,id,rowIndex){
		var key="order"+mini.get("sortType").getValue();
		
		 	var grid = mini.get(id);
	        var row = grid.getSelected();
	        
	        if(doname=="up"){
	        	grid.moveRow ( row, Number(rowIndex)-1 );
	        }else{
	        	grid.moveRow ( row, Number(rowIndex)+2 );
	        }
	        
	        var array=grid.data; 
	        for(var i=0;i<array.length;i++){
	        	var indexRow=array[i];
	        	array[i]["order"+mini.get("sortType").getValue()]=i;
	        	grid.updateRow (indexRow, array[i]);
	        }
	        
	        payment.changeOrderIded=true;
	 },
	 save:function(){
		 if(!payment.changeOrderIded){
			 mini.alert("请先修改表格数据，再保存");
			 return;
		 }
		 payment.changeOrderIded=false;//恢复初值
		 
		 rows=payment.datagrid.data;
		 
		 var json =mini.encode(rows);
		 Cms.save(json, "paymentmgr/sort/update", function(){
	            Cms.reload(id);
	        });
	 }
	
};

payment.init();