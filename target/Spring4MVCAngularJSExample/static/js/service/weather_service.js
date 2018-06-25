'use strict';

angular.module('myApp').factory('WeatherService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = 'http://localhost:8080/WeatherSite/weather/';

    var factory = {
        fetchForecast: fetchForecast,
        createWeather: createWeather
    };

    return factory;

    function fetchForecast() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching Forecast');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function createWeather(weather) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, weather)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while creating Weather');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }


}]);
