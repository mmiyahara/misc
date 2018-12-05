onmessage = (ev) => {
  console.log('The value is passed to the worker thread.');
  postMessage(ev.data);
  console.log('The value is pushed back to the parent thread.');
};
