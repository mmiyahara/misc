# 英語を半年でマスターするための Quizlet 単語帳を作った話
## はじめに
- **2019年の目標: 英語を何とかする**  
- 目標達成のために [Quizlet](https://quizlet.com/ja) (単語帳作成サービス) で単語帳を作った話です
- 作成した単語セットは以下に公開しています
  - [Oxford 3000 - 1 Flashcards | Quizlet](https://quizlet.com/355331197/oxford-3000-1-flash-cards/)
  - [Oxford 3000 - 2 Flashcards | Quizlet](https://quizlet.com/355331155/oxford-3000-2-flash-cards/)

## 作戦
以前に以下の動画を視聴し、(正直半信半疑ですが)気になっていました。
- [How to learn any language in six months | Chris Lonsdale | TEDxLingnanUniversity - YouTube](https://www.youtube.com/watch?v=d0yGdNEWdn0)

日本語訳としては、以下の記事が詳しいです。
- [外国語や中国語の語学習得期間が6か月の勉強・マスター方法 - ログミー[o_O]](https://logmi.jp/business/articles/13487)
- [外国語習得を6か月でマスターする英語や中国語の言語の覚え方 - ログミー[o_O]](https://logmi.jp/business/articles/13562)

> 英語であれば、核となる1,000語で、日常コミュニケーションで使う語彙の85%がカバー出来ます。  
> 3,000語で、日常会話の中で言いたいことの98%を表すことが出来ます。  
> 3,000語の語彙があれば、その言語が話せるのです。残りは添え物のようなものです。  
> 新しい言語を学び始めた時、まずは「ツール」を揃えることから始めましょう。  
> \-- [外国語習得を6か月でマスターする英語や中国語の言語の覚え方 - ログミー[o_O]](https://logmi.jp/business/articles/13562)  

上記の記事によると、3000 単語を使いこなせれば 98% を表現できるとのこと。
では、どの 3000 単語を使いこなせれば 98% 表現できるのかというと、
[Oxford 3000](https://www.oxfordlearnersdictionaries.com/about/oxford3000) という単語リストが優秀みたいです。

> 海外の英語学習者の間では常識であり、その真実があまり日本には伝わっていないため、単語帳が書店に溢れかえっています。  
> ですが、通常外国語を学習するのにいくつも単語帳を持つ必要はなく、国際スタンダードに則って、OXFORD 3000単語だけ学習すればいい。というのが私の持論です。  
> \-- [世界では常識？日常英会話の90％以上は、OXFORD 3000単語だけで十分な理由 - MULTILINGIRL♪](https://www.multilingirl.com/2014/12/blog-post_75.html)

検索した範囲では、手持ちのスマートフォンで良い感じに学習できるアプリが見つからなかったため、[puppeteer](https://github.com/GoogleChrome/puppeteer)でクロールし、[Quizlet](https://quizlet.com/ja) 上に単語セットを作成した話です。

## Quizlet(単語帳アプリ)を選んだ理由
- アプリマーケットへの公開等が面倒なので0ベースで作るのは避けたい
- Android (できればiOSも) で使える
- API 経由で編集できる
- 英語を読み上げる機能がある

## 実装
以下の流れで実装しました。コード等は[Github](https://github.com/mmiyahara/misc/tree/master/quizlet-oxford-3000)上に公開しています。

1. [puppeteer](https://github.com/GoogleChrome/puppeteer) で [Oxford 3000](https://www.oxfordlearnersdictionaries.com/about/oxford3000) をクロールしデータを作成
2. クロールしたデータを確認し適宜修正
3. [Quizlet](https://quizlet.com/ja) に API経由で単語セットを作成しデータを登録

### 1. puppeteer で Oxford 3000 をクロールしデータを作成
- 3000単語が掲載されているURLを順にクロールしています。
- 取得したデータを`words.js`に書き出しています。

[misc/getOxford3000words.js at master · mmiyahara/misc](https://github.com/mmiyahara/misc/blob/master/quizlet-oxford-3000/getOxford3000words.js)  

```sh
# Crawl data from web
$ node getOxford3000words.js 
```
```javascript
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
```

### 2. クロールしたデータを確認し適宜修正
- `words.js`に[こんな感じ](https://github.com/mmiyahara/misc/blob/751d176db5cf1ee268ebf3db8ec3323f743f16f5/quizlet-oxford-3000/words.js#L171)でHTMLタグが残っていたため、取り除きます。
また、単語の意味として、例文が含まれている場合があったので、例文も取り除きます。
- \#1 で取得したデータの書き出し方法がイケてなかったので、少し編集しています。
- 編集したファイルを`wordsFlatten.js`に書き出しています。
(単語名が被ると面倒だと思い`.js`で書き出しましたが、`.json`でよかったと後から反省しました😐)  

[misc/stripHTML.js at master · mmiyahara/misc](https://github.com/mmiyahara/misc/blob/master/quizlet-oxford-3000/stripHTML.js)

```sh
# Remove HTML tags from the crawled data
$ node stripHTML.js
```
```javascript
const fs = require('fs');
const words = require('./words.js');

const wordsFlatten = [];
words.forEach(ary => {
  ary.forEach(obj => {
    obj.def = obj.def.replace(/<(?:.|\n)*?>/gm, '').split('.')[0];
    wordsFlatten.push(obj);
  });
});

if (!fs.exists('wordsFlatten.js')) {
  fs.writeFileSync('wordsFlatten.js', JSON.stringify(wordsFlatten, null, '  '));
} else {
  console.log('File already exists.');
}
```

[こんな感じ](https://github.com/mmiyahara/misc/blob/751d176db5cf1ee268ebf3db8ec3323f743f16f5/quizlet-oxford-3000/words.js#L241)にそもそもデータ取得に失敗しているものもありましたが、
21単語と比較的少なかったため、手作業で直しました。

### 3. Quizlet に API経由で単語セットを作成しデータを登録
[Quizlet の API ドキュメント](https://quizlet.com/api/2.0/docs) を確認すると、`OAuth 2.0` で認証を通す必要があります。
`OAuth 2.0` に明るくなかったため、[OAuth 2.0: An Overview - YouTube](https://www.youtube.com/watch?v=CPbvxxslDTU) 等を観ながら実装しました。
(`OAuth 2.0` 形式の認証をコマンドライン経由で通すのが面倒でした。`puppeteer`で通しましたが少し無理やり感があります。いい方法があれば教えてください🙏)

- 事前に [API Dashboard | Quizlet](https://quizlet.com/api-dashboard) で、
`Your Redirect URI` を http://127.0.0.1:1337 に設定しています。
  OAuth 認証成功後ローカルサーバにリダイレクトし、ブラウザのロケーションバーから`code`を取得するためです。

```sh
# Add new set on Quizlet
$ QUIZLET_CLIENT_ID=CLIENT_ID \
  QUIZLET_SECRET=SECRET_KEY \
  QUIZLET_GMAIL_ADDRESS=GMAIL_ADDRESS_FOR_OAUTH \
  QUIZLET_GMAIL_PASSWORD=GMAIL_PASSWORD_FOR_OAUTH \
  node addQuizletSet.js

    Quizlet Code is being generated...
    Quizlet Code is generated successfully.
    Quizlet Token is being generated...
    Quizlet Token is generated successfully.
    Set 355331197 has been created.
    Set 355331197 has been updated.
```

```javascript
const words = require('./wordsFlatten');
const http = require('http');
const puppeteer = require('puppeteer');
const rp = require('request-promise');

http.createServer((_, res) => {
  res.writeHead(200, {'Content-Type': 'text/plain'});
  res.end('You should be redirected here.\n');
}).listen(1337, '127.0.0.1');

// ...

(async () => {
  const code = await getCode(clientId);
  const token = await getToken(code);
  const setId = await postSet(token);
  const putBody = generatePutBody('Oxford 3000 - 1', words);
  await putSet(token, putBody, setId);
  process.exit();
})();
```
各関数では、ざっくりと以下を実行しています。

|関数|役割|
|---|---|
|getCode|`puppeteer` で OAuth 認証用の `code` を取得(認可サーバはGoogleを使用)|
|getToken|`code` を元に `access_token` を取得|
|postSet|`Quizlet` 上に新規セットを作成|
|generatePutBody|新規セットを更新するためのリクエストボディを作成|
|putSet|`Quizlet` 上に作成した新規セットを更新|

## まとめ / 所感
- 作成した単語セットは以下に公開しています。
  - [Oxford 3000 - 1 Flashcards | Quizlet](https://quizlet.com/355331197/oxford-3000-1-flash-cards/)
  - [Oxford 3000 - 2 Flashcards | Quizlet](https://quizlet.com/355331155/oxford-3000-2-flash-cards/)
- `Quizlet` 上の単語セットに単語データ元のURLへのWebリンクを置きたかったのですが、単語セット上でHTMLを使えさなさそう(プレーンテキストのみ)だったため断念しました。
- `Quizlet` の単語セットには、1単語セットにつき上限2000単語の制限があったため、取得した単語を二分し、2単語セットを作成しています。
- `Quizlet` には仕様上CSV形式のデータを読み込めるみたいようですが、2000単語のCSVをアップロードしようとしたところ、ブラウザ画面の描画が追い付かず事実上アップロード出来ませんでした。
- [API Dashboard | Quizlet](https://quizlet.com/api/2.0/docs) 上に以下の記載があったので、2019年1月現在では APIキーの発行が出来ない可能性があります。

  > Note: As of December 2018, Quizlet has paused issuing new API keys.

## 参考
- [puppeteer/api.md at master · GoogleChrome/puppeteer](https://github.com/GoogleChrome/puppeteer/blob/master/docs/api.md)
- [Node.jsで5行Webサーバを書いてみよう〈Node.jsシリーズ vol.1〉 - Tech Blog - Recruit Lifestyle Engineer](https://engineer.recruit-lifestyle.co.jp/techblog/2015-06-22-node1/)
