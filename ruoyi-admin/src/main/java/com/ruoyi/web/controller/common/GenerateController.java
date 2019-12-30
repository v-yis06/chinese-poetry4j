package com.ruoyi.web.controller.common;

import com.ruoyi.generator.domain.BeanField;
import com.ruoyi.generator.domain.GenerateDetail;
import com.ruoyi.generator.domain.GenerateInput;
import com.ruoyi.generator.service.GenerateService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @auther 易胜
 * @date 2019-12-30
 * @desc
 */

@RestController
@RequestMapping({"/tool/generate"})
public class GenerateController {
    @Autowired
    private GenerateService generateService;

    private String prefix = "tool/generate";

    @RequiresPermissions("tool:gen:view")
    @GetMapping()
    public ModelAndView generate(ModelAndView modelAndView)
    {

        modelAndView.setViewName(prefix + "/generate");
        return modelAndView;
    }

    @GetMapping(value = "/list" ,params = {"tableName"})
    public GenerateDetail generateByTableName(String dbName, String tableName, String tableHead) {
        GenerateDetail detail = new GenerateDetail();
        detail.setBeanName(this.generateService.upperFirstChar(tableName, tableHead));
        detail.setBeanComment(this.generateService.tableComment(tableName, dbName));
        List<BeanField> fields = this.generateService.listBeanField(tableName, dbName);
        detail.setFields(fields);
        return detail;
    }

    @PostMapping("/save")
    public void save(@RequestBody GenerateInput input) {
        this.generateService.saveCode(input);
    }
}
