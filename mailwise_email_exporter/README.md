# Mailwise email exporter
Export E-mail data from Mailwise using [puppeteer](https://github.com/GoogleChrome/puppeteer)

## Usage
```sh
# Download files
git clone https://github.com/mmiyahara/snippets
cd snippets/mailwise_email_exporter

# This may take a time
npm install

# Need to input domain, user_id and user_password in advance
vi exportCSV.js

# Export E-mail data from Mailwise
node exportCSV.js
```

## Debug
Edit `exportCSV.js` to see what is happeing for debug
```js
  const launchOptions = {
    // If you want to run in the background (e.g. as a batch program), change the value of 'headless' to 'true'
    // If you want to look into the behavior, change the value of 'headless' to 'false'
    headless: true,
    // ...
  };
```
