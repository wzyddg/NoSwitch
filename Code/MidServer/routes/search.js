var express = require('express');
var router = express.Router();
var dao = require('../utilities/MongoDAO');
var dbo = require('../utilities/DatabaseOperator');

router.get("/",function (req, res, next) {
    var query = req.query.q;
    var page = req.query.p;
    if(!page){
        page = "0";
    }

    dao.find(query, page).then((result)=>{
        if(result){
            res.writeHead(200,{'Content-Type' : 'application/json'});
            res.write(result.content);
            res.end();
        }else{
            return dbo.fetchAndInsert(query,page).then((data)=>{
                if(data.toString().indexOf('RequestError')!=-1){
                    res.writeHead(500,{'Content-Type' : 'text/plain'});
                }else {
                    res.writeHead(200,{'Content-Type' : 'application/json'});
                }
                res.write(data.toString());
                res.end();
            })
        }
    }).catch((error)=>{
        console.log(error);
        res.writeHead(500,{'Content-Type' : 'text/plain'});
        res.write({"result":"server error"});
        res.end();
    });

    for(var i=1;i<5;i++){
        checkAndFill(query, ""+(parseInt(page)+i));
    }
});

function checkAndFill(query, page) {
    return dao.find(query, page).then((result)=>{
        if(!result){
            return dbo.fetchAndInsert(query,page)
        }else {
            return result;
        }
    }).catch((error)=>{
        console.log("ERRRRRR"+error);
        return error;
    });
}

router.get("/force_fetch",function (req, res, next){
    // fetch new content. find it. if exist update, else insert. return content.
    var query = req.query.q;
    var page = req.query.p;
    if(!page){
        page = "0";
    }

    dao.find(query, page).then((result)=>{
        if(result){
            return dbo.fetchAndUpdate(query,page).then((data)=>{
                if(data.toString().indexOf('RequestError')!=-1){
                    res.writeHead(500,{'Content-Type' : 'text/plain'});
                }else {
                    res.writeHead(200,{'Content-Type' : 'application/json'});
                }
                res.write(data.toString());
                res.end();
            })
        }else{
            return dbo.fetchAndInsert(query,page).then((data)=>{
                if(data.toString().indexOf('RequestError')!=-1){
                    res.writeHead(500,{'Content-Type' : 'text/plain'});
                }else {
                    res.writeHead(200,{'Content-Type' : 'application/json'});
                }
                res.write(data.toString());
                res.end();
            })
        }
    }).catch((error)=>{
        console.log(error);
        res.writeHead(500,{'Content-Type' : 'text/plain'});
        res.write({"result":"server error"});
        res.end();
    });
});

module.exports = router;