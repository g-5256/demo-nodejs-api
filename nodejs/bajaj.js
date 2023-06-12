const http = require('http');

const Mydata = {
    "name": "Gaurav Suryavanshi",
"email": "gauravsuryavanshi.dev@gmail.com",
"roll_no": "TECOMPB39"}

const secretKey = "xyxz";
const numbers = [10, 20, 30, 40, 50,50];

const count = numbers.length;
const minimum = Math.min(...numbers);
const maximum = Math.max(...numbers);
const mean = numbers.reduce((sum, num) => sum + num, 0) / count;
const sortedNumbers = [...numbers].sort((a, b) => a - b);
const median = count % 2 === 0 ? (sortedNumbers[count / 2 - 1] 
+ sortedNumbers[count / 2]) / 2 : sortedNumbers[Math.floor(count / 2)];

const modeMap = new Map();
let maxFrequency = 0;
for (const num of numbers) {
  const frequency = modeMap.get(num) + 1 || 1;
  modeMap.set(num, frequency);
  maxFrequency = Math.max(maxFrequency, frequency);
}
const mode = Array.from(modeMap.entries()).filter(([num, frequency]) => frequency === maxFrequency).map(([num]) => num);

const payload = JSON.stringify({
  Mydata,
  secretKey,
  count,
  minimum,
  maximum,
  mean,
  median,
  mode
});

const postOptions = {
  hostname: 'postman-echo.com', 
  path: '/post', 
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Content-Length': payload.length
  }
};

const postRequest = http.request(postOptions, (response) => {
  console.log('Response Code:', response.statusCode);
  console.log('Response Body:');
  response.pipe(process.stdout);
});

postRequest.on('error', (error) => {
  console.error('Error occurred during the request:', error);
});

postRequest.write(payload);
postRequest.end();
