const puppeteer = require('puppeteer');

/**
 * NOTE:
 * If needed to change download folder, the following link might be a help
 * https://github.com/xolvio/chimp/issues/679#issuecomment-406530885
 */

const Settings = {
  // Might need to extend TIMEOUT if CSV filesize is large
  TIMEOUT: 60000,
  DOMAIN: 'example',
  USER_ID: 'user',
  USER_PASSWORD: 'password',
  WAIT_UNTIL: 'networkidle0'
};

const DURATION = {
  begin: {
    month: 1,
    day: 1,
    year: 2018
  },
  end: {
    month: 1,
    day: 31,
    year: 2018
  }
};

const specifyDuration = async (duration, page) => {
  const _initTime = async () => {
    const SELECTORS = [
      'select[name="SetDate.Year"]',
      'select[name="SetDate.Month"]',
      'select[name="SetDate.Day"]',
      'select[name="EndDate.Year"]',
      'select[name="EndDate.Month"]',
      'select[name="EndDate.Day"]'
    ];
    for (const selector of SELECTORS) {
      const elmBeginTimeSelect = await page.$(selector);
      await elmBeginTimeSelect.click();
      [...Array(100)].forEach(async () => {
        await page.keyboard.press('ArrowUp');
      });
      await page.keyboard.press('Enter');
    }
  };

  const _specifyTime = async (selector, value) => {
    const elmBeginTimeSelect = await page.$(selector);
    await elmBeginTimeSelect.click();
    [...Array(value)].forEach(async () => {
      await page.keyboard.press('ArrowDown');
    });
    await page.keyboard.press('Enter');
  };

  await _initTime();
  await _specifyTime('select[name="SetDate.Year"]', duration.begin.year - 2000);
  await _specifyTime('select[name="SetDate.Month"]', duration.begin.month);
  await _specifyTime('select[name="SetDate.Day"]', duration.begin.day);
  await _specifyTime('select[name="EndDate.Year"]', duration.end.year - 2000);
  await _specifyTime('select[name="EndDate.Month"]', duration.end.month);
  await _specifyTime('select[name="EndDate.Day"]', duration.end.day);
};

(async function() {
  const URL = `https://${Settings.DOMAIN}.cybozu.com/m/`;
  const launchOptions = {
    // If you want to run in the background (e.g. as a batch program), change the value of 'headless' to 'true'
    // If you want to look into the behavior, change the value of 'headless' to 'false'
    headless: false,
    args: ['--window-size=1500,1200'],
    slowMo: 0,
  };

  const browser = await puppeteer.launch(launchOptions);
  const page = await browser.newPage();
  await page.setViewport({width: 1500, height: 1200});

  // Open a login page
  await page.goto(URL, {waitUntil: Settings.WAIT_UNTIL, timeout: Settings.TIMEOUT});

  // Login
  const elmLoginName = await page.$('#username-\\3a 0-text');
  await elmLoginName.type(Settings.USER_ID);
  const elmPassword = await page.$('#password-\\3a 1-text');
  await elmPassword.type(Settings.USER_PASSWORD);
  await page.keyboard.press('Enter');
  await page.waitForNavigation({waitUntil: Settings.WAIT_UNTIL});

  // Click 「ファイルを出力」 button
  const elmExportFilePage = await page.$('.mail-index-menu-item .button-standard:nth-child(3)');
  await elmExportFilePage.click();
  await page.waitForNavigation({waitUntil: Settings.WAIT_UNTIL});

  // Specify the output duration
  await specifyDuration(DURATION, page);

  // Click 「書き出す」 button
  /**
   * NOTE:
   * I used Settings.TIMEOUT to wait for the completion of download
   * because Puppeteer doesn't have an API to watch the completion of file download.
   * There is a workaround for this. Check below for details.
   * https://github.com/GoogleChrome/puppeteer/issues/299#issuecomment-406271283
   */
  await page.click('.inputButton[value="書き出す"]');
  await page.waitFor(Settings.TIMEOUT);

  await browser.close();
})();
