window.onbeforeunload = function (e) {
    if (window.isCallInProgress){
        return "An call is currently in progress. If you exit the page, the call will stop. Are you sure you want to exit the page?";
    }
};