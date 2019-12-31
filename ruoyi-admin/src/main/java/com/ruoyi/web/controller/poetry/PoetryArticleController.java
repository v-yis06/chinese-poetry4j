package com.ruoyi.web.controller.poetry;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.poetry.domain.PoetryArticle;
import com.ruoyi.poetry.service.IPoetryArticleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 诗词文章Controller
 * 
 * @author ruoyi
 * @date 2019-12-31
 */
@Controller
@RequestMapping("/poetry/article")
public class PoetryArticleController extends BaseController
{
    private String prefix = "poetry/article";

    @Autowired
    private IPoetryArticleService poetryArticleService;

    @RequiresPermissions("poetry:article:view")
    @GetMapping()
    public String article()
    {
        return prefix + "/article";
    }

    /**
     * 查询诗词文章列表
     */
    @RequiresPermissions("poetry:article:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PoetryArticle poetryArticle)
    {
        startPage();
        List<PoetryArticle> list = poetryArticleService.selectPoetryArticleList(poetryArticle);
        return getDataTable(list);
    }

    /**
     * 导出诗词文章列表
     */
    @RequiresPermissions("poetry:article:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PoetryArticle poetryArticle)
    {
        List<PoetryArticle> list = poetryArticleService.selectPoetryArticleList(poetryArticle);
        ExcelUtil<PoetryArticle> util = new ExcelUtil<PoetryArticle>(PoetryArticle.class);
        return util.exportExcel(list, "article");
    }

    /**
     * 新增诗词文章
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存诗词文章
     */
    @RequiresPermissions("poetry:article:add")
    @Log(title = "诗词文章", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PoetryArticle poetryArticle)
    {
        return toAjax(poetryArticleService.insertPoetryArticle(poetryArticle));
    }

    /**
     * 修改诗词文章
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        PoetryArticle poetryArticle = poetryArticleService.selectPoetryArticleById(id);
        mmap.put("poetryArticle", poetryArticle);
        return prefix + "/edit";
    }

    /**
     * 修改保存诗词文章
     */
    @RequiresPermissions("poetry:article:edit")
    @Log(title = "诗词文章", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PoetryArticle poetryArticle)
    {
        return toAjax(poetryArticleService.updatePoetryArticle(poetryArticle));
    }

    /**
     * 删除诗词文章
     */
    @RequiresPermissions("poetry:article:remove")
    @Log(title = "诗词文章", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(poetryArticleService.deletePoetryArticleByIds(ids));
    }
}
