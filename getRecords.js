kintone.api('/k/v1/records', 'GET', { app: kintone.app.getId() }).then(r => {
  console.dir(r.records);
});