var documentIdForShare = null;

function selectDocument(obj) {
    if ($(obj).hasClass('selected')) {
        $(obj).removeClass('selected');
        documentIdForShare = null;
    } else {
        $(obj).addClass('selected').siblings().removeClass('selected');
        documentIdForShare = obj.getAttribute('parameterId');
    }
    console.log(documentIdForShare)
}

function share(obj) {
    if (documentIdForShare != null) {
        var userId = obj.getAttribute("parameterId");
        if (userId != null) {
            fetch('/permissions/document/' + documentIdForShare + '/share/' + userId, {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            })
        }
    }
}