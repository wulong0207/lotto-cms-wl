/**
 * Created by ligs on 2018/8/17.
 * 交易流水管理
 */
trans_log = {
    init: function () {
        mini.parse();
        trans_log.thirdStatus = mini.get("thirdStatus");
        trans_log.thirdType = mini.get("thirdType");
        trans_log.returnStatus = mini.get("returnStatus");
        trans_log.form = new mini.Form("form");
        trans_log.transLogGrid = mini.get("transLogGrid");

        $.get("sysmgr/dic/dictionary?code=9010,9011,9012", function (result) {
            Dic.thirdStatus = result["9010"];
            Dic.thirdType = result["9011"];
            Dic.returnStatus = result["9012"];

            trans_log.thirdStatus.setData(Dic.thirdStatus);
            trans_log.thirdType.setData(Dic.thirdType);
            trans_log.returnStatus.setData(Dic.returnStatus);
            trans_log.search();
        });

    },
    search: function () {
        trans_log.transLogGrid.load(trans_log.form.getData(true));
    },
}
trans_log.init();
