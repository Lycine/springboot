package com.artbrain.controller;

import com.artbrain.entity.Clazz;
import com.artbrain.entity.Msg;
import com.artbrain.service.ClazzService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static com.artbrain.util.Global.REGISTER_PAGE_UNKNOWN;


@CommonsLog
@Controller
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    @RequestMapping(value = "/admin/addClazz", method = RequestMethod.POST)
    public String addClazz(@RequestParam String uid, @RequestParam String name, Model model) {
        try {
            if (null == name || "".equals(name)) {
                log.debug("班级名为空");
                Msg msg = new Msg("failure", "新增班级失败，班级名为空。");
                model.addAttribute("msg", msg);
                return "admin/admin_index";
            } else {
                log.debug("新建班级");
                Clazz clazz = new Clazz();
                clazz.setName(name);
                clazz.setFrom(uid);
                if (clazzService.clazzAdd(clazz)) {
                    log.debug("新建班级成功");
                    Msg msg = new Msg("success", "新建班级成功。");
                    model.addAttribute("msg", msg);
                    return "admin/admin_index";
                } else {
                    log.debug("新建班级失败，dao层错误");
                    Msg msg = new Msg("failure", "新建班级失败，未知错误。");
                    model.addAttribute("msg", msg);
                    return "admin/admin_index";
                }
            }
        } catch (Exception e) {
            log.debug("新建用户失败，未知错误");
            e.printStackTrace();
            return "redirect:" + REGISTER_PAGE_UNKNOWN;
        }

    }

}
