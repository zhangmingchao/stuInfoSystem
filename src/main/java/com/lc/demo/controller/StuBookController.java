package com.lc.demo.controller;


import com.lc.demo.bean.Resultss;
import com.lc.demo.bean.StuBook;
import com.lc.demo.bean.Student;
import com.lc.demo.bean.Subject;
import com.lc.demo.service.StuBookService;
import com.lc.demo.service.StudentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class StuBookController {

    @Autowired
    private StuBookService stuBookService;

    @Autowired
    private StudentService studentService;




}
