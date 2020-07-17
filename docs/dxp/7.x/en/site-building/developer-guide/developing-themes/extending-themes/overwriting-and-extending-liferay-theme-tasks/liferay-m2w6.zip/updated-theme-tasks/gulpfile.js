/**
 * © 2017 Liferay, Inc. <https://liferay.com>
 *
 * SPDX-License-Identifier: MIT
 */

'use strict';

var gulp = require('gulp');
var log = require('fancy-log');
var uglify = require('gulp-uglify');
var liferayThemeTasks = require('liferay-theme-tasks');

liferayThemeTasks.registerTasks({
  gulp: gulp,
  hookFn: function(gulp) {
    gulp.hook('before:build:war', function(done) {
     // Fires before build `war` task
     gulp.src('./build/js/*.js')
     .pipe(uglify())
     .pipe(gulp.dest('./build/js'))
     .on('end', done);
     log('Your JS is now minified...');
     });
  }
});