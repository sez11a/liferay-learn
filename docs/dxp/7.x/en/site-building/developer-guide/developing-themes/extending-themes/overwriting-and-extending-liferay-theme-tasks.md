# Overwriting and Extending Liferay Theme Tasks

Themes created with the Liferay Themes JS Toolkit have access to several default Theme tasks that provide the standard features required to develop and build your Theme (build, deploy, watch, etc.). You may, however, want to run additional processes on your Theme's files prior to deploying the Theme to the server---such as minifying your JavaScript files. The Liferay JS Theme Toolkit's APIs expose a `hookFn` property that lets you hook into the default gulp Theme tasks to inject your own logic.

This example runs on Liferay DXP 7.3+.

Here, you'll learn how to extend a Theme tasks to inject your own logic:

1. [Run an Example that Extends the Default Theme Tasks](#run-an-example-that-extends-the-default-theme-tasks)
1. [Update a Task](#update-a-task)
1. [Run and Test](#run-and-test)

## Run an Example that Extends the Default Theme Tasks

First, run an example to see an updated Theme task:

1. Download and unzip the [Minified Task Theme](https://learn.liferay.com/dxp/7.x/en/site-building/developer-guide/developing-themes/extending-themes/liferay-m2w6.zip):

    ```bash
    curl https://learn.liferay.com/dxp/7.x/en/site-building/developer-guide/developing-themes/extending-themes/liferay-m2w6.zip

    unzip liferay-m2w6.zip
    ```

1. Navigtate to the `\liferay-m2w6.zip\m2w6-impl\minified-task-theme\` folder, install the Theme's dependencies:

    ```bash
    cd liferay-m2w6\minified-theme-task-theme
    npm install
    ```

1. Run the `build` task (`npm run build`) to see what the default output looks like. You should see something similar to the output shown below:

    ```bash
    C:\Users\liferay\Desktop\projects\liferay-m2w6.zip\minified-task-theme>npm run build

    > minified-task-theme@1.0.0 build C:\Users\liferay\Desktop\projects\liferay-m2w6.zip\minified-task-theme
    > gulp build

    [11:13:26] Using gulpfile ~\Desktop\projects\liferay-m2w6.zip\minified-task-theme\gulpfile.js
    [11:13:26] Starting 'build'...
    [11:13:26] Starting 'build:clean'...
    [11:13:26] Finished 'build:clean' after 2.31 ms
    [11:13:26] Starting 'build:base'...
    [11:13:28] Finished 'build:base' after 1.78 s
    [11:13:28] Starting 'build:src'...
    [11:13:28] Finished 'build:src' after 11 ms
    [11:13:28] Starting 'build:web-inf'...
    [11:13:28] Finished 'build:web-inf' after 11 ms
    [11:13:28] Starting 'build:liferay-look-and-feel'...
    [11:13:28] Finished 'build:liferay-look-and-feel' after 21 ms
    [11:13:28] Starting 'build:hook'...
    [11:13:28] Finished 'build:hook' after 5.08 ms
    [11:13:28] Starting 'build:themelets'...
    [11:13:28] Starting 'build:themelet-src'...
    [11:13:28] Finished 'build:themelet-src' after 1.11 ms
    [11:13:28] Starting 'build:themelet-css-inject'...
    [11:13:28] Starting 'build:themelet-js-inject'...
    [11:13:28] gulp-inject Nothing to inject into _custom.scss.
    [11:13:28] gulp-inject Nothing to inject into portal_normal.ftl.
    [11:13:28] Finished 'build:themelet-css-inject' after 28 ms
    [11:13:28] Finished 'build:themelet-js-inject' after 25 ms
    [11:13:28] Finished 'build:themelets' after 33 ms
    [11:13:28] Starting 'build:rename-css-dir'...
    [11:13:28] Finished 'build:rename-css-dir' after 13 ms
    [11:13:28] Starting 'build:compile-css'...
    [11:13:28] Starting 'build:compile-lib-sass'...
    [11:13:30] Finished 'build:compile-lib-sass' after 1.93 s
    [11:13:30] Finished 'build:compile-css' after 1.93 s
    [11:13:30] Starting 'build:fix-url-functions'...
    [11:13:30] Finished 'build:fix-url-functions' after 43 ms
    [11:13:30] Starting 'build:move-compiled-css'...
    [11:13:30] Finished 'build:move-compiled-css' after 477 ms
    [11:13:30] Starting 'build:remove-old-css-dir'...
    [11:13:30] Finished 'build:remove-old-css-dir' after 111 ms
    [11:13:30] Starting 'build:fix-at-directives'...
    [11:13:30] Finished 'build:fix-at-directives' after 12 ms
    [11:13:30] Starting 'build:r2'...
    [11:13:31] Finished 'build:r2' after 166 ms
    [11:13:31] Starting 'build:copy:fontAwesome'...
    [11:13:31] Finished 'build:copy:fontAwesome' after 25 ms
    [11:13:31] Starting 'build:war'...
    [11:13:31] Starting 'plugin:version'...
    [11:13:31] Finished 'plugin:version' after 2.41 ms
    [11:13:31] Starting 'plugin:war'...
    [11:13:32] Finished 'plugin:war' after 1.32 s
    [11:13:32] Finished 'build:war' after 1.33 s
    [11:13:32] Finished 'build' after 6 s
    ```

    Note that the built `\liferay-m2w6.zip\m2w6-impl\minified-task-theme\build\main.js` file is currently unminified:

    ```javascript
    AUI().ready(

    	function() {}
    );

    Liferay.Portlet.ready(

    	function(_portletId, _node) {}
    );

    Liferay.on(
    	'allPortletsReady',

    	function() {}
    );
    ```

1. Replace the `liferay-m2w6\m2w6-impl\minified-task-theme\gulpfile.js` file with the contents below:

    ```javascript
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
    ```

1. Install the `gulp-uglify` and `fancy-log` dev dependencies:

    ```bash
    npm install --save-dev gulp-uglify fancy-log
    ```

1. Run the `build` task (`npm run build`) with the updated file. You should see something similar to the output shown below:
    
    ```bash
    ...
    [11:57:35] Starting 'build:war'...
    [11:57:35] Starting '<anonymous>'...
    [11:57:35] Your JS is now minified...
    [11:57:35] Finished '<anonymous>' after 25 ms
    [11:57:35] Starting 'build:war'...
    [11:57:35] Starting 'plugin:version'...
    [11:57:35] Finished 'plugin:version' after 2.31 ms
    [11:57:35] Starting 'plugin:war'...
    [11:57:37] Finished 'plugin:war' after 1.25 s
    [11:57:37] Finished 'build:war' after 1.26 s
    [11:57:37] Finished 'build:war' after 1.29 s
    [11:57:37] Finished 'build' after 6.73 s
    ```

1. Verify that the JavaScript is minified in `\liferay-m2w6.zip\minified-task-theme\build\main.js`:

    ```javascript
    AUI().ready(function(){}),Liferay.Portlet.ready(function(e,n){}),Liferay.on("allPortletsReady",function(){});
    ```

Great! You successfully injected a new task in the build process.

## Overview of the Updated Task

The tasks and sub tasks are listed in their `[task-name].js` file in the [`tasks` folder](https://github.com/liferay/liferay-js-themes-toolkit/tree/master/packages/liferay-theme-tasks/theme/tasks) of the [`liferay-theme-tasks`](https://github.com/liferay/liferay-js-themes-toolkit/tree/master/packages/liferay-theme-tasks) package (except for `build` which is listed in the [`build/index.js` file](https://github.com/liferay/liferay-js-themes-toolkit/blob/master/packages/liferay-theme-tasks/theme/tasks/build/index.js#L73-L88)). For example, the gulp `build` task and sub tasks are defined in the [`build/index.js` file](https://github.com/liferay/liferay-js-themes-toolkit/blob/master/packages/liferay-theme-tasks/theme/tasks/build/index.js#L73-L88):

```javascript
gulp.task('build', function(cb) {
  runSequence(
    'build:clean',
    'build:base',
    'build:src',
    'build:web-inf',
    'build:liferay-look-and-feel',
    'build:hook',
    'build:themelets',
    'build:rename-css-dir',
    'build:compile-css',
    'build:fix-url-functions',
    'build:move-compiled-css',
    'build:remove-old-css-dir',
    'build:fix-at-directives',
    'build:r2',
    'build:war',
    cb
  );
});
```

The `liferayThemeTasks.registerTasks()` method in the Theme's `gulpfile.js` file registers the default gulp Theme tasks. The `hookFn` property is added to the `registerTasks()` method's configuration object, passing in the `gulp` instance:

    ```javascript
    liferayThemeTasks.registerTasks({
      gulp: gulp,
      hookFn: function(gulp) {
        
      }
    });
    ```

The `gulp.hook()` method inside the `hookFn()` function specifies the Theme task or sub task to hook into. You can inject your code before or after a task by prefixing it with the `before:` or `after:`. Alternatively, you can use the `gulp.task()` method to overwrite a gulp task. Both methods have two parameters: the task or sub task you want to hook into and a callback function that invokes `done` or returns a stream with the logic that you want to inject. A few example configuration patterns are shown below:

```javascript
liferayThemeTasks.registerTasks({
  gulp: gulp,
  hookFn: function(gulp) {
    gulp.hook('before:build:src', function(done) {
      // Fires before build:src task
    });

    gulp.hook('after:build', function(done) {
      // Fires after build task
    });

    gulp.task('build:base', function(done) {
      // Overwrites build:base task
    });
  }
});
```

The minified example fires before the `build:war` sub-task (`'before:build:war'`) and reads the JavaScript files in the Theme's `build` folder, minifies them with the [`gulp-uglify`](https://www.npmjs.com/package/gulp-uglify) module, places them back in the `./build/js` folder, invokes `done`, and finally logs that the JavaScript was minified with [`fancy-log`](https://www.npmjs.com/package/fancy-log):

```javascript
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
```

```note::
  The `hook` callback function must invoke the `done` argument or return a stream.
```

Now you know how to hook into and overwrite the default Theme tasks! 

## Related Information

- [Running Theme Tasks](../reference/themes/running-theme-tasks/running-theme-tasks.md)
- [Developing a Theme](../developing-a-theme.md)
- [Generating Themelets](../developing-themelets.md)