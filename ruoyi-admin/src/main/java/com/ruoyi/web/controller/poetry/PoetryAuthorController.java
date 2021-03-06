package com.ruoyi.web.controller.poetry;

import com.alibaba.fastjson.JSONArray;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.file.JSONFilePoetry;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.poetry.domain.PoetryAuthor;
import com.ruoyi.poetry.service.IPoetryAuthorService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Value("${ruoyi.profile}")
    private String baseLocation;

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

    /**
     * 导入诗词文章列表
     */
    @ApiOperation("导入作者json文件")
    @RequiresPermissions("poetry:author:import")
    @PostMapping("/authorImport")
    @ResponseBody
    public AjaxResult authorImport(@RequestParam("file") MultipartFile multipartFile)
    {
        int insertNum = 0;
        JSONFilePoetry jsonFilePoetry = new JSONFilePoetry();
        try {
            // 将文件缓存到服务器，然后获取解析
            JSONArray jsonArray = jsonFilePoetry.readJsonData(multipartFile);
            List<PoetryAuthor> poetryAuthors = jsonArray.toJavaList(PoetryAuthor.class);
            // 说明：json文件部分字符存在乱码问题
//            int num = poetryAuthorService.batchInsertPoetryAuthor(poetryAuthors);
            for (PoetryAuthor poetryAuthor : poetryAuthors) {
                try {
                    poetryAuthor.setCreateBy("admin");
                    int retNum = poetryAuthorService.insertPoetryAuthor(poetryAuthor);
                    if(retNum>0){
                        insertNum++;
                    }
                }catch (Exception e){
                    logger.error("作者新增异常："+e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return new AjaxResult(AjaxResult.Type.SUCCESS,"导入json新增作者数量："+insertNum);
    }
}
