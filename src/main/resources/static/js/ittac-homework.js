//jQuery time
var current_fs, next_fs, previous_fs; //fieldsets
var left, opacity, scale; //fieldset properties which we will animate
var animating; //flag to prevent quick multi-click glitches

$(".next").click(function () {
    if (animating) return false;
    animating = true;

    current_fs = $(this).parent();
    next_fs = $(this).parent().next();

    //activate next step on progressbar using the index of next_fs
    $("#progressbar li").eq($("fieldset").index(next_fs)).addClass("active");

    //show the next fieldset
    next_fs.show();
    //hide the current fieldset with style
    current_fs.animate({opacity: 0}, {
        step: function (now, mx) {
            //as the opacity of current_fs reduces to 0 - stored in "now"
            //1. scale current_fs down to 80%
            scale = 1 - (1 - now) * 0.2;
            //2. bring next_fs from the right(50%)
            left = (now * 50) + "%";
            //3. increase opacity of next_fs to 1 as it moves in
            opacity = 1 - now;
            current_fs.css({'transform': 'scale(' + scale + ')'});
            next_fs.css({'left': left, 'opacity': opacity});
        },
        duration: 800,
        complete: function () {
            current_fs.hide();
            animating = false;
        },
        //this comes from the custom easing plugin
        easing: 'easeInOutBack'
    });
});

$(".previous").click(function () {
    if (animating) return false;
    animating = true;

    current_fs = $(this).parent();
    previous_fs = $(this).parent().prev();

    //de-activate current step on progressbar
    $("#progressbar li").eq($("fieldset").index(current_fs)).removeClass("active");

    //show the previous fieldset
    previous_fs.show();
    //hide the current fieldset with style
    current_fs.animate({opacity: 0}, {
        step: function (now, mx) {
            //as the opacity of current_fs reduces to 0 - stored in "now"
            //1. scale previous_fs from 80% to 100%
            scale = 0.8 + (1 - now) * 0.2;
            //2. take current_fs to the right(50%) - from 0%
            left = ((1 - now) * 50) + "%";
            //3. increase opacity of previous_fs to 1 as it moves in
            opacity = 1 - now;
            current_fs.css({'left': left});
            previous_fs.css({'transform': 'scale(' + scale + ')', 'opacity': opacity});
        },
        duration: 800,
        complete: function () {
            current_fs.hide();
            animating = false;
        },
        //this comes from the custom easing plugin
        easing: 'easeInOutBack'
    });
});

$(".submit").click(function () {
    return false;
})


// 修改信息切换 密码／基本信息 start
function showPasswordForm() {
    $("#studentUpdateBasicForm").hide();
    $("#studentUpdatePasswordForm").show();
    $(".updatePass").addClass('active');
    $(".updateBasic").removeClass('active');
}
function showBasicForm() {
    $("#studentUpdateBasicForm").show();
    $("#studentUpdatePasswordForm").hide();
    $(".updatePass").removeClass('active');
    $(".updateBasic").addClass('active');
}
// 修改信息切换 密码／基本信息 end
// 作业列表 start
$("#toggle-filter").click(function () {
    var flag = $(this).attr('aria-expanded');
    if (flag == 'true') {
        $(this).text('显示');
    } else {
        $(this).text('隐藏');
    }
});
// 作业列表 end
// 信息中心，点击展开更多消息 start
$("#more-msg").click(function () {
    var flag = $(this).attr('aria-expanded');
    if (flag == 'false') {
        $(this).html('已读消息，只显示近两周消息 <span class="glyphicon glyphicon-chevron-down" aria-hidden="true"></span>');
    } else {
        $(this).html('已读消息，只显示近两周消息 <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>');
    }
});
// 信息中心，点击展开更多消息 end
// 任务日历 start
$(document).ready(function () {
    $('#calendar').fullCalendar({
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'listWeek,month,listMonth'
        },
        views: {
            listMonth: {buttonText: 'list month'},
            listWeek: {buttonText: 'list week'}
        },
        defaultDate: '2017-04-12',
        weekends: true,
        weekMode: 'liquid',
        firstDay: 1,
        defaultView: 'month',
        allDayText: '全天',
        businessHours: true,
        defaultEventMinutes: 120,
        eventLimit: true,
        dayClick: function (date) {
            //do something here...
            console.log('dayClick触发的时间为：', date.format());
            // ...
        },
        //设置是否可被单击或者拖动选择
        selectable: true,
        //点击或者拖动选择时，是否显示时间范围的提示信息，该属性只在agenda视图里可用
        selectHelper: true,
        //点击或者拖动选中之后，点击日历外的空白区域是否取消选中状态 true为取消 false为不取消，只有重新选择时才会取消
        unselectAuto: true,
        select: function (start, end) {
            //do something here...
            console.log('select触发的开始时间为：', start.format());
            console.log('select触发的结束时间为：', end.format());
            // ...
        },
        eventClick: function (event) {

            //do something here...
            $('#task-detail').modal('toggle')
            console.log('eventClick中选中Event的id属性值为：', event.id);
            console.log('eventClick中选中Event的title属性值为：', event.title);
            console.log('eventClick中选中Event的start属性值为：', event.start.format('YYYY-MM-DD HH:mm'));
//        console.log('eventClick中选中Event的end属性值为：', event.end.format('YYYY-MM-DD HH:mm'));
            console.log('eventClick中选中Event的color属性值为：', event.color);
            console.log('eventClick中选中Event的className属性值为：', event.className);
            // ...
        },
        eventMouseover: function (event) {
            //do something here...
            console.log('鼠标经过 ...');
            console.log('eventMouseover被执行，选中Event的title属性值为：', event.title);
            // ...
        },
        eventMouseout: function (event) {
            //do something here...
            console.log('eventMouseout被执行，选中Event的title属性值为：', event.title);
            console.log('鼠标离开 ...');
            // ...
        },
        //Event是否可被拖动或者拖拽
        editable: true,
        //Event被拖动时的不透明度
        dragOpacity: 0.5,
        eventDrop: function (event, dayDelta, revertFunc) {
            //do something here...
            console.log('eventDrop --- start ---');
            console.log('eventDrop被执行，Event的title属性值为：', event.title);
            if (dayDelta._days != 0) {
                console.log('eventDrop被执行，Event的start和end时间改变了：', dayDelta._days + '天！');
            } else if (dayDelta._milliseconds != 0) {
                console.log('eventDrop被执行，Event的start和end时间改变了：', dayDelta._milliseconds / 1000 + '秒！');
            } else {
                console.log('eventDrop被执行，Event的start和end时间没有改变！');
            }
            //revertFunc();
            console.log('eventDrop --- end ---');
            // ...
        },
        eventResize: function (event, dayDelta, revertFunc) {
            //do something here...
            console.log(' --- start --- eventResize');
            console.log('eventResize被执行，Event的title属性值为：', event.title);
            if (dayDelta._days != 0) {
                console.log('eventResize被执行，Event的start和end时间改变了：', dayDelta._days + '天！');
            } else if (dayDelta._milliseconds != 0) {
                console.log('eventResize被执行，Event的start和end时间改变了：', dayDelta._milliseconds / 1000 + '秒！');
            } else {
                console.log('eventResize被执行，Event的start和end时间没有改变！');
            }
            //revertFunc();
            console.log('--- end --- eventResize');
            // ...
        },
        events: [
            {
                title: '待完成作业',
                start: '2017-04-07',
                end: '2017-04-10',
                color: '#ffc107'
            },
            {
                title: '未交作业',
                start: '2017-04-09',
                color: '#f44336'
            },
            {
                title: '优秀作业',
                start: '2017-04-02',
                color: '#8bc34a'
            },
            {
                title: '已提交作业',
                start: '2017-04-12',
                color: '#2196f3'
            },
            {
                title: '糊弄作业',
                start: '2017-04-19',
                color: '#607d8b'
            }
        ]
    });

    //初始化语言选择的下拉菜单值
    $.each($.fullCalendar.langs, function (langCode) {
        $('#lang-selector').append(
            $('<option/>')
                .attr('value', langCode)
                .prop('selected', langCode == initialLangCode)
                .text(langCode)
        );
    });

    //当选择一种语言时触发
    $('#lang-selector').on('change', function () {
        if (this.value) {
            $('#calendar').fullCalendar('option', 'lang', this.value);
        }
    });

    var calendar_height = $("#calendar").css("height");
    var calendar_margin = $("#calendar").css("margin");
    $("#problem-task").css("height", calendar_height);
    $("#problem-task").css("margin", calendar_margin);
    var task_title_padding = $("#task-title").css("padding");
    $("#task-upload").css("padding", task_title_padding);
});
// 任务日历 end

// task-deployer start

$("#select2_class").select2({
    tags: true,
});
$("#select2_sort").select2({
    tags: true,
});
$("#select2_task").select2({
    tags: true,
});
$("#select2_student").select2({
    tags: true,
});
$("#select2_task_status").select2({
    tags: true,
});



$(function () {
    var json3 = '[{"text":"托福","nodes":[{"text":"听力","nodes":[{"text":"精听","nodes":[{"text":"托福-听力-精听-TPO1"},{"text":"托福-听力-精听-TPO2"},{"text":"托福-听力-精听-TPO3"},{"text":"托福-听力-精听-TPO4"}]},{"text":"模考+错题分析","nodes":[{"text":"托福-听力-模考+错题分析-TPO1"},{"text":"托福-听力-模考+错题分析-TPO2"},{"text":"托福-听力-模考+错题分析-TPO3"},{"text":"托福-听力-模考+错题分析-TPO4"}]}]}]}]';
    var $checkableTree = $('#treeview-checkable').treeview({
        data: json3,
        showIcon: false,
        showCheckbox: true,
        onNodeChecked: function (event, node) {
            $('#checkable-output').prepend('<div id=' + node.text + ' class="alert alert-info">' + node.text + '</div>');
        },
        onNodeUnchecked: function (event, node) {
            $("#" + node.text).remove();
        }
    });
    var findCheckableNodess = function () {
        var options = {
            ignoreCase: $('#chk-ignore-case').is(':checked'),
            exactMatch: $('#chk-exact-match').is(':checked'),
            revealResults: $('#chk-reveal-results').is(':checked')
        };
        return $checkableTree.treeview('search', [$('#input-check-node').val(), options]);
    };
    var checkableNodes = findCheckableNodess();

    // Check/uncheck/toggle nodes
    $('#input-check-node').on('keyup', function (e) {
        checkableNodes = findCheckableNodess();
        $('.check-node').prop('disabled', !(checkableNodes.length >= 1));
        $('.expand-icon').next().remove();
    });

    $('#btn-check-node.check-node').on('click', function (e) {
        $checkableTree.treeview('checkNode', [checkableNodes, {silent: $('#chk-check-silent').is(':checked')}]);
    });

    $('#btn-uncheck-node.check-node').on('click', function (e) {
        $checkableTree.treeview('uncheckNode', [checkableNodes, {silent: $('#chk-check-silent').is(':checked')}]);
    });

    $('#btn-toggle-checked.check-node').on('click', function (e) {
        $checkableTree.treeview('toggleNodeChecked', [checkableNodes, {silent: $('#chk-check-silent').is(':checked')}]);
    });

    // Check/uncheck all
    $('#btn-check-all').on('click', function (e) {
        $checkableTree.treeview('checkAll', {silent: $('#chk-check-silent').is(':checked')});
    });

    $('#btn-uncheck-all').on('click', function (e) {
        $checkableTree.treeview('uncheckAll', {silent: $('#chk-check-silent').is(':checked')});
    });

    $('#btn-clear-search').on('click', function (e) {


        $checkableTree.treeview('clearSearch');
        $('#input-check-node').val('');
        $('#checkable-output').html('');
    });
});
//去掉多选框 start
$(document).ready(function () {
    $('#btn-check-node').click(function () {
        $('.expand-icon').next().remove();
    });
    $('#btn-uncheck-all').click(function () {
        $('.expand-icon').next().remove();
    });
    $('#treeview-checkable').click(function () {
        $('.expand-icon').next().remove();
    });
    $('.expand-icon').next().remove();
    $("#chk-ignore-case").attr("checked", "true");
    $("#chk-reveal-results").attr("checked", "true");
});
//去掉多选框 end
//动态生成对应作业和deadline设置 start
$('#generate').click(function () {
    $('#generate-dest').html($('#checkable-output').html());
    $('#generate-dest-date').html("");
    for (var i = 0; i < $('#generate-dest').children('div').length; i++) {
        $('#generate-dest-date').append('<div class="form-group" style="margin-bottom: 0px"><div class="input-group date form_datetime col-md-12" data-date-format="yyyy / mm/ dd" data-link-field="dtp_input1"> <input class="form-control" size="16" type="text" value="" style="height:50px" readonly> <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span> <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span> </div> <input type="hidden" id="dtp_input1" value=""/><br/> </div>');
    }
    $('.form_datetime').datetimepicker({
        //language:  'fr',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        forceParse: 0,
        showMeridian: 1,
        minView: 2
    });
});
//动态生成对应作业和deadline设置 end
$('.form_datetime').datetimepicker({
    //language:  'fr',
    weekStart: 1,
    todayBtn: 1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 1,
    minView: 2
});
$('#select-stu').click(function () {
    var stulist = '';
    for (var i = 0; i < $("#select2_student").select2("data").length; i++) {
        stulist += $("#select2_student").select2("data")[i]['text'];
        if (i + 1 != $("#select2_student").select2("data").length) {
            stulist += '，';
        }
    }
    $('.stulist').text(stulist);
});
$(".show-all-student").click(function () {
    var flag = $('.demo').attr('aria-expanded');
    if (flag == 'true') {
        $(this).html('已选学生 <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>');

    } else {
        $(this).html('已选学生 <span class="glyphicon glyphicon-chevron-down" aria-hidden="true"></span>');
    }
});
// task-deployer end
$(".navbar-nav > li").click(function () {
    $(this).parent().children().removeClass('active');
    $(this).addClass('active');
});
