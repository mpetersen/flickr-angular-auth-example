angular.module('app', ['ngRoute'])
.config(function($routeProvider) {
  $routeProvider
  .when('/authorize/:clientToken', {
    controller: 'AuthorizeCtrl',
    templateUrl: '/partials/authorize.html'
  })
  .otherwise({
    controller: 'PhotosCtrl',
    templateUrl: '/partials/photos.html'
  });
})
.config(function($httpProvider) {
  $httpProvider.interceptors.push(function($q, $location) {
    return {
      'request': function(config) {
        config.headers['clientToken'] = localStorage['clientToken'];
        return config;
      },
      'responseError': function(response) {
        if (response.status === 401) {
          localStorage['locationPath'] = $location.path();
          window.location.href = '/authorize';
        }
        return $q.reject(response);
      }
    };
  });
})
.controller('AuthorizeCtrl', ['$location', '$routeParams', function($location, $routeParams){
  var clientToken = $routeParams['clientToken'];
  localStorage['clientToken'] = clientToken;
  var locationPath = localStorage['locationPath'];
  localStorage.removeItem('locationPath');
  $location.path(locationPath);
}])
.controller('PhotosCtrl', ['$scope', '$http', function($scope, $http) {
  $scope.text = "Hello, World!";
  $http
  .get('/photos')
  .success(function(data) {
    $scope.data = data;
  });
}]);
