package com.lc.demo.controller;

import com.lc.demo.bean.Subject;
import com.lc.demo.service.SubjectService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping(value = "/sub/subList")
    @ResponseBody
    public String subList( Model model) {
        List<Subject> list = subjectService.findList();
        model.addAttribute("subjectList", list);
        return "";
    }

}
