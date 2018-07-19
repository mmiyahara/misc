module.exports = {
  extends: 'eslint:recommended',
  rules: {
    indent: ['error', 2],
    'no-console': 0
  },
  env: {
    es6: true
  },
  globals: {
    console: true,
    kintone: true,
    window: true
  },
  parserOptions: {
    'sourceType': 'module',
    'ecmaVersion': 2017
  },
  parser: 'babel-eslint'
}; 