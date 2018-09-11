var Ticket = {
		"sendTicketOK" : function(id,other) {
			Cms.formValidate("#send_grid", "修改数据有误", function() {
				Current.sendGrid.commitEdit();
				var rows = Current.sendGrid.getData();
				var str = "";
				for ( var i = 0; i < rows.length; i++) {
					if (i > 0) {
						str += ",";
					}
					str += rows[i].week + "|";
					var date = rows[i].startDate;
					// 判断是否是字符串，不是解析成日期
					if (Object.prototype.toString.call(date) != "[object String]") {
						str += Ticket.timeJoin(date.getHours(), date.getMinutes(),
								date.getSeconds());
					} else {
						str += date;
					}
					str += "|";
					date = rows[i].endDate;
					// 判断是否是字符串，不是解析成日期
					if (Object.prototype.toString.call(date) != "[object String]") {
						str += Ticket.timeJoin(date.getHours(), date.getMinutes(),
								date.getSeconds());
					} else {
						str += date;
					}
					if(other){
						str= str +"|"+rows[i][other];
					}
			    }
				mini.get(id).setValue(str);
			});
		},
		"timeJoin" : function(hour, minute, second) {
			hour += "";
			minute += "";
			second += "";
			var str = "";
			if (hour.length == 1) {
				str += "0"
			}
			str += hour + ":";
			if (minute.length == 1) {
				str += "0"
			}
			str += minute + ":";
			if (second.length == 1) {
				str += "0"
			}
			str += second;
			return str;
		},
		"onActionRendererSend":function (e) {
	        var gridAdd = e.sender;
	        var record = e.record;
	        var uid = record._uid;
	        var s="";
	        var id = record.id;
	        if(id && gridAdd.isEditingRow(record)){
	            s = '<a class="mini-button"  plain="true" href="javascript:Cms.cancelEditRow(\'send_grid\',\'' + uid + '\')">取消</a>';
	            s +='<a class="mini-button"  plain="true" href="javascript:Cms.delEditRow(\'send_grid\',\'' + uid + '\')">删除</a>';
	        }else if (gridAdd.isEditingRow(record)) {
	            s = '<a class="mini-button"  plain="true" href="javascript:Cms.delEditRow(\'send_grid\',\'' + uid + '\')">取消</a>';
	        }
	        return s;
	    },
	    "split":function(str,other){
	    	var timeDatas =[];
			var allow = str.split(",");
			for(var i =0;i < allow.length ; i++){
				var time = allow[i].split("|");
				var timeData = {};
				timeData.id = i+1;
				timeData.week = time[0];
				timeData.startDate = time[1];
				timeData.endDate = time[2];
				if(other){
					timeData[other] = time[3];
				}
				timeDatas.push(timeData);
			}
			return timeDatas;
	    }
}
