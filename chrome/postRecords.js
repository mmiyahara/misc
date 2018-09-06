const mailAddress = [
  'noboru_sato@example.com',
  'misaki_kato@example.com',
  'kenta_takahashi@example.com'
];

const texts = [
  '佐藤 昇',
  '加藤 美咲',
  '高橋 健太',
];

// 0 - (digits - 1) の整数を取得する関数
const randomInt = (digits) => {
  return parseInt(Math.random() * digits, 10);
};

// ランダムな文字列を生成する関数 (値の重複を禁止するフィールド対応)
const randomTextGenerator = (digits) => {
  const charset = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
  let randomText = '';
  for (let i = 0; i < digits; i++) {
    randomText += charset.charAt(Math.floor(Math.random() * charset.length));
  }
  return randomText;
};

// チェックボックス、複数選択、組織選択、グループ選択の選択肢からランダムに取得する関数
const getRandomChoices = (options) => {
  const choices = Array.isArray(options) ?
    options.filter(() => {
      return Math.random() > 0.5;
    }) :
    Object.keys(options).filter(() => {
      return Math.random() > 0.5;
    });
  if (choices.length === 0) {
    return getRandomChoices(options);
  }
  return choices;
};

// ルックアップ元のレコードをランダムに取得する関数
const fetchRelatedAppRecords = async (fromAppId) => {
  return (await kintone.api('/k/v1/records', 'GET', { app: fromAppId })).records;
};

// 実行関数
const main = (async (num_records) => {
  const user = kintone.getLoginUser().code;
  const ownOrgCodes = (await kintone.api('/v1/user/organizations', 'GET', { code: user }))
    .organizationTitles
    .map((org) => {
      return org.organization.code;
    });
  const ownGroupCodes = (await kintone.api('/v1/user/groups', 'GET', { code: user }))
    .groups
    .map((group) => {
      return group.code;
    });

  const props = (await kintone.api('/k/v1/app/form/fields', 'GET', { app: kintone.app.getId() })).properties;

  // ルックアップフィールドを取得
  const lookupFields = Object.keys(props).reduce((result, code) => {
    if (props[code].lookup) {
      result.push(props[code].lookup);
    }
    return result;
  }, []);

  // ルックアップ元アプリからレコードを取得し、フィールド情報に紐づけ
  for (let lookupField of lookupFields) {
    lookupField.relatedRecords = await fetchRelatedAppRecords(lookupField.relatedApp.app);
  }

  const body = {
    app: kintone.app.getId(),
    records: []
  };

  [...Array(num_records)].forEach(() => {
    const record = {};
    Object.keys(props).forEach((code) => {
      // ルックアップフィールド (ルックアップ先フィールドが値の重複を禁止する場合はエラーになる)
      if (props[code].lookup) {
        const randomRecord = props[code].lookup.relatedRecords[randomInt(props[code].lookup.relatedRecords.length)];
        record[code] = { value: randomRecord[props[code].lookup.relatedKeyField].value }
      }

      // 文字列系フィールド
      if (['SINGLE_LINE_TEXT', 'MULTI_LINE_TEXT', 'RICH_TEXT'].includes(props[code].type) && !props[code].lookup) {
        let randomText = texts[randomInt(texts.length)];
        if (props[code].unique) {
          randomText = randomTextGenerator(10);
        }
        record[code] = { value: randomText };
      }

      // 数値フィールド
      if (['NUMBER'].includes(props[code].type)) {
        const randomNum = randomInt(6);
        record[code] = { value: randomNum };
      }

      // 時刻フィールド
      if (['TIME'].includes(props[code].type)) {
        const randomTime = randomInt(24) + ':' + randomInt(60);
        record[code] = { value: randomTime };
      }

      // 日付フィールド
      if (['DATE'].includes(props[code].type)) {
        const date = new Date().toISOString().slice(0, 10);
        record[code] = { value: date };
      }

      // 日時フィールド
      if (['DATETIME'].includes(props[code].type)) {
        const datetime = new Date().toISOString().slice(0, 17) + '00Z';
        record[code] = { value: datetime };
      }

      // ラジオボタン
      if (['RADIO_BUTTON', 'DROP_DOWN'].includes(props[code].type)) {
        const optionsRadioLength = Object.keys(props[code].options).length;
        const randomRadioChoice = Object.keys(props[code].options)[randomInt(optionsRadioLength)];
        record[code] = { value: randomRadioChoice };
      }

      // チェックボックス、複数選択
      if (['CHECK_BOX', 'MULTI_SELECT'].includes(props[code].type)) {
        const randomCheckboxChoices = getRandomChoices(props[code].options);
        record[code] = { value: randomCheckboxChoices };
      }

      // リンク
      if (['LINK'].includes(props[code].type)) {
        let randomLinkChoice;
        switch (props[code].protocol) {
        case 'WEB':
          randomLinkChoice = 'https://example.com';
          break;
        case 'CALL':
          randomLinkChoice = '01234567890';
          break;
        case 'MAIL':
          randomLinkChoice = mailAddress[randomInt(mailAddress.length)];
          break;
        }
        record[code] = { value: randomLinkChoice };
      }

      // ユーザー選択
      if (['USER_SELECT'].includes(props[code].type)) {
        record[code] = { value: [{ code: user }] };
      }

      // 組織選択
      if (['ORGANIZATION_SELECT'].includes(props[code].type)) {
        record[code] = { value: getRandomChoices(ownOrgCodes).map(code => { return { code: code } }) };
      }

      // グループ選択
      if (['GROUP_SELECT'].includes(props[code].type)) {
        record[code] = { value: getRandomChoices(ownGroupCodes).map(code => { return { code: code } }) };
      }
    });
    body.records.push(record);
  })

  const result = await kintone.api('/k/v1/records', 'POST', body);
  return result;
});

main(10).then((r) => {
  console.log(r.ids.length + ' records were posted!');
  window.location.reload();
});
