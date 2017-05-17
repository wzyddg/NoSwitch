var dao = require('../utilities/mongoDAO');
var rp = require('request-promise');

exports.fetchAndInsert = function (query, page) {
    var https = new require('https');
    var options = {host: 'searchcode.com', port: null, path: '/api/codesearch_I/', method: 'GET'};
    var url = "https://" + options.host + options.path + "?q=" + encodeURIComponent(query) + "&p=" + encodeURIComponent(page);
    return rp(url).then((data)=>{
        dao.insert(query, page, data);
        return data;
    }).catch(function (err) {
        return err;
    });
}