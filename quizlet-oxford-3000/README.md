# Oxford 3000 words set on Quizlet
```sh
# Crawl data from web
$ node getOxford3000words.js 

# Update the existing set on Quizlet
$ QUIZLET_CLIENT_ID=CLIENT_ID \
  QUIZLET_SECRET=SECRET_KEY \
  QUIZLET_GMAIL_ADDRESS=GMAIL_ADDRESS_FOR_OAUTH \
  QUIZLET_GMAIL_PASSWORD=GMAIL_PASSWORD_FOR_OAUTH \
  QUIZLET_SET_ID=QUIZLET_SET_ID \
  node updateQuizletSet.js
```
