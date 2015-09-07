angular.module('ngBoilerplate.account', ['ui.router', 'ngResource', 'base64'])
    .config(function ($stateProvider) {
        $stateProvider
            .state('login', {
                url: '/login',
                views: {
                    'main': {
                        templateUrl: 'account/login.tpl.html',
                        controller: 'LoginCtrl'
                    }
                },
                data: {pageTitle: "Login"}
            })
            .state('register', {
                url: '/register',
                views: {
                    'main': {
                        templateUrl: 'account/register.tpl.html',
                        controller: 'RegisterCtrl'
                    }
                },
                data: {pageTitle: "Registration"}
            });
    })

    .factory('sessionService', function ($http, $base64) {
        var session = {};
        session.login = function (data) {
            return $http.post("/aimessage/login", "username=" + data.login +
                "&password=" + data.password, {
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function (data) {
                localStorage.setItem("session", {});
            }, function (data) {
                alert("error logging in");
            });
        };
        session.logout = function () {
            localStorage.removeItem("session");
        };
        session.isLoggedIn = function () {
            return localStorage.getItem("session") !== null;
        };
        return session;
    })

    .factory('accountService', function ($resource) {
        var service = {};
        service.register = function (account, success, failure) {
            var Account = $resource("/aimessage/rest/accounts");
            Account.save({}, account, success, failure);
        };
        service.getAccountById = function (accountId) {
            var Account = $resource("/aimessage/rest/accounts/:paramAccountId");
            return Account.get({paramAccountId: accountId}).$promise;
        };
        service.userExists = function (account, success, failure) {
            var Account = $resource("/aimessage/rest/accounts");
            var data = Account.get({name: account.login, password: account.password}, function () {
                    var accounts = data.accounts;
                    if (accounts.length !== 0) {
                        success(account);
                    } else {
                        failure();
                    }
                },
                failure);
        };
        service.getAllAccounts = function () {
            var Account = $resource("/aimessage/rest/accounts");
            return Account.get().$promise.then(function (data) {
                return data.accounts;
            });
        };
        return service;
    })

    .controller("LoginCtrl", function ($scope, sessionService, accountService, $state) {
        $scope.login = function () {
            accountService.userExists($scope.account, function (account) {
                    sessionService.login($scope.account).then(function () {
                        $state.go("home");
                    });
                },
                function () {
                    alert("Error logging in user");
                });
        };
    })

    .controller("RegisterCtrl", function ($scope, sessionService, $state, accountService) {
        $scope.register = function () {
            accountService.register($scope.account,
                function (returnedData) {
                    sessionService.login($scope.account).then(function () {
                        $state.go("home");
                    });
                },
                function () {
                    alert("Error registering user");
                });
        };
    })

;