angular.module('ngBoilerplate.home', [
    'ui.router',
    'plusOne',
    'ngBoilerplate.account',
    'ngBoilerplate.chat'
])

    .config(function config($stateProvider) {
        $stateProvider.state('home', {
            url: '/home',
            views: {
                "main": {
                    controller: 'HomeCtrl',
                    templateUrl: 'home/home.tpl.html'
                }
            },
            resolve: {
                chats: function (chatService) {
                    return chatService.getAllChats();
                }
            },
            data: {pageTitle: 'Home'}
        });
    })

    .controller('HomeCtrl', function HomeController($scope, $state, $stateParams, sessionService, chatService, chats) {
        $scope.isLoggedIn = sessionService.isLoggedIn;
        $scope.logout = sessionService.logout;
        $scope.chats = chats;

        $scope.showNewChat = function () {
            localStorage.setItem("newChat", {});
            $scope.newChat.name = "";
        };
        $scope.createChat = function () {
            chatService.createChat($scope.newChat,
                function (returnedData) {
                    localStorage.removeItem("newChat");
                    $state.go("home", $stateParams, {
                        reload: true,
                        inherit: false,
                        notify: true
                    });
                },
                function () {
                    localStorage.removeItem("newChat");
                    alert("Error creating new chat");
                });
        };
        $scope.isNewChat = function () {
            return localStorage.getItem("newChat") !== null;
        };
        $scope.remove = function(chat) {
            chatService.removeChat(chat, function() {
                $state.go("home", $stateParams, {
                    reload: true,
                    inherit: false,
                    notify: true
                });
            });
        };
    })

;