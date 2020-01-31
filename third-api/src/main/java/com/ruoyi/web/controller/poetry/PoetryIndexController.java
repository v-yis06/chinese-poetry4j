package com.ruoyi.web.controller.poetry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.google.common.collect.Maps;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.poetry.domain.PoetryArticle;
import com.ruoyi.poetry.domain.PoetryAuthor;
import com.ruoyi.poetry.service.IPoetryArticleService;
import com.ruoyi.poetry.service.IPoetryAuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
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
@RequestMapping("/poetry/index")
public class PoetryIndexController extends BaseController
{

    @Autowired
    private IPoetryArticleService poetryArticleService;

    @Autowired
    private IPoetryAuthorService poetryAuthorService;

    @ApiOperation(value = "首页数据",notes = "")
    @GetMapping("/index")
    @ResponseBody
    public AjaxResult index(PoetryArticle poetryArticle)
    {
        // 输入转繁体字
        String reqContent = poetryArticle.getContent();
        String reqAuthor = poetryArticle.getAuthor();
        if(StringUtils.isNotEmpty(reqContent)){
            poetryArticle.setContent(ZhConverterUtil.convertToTraditional(reqContent));
        }
        if(StringUtils.isNotEmpty(reqAuthor)){
            poetryArticle.setAuthor(ZhConverterUtil.convertToTraditional(reqAuthor));
        }

        // 输出转简体字
        Map<String,JSONArray> map = Maps.newHashMap();
        List<PoetryArticle> articleList = poetryArticleService.selectRandPoetryArticleTree(poetryArticle);
        for (PoetryArticle article : articleList) {
            article.setAuthor(ZhConverterUtil.convertToSimple(article.getAuthor()));
            article.setContent(ZhConverterUtil.convertToSimple(article.getContent()));
        }
        map.put("poetryArticle", JSONArray.parseArray(JSONObject.toJSONString(articleList)));


        String authorName = null;
        if(!CollectionUtils.isEmpty(articleList)){
            authorName = articleList.get(0).getAuthor();
        }
        if(StringUtils.isNotEmpty(authorName)){
            PoetryAuthor authorReq = new PoetryAuthor();
            authorReq.setName(authorName);
            List<PoetryAuthor> authorList = poetryAuthorService.selectPoetryAuthorList(authorReq);
            for (PoetryAuthor poetryAuthor : authorList) {
                poetryAuthor.setName(ZhConverterUtil.convertToSimple(poetryAuthor.getName()));
                poetryAuthor.setDescription(ZhConverterUtil.convertToSimple(poetryAuthor.getDescription()));
            }
            if(!CollectionUtils.isEmpty(authorList)){
                map.put("author",  JSONArray.parseArray(JSONObject.toJSONString(authorList)));
            }
        }

        return new AjaxResult(AjaxResult.Type.SUCCESS,"",map);

    }

    /**
     * 加载诗词文章树列表
     */
    @ApiOperation(value = "加载诗词文章树列表",notes = "")
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = poetryArticleService.selectPoetryArticleTree();
        return ztrees;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String str = ZhConverterUtil.convertToSimple("弄篙莫濺水，畏濕紅蓮衣。");
        System.out.println(str);
        System.out.println(ZhConverterUtil.convertToTraditional("李白"));
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("弄篙莫濺水，畏濕紅蓮衣。");
    }
}

