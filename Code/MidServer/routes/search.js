var express = require('express');
var router = express.Router();

router.get("/",function (req, res, next) {
    var query = req.query.q;
    var page = req.query.p;
    res.send(req.query);
});

module.exports = router;