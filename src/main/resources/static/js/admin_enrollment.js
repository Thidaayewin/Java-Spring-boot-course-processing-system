// Function to populate the edit modal with lecturer details
var studentcohortid;
function populateEditModal(studentcohortid) {
    $("#enrollmentIdUpdate").val(studentcohortid);
  }
//data binding
  $(document).ready(function () {
    // Handle click event on Edit button
    $('[data-target="#editModal"]').click(function () {
        studentcohortid = $(this).data("studentcohortid");
      populateEditModal(studentcohortid);
    });

    $('[data-target="#deleteModal"]').click(function () {
        studentcohortid = $(this).data("studentcohortid");
      $("#enrollmentIdDelete").val(studentcohortid);
    });
  });

  $(document).ready(function() {
    $('#enrollmentDataTable').DataTable({
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

//update
$(document).ready(function() {
  $('#updateButton').click(function() {
    var enrollmentId =  $('#enrollmentIdUpdate').val();
    var enrollmentStatus = $('#updateEnrollmentStatus').val();
    var data = {
        id: enrollmentId,
        enrolmentStatus: enrollmentStatus
    };

    // Send the data to the server using AJAX
    $.ajax({
      type: 'POST',
      url: '/admin/enrollment/update', 
      data: JSON.stringify(data),
      contentType: 'application/json',
      success: function(response) {
        swal("Successful!", "Enrollment Status updated successfully!", "success").then((value) => {
          window.location.reload();
        });
        // console.log('Enrollment Status updated successfully');
        // window.location.reload();
      },
      error: function(xhr, status, error) {
        if (xhr.status === 500) {
            swal("Failed updating!", "Internal Server Error", "error");
        } else if (xhr.status === 404) {
            swal("Failed updating!", "Not Found", "error");
        } else {
            swal("Failed updating!", "Error updating course: " + error, "error");
        }
    }
    });

  });
});

//delete
$(document).ready(function() {
  $('#deleteButton').click(function() {
    var enrollmentId =  $('#enrollmentIdDelete').val();
    var data = {
      id: enrollmentId,
      enrolmentStatus: ""
    };

    $.ajax({
      type: 'DELETE',
      url: '/admin/enrollment/delete', 
      data: JSON.stringify(data),
      contentType: 'application/json',
      success: function(response) {
        console.log('Enrollment deleted successfully');
        window.location.reload();
      },
      error: function(xhr, status, error) {
        if (xhr.status === 500) {
            swal("Failed updating!", "Internal Server Error", "error");
        } else if (xhr.status === 404) {
            swal("Failed updating!", "Not Found", "error");
        } else {
            swal("Failed updating!", "Error updating course: " + error, "error");
        }
    }
    });

    
  });
});

