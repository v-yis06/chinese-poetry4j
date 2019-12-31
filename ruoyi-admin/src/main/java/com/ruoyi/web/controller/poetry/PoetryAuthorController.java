package com.ruoyi.web.controller.poetry;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.poetry.domain.PoetryAuthor;
import com.ruoyi.poetry.service.IPoetryAuthorService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 作者Controller
 * 
 * @author ruoyi
 * @date 2019-12-31
 */
@Controller
@RequestMapping("/poetry/author")
public class PoetryAuthorController extends BaseController
{
    private String prefix = "poetry/author";

    @Autowired
    private IPoetryAuthorService poetryAuthorService;

    @RequiresPermissions("poetry:author:view")
    @GetMapping()
    public String author()
    {
        return prefix + "/author";
    }

    /**
     * 查询作者列表
     */
    @RequiresPermissions("poetry:author:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PoetryAuthor poetryAuthor)
    {
        startPage();
        List<PoetryAuthor> list = poetryAuthorService.selectPoetryAuthorList(poetryAuthor);
        return getDataTable(list);
    }

    /**
     * 导出作者列表
     */
    @RequiresPermissions("poetry:author:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PoetryAuthor poetryAuthor)
    {
        List<PoetryAuthor> list = poetryAuthorService.selectPoetryAuthorList(poetryAuthor);
        ExcelUtil<PoetryAuthor> util = new ExcelUtil<PoetryAuthor>(PoetryAuthor.class);
        return util.exportExcel(list, "author");
    }

    /**
     * 新增作者
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存作者
     */
    @RequiresPermissions("poetry:author:add")
    @Log(title = "作者", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PoetryAuthor poetryAuthor)
    {
        return toAjax(poetryAuthorService.insertPoetryAuthor(poetryAuthor));
    }

    /**
     * 修改作者
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        PoetryAuthor poetryAuthor = poetryAuthorService.selectPoetryAuthorById(id);
        mmap.put("poetryAuthor", poetryAuthor);
        return prefix + "/edit";
    }

    /**
     * 修改保存作者
     */
    @RequiresPermissions("poetry:author:edit")
    @Log(title = "作者", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PoetryAuthor poetryAuthor)
    {
        return toAjax(poetryAuthorService.updatePoetryAuthor(poetryAuthor));
    }

    /**
     * 删除作者
     */
    @RequiresPermissions("poetry:author:remove")
    @Log(title = "作者", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(poetryAuthorService.deletePoetryAuthorByIds(ids));
    }
}
