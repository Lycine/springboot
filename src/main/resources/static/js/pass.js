$(function () {
    $('#signInForm').bootstrapValidator({
        feedbackIcons: {
            /*input状态样式图片*/
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            /*验证：规则*/
            username: {//验证input项：验证规则
                validators: {
                    notEmpty: {//非空验证：提示消息
                        message: '用户名不能为空'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    }
                }
            }
        }
    })
});
$(function () {
    $('#registerForm').bootstrapValidator({
        feedbackIcons: {
            /*input状态样式图片*/
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            /*验证：规则*/
            password: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 20,
                        message: '密码长度必须在6到20之间'
                    },
                    different: {//不能和用户名相同
                        field: 'email',//需要进行比较的input name值
                        message: '不能和邮箱相同'
                    }
                }
            },
            passwordCheck: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
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
                    different: {//不能和用户名相同
                        field: 'email',//需要进行比较的input name值
                        message: '不能和邮箱相同'
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: '邮箱不能为空'
                    },
                    regexp: {
                        regexp: /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/,
                        message: '邮箱格式不正确'
                    }
                }
            }
        }
    })
});
$(function () {
    $('#resetPasswordForm').bootstrapValidator({
        feedbackIcons: {
            /*input状态样式图片*/
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            /*验证：规则*/
            uid: {},
            token: {},
            password: {
                validators: {
                    notEmpty: {
                        message: '新密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 20,
                        message: '密码长度必须在6到20之间'
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
                    }
                }
            }
        }
    })
});
$(function () {
    $('#forgetPasswordForm').bootstrapValidator({
        feedbackIcons: {
            /*input状态样式图片*/
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            /*验证：规则*/
            email: {
                validators: {
                    notEmpty: {
                        message: '邮箱不能为空'
                    },
                    regexp: {
                        regexp: /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/,
                        message: '邮箱格式不正确'
                    }
                }
            }
        }
    })
});