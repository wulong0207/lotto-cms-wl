/**
 * Created by ligs on 2018/8/17.
 * 请求记录管理
 */
merchant = {
    init: function () {
        mini.parse();
        merchant.status = mini.get("status");
        merchant.statusSearch = mini.get("statusSearch");
        merchant.searchForm = new mini.Form("form");
        merchant.grid = mini.get("grid");
        merchant.editform = new mini.Form("editform");
        merchant.editWindow = mini.get("editWindow");
        merchant.id = mini.get("id");
        merchant.url = mini.get("url");

        $.get("sysmgr/dic/dictionary?code=0003", function (result) {
            Dic.status = result["0003"];
            merchant.statusSearch.setData(Dic.status);
            merchant.status.setData(Dic.status);
            merchant.search();
        });
    },
    search: function () {
        merchant.grid.load(merchant.getParamJson());
    },
    getParamJson: function () {
        var para = {};
        para.code = mini.get("codeSearch").getValue();
        para.company = mini.get("companySearch").getValue();
        para.status = mini.get("statusSearch").getValue();
        return para;
    },
    windowShow: function () {
        merchant.editWindow.show();
    },
    windowClose: function () {
        merchant.editform.clear();
        merchant.editWindow.hide();
    },
    submit: function () {
        var id = merchant.id.getValue();
        var url = "";

        if (id == null || id == '') {
            url = "paymgr/merchant/insert"
        } else {
            url = "paymgr/merchant/update"
        }
        merchant.url.setValue(url);
        Cms.submit(merchant.editform, function () {
            merchant.windowClose();
            merchant.grid.reload();
        });

    },
    _add: function () {
        merchant.editform.clear();
        merchant.windowShow();
    },
    _edit: function () {
        var row = merchant.grid.getSelected();
        if (row) {
            merchant.editform.setData(row);
            merchant.windowShow();
        }
    }
}
merchant.init();
