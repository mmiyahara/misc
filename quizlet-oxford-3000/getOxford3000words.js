const fs = require('fs');
const puppeteer = require('puppeteer');

let count = 0;

const sleep = async(ms) => {
  return new Promise(resolve => setTimeout(resolve, ms));
};

/**
 * Oxford 3000 英単語を取得する関数
 * @param {Browser} browser puppeteer.launchで作成されるBrowserオブジェクト
 * @param {string} url - 単語の一覧が表示されるurl
 *                       e.g. https://www.oxfordlearnersdictionaries.com/wordlist/english/oxford3000/Oxford3000_A-B/?page=1
 * @return {Object} {word, link, meaning} を持つオブジェクト
 */
const fetchWords = async (browser, url) => {
  const page = await browser.newPage();
  try {
    await page.goto(url, {waitUntil: 'networkidle2'});
  } catch (err) {
    console.log(`Failed to fetch from ${url}. Try again...`);
    await sleep(10000);
    await page.goto(url, {waitUntil: 'networkidle2'});
  }
  console.log(`Fetching from ${url}...`);

  // 単語とリンクを取得する
  const data = await page.evaluate(() => {
    return [...Array(100)].map((_, i) => {
      const wordSelector = `#entrylist1 > ul > li:nth-child(${i + 1}) > a`;
      const word = document.querySelector(wordSelector);

      return (word)
        ? {
          word : word.innerText,
          link : word.href,
          def: ''}
        : null;
    }).filter(d => d);
  });

  // 取得したリンクにアクセスし、単語の最初の意味を取得する
  for (const d of data) {
    console.log(`[${++count}/3000]  Feaching the meaning of ${d.word}...`);
    try {
      await page.goto(d.link, {waitUntil: 'networkidle2'});
      d.def = await page.evaluate(() => {
        const defSelector = '.def';
        const def =  document.querySelector(defSelector);
        return (def) ? def.innerHTML : 'Failed to fetch!';
      });
    } catch (err) {
      console.log(err.message);
    }
  }

  await page.close();
  return data;
};

const host = 'https://www.oxfordlearnersdictionaries.com/wordlist/english/oxford3000/Oxford3000_';
const targets = [
  'A-B/?page=1', 'A-B/?page=2',
  'A-B/?page=3', 'A-B/?page=4', 'A-B/?page=5',
  'C-D/?page=1', 'C-D/?page=2', 'C-D/?page=3', 'C-D/?page=4', 'C-D/?page=5', 'C-D/?page=6', 'C-D/?page=7',
  'E-G/?page=1', 'E-G/?page=2', 'E-G/?page=3', 'E-G/?page=4', 'E-G/?page=5', 'E-G/?page=6',
  'H-K/?page=1', 'H-K/?page=2', 'H-K/?page=3', 'H-K/?page=4',
  'L-N/?page=1', 'L-N/?page=2', 'L-N/?page=3', 'L-N/?page=4',
  'O-P/?page=1', 'O-P/?page=2', 'O-P/?page=3', 'O-P/?page=4', 'O-P/?page=5',
  'Q-R/?page=1', 'Q-R/?page=2', 'Q-R/?page=3',
  'S/?page=1', 'S/?page=2', 'S/?page=3', 'S/?page=4', 'S/?page=5',
  'T/?page=1', 'T/?page=2', 'T/?page=3',
  'U-Z/?page=1', 'U-Z/?page=2', 'U-Z/?page=3'
];
const urls = targets.map((target) => host + target);

puppeteer.launch().then(async browser => {
  let words = [];
  for (const url of urls) {
    words.push(await fetchWords(browser, url));
  }
  await browser.close();
  await fs.writeFile('words.js', JSON.stringify(words, null, '  '));
});
