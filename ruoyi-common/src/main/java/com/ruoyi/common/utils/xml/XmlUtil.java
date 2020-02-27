package com.ruoyi.common.utils.xml;

import com.google.common.collect.Lists;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @auther 易胜
 * @date 2020-02-20
 * @desc xml文件工具类
 */
public class XmlUtil {
    private static final Logger log = LoggerFactory.getLogger(XmlUtil.class);

    public static void main(String[] args) {

        URL classPath = Thread.currentThread().getContextClassLoader().getResource("");

        List<Map<String,String>> dataList = Lists.newArrayList();
        Map<String,String> map = new HashMap<>();
        map.put("I_ITEM_CODE","啊啊啊啊啊");
        map.put("YPPH","1901213");
        map.put("JZ_DATE","2019-02-01 00:00:00");
        dataList.add(map);

        String templatePathName = classPath.getPath()+"template/ftp/hebei_k_in_template.xml";
        String fileName = "template/ftp/test-001.xml";
        String nodeName = "DATAINFO";

        exportXml(dataList,templatePathName,classPath.getPath()+fileName,nodeName);

        System.out.println("success");
    }

    /**
     * 将数据以xml格式导出
     */
    public static boolean exportXml(List<Map<String, String>> dataList, String templatePathName, String fileName, String midNodeName){

        if(StringUtils.isEmpty(midNodeName)){
            midNodeName = "DATAINFO";
        }
        try{
            XmlUtil xmlUtil = new XmlUtil();
            xmlUtil.xmlHandler(templatePathName,fileName,midNodeName,dataList);

        }catch (Exception e){
            log.error("exportXml失败！",e);
            return false;
        }

        return true;
    }

    /**
     * 将map转化为XML格式的字符串
     * @param resmap
     * @return
     */
    public static String requesttoxml(Map<String, String> resmap){
        StringBuffer soapResultData = new StringBuffer();
        for(String key : resmap.keySet()){
            soapResultData.append("<");
            soapResultData.append(key);
            soapResultData.append(">");
            soapResultData.append(resmap.get(key));
            soapResultData.append("</");
            soapResultData.append(key);
            soapResultData.append(">");
        }
        return soapResultData.toString();
    }

    public Boolean addChildren(Element nodeMatched, List<Map<String,String>> dataList) {
        Element rowEleTemp = (Element) nodeMatched.elements().get(0);
        String rowEleName = rowEleTemp.getName();

        // 校验dom格式
        if (validDom(nodeMatched, dataList)) return false;

        for (Map<String, String> dataMap : dataList) {
            Element rowEle = new DefaultElement(rowEleName);
            for (Map.Entry<String,String> entry : dataMap.entrySet()){
                Element columnEle = new DefaultElement(entry.getKey());
                columnEle.setText(entry.getValue());
                rowEle.add(columnEle);
            }
            nodeMatched.add(rowEle);
        }
        return true;
    }

    /**
     *  校验实体属性名称根模版映射是否正确
     */
    private boolean validDom(Element nodeMatched, List<Map<String, String>> dataList) {
        for (Iterator iterator = nodeMatched.elementIterator(); iterator.hasNext();){
            Element rowNode = (Element) iterator.next();
            List<String> qnameList = Lists.newArrayList();
            for (Iterator iterator1 = rowNode.elementIterator();iterator1.hasNext();){
                Element leafNode = (Element) iterator1.next();
                qnameList.add(leafNode.getName());
            }
            for (String columnName : dataList.get(0).keySet()) {
                if(!qnameList.contains(columnName)){
                    log.info("模版和数据不一致！请先确认一致！");
                    return true;
                }
            }

            nodeMatched.remove(rowNode);
        }
        return false;
    }

    public void xmlHandler(String sourceFileName,String targetFileName, String nameMatched, List<Map<String,String>> dataList) {
        File inputXml = new File(sourceFileName);
        SAXReader saxReader = new SAXReader();
        try {
            // 解析模版
            Document document = saxReader.read(inputXml);
            Element root = document.getRootElement();

            // 匹配数据添加位置节点
            Element elementMatched = iteratorNode(root,nameMatched);

            // 添加数据
            Boolean addFlag = addChildren(elementMatched,dataList);

            // 创建文件
            if(addFlag){
                createXml(targetFileName,document);
            }

        } catch (DocumentException e) {
            log.error("parserXml失败！",e);
        }
    }

    private Element iteratorNode(Element node,String nameMatched) {
        for (Element childNode : (List<Element>)node.elements()) {
            log.info(childNode.getName() + ":" + childNode.getText());

            if(nameMatched.equals(childNode.getName())){
                return childNode;
            }else {
                if(!CollectionUtils.isEmpty(childNode.elements())){
                    Element retNode = iteratorNode(childNode,nameMatched);
                    if(retNode!=null){
                        // 将匹配节点递归传递返回
                        return retNode;
                    }
                }
            }
        }
        return null;
    }

    private void createXml(String fileName, Document document){
        try {
            Writer fileWriter = new FileWriter(fileName);
            XMLWriter xmlWriter = new XMLWriter(fileWriter);
            xmlWriter.write(document);
            xmlWriter.close();
        } catch (IOException e) {
            log.error("创建xml文件失败！",e);
        }
    }

}
