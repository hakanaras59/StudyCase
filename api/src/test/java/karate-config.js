function fn() {

    var config = {
        mockUrl: 'https://api.restful-api.dev',
    };
      karate.configure('connectTimeout', 70000);
      karate.configure('readTimeout', 70000);
     return config;
}
