var express = require('express');
var router = express.Router();
var dao = require('../utilities/MongoDAO');
var dbo = require('../utilities/DatabaseOperator');

router.get("/one",function (req, res, next){
    // fetch new content. find it if exist update, else nothing, return status
    var query = req.query.q;
    var page = req.query.p;
    if(!page){
        page = "0";
    }

    dbo.fetchAndUpdate(query,page).then((result)=>{
        if(!result && result==={} && result === '{}'){
            res.send({"result":"failed"});
        }else{
            res.send({"result":"successful"});
        }
    });
});

router.get("/all",function (req, res, next){
    // find all content and update them , return the status first
    dbo.fetchAndUpdateAll();
    res.send({"result":"started"});
});

module.exports = router;