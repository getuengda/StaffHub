// replaced the “Status” text input in edit training with a dropdown
function checkOther(select) {
    var otherStatus = document.getElementById('otherStatus');
    if (select.value == 'other') {
        otherStatus.style.display = 'block';
    } else {
        otherStatus.style.display = 'none';
    }
}
