// Function to populate the edit modal with lecturer details
var studentcohortid;
var selectedCohort=null;
function populateEditModal(studentid,cohortid) {
    $("#studentIdUpdate").val(studentid);
    $("#cohortIdUpdate").val(cohortid);
  }

//data binding
  $(document).ready(function () {
    loadCourse();
    // Handle click event on Edit button
    $('[data-target="#exampleModal"]').click(function () {
        var studentid = $(this).data("studentid");
        var cohortid = $(this).data("cohortid");
       // alert(studentcohortid);
      populateEditModal(studentid,cohortid);
    });
   
  });

  //update
$(document).ready(function() {
    $('#updateButton').click(function() {
      //test//
      
      var studentid =  $('#studentIdUpdate').val();
      var cohortid =  $('#cohortIdUpdate').val();
      var enrollmentScore = $('#scoreId').val();
      var data = {
          studentid: studentid,
          cohortid:cohortid,
          score: enrollmentScore
      };
  
      // Send the data to the server using AJAX
      $.ajax({
        type: 'POST',
        url: '/student/editScore', 
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(response) {
          console.log('Enrollment Status updated successfully');
          window.location.reload();
        },
        error: function(error) {
          console.error('Error updating lecturer:', error);
        }
      });
    });





    //Filter student list by course/cohort value
  
    var originalRows=[]; //need to call to fetch all student data
    var filteredRows = originalRows;
    // fetchAllStudentsInCohort(selectedCohortId)
    // console.log(filteredRows)
    // fetchAllRows();

    //Course Dropdown
    $('#courseDropdown').on('change', function() {
      selectedCohort=null;
      loadCourse(selectedCohort);
    });

    //Cohort Dropdown
    // Add event listener to the select element
    $('#cohortDropdown').on('change', function() {
      selectedCohortId = $(this).val();
    });

    $('#filterButton').on('click', function() {
      var selectedCohortId = $('#cohortDropdown option:selected').val();
      var selectedCourseId=$('#courseDropdown option:selected').val();

      // testing condition for dropdown
      if (selectedCohortId === '' || selectedCourseId === '') {
        return;
      }
      
      //update url
      var newUrl='/student/list/'+ selectedCourseId +'/'+ selectedCohortId;
      history.pushState(null,null,newUrl);
      location.reload();
      
      // fetchAllRows();
      // fetchAllStudentsInCohort(selectedCohortId)
      // filteredRows=originalRows;
      // console.log(filteredRows)

      // Iterate through each table row and filter based on selected cohort
      filteredRows.each(function() {
        var cohortId = $(this).find('td:nth-child(1)').text();
        if (selectedCohortId === "" || cohortId === selectedCohortId) {
          $(this).show();
        } else {
          $(this).hide();
        }
      });

      selectedCohort=$('#cohortDropdown').val();
      loadCourse(selectedCohort);

  });

});

function loadCourse(selectedCohort){
  selectedCourseId = $("#courseDropdown").val();
   if(selectedCourseId == "") selectedCourseId=0;
   $.ajax({
    type: 'GET',
    url: '/cohorts/' + selectedCourseId,
    success: function(data) {
      // $('#cohortDropdown').val();
      

      // Clear the existing options in the cohort dropdown
      $('#cohortDropdown').empty();
  
      // Add the default "Select Cohort" option
      $('#cohortDropdown').append($('<option>').val('').text('Select Cohort'));
  
      // Iterate through the retrieved cohorts and add them as options in the cohort dropdown
      data.forEach(function(cohort) {
        $('#cohortDropdown').append($('<option>').val(cohort.id).text(cohort.name));
      });

      $('#cohortDropdown').val(selectedCohort);

      if (selectedCohort == null) {
        $('#cohortDropdown').find('option:first').text('Select Cohort');
      }
      console.log('Cohort list updated successfully');
    },
    error: function(error) {
      console.error('Error updating cohort:', error);
    }
  });
}

//NOT USING
// function fetchAllRows() {
//   $.ajax({
//     type: 'GET',
//     // url: 'https://localhost:8443/api/students/all', // Replace with the appropriate endpoint to fetch all rows from the server
//     success: function(response) {
//       originalRows = response; // Assign the retrieved rows to originalRows
//       // Perform any additional operations on the retrieved rows if needed
//       console.log('Original rows fetched successfully');
//     },
//     error: function(error) {
//       console.error('Error fetching original rows:', error);
//     }
//   });
// }


$(document).ready(function() {
  $('#StudentsDataTable').DataTable({
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
    function goBack() {
      window.location.href = "/lecturer";
    }


    $(document).ready(function() {
      $('#viewButton').click(function() {

    
      var currentUrl = window.location.href;
      var previousUrls = JSON.parse(localStorage.getItem("previousUrls")) || [];
      previousUrls.forEach(function(element){
        if(element.includes("/student/list/")){
          previousUrl=element;
        }
      
      });
      previousUrls.push(currentUrl);
      localStorage.setItem("previousUrls", JSON.stringify(previousUrls));
    
  });
});
