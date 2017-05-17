var express = require('express');
var mongoClient = require('mongodb').MongoClient;
var router = express.Router();
var test = require("assert");
var dao = require('../utilities/mongoDAO');
var url=require("url");

/* GET users listing. */
var authedDB = null;

router.get('/', function(req, res, next) {
    // dao.insert("222","3dd",{"you":["are","66666"]}).then((data)=>{
        dao.findAll().then((data)=>{
        if(data){
            res.send(data);
        }else{
            res.send("gg");
        }
    });
});

router.get('/aa', function(req, res, next) {
    var url = 'mongodb://localhost:27017/wzy_gd';

    mongoClient.connect(url)
        .then((db) => {
        console.log("connect OK!");
    authedDB = db;
    return db.authenticate("wzy","wzyddg");
}).then((result)=>{
        console.log("authenticate OK!")
    console.log(result)
    return authedDB.collection("student").insertOne({ "name": "筱原明里", "age": "18" });
}).then((result)=>{
        authedDB.close();
    res.send(result);
}).catch ((error)=>{
        console.log("error catched!");
    console.log(error)
    res.send('err!');
})
});

module.exports = router;
