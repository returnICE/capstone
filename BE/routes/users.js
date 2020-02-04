var express = require('express');
var router = express.Router();

var User = require('mongoose').model('User');
var async = require('async');
var mongoose = require('mongoose');

router.post('/', checkUserRegValidation, function(req, res, next){
  User.create(req.body, function(err,user){
    if(err) return res.json({success: false, message: err});
    res.json({success:true});
  });
});// user 생성

router.get('/:id', isLoggedIn, function(req, res, next) {
  User.findById(req.params.id,function(err,user){
    if(err) return res.json({success: false, message: err});
    res.json({success:true, user: user});
  })
}); // user 정보 보기

router.put('/:id', isLoggedIn, checkUserRegValidation, function(req, res){
  User.findById(req.params.id, req.body, function(err,user){
    if(err) return res.json({success: false, message: err});
    if(user.authenticate(req.body.PW)){
      if(req.body.newPW){
        req.body.PW = user.hash(req.body.newPW);
      } else{
        delete req.body.PW;
      }
      User.findByIdAndUpdate(req.params.id, req.body, function(err, user){
        if(err) return res.json({success: false, message: err});
        res.json({success:true, result:req.body});
      });
    } else{
      res.json({success:false, message:"Check ID or Password"});
    }
  });
}); // user 정보 수정

module.exports = router;

function checkUserRegValidation(req,res,next){//중복 확인
  var isValid=true;

  async.waterfall(
      [function(callback){
          User.findOne({ID: req.body.ID, _id:{$ne: mongoose.Types.ObjectId(req.params.id)}},
              function(err,user){
                  if(user){
                      isValid=false;
                  }
                  callback(null,isValid);
              }
          );
      }, function(isValid,callback){
          User.findOne({phone: req.body.phone, _id:{$ne:mongoose.Types.ObjectId(req.params.id)}},
              function(err,user){
                  if(user){
                      isValid=false;
                  }
                  callback(null,isValid);
              }
          );
      }], function(err,isValid){
          if(err) return res.json({success:false, message:err});
          if(isValid){
              return next();
          } else{
              res.json({success:false, message:"already ID or email"});
          }
      }
  );
}
function isLoggedIn(req, res, next){
  if(req.isAuthenticated()){
    return next();
  }
  res.json({success:false, message:"required login"});
}