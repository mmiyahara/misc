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
