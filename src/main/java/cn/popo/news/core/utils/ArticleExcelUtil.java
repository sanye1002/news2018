package cn.popo.news.core.utils;

import cn.popo.news.core.entity.form.ArticleForm;
import cn.popo.news.core.service.ArticleService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-07-19 下午 2:38
 * @Description description
 */
public class ArticleExcelUtil {


    public static List<ArticleForm> importData(File file)
    {
        Workbook wb = null;
        List<ArticleForm> ArticleFormList = new ArrayList();
        try
        {
            if (ExcelUtil.isExcel2007(file.getPath())) {
                wb = new XSSFWorkbook(new FileInputStream(file));
            } else {
                wb = new HSSFWorkbook(new FileInputStream(file));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();

            return null;
        }

        Sheet sheet = wb.getSheetAt(0);//获取第一张表

        /*String contentAll = "";
        String titleTemp = null;
        String splitFlag = "!@#$%";*/
        for (int i = 1; i < sheet.getLastRowNum()+1; i++)
        {
            Row row = sheet.getRow(i);//获取索引为i的行，以0开始
            String title= row.getCell(0).getStringCellValue();//获取第i行的索引为0的单元格数据
            String content= row.getCell(1).getStringCellValue();

            String keywords= row.getCell(2).getStringCellValue();
            String classifyIdTemp = row.getCell(3).getStringCellValue();
            int classifyId= Integer.parseInt(classifyIdTemp);
            String imgUrl= row.getCell(4).getStringCellValue();
            ArticleForm articleForm = new ArticleForm();
            articleForm.setIsOwn(0);
            articleForm.setOriginal(0);
            articleForm.setDraft(0);
            articleForm.setTypeId(1);
            articleForm.setContent(content);
            articleForm.setTitle(title);
            articleForm.setClassifyId(classifyId);
            articleForm.setImgUrl(imgUrl);
            articleForm.setKeywords(keywords);
            ArticleFormList.add(articleForm);

            if (content==null||content=="")
            {
                break;
            }
            /*if (titleTemp==null){
                titleTemp = title;
                contentAll = content;
            }else {
                if (title.equals(titleTemp)){
                    contentAll = contentAll + splitFlag + content;
                }else {
                    String keywords= sheet.getRow(i-1).getCell(2).getStringCellValue();
                    String classifyIdTemp = sheet.getRow(i-1).getCell(3).getStringCellValue();
                    int classifyId= Integer.parseInt(classifyIdTemp);
                    String imgUrl= sheet.getRow(i-1).getCell(4).getStringCellValue();
                    ArticleForm articleForm = new ArticleForm();
                    articleForm.setIsOwn(0);
                    articleForm.setOriginal(0);
                    articleForm.setDraft(0);
                    articleForm.setTypeId(1);
                    articleForm.setContent(contentAll);
                    articleForm.setTitle(title);
                    articleForm.setClassifyId(classifyId);
                    articleForm.setImgUrl(imgUrl);
                    articleForm.setKeywords(keywords);
                    ArticleFormList.add(articleForm);

                    if (content==null||content=="")
                    {
                        break;
                    }

                    titleTemp = title;
                    contentAll = content;
                }
            }*/

//            int age = (int) rowTemp.getCell(1).getNumericCellValue();

        }
        try
        {
            wb.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return ArticleFormList;
    }

    /*public static void main(String[] args) throws IOException  {
//              excel 导入数据demo
        File file = new File("D:\\LIN\\excle\\网易四川有态度的四川门户.xlsx");
        importData(file);

        *//*List<List<Object>> dataList=importData(file);
        for (int i = 1; i < dataList.size(); i++) {
            for (int j = 1; j < dataList.get(i).size(); j++) {
                System.out.println(dataList.get(i).get(j));
            }
            System.out.println("------------------");
        }*//*
    }*/


}
