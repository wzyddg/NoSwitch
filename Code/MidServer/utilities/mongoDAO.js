'use strict';
var mongoClient = require('mongodb').MongoClient;
var protocol = 'mongodb';

var host = 'localhost';
var port = 27017;
var database = 'wzy_gd';

exports.insert = function (query, page, content) {
    var authedDB = null;
    var url = protocol+'://'+host+':'+port+'/'+database;
    return mongoClient.connect(url).then((db) => {
        authedDB = db;
        return db.authenticate("wzy","wzyddg");
    }).then((result)=> {
        return authedDB.collection("Cache").insertOne({"query": query, "page": page, "content": content});
    }).then((result)=> {
        authedDB.close();
        return result;
    }).catch((error)=>{
        return error;
    })
};

exports.update = function (query, page, content) {
    var authedDB = null;
    var url = protocol+'://'+host+':'+port+'/'+database;
    return mongoClient.connect(url).then((db) => {
        authedDB = db;
        return db.authenticate("wzy","wzyddg");
    }).then((result)=> {
        return authedDB.collection("Cache").updateMany({"query": query, "page": page}, {$set:{"content": content}});
    }).then((result)=> {
        authedDB.close();
        return result;
    }).catch((error)=>{
        return error;
    })
};

exports.find = function (query, page) {
    var authedDB = null;
    var url = protocol+'://'+host+':'+port+'/'+database;
    return mongoClient.connect(url).then((db) => {
        authedDB = db;
        return db.authenticate("wzy","wzyddg");
    }).then((result)=> {
        return authedDB.collection("Cache").findOne({"query": query, "page": page});
    }).then((result)=> {
        authedDB.close();
        return result;
    }).catch((error)=>{
        return error;
    })
};

exports.findAll = function () {
    var authedDB = null;
    var url = protocol+'://'+host+':'+port+'/'+database;
    return mongoClient.connect(url).then((db) => {
        authedDB = db;
    return db.authenticate("wzy","wzyddg");
    }).then((result)=> {
        return authedDB.collection("Cache").find({}).toArray();
    }).then((result)=> {
        authedDB.close();
        return result;
    }).catch((error)=>{
        return error;
    })
};