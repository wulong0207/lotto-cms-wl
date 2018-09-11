/**
 * Created by ligs on 2018/8/17.
 * 请求记录管理
 */
request_log = {
    init: function () {
        mini.parse();
        request_log.payType = mini.get("payType");
        request_log.platform = mini.get("platform");
        request_log.form = new mini.Form("form");
        request_log.grid = mini.get("grid");

        $.get("sysmgr/dic/dictionary?code=9013,9014", function (result) {
            Dic.platform = result["9014"];
            Dic.payType = result["9013"];

            request_log.payType.setData(Dic.payType);
            request_log.platform.setData(Dic.platform);
            request_log.search();
        });

    },
    search: function () {
        request_log.grid.load(request_log.form.getData(true));
    },
}
request_log.init();
