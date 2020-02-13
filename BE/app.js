var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var bodyParser = require('body-parser');
var methodOverride = require('method-override');
var session = require('express-session');
var passport = require('passport');
var flash = require('connect-flash');

//DB 연결
var mongoose = require('mongoose');
mongoose.connect("mongodb+srv://turrymall:turrymall@cluster0-7wnzw.mongodb.net/test?retryWrites=true&w=majority");
var db = mongoose.connection;
db.once("open",function(){
  console.log("DB connected");
});
db.on("error",function(err){
  console.log("DB ERROR : ",err);
});

require('./models/user.js');

//라우팅
var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var loginRouter = require('./routes/login');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(bodyParser.json());
app.use(methodOverride("_method"));
app.use(session({
  key:'sid',
  secret:'TURRYMALL',
  resave: false,
  saveUninitialized: true,
  cookie:{
    maxAge: 24000*60*60
  }
})); //세션 설정
app.use(passport.initialize());
app.use(passport.session());
app.use(flash());

app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/login', loginRouter);

var User = require('mongoose').model('User');
passport.serializeUser(function(user,done){
  done(null,user.id);
}); // session 생성 시 user개체의 id(DB의 id)를 저장

passport.deserializeUser(function(id,done){
  User.findById(id,function(err,user){
    done(err,user);
  });
}); // session으로부터 개체 가져올 때 id를 넘겨받아서 DB에서 user찾음

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
