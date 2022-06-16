function confirmDel(ObjectName, delPath){
    $("#delBoxObjectName").html(ObjectName);
    
    $("#delBoxConfirm").click(function() {
        window.location.href=delPath;
    });
    
    $("#delBox").modal({
        show: true
    });
}