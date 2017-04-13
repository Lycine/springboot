$(document).ready(function(){
    $(".close").click(function(){
        $(this).parent().remove();
    });
    setTimeout('$(".autoDismiss").remove()',2000);
});

