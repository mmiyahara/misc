module.exports = {
  'extends': 'eslint-config-kintone',
  'plugins': [
    'react',
    'jsx-a11y',
    'import'
  ],
  'globals': {
    'example': true,
    'Promise': true,
    'wijmo': true,
    'vue': true
  },
  'rules': {
    'indent': [2, 2, { 'SwitchCase': 1 }],
    'strict': ['error', 'global'],
    'no-console': ['error', { allow: ['log']}],
    'max-len': ['error', {
      code: 120,
      ignoreUrls: true
    }]
  }
};
