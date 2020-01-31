package com.ruoyi.web.controller.poetry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.google.common.collect.Lists;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.JSONFilePoetry;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.poetry.domain.PoetryArticle;
import com.ruoyi.poetry.service.IPoetryArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 诗词文章Controller
 * 
 * @author yisheng
 * @date 2020-01-02
 */
@Api("诗文信息管理")
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
     * 查询诗词文章列表(分页)
     */
    @RequiresPermissions("poetry:article:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PoetryArticle poetryArticle)
    {
        String reqContent = poetryArticle.getContent();
        String reqAuthor = poetryArticle.getAuthor();
        if(StringUtils.isNotEmpty(reqContent)){
            poetryArticle.setContent(ZhConverterUtil.convertToTraditional(reqContent));
        }
        if(StringUtils.isNotEmpty(reqAuthor)){
            poetryArticle.setAuthor(ZhConverterUtil.convertToTraditional(reqAuthor));
        }

        startPage();
        List<PoetryArticle> list = poetryArticleService.selectPoetryArticleList(poetryArticle);
        for (PoetryArticle article : list) {
            article.setAuthor(ZhConverterUtil.convertToSimple(article.getAuthor()));
            article.setContent(ZhConverterUtil.convertToSimple(article.getContent()));
        }
        return getDataTable(list);
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


    /**
     * 查询字典详细
     */
    @RequiresPermissions("poetry:article:list")
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap mmap)
    {
//        mmap.put("dict", dictTypeService.selectDictTypeById(dictId));
//        mmap.put("dictList", dictTypeService.selectDictTypeAll());
        PoetryArticle poetryArticle = poetryArticleService.selectPoetryArticleById(id);
        Long parentId = poetryArticle.getParentId();
        if(parentId!=null && parentId!=0L){
            mmap.put("parentId",parentId);
        }else {
            mmap.put("parentId",id);
        }
        return prefix+"/tree/article";
    }

    /**
     * 查询诗词文章树列表（不分页，树结构）
     */
    @RequiresPermissions("poetry:article:list")
    @PostMapping("/list/tree")
    @ResponseBody
    public List<PoetryArticle> listTree(PoetryArticle poetryArticle)
    {

        String reqContent = poetryArticle.getContent();
        String reqAuthor = poetryArticle.getAuthor();
        if(StringUtils.isNotEmpty(reqContent)){
            poetryArticle.setContent(ZhConverterUtil.convertToTraditional(reqContent));
        }
        if(StringUtils.isNotEmpty(reqAuthor)){
            poetryArticle.setAuthor(ZhConverterUtil.convertToTraditional(reqAuthor));
        }

        List<PoetryArticle> list = poetryArticleService.selectPoetryArticleTreeList(poetryArticle);
        for (PoetryArticle article : list) {
            article.setAuthor(ZhConverterUtil.convertToSimple(article.getAuthor()));
            article.setContent(ZhConverterUtil.convertToSimple(article.getContent()));
        }
        return list;
    }

    /**
     * 导出诗词文章列表
     */
    @RequiresPermissions("poetry:article:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PoetryArticle poetryArticle)
    {
        List<PoetryArticle> list = poetryArticleService.selectPoetryArticleTreeList(poetryArticle);
        ExcelUtil<PoetryArticle> util = new ExcelUtil<PoetryArticle>(PoetryArticle.class);
        return util.exportExcel(list, "article");
    }

    /**
     * 新增诗词文章
     */
    @GetMapping(value = { "/add/{id}", "/add/" })
    public String add(@PathVariable(value = "id", required = false) Long id, ModelMap mmap)
    {
        if (StringUtils.isNotNull(id))
        {
            mmap.put("poetryArticle", poetryArticleService.selectPoetryArticleById(id));
        }
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
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
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
     * 删除
     */
    @RequiresPermissions("poetry:article:remove")
    @Log(title = "诗词文章", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{id}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("id") Long id)
    {
        return toAjax(poetryArticleService.deletePoetryArticleById(id));
    }

    /**
     * 选择诗词文章树
     */
    @GetMapping(value = { "/selectArticleTree/{id}", "/selectArticleTree/" })
    public String selectArticleTree(@PathVariable(value = "id", required = false) Long id, ModelMap mmap)
    {
        if (StringUtils.isNotNull(id))
        {
            mmap.put("poetryArticle", poetryArticleService.selectPoetryArticleById(id));
        }
        return prefix + "/tree";
    }

    /**
     * 加载诗词文章树列表
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = poetryArticleService.selectPoetryArticleTree();
        if(CollectionUtils.isEmpty(ztrees)){
            return null;
        }
        for (Ztree article : ztrees) {
            String name = article.getName();
            String title = article.getTitle();
            if(StringUtils.isNotEmpty(name)){
                article.setName(ZhConverterUtil.convertToSimple(name));
            }
            if(StringUtils.isNotEmpty(title)){
                article.setTitle(ZhConverterUtil.convertToSimple(title));
            }
        }
        return ztrees;
    }

    /**
     * 导入诗词文章列表
     */
    @ApiOperation("导入唐诗json文件")
    @RequiresPermissions("poetry:article:import")
    @PostMapping("/articleImport")
    @ResponseBody
    public AjaxResult articleImport(@RequestParam("file") MultipartFile multipartFile)
    {
        Integer insertNum = 0;
        JSONFilePoetry jsonFile = new JSONFilePoetry();
        try {
            JSONArray jsonArray = jsonFile.readJsonData(multipartFile);
            for (Object obj : jsonArray) {
                try {
                    // 获取最大主键值
                    Long maxId = poetryArticleService.getMaxKeyId();
                    maxId = maxId==null ? 1 : maxId;

                    // 数据写表
                    InsertCell insertCell = new InsertCell(maxId, insertNum, (Map) obj).invoke();
                    insertNum = insertCell.getInsertNum();
                }catch (Exception e){
                    logger.error(e.getMessage());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return new AjaxResult(AjaxResult.Type.SUCCESS,"json文件导入诗文数量："+insertNum);
    }

    private class InsertCell {
        private Long maxId;
        private Integer insertNum;
        private Map o;

        public InsertCell(Long maxId, Integer insertNum, Map o) {
            this.maxId = maxId;
            this.insertNum = insertNum;
            this.o = o;
        }

        public Long getMaxId() {
            return maxId;
        }

        public Integer getInsertNum() {
            return insertNum;
        }

        public InsertCell invoke() {
            List<PoetryArticle> poetryArticles = Lists.newArrayList();
            PoetryArticle poetryArticle = new PoetryArticle();
            Map<String,Object> articleMap = o;
            poetryArticle.setId(++maxId);
            poetryArticle.setParentId(0L);
            poetryArticle.setAuthor((String) articleMap.get("author"));
            poetryArticle.setContent((String) articleMap.get("title"));
            poetryArticle.setCreateBy("admin");
            int parentInsertNum = poetryArticleService.insertPoetryArticle(poetryArticle);

            if(parentInsertNum>0){
                insertNum++;
                Long parentId = maxId.longValue();
                Integer orderNo = 1;
                JSONArray paragraphsArray = JSONArray.parseArray(JSONObject.toJSONString(articleMap.get("paragraphs")));
                for (Object o2 : paragraphsArray) {
                    poetryArticle.setId(++maxId);
                    poetryArticle.setParentId(parentId);
                    poetryArticle.setContent(String.valueOf(o2));
                    poetryArticle.setOrderNum(orderNo++);
                    poetryArticleService.insertPoetryArticle(poetryArticle);
                }
            }

            return this;
        }
    }

}

