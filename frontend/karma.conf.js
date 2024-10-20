module.exports = function(config) {
  config.set({
    browsers: ['ChromeHeadless'],
    customLaunchers: {
      ChromeHeadlessCI: {
        base: 'ChromeHeadless',
        flags: ['--no-sandbox', '--disable-gpu']
      }
    },
    browserDisconnectTimeout: 10000,
    browserNoActivityTimeout: 30000,
    captureTimeout: 60000,
    frameworks: ['jasmine', '@angular-devkit/build-angular'],
    plugins: [
      require('karma-jasmine'),
      require('karma-chrome-launcher'),
      require('karma-coverage'),
      require('@angular-devkit/build-angular/plugins/karma')
    ],
    reporters: ['progress', 'coverage'],
    coverageReporter: {
      type: 'lcov',
      dir: require('path').join(__dirname, 'coverage'),
      subdir: '.',
      check: {
        global: {
          statements: 80,
          branches: 80,
          functions: 80,
          lines: 80
        }
      }
    },
    // other configurations...
  });
};
