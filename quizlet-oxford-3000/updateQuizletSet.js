const rp = require('request-promise');
const puppeteer = require('puppeteer');

const clientId = process.env.QUIZLET_CLIENT_ID;
const secret = process.env.QUIZLET_SECRET;
const email = process.env.QUIZLET_GMAIL_ADDRESS;
const password = process.env.QUIZLET_GMAIL_PASSWORD;
const setId = process.env.QUIZLET_SET_ID;

const sleep = async(ms) => {
  return new Promise(resolve => setTimeout(resolve, ms));
};

const base64encode = (string) => {
  return Buffer.from(string).toString('base64');
};

const getCode = async(clientId) => {
  const RANDOM_STRING = 'RANDOM_STRING'; // TODO: generate random string on each time
  const url = `https://quizlet.com/authorize?response_type=code&client_id=${clientId}&scope=write_set&state=${RANDOM_STRING}`;
  // TODO: Change headless to be true
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

  await sleep(2000);

  const inputPassword = await page.$('input[name=password]');
  await inputPassword.type(password);
  await Promise.all([
    page.waitForNavigation({waitUntil: 'networkidle0'}),
    page.click('#passwordNext > content')
  ]);

  await sleep(2000);

  await Promise.all([
    page.waitForNavigation({waitUntil: 'networkidle0'}),
    page.click('.UIButton')
  ]);

  await sleep(2000);
  return page.url().split('&')[1].split('=')[1];
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
      redirect_uri: 'https://example.com'
    },
    json: true
  };
  return await rp(options).then(r => r.access_token);
};


const putSet = async(setId, token, body) => {
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
  await rp(options).then(() => {console.log(`Set ${setId} has been updated.`);});
};

(async () => {
  const code = await getCode(clientId);
  const token = await getToken(code);
  // TODO: Read crawled words file and create body based on it
  const body = {
    title: 'aaa',
    terms: [
      'aaaaaa',
      'bbbbbb'
    ],
    definitions: [
      'aaaaaaaaaaaa',
      'bbbbbbbbbbbb'
    ]
  };
  await putSet(setId, token, body);
})();
