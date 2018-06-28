'use strict';

angular.module('myApp').controller('WeatherController', ['$scope', 'WeatherService', function($scope, WeatherService) {
    var self = this;
    self.weather={id:null, description:'',temp:0.0,date:''};
    self.forecast=[];

    self.submit = submit;
    self.reset = reset;
    self.addFavorite = addFavorite;


    fetchForecast();

    function fetchForecast(){
        WeatherService.fetchForecast()
            .then(
            function(d) {
                self.forecast = d;
            },
            function(errResponse){
                console.error('Error while fetching Forecast');
            }
        );
    }

    function createWeather(weather){
        WeatherService.createWeather(weather)
            .then(
            fetchAllWeathers,
            function(errResponse){
                console.error('Error while creating Forecast');
            }
        );
    }

    function updateWeather(Weather, id){
        WeatherService.updateWeather(Weather, id)
            .then(
            fetchForecast,
            function(errResponse){
                console.error('Error while updating Forecast');
            }
        );
    }
    
    function addFavorite(weather) {
    	console.log('Starting request');
    	WeatherService.addFavorite(weather);
    }

    function submit() {
        if(self.Weather.id===null){
            console.log('Saving New Weather', self.Weather);
            createWeather(self.Weather);
        }else{
            updateWeather(self.Weather, self.Weather.id);
            console.log('Weather updated with id ', self.Weather.id);
        }
        reset();
    }

    function reset(){
        self.Weather={id:null,description:'',temp:0,date:''};
        $scope.myForm.$setPristine(); //reset Form
    }

}]);
