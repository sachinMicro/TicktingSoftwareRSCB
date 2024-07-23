


$(document).ready(function () {
    new DataTable('#billSummary', {
        info: false,
        ordering: true,
        paging: false,
        searching: false
    });

  $('form input[type=checkbox] , [type=number] ').on("click",function(){
             console.log("Hello "+$(this).val());
  });
  $('form input[type=number]').on("keyup",function(){
    console.log("Hello "+$(this).val());
 });

});