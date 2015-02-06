/**
 * Created by Anton.Nekrasov on 8/15/2014.
 */

/* global ko*/

define(["application/viewModel/loginVM",
        "application/viewModel/listVM",
        "application/viewModel/detailsVM"], function (loginVM, listVM, detailsVM) {

    "use strict";

    function ViewModel(){
        var self = this,
            login = {
                name: "Login",
                id: "lgn"
            },
            list = {
                name: "List",
                id: "lst"
            },
            details = {
                name: "Details",
                id: "det"
            };

        self.loginVM = loginVM;
        self.listVM = listVM;
        self.detailsVM = detailsVM;
        self.sections = [login, list, details];
        self.chosenSectionId = ko.observable();
        self.user = ko.observable();

        self.goTo = function(sectionId) {
            location.hash = sectionId;
        };

        Sammy(function() {
            this.get('#:section', function() {
                var sectionId = this.params.section;
                for(var ind in self.sections) {
                    if(self.sections.hasOwnProperty(ind) && self.sections[ind].id === sectionId) {
                        self.chosenSectionId(self.sections[ind]);
                    }
                }
            });

            this.get("/", function() {
                location.hash = "lgn";
            });

        }).run();

        $("body").on("authorized", function(evt, user) {
            self.user(user);
            self.listVM.list(1, self.listVM.PAGE_SIZE);
            self.goTo("lst");
        });

        $.ajaxSetup({
            global: true,
            error: function(xhr) {
                if (xhr.status == 401) {
                    location.hash = "lgn";
                }
            }
        });
    }

    var launch = function() {
        ko.applyBindings(new ViewModel());
    };

    return {
        launch: launch,
        goTo: self.goTo
    }

});