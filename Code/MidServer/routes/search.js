var express = require('express');
var router = express.Router();
var dao = require('../utilities/MongoDAO');
var dbo = require('../utilities/DatabaseOperator');

router.get("/",function (req, res, next) {
    var query = req.query.q;
    var page = req.query.p;
    if(!page){
        page = 0;
    }

    res.writeHead(200,{'Content-Type' : 'application/json'});
    dao.find(query, page).then((result)=>{
        if(result){
            res.write(result.content);
            res.end();
        }else{
            return dbo.fetchAndInsert(query,page).then((data)=>{
                res.write(data);
                res.end();
            })
        }
    }).catch((error)=>{
        console.log(error);
        res.write({"status":"server error"});
        res.end();
    });
});

module.exports = router;