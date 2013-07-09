jRouter = {
  root: {},
  append: function(ob) {
    if (ob == null) {
      ob = {};
    }
    return jRouter.root += ob;
  },
  path2Method: function(c, f, b) {
    var a, d, e, g;

    e = c.split(f || ".");
    g = b || jRouter.root;
    d = void 0;
    a = void 0;
    d = 0;
    a = e.length;
    while (d < a) {
      g = g[e[d]] = g[e[d]] || {};
      d++;
    }
    return g;
  },
  qString: function(items) {
    var i, qs;

    qs = "";
    i = 0;
    while (i < items.length) {
      if (items[i]) {
        qs += (qs ? "&" : "") + items[i];
      }
      i++;
    }
    if (qs) {
      return "?" + qs;
    } else {
      return "";
    }
  },
  secure: function(p, s) {
    return p + (s === true || (s && s.secure) ? "s" : "") + "://";
  },
  route: function(r) {
    return {
      ajax: function(c) {
        c = c || {};
        c.url = r.url;
        c.type = r.method;
        return jQuery.ajax(c);
      },
      method: r.method,
      type: r.method,
      url: r.url,
      go2: location.href = r.url,
      absoluteURL: function(s) {
        return secure("http", s) + "www.domain.com" + r.url;
      },
      webSocketURL: function(s) {
        return secure("ws", s) + "www.domain.com" + r.url;
      }
    };
  }
};
router = jRouter.root;
