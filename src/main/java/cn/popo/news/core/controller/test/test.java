package cn.popo.news.core.controller.test;

import cn.popo.news.core.entity.common.ArticleInfo;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.entity.form.ArticleForm;
import cn.popo.news.core.entity.form.CommentForm;
import cn.popo.news.core.repository.ArticleRepository;
import cn.popo.news.core.repository.UserRepository;
import cn.popo.news.core.service.ArticleService;
import cn.popo.news.core.service.api.AgoCommentService;
import cn.popo.news.core.service.api.AgoPersonalService;
import cn.popo.news.core.utils.ArticleExcelUtil;
import cn.popo.news.core.utils.KeyUtil;
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

    @Autowired
    private AgoCommentService agoCommentService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AgoPersonalService agoPersonalService;

    @GetMapping("/excel")
    public ModelAndView excelUpload() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageId", 1);
        map.put("pageTitle", 112312);
        return new ModelAndView("pages/str-test", map);
    }

    @GetMapping("/class/content")
    public ModelAndView excelUploadClass() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageId", 2);
        map.put("pageTitle", "excel上传content");
        return new ModelAndView("pages/upload-content-class", map);
    }

    @GetMapping("/add/avatar")
    public ModelAndView excelAvatarAdd() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageId", 3);
        map.put("pageTitle", "excel上传avatar");
        return new ModelAndView("pages/add-avatar", map);
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


            List<ArticleForm> list = ArticleExcelUtil.importDataMore(tempFile);

            if (!list.isEmpty()){
                list.forEach(l->{


                    ArticleInfo article = articleRepository.findAllByTitle(l.getTitle());
                    if (article==null){

                        String id = KeyUtil.genUniqueKey();
                        l.setArticleId(id);
                        articleService.articleSave(l);
                        if (!l.getCommentList().isEmpty()) {
                            for (int i=0;i<l.getCommentList().size();i++){
                                String name = l.getCommentName().get(i);
                                User user = userRepository.findAllByNikeName(name);
                                CommentForm commentForm = new CommentForm();
                                if (user==null){
                                    String userId = agoPersonalService.addDefaultUser(name,null);
                                    commentForm.setUid(userId);
                                }else {
                                    commentForm.setUid(user.getUserId());
                                }

                                commentForm.setCommentInfo(l.getCommentList().get(i));
                                commentForm.setAid(id);
                                agoCommentService.commontSave(commentForm);
                            }

                        }
                    }

                });
            }



            deleteFile(tempFile);


        }
        return ResultVOUtil.success();
    }

    @PostMapping("/article/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> importExcel(HttpServletRequest request,
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


            List<ArticleForm> list = ArticleExcelUtil.importDataClass(tempFile);

            if (!list.isEmpty()){
                list.forEach(l->{
                    System.out.println(l);
                    articleService.articleSave(l);
                });
            }



            deleteFile(tempFile);


        }
        return ResultVOUtil.success();
    }

    @PostMapping("/avatar/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> addUserAvatar(HttpServletRequest request,
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


            List<User> user = userRepository.findAll();
            List<String> list = ArticleExcelUtil.importAvatar(tempFile);

            if (!user.isEmpty()){
                for (int i=0;i<user.size();i++){
                    User u = user.get(i);
                    if (u.getAvatar()==null){
                        User uu = userRepository.findOne(u.getUserId());
                        uu.setAvatar(list.get(i));
                        userRepository.save(uu);
                    }
                }
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
