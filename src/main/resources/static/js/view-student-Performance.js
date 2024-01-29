
function goBack() {
  var previousUrl =""// getLastMatchingUrl("/student/list"); // Specify your desired path pattern

  var previousUrls = JSON.parse(localStorage.getItem("previousUrls")) || [];
    var lastMatchingUrl = "";
    
    for (var i = previousUrls.length - 1; i >= 0; i--) {
        if (previousUrls[i].includes("/student/list")) {
          lastMatchingUrl = previousUrls[i];
          break;
        }
      }

      previousUrl=lastMatchingUrl;
  // Redirect back to the previous URL
  window.location.href = previousUrl;
}


    