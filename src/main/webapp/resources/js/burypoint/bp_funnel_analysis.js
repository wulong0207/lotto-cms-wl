var bp_funnel_analysis = {
	init : function() {
		mini.parse();
		bp_funnel_analysis.numbers = ['一','二','三','四','五','六','七','八'];
		$.ajax({
			url : "operatemgr/software/version/name/distinct",
			async : false
		}).done(function(data){
			Dic.versions = [];
			data.forEach(function(version) {
				Dic.versions.push({id:version.code, text:version.name});
			});
			mini.get('versionId').setData(Dic.versions);
		});
		ComReq.dictionary("1515", function(result) {
			mini.get("platform").setData(result["1515"]);
		});
		// 动态绑定关闭漏斗步骤事件
		$('.closeStep').live('click', function() {
			$(this).parent().parent().remove();
		})
		bp_funnel_analysis.datagrid = mini.get('datagrid');
		$('#conditonText').click(bp_funnel_analysis.addCondition);
		$('canvas.step').live('click',function() {
			var bpCode = $(this).data('step');
            bp_funnel_analysis.updateChart(bpCode);
		});
		// 点击"转化详情"的人数时
		$('#datagrid a').live('click', function () {
            var row=bp_funnel_analysis.datagrid.getSelected();
            var bpCode = bp_funnel_analysis.funnelData.conversionInfo.columns[$(this).data('index')];
            var myDate = row.column0;
            bp_funnel_analysis.toUserList(bpCode, myDate);
        });
		// 设置用户所选择的漏斗
        var tabs = window.parent.mini.get("mainTabs"),funnelListTab;
        // 找出‘数据埋点设置’页刷新
        tabs.tabs.forEach(function(tab){
            if(tab.title === '漏斗列表') {
                funnelListTab = tab;
                return;
            }
        });
        if(funnelListTab) {
            // 页面、板块、按钮的字典要重新刷新
            var funnel = tabs.getTabIFrameEl(funnelListTab).contentWindow.bp_funnel.datagrid.getSelected();
            mini.get('id').setValue(funnel.id);
            mini.get('orderType').setValue(funnel.orderType);
            mini.get('dayNumber').setValue(funnel.dayNumber);
        }
        //手动触发查询
        bp_funnel_analysis.search();
    },
    toUserList : function(bpCode, myDate) {
    	Cms.addTab({
			tabs : window.parent.mini.get("mainTabs"),
			url : 'bp/user',
			title: '用户详情',
			bpCode : bpCode,
			myDate : myDate,
			callBack : bp_funnel_analysis.setUserIdsThenKLoadGrid
    	});
	},
    setUserIdsThenKLoadGrid : function(sender, op) {
        var p = window.parent,
			tabs = p.mini.get("mainTabs"),
        	tab = tabs.getActiveTab(),
			w = tabs.getTabIFrameEl(tab).contentWindow,
			userTabMini = w.mini
			;
       var userIdsStr = bp_funnel_analysis.funnelData.userIds[op.bpCode][op.myDate];
			//userIds.join(',');
        if(userIdsStr.length === 0) {
        	// 如果没有符合条件的用户id
			return;
		}
		var userIds = userIdsStr.split(',').filter(function (userId) {
			return userId != 'null';
        });
        if(userIds.length === 0) {
            // 如果没有符合条件的用户id
            return;
        }
        userIdsStr = userIds.join(',');
        userTabMini.get('userIds').setValue(userIdsStr);

        // 查询需要显示的用户名
        $.ajax({
            url : "customermgr/user/list",
            data : {type:'u.id', values:userIdsStr,pageIndex:0,pageSize:userIds.length},
            async : false
        }).done(function(data){
            w.Dic.userNames = [];
            data.data.forEach(function(user) {
                w.Dic.userNames.push({id:user.id, text:user.accountName});
            });
        }).fail(function (jqx) {
            console.error(jqx);
        });
        userTabMini.parse();
        userTabMini.get('datagrid').load({'userIds' : userTabMini.get('userIds').getValue()});

    },
	draw : function(data) {
        var ret = JSON.parse(data);
        if(ret.errorCode && ret.errorCode != Code.success){
            mini.alert(ret.message);
            return;
        }
        // 查询需要显示的埋点
        Dic.pointMap = {};
        $.ajax({
            url : "bp/point/page",
            async : false
        }).done(function(points){
            points.data.forEach(function(point) {
                Dic.pointMap[point.code]=point.name;
            });
        }).fail(function (jqx) {
            console.error(jqx);
        });

        // 保存全局变量，在点击每一步时给拆线图提供数据
        bp_funnel_analysis.funnelData = ret;
		var width=300,
		height = 100,
		centerX=150,
		totalRatio = (ret.conversionRates[0]*100).toFixed(2)+'%',
		totalRecHeight = 40,
		totalRecWidth = 100,
		stepRecHeight = 40,
		stepRecWidth = 280,
		stepRec2Height = 20,
		stepRec2Width = 80,
		stepTriangleHeight = 20,
		stepTriangleWidth = 120,
		yOffset = 8,
		fontPadding = 10,
		fontFamily = '15px Arial'
		;
        $('div#funnelTransformDiv').empty().append(
    		$("<canvas>",{id:'totalRatioCv','data-step':'whole',class:'step'})
		);
        
        var canvas = document.getElementById('totalRatioCv'),
        context = canvas.getContext("2d");
        canvas.width = width;
        canvas.height = 80;
        context.fillStyle="rgb(85,159,240)";
        context.fillRect(centerX-totalRecWidth/2, yOffset, totalRecWidth, totalRecHeight);
		context.beginPath();
		context.moveTo(centerX-totalRecWidth/2,yOffset+totalRecHeight); 
		context.lineTo(centerX+totalRecWidth/2,yOffset+totalRecHeight);
		context.lineTo(centerX,yOffset+totalRecHeight+totalRecHeight/2);
		context.fill(); //闭合形状并且以填充方式绘制出来
		context.fillStyle="white";
		context.font=fontFamily;
		context.fillText("总转化率" ,centerX/1.25, totalRecHeight-yOffset);
		context.fillText(totalRatio ,centerX/1.15, totalRecHeight+yOffset);
		
		
		var len = ret.conversionRates.length,step;
		// 为保证顺序取列用于循环map
        var bpCodes = ret.conversionInfo.columns.slice(2);
        bpCodes.forEach(function (bpCode,i) {
            step = {};
            step.name = Dic.pointMap[bpCode];
            step.peoples = ret.totalMap[bpCode];
            // 第一步和总休转化率都用都一个元素
			step.ratio = ret.conversionRates[i];
			if(i > 0 ) {
                $('div#funnelTransformDiv').append(
                    $("<canvas>",{class:'step',id:'step'+i,'data-step':bpCode})
                );
			}
            else {
				// 如果是第一步,则显示总体转化率
                $('div#funnelTransformDiv').append(
                    $("<canvas>",{class:'step',id:'step'+i,'data-step':'whole'})
                );
			}
            canvas = document.getElementById('step'+i),
                context = canvas.getContext("2d");

            if(i > 0) {
                canvas.width = width;
                canvas.height = height;
                context.fillStyle="rgb(234,239,244)";
                //context.fillStyle="red";
                context.fillRect(centerX-stepRec2Width/2,0, stepRec2Width, stepRec2Height);
                context.beginPath();
                context.moveTo(centerX-stepTriangleWidth/2, stepRec2Height);
                context.lineTo(centerX+stepTriangleWidth/2, stepRec2Height);
                context.lineTo(centerX,stepRec2Height+stepTriangleHeight);
                context.fill(); //闭合形状并且以填充方式绘制出来
                context.fillStyle="black";
                context.font=fontFamily;
                context.fillText((step.ratio*100).toFixed(2)+'%' ,centerX/1.25, stepRec2Height+3);

                context.fillStyle="rgb(248,248,248)";
                context.fillRect(centerX-stepRecWidth/2, stepRec2Height+stepTriangleHeight+yOffset, stepRecWidth, stepRecHeight);
                context.fillStyle="black";
                context.font= fontFamily;
                context.fillText(String.format("第{0}步-{1}",bp_funnel_analysis.numbers[i],step.name) ,centerX-stepRecWidth/2+fontPadding, stepRec2Height+stepTriangleHeight+stepRecHeight/1.1);
                context.fillText(step.peoples+'人' ,centerX+stepRecWidth/3, stepRec2Height+stepTriangleHeight+stepRecHeight/1.1);
            } else {
                canvas.width = width;
                canvas.height = 50;
                context.fillStyle="rgb(248,248,248)";
                context.fillRect(centerX-stepRecWidth/2, 0, stepRecWidth, stepRecHeight);
                context.fillStyle="black";
                context.font= fontFamily;
                context.fillText(String.format("第{0}步-{1}",bp_funnel_analysis.numbers[i],step.name) ,centerX-stepRecWidth/2+fontPadding, stepRecHeight/1.5);
                context.fillText(step.peoples+'人' ,centerX+stepRecWidth/3, stepRecHeight/1.5);
			}
        });

        // ret.totalMap.forEach(function(step, i) {});
        bp_funnel_analysis.updateChart('whole');
		// 设置转化详情
        bp_funnel_analysis.setTransformDetail(ret.conversionInfo);
	},
    setTransformDetail : function(data) {
		var g = bp_funnel_analysis.datagrid,
		gridRows ,stepColumns = [],columns=[],tmp;
		// 第1个，第2个元素分别表示"日期"、"总体转化情况",所以不用取
        stepColumns = data.columns.slice(2);
        // 设置表头
        // 日期列
        columns.push({ field: "column0", headerAlign: "center", header: "日期",align:"center"});
        // “总体转化情况”列
        columns.push({ field: "columnX", headerAlign: "center", header: "总体转化情况",align:"right"});
        stepColumns.forEach(function(step, i) {
        	if(i === 0) {
                columns.push( { field: "columnY", headerAlign: "center", header: String.format('第{0}步（{1}）','一',Dic.pointMap[step]),align:"right"});
			} else {
                columns.push( { field: "column"+(i+1), headerAlign: "center", header: String.format('第{0}步（{1}）',bp_funnel_analysis.numbers[i],Dic.pointMap[step]),align:"right"});
			}

        });
        g.set({
            columns: columns
        });

        // 返回的原始数据中，rows每行中的第2个元素是"总体转化率,第一步的转化人数"，所以要拆成两项以显示到两列中
        gridRows = data.rows;
        gridRows.forEach(function (row) {
			// 把第2个元素拆成column1.1,column1.2
			for(var key in row) {
                tmp = row[key].split(',');
				if(key == 'column1') {
                    row['columnX'] = (tmp[0]*100).toFixed(2)+'%';
                    // 第3列
                    row['columnY'] = String.format('<a data-index={0}>{1}</a>',2,tmp[1]);
                    delete row[key];
				} else if(key !== 'column0'){
                    var index = Number.parseInt(key.substr(key.length-1))+1;
					row[key] = '<a>'+tmp[1] + '</a><br/>' + (tmp[0]*100).toFixed(2)+'%'
                    row[key] = String.format('<a data-index={0}>{1}</a><br/>{2}',index,tmp[1],(tmp[0]*100).toFixed(2)+'%');
				}
			}
        })
        // 设置分页数据
        g.setTotalCount(gridRows.length);
        g.setPageIndex(0);
        g.setPageSize(gridRows.length);
        g.setData(gridRows);
	},
	whenChangeFunnle : function(e) {
		var id = e.selected.id
		$.get('bp/funnel/one',{id : id})
		.done(function(data) {
			mini.get('orderType').setValue(data.orderType);
			mini.get('dayNumber').setValue(data.dayNumber);
		})
		.fail(function(x) {
			console.error(x);
		});
	},
	toAdd : function(e) {
		var tabDom = window.parent.mini.get('mainTabs'),
		tab;
		tabDom.tabs.forEach(function(tabTmp) {
			if(tabTmp.title === '漏斗列表') 
				tab = tabTmp;
		});
		if(tab) {
			tabDom.activeTab(tab);
			tabDom.getTabIFrameEl(tab).contentWindow.bp_funnel.toAdd();
		}
	},
	addCondition : function() {
		var $steps = $('div#form1 table tr.codition-tr'),
		n = $steps.length+1;
		if(n>3) {
			mini.alert('查询条件最多可新增三条');
			return;
		}
		var myCriteriaName = 'criteriaName'+n;
		var myCriteriaOpt = 'criteriaOpt'+n;
		var myCriteriaValue = 'criteriaValue'+n;
		$step = $('<tr>',{class:'codition-tr'})
			.append(
					$('<td>')
					.append($('<input>',{
					name:myCriteriaName, id:myCriteriaName, class:'mini-combobox',emptyText:'请选择',
					valueFromSelect:true,oncloseclick:'Cms.onCloseClick'
					}).after('&nbsp;&nbsp;'))
					.append($('<input>',{
						name:myCriteriaOpt, id:myCriteriaOpt, class:'mini-combobox',emptyText:'请选择',
						valueFromSelect:true,oncloseclick:'Cms.onCloseClick'
					}).after('&nbsp;&nbsp;'))
					.append($('<input>',{
						name:myCriteriaValue, id:myCriteriaValue,class:'mini-textbox',emptyText:'请输入',
						oncloseclick:'Cms.onCloseClick'
					}).after('&nbsp;&nbsp;'))
					.append($('<span>',{class:'closeStep'}).text('x'))
				);
		$('tr#afterSearchTr').before($step);
		mini.parse();
		var criteriaNameDom = mini.get(myCriteriaName);
		var criteriaOptDom = mini.get(myCriteriaOpt);
		criteriaNameDom.setData(bp_comm.funnelSearchName);
		criteriaOptDom.setData(bp_comm.funnelSearchOpt);
	},
	search:function(){
		var param = new mini.Form('form1').getData(true),
		criterions = [],n;
		for(var key in param) {
			if(key.indexOf('criteriaName')>-1) {
				n = key.substring('criteriaName'.length);
				var criteria = {
					name : mini.get(key).getValue(),
					opt : mini.get('criteriaOpt'+n).getValue(),
					value : mini.get('criteriaValue'+n).getValue()
				};
				criterions.push(criteria);
			}
		}
		param.criterions = criterions;
		$.ajax({
			headers: { 
				'Accept': 'application/json',
				'Content-Type': 'application/json' 
			},
			url : 'bp/funnel/analysis/search',
			data : JSON.stringify(param),
			type : 'POST',
		}).done(
			function(data) {
				bp_funnel_analysis.draw(data);
		}).fail(
			function(jqXHR, textStatus, errorThrown) {
				console.error(jqXHR);
			}
		);
		
	},
	updateChart : function(bpCode) {
		//投注用户
		var funnelChart = echarts.init(document.getElementById('funnelChart'));
        var xAxis = bp_funnel_analysis.funnelData.conversionDateRates[bpCode].date,
			yAxis = bp_funnel_analysis.funnelData.conversionDateRates[bpCode].rates.map(function (rate) {
				return (rate*100).toFixed(2)
            }),
            name = (Dic.pointMap[bpCode]||'总体')+' 转化率';
		var funnelChartOption = {
			    color: ['#3398DB'],
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    // toolbox: {
			    //     feature: {
			    //         dataView: {show: true, readOnly: true},
			    //         magicType: {show: true, type: ['line', 'bar']},
			    //         restore: {show: true},
			    //         saveAsImage: {show: true}
			    //     }
			    // },
                legend: {
                    data:[name],
					bottom:0,
                    selectedMode : false
                },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '8%',
			        containLabel: true
			    },
			    xAxis : [
			        {
			            type : 'category',
			            data : xAxis,
			            axisTick: {
			                alignWithLabel: true
			            }
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
                        axisLabel: {
                            formatter: '{value}%'
                        }
                    }
			    ],
			    series : [
			        {
			            name:name,
			            type:'line',
			            barWidth: '60%',
			            itemStyle: {
			                normal: {                   // 系列级个性化，横向渐变填充
			                    borderRadius: 5,
			                    label : {
			                        show : true,
			                        textStyle : {
			                            fontSize : '15',
			                            fontFamily : '微软雅黑',
			                            fontWeight : 'bold',
			                            color:'red'
			                        },
			                        position:'top'
			                    }
			                }
			            },
			            data:yAxis,
                        label: {
			            	normal: {formatter: '{c}%'},
						},
			        }
			    ]
			};
		////可选，是否不跟之前设置的option进行合并，默认为false，即合并。
        funnelChart.setOption(funnelChartOption,false);
	}
}
bp_funnel_analysis.init();