package com.lc.demo.controller;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lc.demo.bean.*;
import com.lc.demo.service.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

/**
 * @ClassName StudentController
 * @Deacription TODO
 * @Author daier
 * @Date 2021/1/5 13:48
 * @Version 1.0
 **/

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassService classService;

    @Autowired
    private ResultssService resultssService;

    @PostMapping(value = "/stu/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password, Map<String,Object> map, HttpSession session)
    {
        Student stu=studentService.login(username,password);
        if(stu!=null)
        {
            session.setAttribute("loginUser",username);
            return "redirect:/stumain.html";
        }
        else
        {
            map.put("msg","用户名或密码错误");
            return  "login";
        }
    }

    //返回首页
    @GetMapping(value = "/stu/toindex")
    public String toindex(){
        return "redirect:/stumain.html";
    }


    //返回学生信息修改页面
    @GetMapping(value = "/stu/toUpdateMsgPage")
    public String toUpdateMsgPage(HttpSession httpSession, Model model)
    {

        Student stu= studentService.selectById((String) httpSession.getAttribute("loginUser"));
        List<Classes> classes=classService.getAllClass();
        model.addAttribute("stu",stu);
        model.addAttribute("classes",classes);
        return "stu/updateStu";
    }

    //更新学生信息操作
    @PutMapping(value = "/stu/updateStuMsg")
    public String updateStuMsg(@Valid Student student, BindingResult bindingResult, Model model,HttpSession httpSession)
    {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        List<Classes> classes = classService.getAllClass();
        if(allErrors.size()==0)
        {
            Student studentInit=studentService.selectById((String) httpSession.getAttribute("loginUser"));
            student.setStuId(studentInit.getStuId());
            student.setStuName(studentInit.getStuName());
            student.setStuClass(studentInit.getStuClass());
            student.setStuSex(studentInit.getStuSex());

            studentService.deleStu(studentInit.getStuId());
            studentService.addStudentHavePass(student);
            return "redirect:/updateSucc.html";
        }
        else
        {
            for (ObjectError error:allErrors)
            {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            model.addAttribute("errors",errmsg);
            model.addAttribute("stu",student);
            model.addAttribute("classes",classes);
            return "stu/updateStu";
        }
    }

    //返回学生成绩首页
    @GetMapping(value = "/stu/toresdmin/{pn}")
    public String toresdmin(@PathVariable("pn") Integer pn,Model model,HttpSession httpSession)
    {
        PageHelper.startPage(pn, 9);
        List<Resultss> resultsses = resultssService.selectByStuId((String) httpSession.getAttribute("loginUser"));
        PageInfo<Resultss> page = new PageInfo<Resultss>(resultsses, 5);
        model.addAttribute("pageInfo",page);
        return "stu/resultlist";
    }

    //根据学期查询成绩
    @GetMapping(value = "/stu/selectResByTerm/{pn}")
    public String selectResByTerm(@PathVariable("pn") Integer pn,@Param("resTerm") String resTerm, Model model,HttpSession httpSession)
    {
        PageHelper.startPage(pn, 9);
        List<Resultss> resultsses=resultssService.selectByStuIdAndResTerm((String) httpSession.getAttribute("loginUser"),resTerm);
        PageInfo<Resultss> page = new PageInfo<Resultss>(resultsses, 5);
        model.addAttribute("pageInfo",page);
        model.addAttribute("resTerm",resTerm);
        return "stu/reslistbyterm";
    }

    @Autowired
    private SubjectService subjectService;

        //成绩分析
    @GetMapping(value = "/stu/stuScoreAnalysis")
    public String toresdmin(Model model,@Param("subName") String subName,HttpSession httpSession)
    {
        // 查看传入的参数
        System.out.println("subName: " + subName);
        Student studentInit=studentService.selectById((String) httpSession.getAttribute("loginUser"));

        List<Subject> list = subjectService.findList();

        List<Subject> subjectList = new ArrayList<>();
        Set<String> subSet = new HashSet<>();
        for (Subject subject : list) {
            if (!subSet.contains(subject.getSubName())) {
                subjectList.add(subject);
                subSet.add(subject.getSubName());
            }
        }
        model.addAttribute("subjectList", subjectList);
        if (!ObjectUtils.isEmpty(list)) {
            if (StringUtils.isEmpty(subName)) {
                subName = list.get(0).getSubName();
            }
            Map<String,Object> map = new HashMap<>();
            //需要查询数据库了
            model.addAttribute("defaultSubNam",subName);
            List<Resultss> resultList = resultssService.findResultByStuIdAndSubName(studentInit.getStuId(), subName);
            List<String> termList = new ArrayList<>();
            List<String> scoreList = new ArrayList<>();
            Set<String> termSet = new HashSet<>();
            for (Resultss resultss : resultList) {
                if (!termSet.contains(resultss.getResTerm())) {
                    termList.add(resultss.getResTerm());
                    scoreList.add(String.valueOf(resultss.getResNum()));
                    termSet.add(resultss.getResTerm());
                }
            }
            map.put("term",termList);
            map.put("score",scoreList);
            model.addAttribute("data",map);
        }
        return "stu/stuScoreAnalysis";
    }

    @Autowired
    private StuBookService stuBookService;


    @GetMapping("/stu/getBookInfo")
    public String getBookInfo(Model model, @Param("subName") String subName,HttpSession httpSession){
        Student studentInit=studentService.selectById((String) httpSession.getAttribute("loginUser"));
        List<Subject> list = subjectService.findList();

        List<Subject> subjectList = new ArrayList<>();
        Set<String> subSet = new HashSet<>();
        for (Subject subject : list) {
            if (!subSet.contains(subject.getSubName())) {
                subjectList.add(subject);
                subSet.add(subject.getSubName());
            }
        }
        model.addAttribute("subjectList", subjectList);
        if (!ObjectUtils.isEmpty(list)) {
            if (StringUtils.isEmpty(subName)) {
                subName = list.get(0).getSubName();
            }
            Map<String,Object> map = new HashMap<>();
            //需要查询数据库了
            model.addAttribute("defaultSubNam",subName);
            StuBook stuBook = stuBookService.getByStuIdAndSubName(studentInit.getStuId(), subName);
            if (ObjectUtil.isEmpty(stuBook)) {
                stuBook = new StuBook(studentInit.getStuId(),subName,"");
            }
            model.addAttribute("data",stuBook);
        }
        return "stu/book";
    }

    @GetMapping("/stu/saveBook")
    //@ResponseBody
    public String getBookInfo(Model model, @Param("subName")String subName,@Param("content") String content,HttpSession httpSession){
        Student studentInit=studentService.selectById((String) httpSession.getAttribute("loginUser"));
        StuBook stuBook = new StuBook();
        stuBook.setSubName(subName);
        stuBook.setStuId(studentInit.getStuId());
        stuBook.setContent(content);
        StuBook exit = stuBookService.getByStuIdAndSubName(stuBook.getStuId(), stuBook.getSubName());
        if (ObjectUtil.isEmpty(exit)) {
            stuBookService.save(stuBook);
        } else {
            exit.setContent(content);
            stuBookService.updateById(stuBook);
        }
        model.addAttribute("data",stuBook);
        List<Subject> list = subjectService.findList();

        List<Subject> subjectList = new ArrayList<>();
        Set<String> subSet = new HashSet<>();
        for (Subject subject : list) {
            if (!subSet.contains(subject.getSubName())) {
                subjectList.add(subject);
                subSet.add(subject.getSubName());
            }
        }
        model.addAttribute("subjectList", subjectList);
        return "stu/book";
    }
}
