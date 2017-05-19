var dao = require('../utilities/mongoDAO');
var rp = require('request-promise');

exports.fetchAndInsert = function (query, page) {
    var https = new require('https');
    var options = {host: 'searchcode.com', port: null, path: '/api/codesearch_I/', method: 'GET'};
    var url = "https://" + options.host + options.path + "?q=" + encodeURIComponent(query) + "&p=" + encodeURIComponent(page);
    return rp(url).then((data)=>{
        if(JSON.parse(data).page == page)
            dao.insert(query, page, data);
        return data;
    }).catch(function (err) {
        return err;
    });
}

exports.fetchAndUpdate = function (query, page) {
    var https = new require('https');
    var options = {host: 'searchcode.com', port: null, path: '/api/codesearch_I/', method: 'GET'};
    var url = "https://" + options.host + options.path + "?q=" + encodeURIComponent(query) + "&p=" + encodeURIComponent(page);
    return rp(url).then((data)=>{
        return dao.update(query, page, data).then((result)=>{
            return data;
        });
    }).catch(function (err) {
        return err;
    });
}

exports.fetchAndUpdateAll = function () {
    dao.findAll().then((data)=>{
        for(var i = 0;i< data.length;i++){
            this.fetchAndUpdate(data[i].query,data[i].page);
        }
    }).catch(function (err) {
        console.log("update all failed. See err:"+err);
    });
}
