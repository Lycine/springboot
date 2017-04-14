$(function () {
    $('#studentUpdateBasicForm')
        .bootstrapValidator({
            feedbackIcons: {
                /*input状态样式图片*/
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                /*验证：规则*/
                email: {//验证input项：验证规则
                    validators: {
                        notEmpty: {
                            message: '邮箱不能为空'
                        },
                        regexp: {
                            regexp: /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/,
                            message: '邮箱格式不正确'
                        }
                    }
                },
                phoneNumber: {
                    validators: {
                        notEmpty: {
                            message: '手机号不能为空'
                        },
                        stringLength: {
                            min: 11,
                            max: 11,
                            message: '请输入11位手机号码'
                        },
                        regexp: {
                            regexp: /^1[3|5|8]{1}[0-9]{9}$/,
                            message: '请输入正确的手机号码'
                        }
                    }
                },
                nickName: {
                    validators: {
                        notEmpty: {
                            message: '昵称'
                        },
                        stringLength: {
                            min: 2,
                            max: 30,
                            message: '昵称长度必须为2-30位之间'
                        }
                    }
                }
            }
        })
});
$(function () {
    $('#studentUpdatePasswordForm')
        .bootstrapValidator({
            feedbackIcons: {
                /*input状态样式图片*/
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                /*验证：规则*/
                hiddenPhoneNumber:{

                },
                hiddenEmail:{

                },
                hiddenNickName:{

                },
                password: {
                    validators: {
                        notEmpty: {
                            message: '新密码不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 20,
                            message: '密码长度必须在6到20之间'
                        },
                        different: {
                            // field: ['oldPassword','hiddenEmail', 'hiddenNickName', 'hiddenPhoneNumber'],
                            field: 'oldPassword,hiddenEmail,hiddenNickName,hiddenPhoneNumber',//需要进行比较的input name值
                            message: '不能和旧密码，邮箱，昵称，手机号相同'
                        }
                    }
                },
                passwordCheck: {
                    validators: {
                        notEmpty: {
                            message: '重复密码不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 20,
                            message: '密码长度必须在6到20之间'
                        },
                        identical: {//相同
                            field: 'password', //需要进行比较的input name值
                            message: '两次密码不一致'
                        },
                        different: {
                            field: 'hiddenEmail,hiddenNickName,hiddenPhoneNumber',//需要进行比较的input name值
                            message: '不能和邮箱，昵称，手机号相同'
                        }
                    }
                },
                oldPassword: {
                    validators: {
                        notEmpty: {
                            message: '旧密码不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 20,
                            message: '密码长度必须在6到20之间'
                        },
                        different: {
                            field: 'hiddenEmail,hiddenNickName,hiddenPhoneNumber',//需要进行比较的input name值
                            message: '不能和邮箱，昵称，手机号相同'
                        }
                    }
                }
            }
        })
});
function showPasswordForm() {
    $("#studentUpdateBasicForm").hide();
    $("#studentUpdatePasswordForm").show();
}
function showBasicForm() {
    $("#studentUpdateBasicForm").show();
    $("#studentUpdatePasswordForm").hide();
}

$(".nav li").each(function () {
    $(this).click(function () {
        $(".nav li").removeClass("active");
        $(this).addClass("active");
    });
});