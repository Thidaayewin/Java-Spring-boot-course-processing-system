
//Filter course list
  $(document).ready(function() {
    // Add event listener to the select element
    $('#courseDropdown').on('change', function() {
      var selectedCourse = $(this).val();
      
      // Iterate through each table row and filter based on selected course
      $('#CourseDataTable tbody tr').each(function() {
        var courseName = $(this).find('td:nth-child(2)').text();
        if (selectedCourse === "" || courseName === selectedCourse) {
          $(this).show();
        } else {
          $(this).hide();
        }
      });
    });
  });
  
  $(document).ready(function() {
    $('#CourseDataTable').DataTable({
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