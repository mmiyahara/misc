const text1 = document.getElementById('text1');
const text2 = document.getElementById('text2');
const result = document.getElementById('result');

const worker = new Worker('worker.js');

text1.addEventListener('keyup', (e) => {
  console.log('The value is changed.');
  worker.postMessage(e.target.value + text2.value);
  console.log('The value is sent to the dedicated worker.');
});

text2.addEventListener('keyup', (e) => {
  console.log('The value is changed.');
  worker.postMessage(text1.value + e.target.value);
  console.log('The value is sent to the dedicated worker.');
});

worker.onmessage = (e) => {
  result.textContent = e.data;
};
