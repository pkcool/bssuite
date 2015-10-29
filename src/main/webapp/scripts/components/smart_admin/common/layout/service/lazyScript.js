'use strict';

angular.module('bssuiteApp').factory('lazyScript', function($q, $http){
    var scripts = null;
    var initialized = false;
    var initializingPromise = null;

    function init(){
        if(!initialized){
            if(!initializingPromise){
                initializingPromise = $http.get('app.scripts.json').then(function(res){
                    scripts = res.data
                    initialized = true;
                });
            }
            return initializingPromise;

        } else {
            return $q.resolve();
        }
    }

    var cache = {};

    function isPending(scriptName){
        return (cache.hasOwnProperty(scriptName) && cache[scriptName].promise && cache[scriptName].promise.$$state.pending)
    }

    function isRegistered(scriptName){
        if(cache.hasOwnProperty(scriptName)){
            return true;
        } else {
            return (scripts.prebuild.indexOf(scriptName) > -1);
        }
    }
    function loadScript(scriptName){
        if(!cache[scriptName]){
            cache[scriptName] = $q.defer();
            var el = document.createElement( 'script' );
            el.onload = function(script){
                console.log('script is lazy loaded:', scriptName)
                cache[scriptName].resolve(scriptName);
            };
            el.src = scripts.paths[scriptName];
            var x = document.getElementsByTagName('script')[0];
            x.parentNode.insertBefore(el, x);

        }
        return cache[scriptName].promise;

    }

    function register(scriptName){
        if(isPending(scriptName)){
            return cache[scriptName].promise
        }
        if(isRegistered(scriptName)){
            return $q.resolve(scriptName);
        } else {
            var dfd = $q.defer();
            if(scripts.shim.hasOwnProperty(scriptName) && scripts.shim[scriptName].deps){
                var depsPromises = [];
                angular.forEach(scripts.shim[scriptName].deps, function(dep){

                    depsPromises.push(register(dep))

                })
                $q.all(depsPromises).then(function(){
                    loadScript(scriptName).then(function(){
                        dfd.resolve(scriptName);
                    })
                })

            } else {

                loadScript(scriptName).then(function(){
                    dfd.resolve(scriptName);
                })

            }
            return dfd.promise;

        }
    }
    return {
        register: function (scripts) {

            var dfd = $q.defer();
            init().then(function(){
                var promises = [];
                if (angular.isString(scripts))
                    scripts = [scripts];

                angular.forEach(scripts, function(script){
                    promises.push(register(script));
                })

                $q.all(promises).then(function(resolves){
                    dfd.resolve(resolves);
                })
            })
            return dfd.promise;

        }
    };
});
