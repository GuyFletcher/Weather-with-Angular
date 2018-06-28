'use strict';

angular.module('myApp').factory('WeatherService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = 'http://localhost:8080/Weather/weather/';

    var factory = {
        fetchForecast: fetchForecast,
        createWeather: createWeather,
        addFavorite: addFavorite
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
    function addFavorite(weather) {
    	var deferred = $q.defer();
    	console.log('Sending data')
    	$http.post(REST_SERVICE_URI, weather)
	        .then(
	        function (response) {
	        	console.log('Sent data')
	            deferred.resolve(response.data);
	        },
	        function(errResponse){
	            console.error('Error while adding Weather to favorites');
	            deferred.reject(errResponse);
	        }
	    );
	    return deferred.promise;
    }


}]);
