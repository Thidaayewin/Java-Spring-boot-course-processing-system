// Function to populate the edit modal with lecturer details
function populateEditModal(courseId,courseName,credits) {
    $("#courseId").val(courseId);
    $('#courseName').val(courseName);
    $('#credits').val(credits);
  }

  $(document).ready(function() {
    $('.cohort-button').click(function() {
        var courseId = $(this).data('courseid');
        window.location.href = "/admin/course/cohort/list/" + courseId;
    });
});

  $(document).ready(function () {
    // Handle click event on Edit button
    $('[data-target="#editModal"]').click(function () {
      
      var courseId = $(this).data("courseid");
      var courseName = $(this).data("coursename");
      var credits = $(this).data("credits");
      
      populateEditModal(courseId,courseName,credits);
    });

    $('[data-target="#deleteModal"]').click(function () {
      
      var courseId = $(this).data("courseid");
      $("#courseIdDelete").val(courseId);
    });
  });
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
  
//update
  $(document).ready(function() {
    // Handle form submission
    $('#updateButton').click(function() {
      var courseId =  $('#courseId').val();
      var courseName = $('#courseName').val();
      var credits = $('#credits').val();

      var data = {
        id: courseId,
        courseName: courseName,
        credits: credits
      };

      // Send the data to the server using AJAX
      $.ajax({
        type: 'POST',
        url: '/admin/course/update', // Replace with the appropriate URL for your server
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(response) {
          swal("Successful!", "Update Successfully!", "success").then((value) => {
            window.location.reload();
          });
        },
        error: function(xhr, status, error) {
          if (xhr.status === 500) {
              swal("Failed updating!", "Internal Server Error", "error");
          } else if (xhr.status === 404) {
              swal("Failed updating!", "Not Found", "error");
          } else if (xhr.status === 400) {
            var showMessage="";
            var errorResponse = JSON.parse(xhr.responseText);
            var errorMessages = [];
        
            if (Array.isArray(errorResponse.errors)) {
              
              errorResponse.errors.forEach(function(error) {
                errorMessages.push(error.defaultMessage); 
              });
            } else {
              errorMessages.push(errorResponse);
            }
            
            errorMessages.forEach(function(errorMessage) {
              showMessage += errorMessage+"\n";
            });
            swal("Failed updating!", showMessage, "error");
          }else{
            swal("Failed updating!", "Error updating course: " + error, "error");
          }
      }
      });

      // Close the modal after updating
      
      
    });
  });

  //create
  $(document).ready(function() {
    $('#createButton').click(function() {
      var courseId =  0;
      var courseName = $('#courseNameCreate').val();
      var credits = $('#creditsCreate').val();
      var data = {
        id: courseId,
        courseName: courseName,
        credits: credits
      };

      // Send the data to the server using AJAX
      $.ajax({
        type: 'POST',
        url: '/admin/course/create', 
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(response) {
          // console.log('Course created successfully');
          // window.location.reload();
          swal("Successful!", "Create Successfully!", "success").then((value) => {
            window.location.reload();
          });
        },
        error: function(xhr, status, error) {
          if (xhr.status === 500) {
              swal("Failed updating!", "Internal Server Error", "error");
          } else if (xhr.status === 404) {
              swal("Failed updating!", "Not Found", "error");
          }  else if (xhr.status === 400) {
            var showMessage="";
            var errorResponse = JSON.parse(xhr.responseText);
            var errorMessages = [];

            if (Array.isArray(errorResponse.errors)) {

              errorResponse.errors.forEach(function(error) {
                errorMessages.push(error.defaultMessage);
              });
            } else {
              errorMessages.push(errorResponse);
            }

            errorMessages.forEach(function(errorMessage) {
              showMessage += errorMessage+"\n";
            });
            swal("Failed updating!", showMessage, "error");
          }else{
            swal("Failed updating!", "Error updating course: " + error, "error");
          }
      }
      });

    });
  });

  //delete
  $(document).ready(function() {
    $('#deleteButton').click(function() {
      var courseId =  $('#courseIdDelete').val();
      var data = {
        id: courseId,
        courseName: "",
        credits: 0
      };

      $.ajax({
        type: 'DELETE',
        url: '/admin/course/delete', 
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(response) {
          // console.log('Course deleted successfully');
          // window.location.reload();
          swal("Successful!", "Delete Successfully!", "success").then((value) => {
            window.location.reload();
          });
        },
        error: function(xhr, status, error) {
          if (xhr.status === 500) {
              swal("Failed updating!", "Internal Server Error", "error");
          } else if (xhr.status === 404) {
              swal("Failed updating!", "Not Found", "error");
          }else if (xhr.status === 400) {
            swal("Failed updating!", xhr.responseText, "error");
        } else {
              swal("Failed updating!", "Error updating course: " + error, "error");
          }
      }
      });

      
    });
  });

 
  