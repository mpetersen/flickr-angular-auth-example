angular.module('app', ['ngRoute', 'flickrAuth'])
.config(['$routeProvider', function($routeProvider) {
  $routeProvider
  .otherwise({
    controller: 'PhotosCtrl',
    templateUrl: '/partials/photos.html'
  });
}])
.controller('PhotosCtrl', ['$scope', '$http', function($scope, $http) {
  $http
  .get('/photos')
  .success(function(data) {
    $scope.data = data;
  });
}]);
