"use strict";

var restBaseUrl;
AJS.toInit(function () {
    var baseUrl = AJS.params.baseURL;
    restBaseUrl = baseUrl + "/rest/estimation/1.0/";

    AJS.messages.generic({
        title: 'This is a title in a default message.',
        body: '<p> And this is just content in a Default message.</p>'
    });
    AJS.$("#workflow-pools").auiSelect2();

    AJS.$(document).ready(function() {
        AJS.$('#duedate').datePicker({'overrideBrowserDefault': true});
    });
});

function getSettings() {
    var projectId = document.getElementById("project").value
    var project_name = document.getElementById("project").text
    var method = document.getElementById("method-select").value
    method = method.replace(" ", "").toLowerCase()

    var settingsdata = AJS.$.ajax({
        type: 'GET',
        url: restBaseUrl+'check_settings/'+projectId+'/'+method,
        contentType: "application/json"
    });
    AJS.$.when(settingsdata)
        .done(function()
        {
          AJS.messages.generic({
              title: 'Succsess',
              body: '<p> Settings for' + project_name + 'were successfully saved</p>'
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

function checkForSaveResponse()
{
    var form_data = {}
    var method = document.getElementById("method-select").value;
    form_data.method = method.toString().toLowerCase().replace(" ","");
    form_data.project= document.getElementById("project").value
    form_data.programmers = document.getElementById("programmers").value
    form_data.based = document.getElementById("based").value
    form_data.pools = AJS.$("#workflow-pools").val()

    alert(JSON.stringify(form_data))
    var save_status = AJS.$.ajax(
        {
            type: 'POST',
            url: restBaseUrl+'save_settings/1040/forward',
            contentType: "application/json",
            data: JSON.stringify(form_data),
            success: function(data)
            {
                alert(data.responseText)
            }
        }
    );
/*    AJS.$.when(save_status)
        .done(function() {
            alert("rest call worked")

        })
        .fail(function (e) {
            alert("rest call FAILED !!!!!!!!!")
        });*/
    return false;
}