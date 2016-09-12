"use strict";

var restBaseUrl;
AJS.toInit(function () {
    AJS.messages.generic({
        title: 'This is a title in a default message.',
        body: '<p> And this is just content in a Default message.</p>'
    });
    AJS.$("#workflow-pools").auiSelect2();
    
    AJS.$(document).ready(function() {
        AJS.$('#duedate').datePicker({'overrideBrowserDefault': true});
    });
    var baseUrl = AJS.params.baseURL;
    restBaseUrl = baseUrl + "/rest/estimation/1.0/";
});

function getSettings() {
    var projectId = document.getElementById("project").value
    var method = document.getElementById("method-select").value
    method = method.replace(" ", "").toLowerCase()

    var settingsdata = AJS.$.ajax({
        type: 'GET',
        url: restBaseUrl+'settings/'+projectId+'/'+method,
        contentType: "application/json"
    });
    AJS.$.when(settingsdata)
        .done(function()
        {
          AJS.messages.generic({
              title: 'Jupidoo basil.',
              body: '<p> es hat fuktioniert yay</p>'
          })
            var json = JSON.parse(settingsdata.responseText)
            alert(json.project)
        })
        .fail(function(e)
        {
            AJS.messages.generic({
                title: 'ned so geil',
                body: '<p> ned so geil</p>'
            })
            console.log(e)
        })
}
 function checkforsettings()
 {
     
 }