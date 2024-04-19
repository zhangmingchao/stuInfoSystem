package com.lc.demo.controller;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lc.demo.bean.*;
import com.lc.demo.mapper.ResultMapper;
import com.lc.demo.mapper.TeacherMapper;
import com.lc.demo.service.*;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName TeacherController
 * @Deacription TODO
 * @Author daier
 * @Date 2021/1/6 0:46
 * @Version 1.0
 **/
@Controller
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ResultssService resultssService;

    @Autowired
    private ClassService classService;

    @Autowired
    private StudentService studentService;


    @PostMapping(value = "/tea/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password, Map<String,Object> map, HttpSession session)
    {
        Teacher tea=teacherService.login(username,password);
        if(tea!=null)
        {
            session.setAttribute("loginUser",username);
            return "redirect:/teamain.html";
        }
        else
        {
            map.put("msg","用户名或密码错误");
            return  "login";
        }
    }

    //返回首页
    @GetMapping(value = "/tea/toindex")
    public String teaToIndex(){
        return "redirect:/teamain.html";
    }


    //返回教师信息修改页面
    @GetMapping(value = "/tea/toUpdateMsgPage")
    public String teaToUpdateMsgPage(HttpSession httpSession, Model model)
    {

        Teacher tea= teacherService.selectById((String) httpSession.getAttribute("loginUser"));
        model.addAttribute("tea",tea);
        return "tea/updatetea";
    }
    //导出


    //更新教师信息操作
    @PutMapping(value = "/tea/updateTeaMsg")
    public String updateTeaMsg(@Valid Teacher teacher, BindingResult bindingResult, Model model, HttpSession httpSession)
    {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        if(allErrors.size()==0)
        {
            Teacher teacherInit=teacherService.selectById((String) httpSession.getAttribute("loginUser"));
            teacher.setTeaId(teacherInit.getTeaId());
            teacher.setTeaName(teacherInit.getTeaName());
            teacher.setTeaSex(teacherInit.getTeaSex());

            teacherService.deleTea(teacherInit.getTeaId());
            teacherService.addTeacherHavePass(teacher);
            return "redirect:/updateTeaSucc.html";
        }
        else
        {
            for (ObjectError error:allErrors)
            {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            model.addAttribute("errors",errmsg);
            model.addAttribute("tea",teacher);
            return "tea/updatetea";
        }
    }

    //返回成绩管理首页
    @GetMapping(value = "/tea/toteadmin/{pn}")
    public String toteadmin(@PathVariable("pn") Integer pn, Model model)
    {
        PageHelper.startPage(pn, 6);
        List<Resultss> resultsses=resultssService.getAllResult();
        PageInfo<Resultss> page = new PageInfo<Resultss>(resultsses, 5);
        List<Classes> classes = classService.getAllClass();
        model.addAttribute("classes",classes);
        model.addAttribute("pageInfo",page);
        return "tea/tearesultlist";
    }

    //返回成绩添加页面
    @GetMapping(value = "/tea/resadd")
    public String toResAddPage()
    {
        return "tea/addResult";
    }

    //处理成绩添加事务
    @PostMapping(value = "/tea/resAdd")
    public String resAdd(@Valid Resultss resultss,BindingResult bindingResult,Model model)
    {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        Resultss resultssVail=null;
        if(allErrors.size()==0)
        {
            if(studentService.selectById(resultss.getStuId())!=null) {
                resultssVail = resultssService.selectResultByStuIdAndSubName(resultss.getStuId(), resultss.getSubName());
                if (resultssVail == null) {
                    resultssService.addResult(resultss);
                    return "redirect:/tea/toteadmin/1";
                } else {
                    errmsg.add(new MyError("已存在该学生的此成绩信息"));
                    model.addAttribute("errors", errmsg);
                    model.addAttribute("res", resultss);
                    return "tea/addResult";
                }
            }
            else{
                errmsg.add(new MyError("不存在该学号的学生"));
                model.addAttribute("errors", errmsg);
                model.addAttribute("res", resultss);
                return "tea/addResult";
            }
        }
        else {
            for (ObjectError error:allErrors)
            {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            model.addAttribute("errors",errmsg);
            model.addAttribute("res",resultss);
            return "tea/addResult";
        }
    }

    //返回成绩修改页面
    @GetMapping(value = "/tea/res/{resId}")
    public String updateResPage(@PathVariable("resId") int resId,Model model)
    {
        Resultss resultss=resultssService.selectResultByResId(resId);
        model.addAttribute("res",resultss);
        model.addAttribute("resId",resId);
        return "tea/updateResult";
    }

    //更新成绩信息操作
    @PutMapping(value = "/tea/res")
    public String updateRes(@Valid Resultss resultss,BindingResult bindingResult,Model model)
    {


        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        if(allErrors.size()==0)
        {
            System.out.println(resultss);
            resultssService.deleteResultById(resultss.getResId());
            resultssService.addResult(resultss);
            return "redirect:/tea/toteadmin/1";
        }
        else
        {
            for (ObjectError error:allErrors)
            {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            model.addAttribute("errors",errmsg);
            model.addAttribute("res",resultss);
            return "tea/updateResult";
        }
    }

    //处理删除成绩信息事务
    @DeleteMapping(value = "/tea/res/{resId}")
    public String deleres(@PathVariable("resId") int stuId)
    {
        resultssService.deleteResultById(stuId);
        return "redirect:/tea/toteadmin/1";
    }

    //根据ID查询学生的成绩
    @GetMapping(value = "/tea/selectById/{pn}")
    public String selectResultByStuId(@PathVariable("pn") Integer pn,@Param("stuId") String stuId, Model model)
    {
        PageHelper.startPage(pn, 6);
        List<Resultss> resultsses=resultssService.selectByStuId(stuId);
        PageInfo<Resultss> page = new PageInfo<Resultss>(resultsses, 5);
        List<Classes> classes=classService.getAllClass();
        model.addAttribute("classes",classes);
        model.addAttribute("pageInfo",page);
        model.addAttribute("stuId",stuId);
        return "tea/tearesultlistbystuid";
    }

    @GetMapping(value = "/tea/downLoad{pn}")
    public void downLoad(HttpServletResponse response,@PathVariable("pn") Integer pn, @Param("stuId") String stuId, Model model) throws IOException {
        String fileName = "export.csv";
        String[] head = new String[]{"学号","课程名","成绩"};
        List<String[]> values = new ArrayList<>();
//        List<Resultss> resultsses=resultssService.selectByStuId(stuId);
        List<Resultss> resultsses=resultssService.getAllResult();
        for (Resultss resultss : resultsses) {
            String[] items = new String[]{resultss.getStuId(),resultss.getSubName(),String.valueOf(resultss.getResNum())};
            values.add(items);
        }
        File file = CSVUtils.makeTempCSV(fileName, head, values);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        CSVUtils.downloadFile(response, file);
    }

    //返回成绩修改页面从根据学生号查询的页面发送来的
    @GetMapping(value = "/tea/resbyid/{resId}")
    public String updateResPageById(@PathVariable("resId") int resId,Model model)
    {
        Resultss resultss=resultssService.selectResultByResId(resId);
        model.addAttribute("res",resultss);
        model.addAttribute("resId",resId);
        return "tea/updateResultByid";
    }

    //更新成绩信息操作从根据学生号查询的页面发送来的
    @PutMapping(value = "/tea/resbyid")
    public String updateResById(@Valid Resultss resultss,BindingResult bindingResult,Model model)
    {


        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        if(allErrors.size()==0)
        {
            System.out.println(resultss);
            resultssService.deleteResultById(resultss.getResId());
            resultssService.addResult(resultss);
            return "redirect:/tea/selectById/1?stuId="+resultss.getStuId();
        }
        else
        {
            for (ObjectError error:allErrors)
            {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            model.addAttribute("errors",errmsg);
            model.addAttribute("res",resultss);
            return "tea/updateResultByid";
        }
    }

    //处理删除成绩信息事务从根据学生号查询的页面发送来的
    @DeleteMapping(value = "/tea/resbyid/{resId}")
    public String deleresById(@PathVariable("resId") int resId)
    {
        Resultss resultss=resultssService.selectResultByResId(resId);
        resultssService.deleteResultById(resId);
        return "redirect:/tea/selectById/1?stuId="+resultss.getStuId();
    }

    //返回查询学生排名主页
    @GetMapping(value ="/tea/torank")
    public String torankpage()
    {
        return "tea/rank";
    }

    //处理查询学生排名事务
    @GetMapping(value = "/tea/selectRank")
    public String selectRank(@Param("resTerm") String resTerm, Model model)
    {
        List<Rank> ranks=resultssService.selectRankByTerm(resTerm);
        model.addAttribute("ranks",ranks);
        model.addAttribute("resTerm",resTerm);
        return "tea/ranklist";
    }

    //返回根据班级查询学生排名主页
    @GetMapping(value ="/tea/torankbyclass")
    public String torankbyclasspage(Model model)
    {
        return "tea/rankbyclass";
    }

    @Autowired
    private SubjectService subjectService;
  
    //成绩分析
    @GetMapping(value ="/tea/scoreAnalysis")
    public String scoreAnalysispage(Model model,@Param("subName") String subName,@Param("subTerm")String subTerm)
    {
        // 查看传入的参数
        System.out.println("subName: " + subName);
        System.out.println("subTerm: " + subTerm);
        List<Subject> list = subjectService.findList();

        List<Subject> subjectList = new ArrayList<>();
        List<Subject> termList = new ArrayList<>();
        Set<String> subSet = new HashSet<>();
        Set<String> termSet = new HashSet<>();
        for (Subject subject : list) {
            if (!subSet.contains(subject.getSubName())) {
                subjectList.add(subject);
                subSet.add(subject.getSubName());
            }
            if (!termSet.contains(subject.getSubTerm())) {
                termList.add(subject);
                termSet.add(subject.getSubTerm());
            }
        }
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("termList", termList);
        if (!ObjectUtils.isEmpty(list)) {
            if (StringUtils.isEmpty(subTerm)) {
                subTerm = list.get(0).getSubTerm();
            }
            if (StringUtils.isEmpty(subName)) {
                subName = list.get(0).getSubName();
            }
            //需要查询数据库了

            model.addAttribute("defaultSubNam",subName);
            model.addAttribute("defaultTeam",subTerm);
            Map<String,Object> map = resultssService.getChartData(subName,subTerm);
            model.addAttribute("data",map);
        }

        return "tea/scoreAnalysis";
    }

    //处理根据班级查询学生排名事务
    @GetMapping(value = "/tea/selectRankbyclass")
    public String selectRankbyclass(@Param("resTerm") String resTerm,@Param("className")String className, Model model)
    {
        List<MyError> errmsg = new ArrayList<>();
        if(resTerm.equals("") ||className.equals(""))
        {
            errmsg.add(new MyError("请输入学期信息以及班级信息"));
            model.addAttribute("errors", errmsg);
            return "tea/rankbyclass";
        }
        else
        {
            List<Rank> ranks=resultssService.selectRankByTermAndStuClass(resTerm,className);
            model.addAttribute("ranks",ranks);
            model.addAttribute("resTerm",resTerm);
            model.addAttribute("className",className);
            return "tea/ranklistbyclass";
        }
    }

}
