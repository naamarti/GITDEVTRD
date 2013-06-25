function _error_displayDetails()
{
  var item = document.getElementById("errorMessage");
  item.style.visibility="visible";
}

var error = new Object();
error.displayDetails=_error_displayDetails;
