(async () => {
  const record = kintone.app.record.get().record;

  const multiple = [
    'CHECK_BOX',
    'MULTI_SELECT',
    'FILE'
  ];
  const singleUser = [
    'CREATOR',
    'MODIFIER',
  ];
  const multipleUsers = [
    'USER_SELECT',
    'GROUP_SELECT',
    'ORGANIZATION_SELECT'
  ];

  const includesFieldType = (arr, key) => {
    return arr.reduce((result, fieldType) => {
      if (result) return true;
      return record[key].type === fieldType;
    }, false);
  };

  const props = (await kintone.api('/k/v1/app/form/fields', 'GET', { app: kintone.app.getId() })).properties;
  const recordObj = Object.keys(props).reduce((result, code) => {
    // プロセス管理が無効の場合を考慮
    if (!record[code]) {
      return result;
    }

    result[props[code].label] = {
      code: code,
      value: record[code].value || '',
      specialValue: ''
    };

    const isMultiple = includesFieldType(multiple, code);
    const isSingleUserType = includesFieldType(singleUser, code);
    const isMultipleUsersType = includesFieldType(multipleUsers, code);

    if (isMultiple) {
      result[props[code].label].specialValue = record[code].value.toString();
    }

    if (isSingleUserType) {
      result[props[code].label].specialValue = record[code].value.code;
    }

    if (isMultipleUsersType) {
      result[props[code].label].specialValue = record[code].value.reduce((result, obj) => {
        return result + obj.code + ', ';
      }, '').slice(0, -2);
    }

    return result;
  }, {});

  console.table(recordObj);
})();
