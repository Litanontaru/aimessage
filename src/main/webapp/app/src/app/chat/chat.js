angular.module('ngBoilerplate.chat', [
    'ui.router',
    'placeholders',
    'ngResource',
    'ui.bootstrap',
    'ngBoilerplate.account'
])

    .config(function config($stateProvider) {
        $stateProvider.state('chat', {
            url: '/chat',
            views: {
                "main": {
                    controller: 'ChatCtrl',
                    templateUrl: 'chat/chat.tpl.html'
                }
            },
            data: {pageTitle: 'Chats'}
        });
    })

    .factory('chatService', function ($resource, sessionService) {
        var service = {};
        service.getAllChats = function () {
            if (sessionService.isLoggedIn()) {
                var chats = $resource("/aimessage/rest/chat/all");
                return chats.get().$promise.then(function (data) {
                    return data.chats;
                });
            } else {
                return [];
            }
        };
        service.createChat = function (chat, success, failure) {
            var Chat = $resource("/aimessage/rest/chat");
            Chat.save({}, chat, success, failure);
        };
        service.removeChat = function (chat, success) {
            var ls = chat.links;
            if (ls != null) {
                for (var i in ls) {
                    if (ls.hasOwnProperty(i) && ls[i].rel == "rm") {
                        var Chat = $resource(ls[i].href);
                        return Chat.get().$promise.then(success);
                    }
                }
            }
        };
        return service;
    })

;