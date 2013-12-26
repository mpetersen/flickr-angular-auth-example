angular.module('app', ['ngRoute'/*, 'ngResource'*/])
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
//.controller('PhotosCtrl', ['$scope', '$http', '$timeout', '$resource', '$log', function($scope, $http, $timeout, $resource, $log) {
//  var Photos = $resource('/api/photos');
//  
//  var pages = undefined;
//  
//  var loading = {
//    show: function() {
//      $scope.loading = true;
//    },
//    hide: function() {
//      $scope.loading = false;
//    }
//  };
//  
//  $scope.search = function() {
//    loading.show();
//    $scope.photos = Photos.get({ text: $scope.text, per_page: 10, page: $scope.page }, 
//      function() {
//        pages = $scope.photos.photos.pages;
//        $scope.page = $scope.photos.photos.page;
//        $log.info(pages);
//        $log.info($scope.page);
//        loading.hide();
//      }, 
//      function() {
//        loading.hide();
//        alert('Error searching photos. Status: ' + status);
//      });
//  };
//  
//  $scope.hasNextPage = function() {
//    return $scope.page < pages;
//  };
//  
//  $scope.nextPage = function() {
//    $log.info($scope.hasNextPage());
//    if ($scope.hasNextPage()) {
//      $scope.page++;
//      $scope.search();
//    }
//  };
//  
//  $scope.hasPrevPage = function() {
//    return $scope.page > 1;
//  };
//  
//  $scope.prevPage = function() {
//    if ($scope.hasPrevPage()) {
//      $scope.page--;
//      $scope.search();
//    }
//  };
//  
//  $scope.text = "";
//  $scope.page = 1;
//  $scope.search();
//}])
//.controller('AuthorizeCtrl', ['$location', '$routeParams', function($location, $routeParams){
//  var clientToken = $routeParams['clientToken'];
//  localStorage['clientToken'] = clientToken;
//  var locationPath = localStorage['locationPath'];
//  localStorage.removeItem('locationPath');
//  $location.path(locationPath);
//}])
//.config(function($routeProvider) {
//  $routeProvider
//  .when('/authorize/:clientToken', {
//    controller: AuthorizeCtrl,
//    templateUrl: '/templates/authorize.html'
//  })
//  .otherwise({
//    controller: PhotosCtrl,
//    templateUrl: '/templates/photos.html'
//  });
//})
//.config(function($httpProvider) {
//  $httpProvider.interceptors.push(function($q, $location) {
//    return {
//      'request': function(config) {
//        config.headers['clientToken'] = localStorage['clientToken'];
//        return config;
//      },
//      'responseError': function(response) {
//        if (response.status === 401) {
//          localStorage['locationPath'] = $location.path();
//          window.location.href = '/api/flickr/authorize';
//        }
//        return $q.reject(response);
//      }
//    };
//  });
//});
