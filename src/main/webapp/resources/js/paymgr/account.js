/**
 * Created by ligs on 2018/8/17.
 * 请求记录管理
 */
account = {
    init: function () {
        mini.parse();
        account.type = mini.get("type");
        account.status = mini.get("status");
        account.typeSearch = mini.get("typeSearch");
        account.statusSearch = mini.get("statusSearch");
        account.searchForm = new mini.Form("form");
        account.grid = mini.get("grid");
        account.editform = new mini.Form("editform");
        account.editWindow = mini.get("editWindow");
        account.id = mini.get("id");
        account.url = mini.get("url");

        $.get("sysmgr/dic/dictionary?code=9013,0003", function (result) {
            Dic.status = result["0003"];
            Dic.type = result["9013"];
            account.typeSearch.setData(Dic.type);
            account.statusSearch.setData(Dic.status);
            account.type.setData(Dic.type);
            account.status.setData(Dic.status);
            account.search();
        });
    },
    search: function () {
        account.grid.load(account.getParamJson());
    },
    getParamJson: function () {
        var para = {};
        para.getAccount = mini.get("getAccountSearch").getValue();
        para.companyCode = mini.get("companyCodeSearch").getValue();
        para.companyName = mini.get("companyName").getValue();
        para.status = mini.get("statusSearch").getValue();
        para.phone = mini.get("phoneSearch").getValue();
        para.getName = mini.get("getNameSearch").getValue();
        para.type = mini.get("typeSearch").getValue();
        para.cardCode = mini.get("cardCodeSearch").getValue();
        return para;
    },
    windowShow: function () {
        account.editWindow.show();
    },
    windowClose: function () {
        account.editform.clear();
        account.editWindow.hide();
    },
    submit: function () {
        var id = account.id.getValue();
        var url = "";

        if (id == null || id == '') {
            url = "paymgr/account/insert"
        } else {
            url = "paymgr/account/update"
        }
        account.url.setValue(url);
        Cms.submit(account.editform, function () {
            account.windowClose();
            account.grid.reload();
        });

    },
    _add: function () {
        account.editform.clear();
        account.windowShow();
    },
    _edit: function () {
        var row = account.grid.getSelected();
        if (row) {
            account.editform.setData(row);
            account.windowShow();
        }
    }
}
account.init();
