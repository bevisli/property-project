'use strict'

var gulp = require('gulp');
var uglify = require('gulp-uglify');
var cssmin = require('gulp-cssmin');
var rename = require('gulp-rename');
var del = require('del');
var runSequence = require('run-sequence');
var replace = require('gulp-replace');

var paths = gulp.paths;

gulp.vendors = require('./../vendors.json');
var vendors = gulp.vendors;

gulp.task('copy:vendorsCSS', function () {
    return gulp.src(vendors.css)
        .pipe(gulp.dest(paths.dist.vendors + 'css/'));
});

gulp.task('minify:vendorsCSS', function () {
    return gulp.src([
        paths.dist.vendors + 'css/*.css',
        '!' + paths.dist.vendors + 'css/*.min.css'])
        .pipe(cssmin())
        .pipe(rename({suffix: '.min'}))
        .pipe(gulp.dest(paths.dist.vendors + 'css/'));
});

gulp.task('clean:vendorsCSS', function () {
    return del([
        paths.dist.vendors + 'css/*.css',
        '!' + paths.dist.vendors + 'css/*.min.css'
    ]);
});

gulp.task('vendors:css', function (callback) {
    runSequence('copy:vendorsCSS', 'minify:vendorsCSS', 'clean:vendorsCSS', callback);
});

gulp.task('copy:vendorsJS', function () {
    return gulp.src(vendors.js)
        .pipe(gulp.dest(paths.dist.vendors + 'js/'));
});

gulp.task('minify:vendorsJS', function () {
    return gulp.src([
        paths.dist.vendors + 'js/*.js',
        '!' + paths.dist.vendors + 'js/*.min.js'])
        .pipe(gulp.dest(paths.dist.vendors + 'js/'))
        .pipe(uglify())
        .pipe(rename({suffix: '.min'}))
        .pipe(gulp.dest(paths.dist.vendors + 'js/'));
});

gulp.task('clean:vendorsJS', function () {
    return del([
        paths.dist.vendors + 'js/*.js',
        '!' + paths.dist.vendors + 'js/*.min.js']);
});

gulp.task('vendors:js', function (callback) {
    runSequence('copy:vendorsJS', 'minify:vendorsJS', 'clean:vendorsJS', callback);
});

gulp.task('copy:vendorsFonts', function () {
    return gulp.src(vendors.fonts)
        .pipe(gulp.dest(paths.dist.vendors + 'fonts/'));
});

gulp.task('replace:node_modules', function () {
    return gulp.src([
        paths.dist.root + '**/*.html',
        paths.dist.root + '**/*.js'
    ], {base: './'})
        .pipe(replace(/node_modules+.+(\/[a-z0-9][^/]*\.js+(\'|\"))/ig, 'vendors/js$1'))
        .pipe(replace(/vendors\/js\/(.*).js/ig, '/static/vendors/js/$1.min.js'))
        .pipe(replace(/..\/..\/vendors\/js\/(.*).js/ig, '../../static/vendors/js/$1.min.js'))
        .pipe(replace('.min.min.js', '.min.js'))
        .pipe(replace(/node_modules+.+(\/[a-z0-9][^/]*\.css+(\'|\"))/ig, 'vendors/css$1'))
        .pipe(replace(/vendors\/css\/(.*).css/ig, '/static/vendors/css/$1.min.css'))
        .pipe(replace(/..\/..\/vendors\/css\/(.*).css/ig, '../../static/vendors/css/$1.min.css'))
        .pipe(replace('.min.min.css', '.min.css'))
        .pipe(gulp.dest('./'));
});

gulp.task('vendors', function (callback) {
    runSequence('vendors:css', 'vendors:js', 'copy:vendorsFonts', 'replace:node_modules', callback);
});

gulp.task('clean:dist', function () {
    return del(paths.dist.root);
});

gulp.task('copy:css', function () {
    return gulp.src(paths.src.static + 'css/**/*')
        .pipe(gulp.dest(paths.dist.static + 'css'));
});

gulp.task('copy:img', function () {
    return gulp.src(paths.src.static + 'img/**/*')
        .pipe(gulp.dest(paths.dist.static + 'img'));
});

gulp.task('copy:js', function () {
    return gulp.src(paths.src.static + 'js/**/*')
        .pipe(gulp.dest(paths.dist.static + 'js'));
});

gulp.task('copy:views', function () {
    return gulp.src(paths.src.root + 'views/**/*')
        .pipe(gulp.dest(paths.dist.root + 'views'));
});

gulp.task('copy:html', function () {
    return gulp.src(paths.src.root + 'index.html')
        .pipe(gulp.dest(paths.dist.root));
});

gulp.task('copy:vendors', function () {
    return gulp.src(paths.src.static + 'vendors/**/*')
        .pipe(gulp.dest(paths.dist.static + 'vendors/'));
});

gulp.task('build:dist', function (callback) {
    runSequence('clean:dist', 'copy:css', 'copy:img', 'copy:js', 'copy:views', 'copy:html', 'copy:vendors', 'vendors', callback);
});
