'use strict';
var mongoClient = require('mongodb').MongoClient;
var protocol = 'mongodb';

var host = 'localhost';
var port = 27017;
var database = 'wzy_gd';

var authedDB = null;

exports.insert = function (query, page, content) {
    var url = protocol+'://'+host+':'+port+'/'+database;
    return mongoClient.connect(url).then((db) => {
        authedDB = db;
        return db.authenticate("wzy","wzyddg");
    }).then((result)=> {
        return authedDB.collection("Cache").insertOne({"query": query, "page": page, "content": content});
    }).catch((error)=>{
        return error;
    })
};

exports.update = function (query, page, content) {
    var url = protocol+'://'+host+':'+port+'/'+database;
    return mongoClient.connect(url).then((db) => {
        authedDB = db;
        return db.authenticate("wzy","wzyddg");
    }).then((result)=> {
        return authedDB.collection("Cache").updateMany({"query": query, "page": page}, {$set:{"content": content}});
    }).catch((error)=>{
        return error;
    })
};

exports.find = function (query, page) {
    var url = protocol+'://'+host+':'+port+'/'+database;
    return mongoClient.connect(url).then((db) => {
        authedDB = db;
        return db.authenticate("wzy","wzyddg");
    }).then((result)=> {
        return authedDB.collection("Cache").findOne({"query": query, "page": page});
    }).catch((error)=>{
        return error;
    })
};