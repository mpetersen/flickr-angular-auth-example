angular.module('flickrAuth', ['ngRoute'])
.config(function($routeProvider) {
  $routeProvider
  .when('/authorize/:clientToken', {
    controller: 'AuthorizeCtrl',
    template: '<h1>Authorized</h1><p>Redirecting...</p>'
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
}]);
