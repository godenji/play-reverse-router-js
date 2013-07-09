
(function(_root){
var _nS = jRouter.path2Method
var _qS = jRouter.qString
var _s 	= jRouter.secure
var _wA = jRouter.route  

_nS('foo.Foo.qString');
_root.foo.Foo.qString =

      function(arg1,arg2) {
      return _wA({method:"GET", url:"/foo/" + "qstring" + _qS([(function(k,v){return v!=null?(function(k,v) {return encodeURIComponent(k)+'='+encodeURIComponent(v)})(k,v):''})("arg1", arg1), (function(k,v){return v!=null?(function(k,v) {return encodeURIComponent(k)+'='+encodeURIComponent(v)})(k,v):''})("arg2", arg2)])});
      };
   

_nS('foo.Foo.index');
_root.foo.Foo.index =

      function() {
      return _wA({method:"GET", url:"/foo/" + "index"});
      };
   

_nS('bar.Bar.qString');
_root.bar.Bar.qString =

      function(arg1,arg2) {
      return _wA({method:"GET", url:"/bar/" + "qstring" + _qS([(function(k,v){return v!=null?(function(k,v) {return encodeURIComponent(k)+'='+encodeURIComponent(v)})(k,v):''})("arg1", arg1), (function(k,v){return v!=null?(function(k,v) {return encodeURIComponent(k)+'='+encodeURIComponent(v)})(k,v):''})("arg2", arg2)])});
      };
   

_nS('bar.Bar.index');
_root.bar.Bar.index =

      function() {
      return _wA({method:"GET", url:"/bar/" + "index"});
      };
   

})(jRouter.root);

