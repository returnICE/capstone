var express = require('express');
var passport = require('passport');

var router = express.Router();
var User = require('mongoose').model('User');

var LocalStrategy = require('passport-local').Strategy;
passport.use('local-login',
  new LocalStrategy({
    usernameField: 'ID',
    passwordField: 'PW',
    session:true,
    passReqToCallback: true
  },
  function(req, ID, PW, done){
    User.findOne({'ID':ID}, function(err, user){
      if(err) return done(err);
      if(!user){
        return done(null, false);
      }
      if(!user.authenticate(PW)){
        return done(null, false);
      }
      return done(null,user);
    });
  })
);
router.post('/',function(req,res,next){
    if(req.body.ID.length === 0 || req.body.PW.length === 0){
      res.json({success:false, message:"check input value"});
    }
    else{
      passport.authenticate('local-login',function(err,user,info){
        if(err) return next(err);
        if(!user) return res.json({success:false, message:"login fail"});
        req.logIn(user,function(err){
          if(err) return res.json({success:false, message:"login fail"});
          return res.json({success:true,user: user});
        });
      })(req,res,next);
    }
});
  
router.get('/logout',function(req,res){
    req.session.destroy();
    res.json({success:true});
}); // logout

module.exports = router;