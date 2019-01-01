# Oxford 3000 words set on Quizlet
- [Oxford 3000 - 1 Flashcards | Quizlet](https://quizlet.com/355331197/oxford-3000-1-flash-cards/)
- [Oxford 3000 - 2 Flashcards | Quizlet](https://quizlet.com/355331155/oxford-3000-2-flash-cards/)

```sh
# Crawl data from web
$ node getOxford3000words.js 

# Remove HTML tags from the crawled data
$ node stripHTML.js

# Fix data to make more sense manually...
# e.g.) Some raw data has the definition of "Failed to feach!" as the HTML structure is slightly different.
# e.g.) Some raw data has the definition of "Failed to feach!" as the HTML does not have its own definition.

# Update the existing set on Quizlet
$ QUIZLET_CLIENT_ID=CLIENT_ID \
  QUIZLET_SECRET=SECRET_KEY \
  QUIZLET_GMAIL_ADDRESS=GMAIL_ADDRESS_FOR_OAUTH \
  QUIZLET_GMAIL_PASSWORD=GMAIL_PASSWORD_FOR_OAUTH \
  node updateQuizletSet.js

    Quizlet Code is being generated...
    Quizlet Code is generated successfully.
    Quizlet Token is being generated...
    Quizlet Token is generated successfully.
    Set 355331197 has been created.
    Set 355331197 has been updated.
```
