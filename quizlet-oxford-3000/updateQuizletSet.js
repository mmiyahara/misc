const words = require('./wordsFlatten');
const http = require('http');
const puppeteer = require('puppeteer');
const rp = require('request-promise');

http.createServer((_, res) => {
  res.writeHead(200, {'Content-Type': 'text/plain'});
  res.end('You should be redirected here.\n');
}).listen(1337, '127.0.0.1');

const clientId = process.env.QUIZLET_CLIENT_ID;
const secret = process.env.QUIZLET_SECRET;
const email = process.env.QUIZLET_GMAIL_ADDRESS;
const password = process.env.QUIZLET_GMAIL_PASSWORD;

const sleep = async(ms) => {
  return new Promise(resolve => setTimeout(resolve, ms));
};

const base64encode = (string) => {
  return Buffer.from(string).toString('base64');
};

const getCode = async(clientId) => {
  const RANDOM_STRING = 'RANDOM_STRING'; // TODO: generate random string on each time
  const url = `https://quizlet.com/authorize?response_type=code&client_id=${clientId}&scope=write_set&state=${RANDOM_STRING}`;

  console.log('Quizlet Code is being generated...');
  const browser = await puppeteer.launch({headless: false});
  const page = await browser.newPage();
  await page.goto(url, {waitUntil: 'networkidle0'});

  await Promise.all([
    page.waitForNavigation({waitUntil: 'networkidle0'}),
    page.click('.UIButton--social')
  ]);
  const inputEmail = await page.$('#identifierId');
  await inputEmail.type(email);
  await Promise.all([
    page.waitForNavigation({waitUntil: 'networkidle0'}),
    page.click('#identifierNext > content > span')
  ]);

  await sleep(3000);

  const inputPassword = await page.$('input[name=password]');
  await inputPassword.type(password);
  await Promise.all([
    page.waitForNavigation({waitUntil: 'networkidle0'}),
    page.click('#passwordNext > content')
  ]);

  await sleep(3000);

  await Promise.all([
    page.waitForNavigation({waitUntil: 'networkidle0'}),
    page.click('.UIButton')
  ]);

  await sleep(3000);
  const code = page.url().split('&')[1].split('=')[1];
  await page.close();
  await browser.close();
  console.log('Quizlet Code is generated successfully.');

  return code;
};


const getToken = async(code) => {
  const url = 'https://api.quizlet.com/oauth/token';
  const options = {
    method: 'POST',
    uri: url,
    headers: {
      Authorization: `Basic ${base64encode(`${clientId}:${secret}`)}`
    },
    body: {
      grant_type: 'authorization_code',
      code: code,
      redirect_uri: 'http://127.0.0.1:1337'
    },
    json: true
  };
  console.log('Quizlet Token is being generated...');
  return await rp(options).then(r => {
    console.log('Quizlet Token is generated successfully.');
    return r.access_token;
  });
};

const generatePutBody = (title, words) => {
  return {
    title: title,
    terms: words.map(word => word.word),
    definitions: words.map(word => word.def)
  };
};

const postSet = async(token) => {
  const url = `https://api.quizlet.com/2.0/sets`;
  const options = {
    method: 'POST',
    uri: url,
    headers: {
      Authorization: `Bearer ${token}`
    },
    body: {
      title: 'Temp title',
      terms: ['temp', 'temp'],
      definitions: ['temp def', 'temp def'],
      lang_terms: 'en',
      lang_definitions: 'en'
    },
    json: true
  };

  return await rp(options)
    .then(r => {console.log(`Set ${r.set_id} has been created.`); return r.set_id;})
    .catch(e => {console.log(`Error: ${e.message}`); process.exit();});
};

const putSet = async(token, body, setId) => {
  const url = `https://api.quizlet.com/2.0/sets/${setId}`;
  const options = {
    method: 'PUT',
    uri: url,
    headers: {
      Authorization: `Bearer ${token}`
    },
    body: body,
    json: true
  };

  await rp(options)
    .then(() => {console.log(`Set ${setId} has been updated.`);})
    .catch(e => {console.log(`Error: ${e.message}`);});
};

(async () => {
  const code = await getCode(clientId);
  const token = await getToken(code);
  const setId = await postSet(token);
  const putBody = generatePutBody('Oxford 3000 - 1', words);
  await putSet(token, putBody, setId);
  process.exit();
})();
