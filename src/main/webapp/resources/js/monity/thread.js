var Current = {
	"init" : function() {
		Current.time = 0;
		Current.threadPoolValue = {
			"activeCount" : [],
			"queueSize" : [],
			"max" : 10,
			"name" : "THREAD_POOL",
			"desc" : "任务执行线程池",
			"labels":[],
		};
		Current.scheduledThreadPoolValue = {
			"activeCount" : [],
			"queueSize" : [],
			"max" : 10,
			"name" : "SCHEDULED_EXECUTOR",
			"desc" : "定时任务线程池",
			"labels":[],
		};
		mini.parse();
		Current.tabs = mini.get("threadTab");
		Current.index = 1;

		Current.initData();
	},
	"addTab" : function() {
		var tab = {
			title : "tab" + Current.index++
		};
		tab = Current.tabs.addTab(tab);
		// tab body
		var el = Current.tabs.getTabBodyEl(tab);
		el.innerHTML = "";
		// active tab
		Current.tabs.activeTab(tab);
	},
	"getData" : function() {
		var param  ={
			"url":"monity/thread/pool",
			 "action" : "get" 
		}
		Cms.ajaxResultJsonStr(param,function(data){
			for (var i = 0; i < data.length; i++) {
				var thread = data[i];
				if (thread.name == Current.threadPoolValue.name) {
					Current.draw(thread, Current.threadPoolValue,"threadPool");
				} else if (thread.name == Current.scheduledThreadPoolValue.name) {
					Current.draw(thread,Current.scheduledThreadPoolValue,"scheduleThreadPool");
				}
			}
		});
	},
	"draw" : function(thread, value, id) {
		var activeCount = value["activeCount"];
		var queueSize = value["queueSize"];

		activeCount.push(thread.activeCount);
		queueSize.push(thread.queueSize);

		if (value.max < thread.activeCount) {
			value.max = thread.activeCount;
		}

		if (value.max < thread.queueSize) {
			value.max = thread.queueSize;
		}
		var labels = value["labels"];
		
		var date = new Date();
		var hour = date.getHours();
		var minute = date.getMinutes();
		var time = hour + ":" + minute +  ":" + date.getSeconds();
		
		if(labels.length == 0){
			labels.push(time);
		}else {
			labels[1] = time;
		}
		var data = [ {
			name : 'activeCount',
			value : activeCount,
			color : '#0d8ecf',
			line_width : 2
		}, {
			name : 'queueSize',
			value : queueSize,
			color : '#ef7707',
			line_width : 2
		}];
		
		//横坐标计算
		var line = new iChart.LineBasic2D({
			render : id,
			data : data,
			align : 'center',
			title : value.desc,
			subtitle : "当前线程数:" + thread.poolSize + "，最大线程数:"
					+ thread.maximumPoolSize + "，任务总数:" + thread.taskCount
					+ "，执行完任务数:" + thread.completedTaskCount,
			footnote : '数据来源：定时器项目',
			width : 1500,
			height : 400,
			tip : {
				enable : true,
				shadow : true
			},
			legend : {
				enable : true,
				row : 1,// 设置在一行上显示，与column配合使用
				column : 'max',
				valign : 'top',
				sign : 'bar',
				background_color : null,// 设置透明背景
				offsetx : -80,// 设置x轴偏移，满足位置需要
				border : true
			},
			crosshair : {
				enable : true,
				line_color : '#62bce9'
			},
			sub_option : {
				label : false,
				point_hollow : false
			},
			coordinate : {
				width : 1200,
				height : 240,
				axis : {
					color : '#9f9f9f',
					width : [ 0, 0, 2, 2 ]
				},
				grids : {
					vertical : {
						way : 'share_alike',
						value : 5
					}
				},
				scale : [ {
					position : 'left',
					start_scale : 0,
					end_scale : value.max,
					scale_space : Math.round(value.max / 10),
					scale_size : 2,
					scale_color : '#9f9f9f'
				}, {
					position : 'bottom',
					labels : labels
				} ]
			}
		});
		// 开始画图
		line.draw();
	},
	"lineBasic2D" : function(data, labels, id) {
		return line;
	},
	"initData" : function() {
		Current.getData()
		var t1 = window.setInterval("Current.getData()", 5000);
	}
}
Current.init();