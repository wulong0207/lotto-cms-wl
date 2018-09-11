$(function () {
    rcmd.init();
});

rcmd = {
    init: function () {
        mini.parse();
        rcmd.type = mini.get("type");
        rcmd.passWay = mini.get("passWay");
        rcmd.from = new mini.Form("form");
        rcmd.grid = mini.get("rcmdGrid");
        ComReq.dictionary("9001,9002", function (result) {
            Dic.passWay = result["9001"];
            Dic.type = result["9002"];

            rcmd.type.setData(Dic.type);
            rcmd.passWay.setData(Dic.passWay);
        });
        // 查询
        rcmd.search();
    },
    search: function () {
        rcmd.grid.load(rcmd.from.getData());
    },
    excel: function () {

        location = "/excel?" + Cms.jsonParamStr(rcmd.from.getData());
    }

};