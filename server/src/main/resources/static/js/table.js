var value = null;

function rowClicked(obj) {
    if ($(obj).hasClass('selected')) {
        $(obj).removeClass('selected');
        value = null;
    } else {
        $(obj).addClass('selected').siblings().removeClass('selected');
        value = obj.getAttribute('parameterId');
    }
    console.log(value)
}

function share(obj) {
    if (value != null) {
        var userId = obj.getAttribute("parameterId");
        if (userId != null) {
            fetch('/permissions/document/' + value + '/share/' + userId, {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            })
        }
    }

}

function loadPermissions() {
    fetch('/permissions/documents/available', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    })
}

function loadDocuments() {
    fetch('/home', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    })
}


