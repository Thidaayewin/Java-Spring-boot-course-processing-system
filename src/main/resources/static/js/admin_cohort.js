// Function to populate the edit modal with lecturer details
function populateEditModal(cohortid,courseid,courseName,name,description,capacity,classday,classslot,cohortstart,lecturer) {
    $("#editCohortId").val(cohortid);
    $("#editCourseId").val(courseid);
    $('#editCourseName').val(courseName);
    $('#editName').val(name);
    $('#editDescription').val(description);
    $('#editCapacity').val(capacity);
    $('#editClassDay').val(classday);
    $('#editClassSlot').val(classslot);
    $('#editCohort_start').val(cohortstart);
    $('#editLecturer').val(lecturer);
  }

  $(document).ready(function () {
    // Handle click event on Edit button
    $('[data-target="#editModal"]').click(function () {
     
      var cohortid = $(this).data("cohortid");
      var courseid = $(this).data("courseid");
      var courseName = $(this).data("coursename");
      var name = $(this).data("name");
      var description = $(this).data("description");
      var capacity = $(this).data("capacity");
      var classday = $(this).data("classday");
      var classslot = $(this).data("classslot");
      var cohortstart = $(this).data("cohort_start");
      var lecturer = $(this).data("lecturer");
      populateEditModal(cohortid,courseid,courseName,name,description,capacity,classday,classslot,cohortstart,lecturer);
    });

    $('[data-target="#deleteModal"]').click(function () {
      
      var cohortid = $(this).data("cohortid");
      $("#cohortIdDelete").val(cohortid);
    });
    $('[data-target="#createModal"]').click(function () {
      var path = window.location.pathname;
      var courseid = path.substring(path.lastIndexOf('/') + 1);
      // var courseid = $(this).data("courseid");
      $("#createCourseId").val(courseid);
    });
  });
  $(document).ready(function() {
    $('#cohortDataTable').DataTable({
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
      
      var cohortid = $('#editCohortId').val();
      var courseid = $('#editCourseId').val();
      var courseName =$('#editCourseName').val();
      var name = $('#editName').val();
      var description = $('#editDescription').val();
      var capacity = $('#editCapacity').val();
      var classday = $('#editClassDay').val();
      var classslot = $('#editClassSlot').val();
      var cohortstart = $('#editCohort_start').val();
      var lecturer = $('#editLecturer').val();
      var data = {
        cohortId: parseInt(cohortid),
        courseId: parseInt(courseid),
        courseName: courseName,
        name: name,
        description: description,
        capacity: capacity,
        classDay: classday,
        classSlot: classslot,
        cohort_start: cohortstart,
        lecturer:lecturer,
        lecturerName:""
      };

      // Send the data to the server using AJAX
      $.ajax({
        type: 'POST',
        url: '/admin/course/cohort/update', // Replace with the appropriate URL for your server
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(response) {
          
          swal("Successful!", "update successfully!", "success").then((value) => {
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

  //create
  $(document).ready(function() {
    $('#createButton').click(function() {
      var cohortid = 0;
      var courseid = $('#createCourseId').val();
      var courseName =$('#createCourseName').val();
      var name = $('#createName').val();
      var description = $('#createDescription').val();
      var capacity = $('#createCapacity').val();
      var classday = $('#createClassDay').val();
      var classslot = $('#createClassSlot').val();
      var cohortstart = $('#createCohort_start').val();
      var lecturer = $('#createLecturer').val();
      var data = {
        cohortId: parseInt(cohortid),
        courseId: parseInt(courseid),
        courseName: courseName,
        name: name,
        description: description,
        capacity: capacity,
        classDay: classday,
        classSlot: classslot,
        cohort_start: cohortstart,
        lecturer:lecturer,
        lecturerName:""
      };

      $.ajax({
        type: 'POST',
        url: '/admin/course/cohort/create', 
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(response) {
          swal("Successful!", "Create Successfully!", "success").then((value) => {
            window.location.reload();
          });
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
      var cohortid = $('#cohortIdDelete').val();
      
      var data = {
        cohortId: cohortid,
        courseId: 0,
        courseName: "",
        name: "",
        description: "",
        capacity: 0,
        classDay: 0,
        classSlot: 0,
        cohort_start: "",
        lecturer:0,
        lecturerName:""
      };

      $.ajax({
        type: 'DELETE',
        url: '/admin/course/cohort/delete', 
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(response) {
          swal("Successful!", "Delete Successfully!", "success").then((value) => {
            window.location.reload();
          });
        },
        error: function(xhr, status, error) {
          if (xhr.status === 500) {
              swal("Failed updating!", "Internal Server Error", "error");
          } else if (xhr.status === 400) {
            swal("Deletion not allowed as there are existing students in cohort", xhr.responseText, "error");
          } else if (xhr.status === 404) {
            swal("Failed updating!", "Not Found", "error");
          } else {
              swal("Failed updating!", "Error updating course: " + error, "error");
          }
      }
      });
    });
  });

 
  