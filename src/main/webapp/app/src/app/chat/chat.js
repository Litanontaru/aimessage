angular.module( 'ngBoilerplate.chat', [
  'ui.router',
  'placeholders',
  'ngResource',
  'ui.bootstrap'
])

.config(function config( $stateProvider ) {
  $stateProvider.state( 'chat', {
    url: '/chat',
    views: {
      "main": {
        controller: 'ChatCtrl',
        templateUrl: 'chat/chat.tpl.html'
      }
    },
    data:{ pageTitle: 'Chats' }
  });
})

.factory('chatService', function($resource) {
  var service = {};
  service.getChats = function() {
    var chats = $resource("/aimessage/rest/chat/all");
    return chats.get().$promise.then(function(data) {
      return data.chats;
    });
  };
  return service;
})

;