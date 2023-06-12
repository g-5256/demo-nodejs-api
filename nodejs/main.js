const http = require('http');

const payload = {
  "name": "Gaurav Suryavanshi",
  "email": "gauravsuryavanshi.dev@gmail.com",
  "roll_no": "TECOMPB39"
};

const post_options = {
  hostname: 'postman-echo.com',
  method: "POST",
  path: "/post",
  headers: {
    "Content-Type": "application/json"
  }
};

const post_request = http.request(post_options, (response) => {
  console.log('Response Code:', response.statusCode);
  console.log('Response Body:');
  response.pipe(process.stdout);
});

// post_request.on('error', (err) => {});

post_request.end(JSON.stringify(payload));
