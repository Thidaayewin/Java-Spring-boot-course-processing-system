function enroll(url) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4) {
        if (xhr.status === 200) {
          var response = JSON.parse(xhr.responseText);
          // alert("You have successfully enrolled.");
          swal("Successful!", "Enroll successfully!", "success").then((value) => {
            window.location.href = "/student/view-all-courses";
          });
        } else {
          //alert("You failed to enroll. Error: " + xhr.responseText);
          swal("Failed updating!", "Internal Server Error", "error");
        }
      }else if(xhr.readyState===2){
        swal("Successful!", "Enrolled successfully!", "success").then((value) => {
          window.location.href = "/student/view-all-courses";
        });
      }
    };
    xhr.send(JSON.stringify({ student: {}, cohort: {}, enrollmentStatus: "" }));
  }


  $(document).ready(function() {
    $('#courseDataTable').DataTable({
      'aoColumnDefs':[{'bSortable':false,'aTargets':[-1,-2]}],
      dom: '<"top"lfB>rt<"bottom"ip><"clear">',
      buttons: [
        {
          extend: 'copy',
          className: 'custom-copy-btn btn-icon',
          text: '<i class="fas fa-copy"></i> Copy',
        },
        {
          extend: 'csv',
          className: 'custom-csv-btn btn-icon',
          text: '<i class="fas fa-file-csv"></i> CSV',
        },
        {
          extend: 'excel',
          className: 'custom-excel-btn btn-icon',
          text: '<i class="fas fa-file-excel"></i> Excel',
        },
        {
          extend: 'pdf',
          className: 'custom-pdf-btn btn-icon',
          text: '<i class="fas fa-file-pdf"></i> PDF',
        },
        {
          extend: 'print',
          className: 'custom-print-btn btn-icon',
          text: '<i class="fas fa-print"></i> Print',
        }
      ],
      scrollX: true
    });
  });