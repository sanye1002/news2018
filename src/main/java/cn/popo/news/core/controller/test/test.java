package cn.popo.news.core.controller.test;

import cn.popo.news.core.entity.form.ArticleForm;
import cn.popo.news.core.service.ArticleService;
import cn.popo.news.core.utils.ArticleExcelUtil;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-07-18 下午 4:26
 * @Description description
 */
@RestController
@RequestMapping("/api/test")
public class test {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/excle")
    public ModelAndView articleDeletePraise() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageId", 1);
        map.put("pageTitle", 112312);
        return new ModelAndView("pages/str-test", map);
    }

    @PostMapping("/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> importSalaryExcel(HttpServletRequest request,
                                                           MultipartFile file) {


        if (!file.isEmpty()) {
            String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdir();
            }

            String path = filePath + file.getOriginalFilename();
            File tempFile = null;
            //save to the /upload path
            try {
                tempFile = new File(path);

                file.transferTo(tempFile);

            } catch (IOException e) {
                e.printStackTrace();
            }


            List<ArticleForm> list = ArticleExcelUtil.importData(tempFile);

            if (!list.isEmpty()){
                list.forEach(l->{
                    articleService.articleSave(l);
                });
            }



            deleteFile(tempFile);


        }
        return ResultVOUtil.success();
    }

    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
