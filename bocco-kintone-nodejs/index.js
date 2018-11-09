'use strict';

const rp = require('request-promise');
const uuid4 = require('uuid4');

const sendRequest = (url, method, headers, body) => {
  const options = {
    url: url,
    method: method,
    headers: headers,
    body: JSON.stringify(body)
  };
  return rp(options).then(r => JSON.parse(r));
};

const getRecordsInGuest = (domain, apiToken, spaceId, appId) => {
  const url = `https://${domain}/k/guest/${spaceId}/v1/records.json`;
  const method = 'GET';
  const headers = {
    'X-Cybozu-API-Token': apiToken,
    'Content-Type': 'application/json'
  };
  const body = {
    app: appId
  };
  return sendRequest(url, method, headers, body).then(r => r.records);
};

const getRandomMessage = (records) => {
  const randomInt = Math.floor(Math.random() * records.length);
  return records[randomInt].text.value;
};

const signIn = (apiKey, email, password) => {
  const url = `https://api.bocco.me/alpha/sessions?apikey=${apiKey}&email=${email}&password=${password}`;
  const method = 'POST';
  const headers = {
    'Content-Type': 'application/x-www-form-urlencoded'
  };
  const body = {};
  return sendRequest(url, method, headers, body).then(r => r.access_token);
};

const join = (token) => {
  const url = `https://api.bocco.me/alpha/rooms/joined?access_token=${token}`;
  const method = 'GET';
  const headers = {};
  const body = {};
  return sendRequest(url, method, headers, body).then(r => r[0].uuid);
};

const postMessage = (token, roomId, uuid, message) => {
  const url = `https://api.bocco.me/alpha/rooms/${roomId}/messages?media=text&text=${message}&access_token=${token}&unique_id=${uuid}`;
  const method = 'POST';
  const headers = {
    'Content-Type': 'application/x-www-form-urlencoded',
    'Accept-Language': 'ja'
  };
  const body = {};
  return sendRequest(url, method, headers, body);
};

// Need to input below before running
// kintone
const DOMAIN = 'example.cybozu.com';
const API_TOKEN = 'kintone_api_token';
const SPACE_ID = 1;
const APP_ID = 2;

// bocco
const API_KEY = 'bocco_api_key';
const EMAIL = 'hoge@example.com';
const PASSWORD = 'password';

let message;
let accessToken;
getRecordsInGuest(DOMAIN, API_TOKEN, SPACE_ID, APP_ID)
  .then(records => {
    message = getRandomMessage(records);
    return signIn(API_KEY, EMAIL, PASSWORD);
  })
  .then((token) => {
    accessToken = token;
    return join(accessToken);
  })
  .then((roomId) => {
    return postMessage(accessToken, roomId, uuid4(), message);
  })
  .then(r => {
    console.log(`${r.text} has been sent to your Bocco!`);
  });
