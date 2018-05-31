package cn.popo.news.common.handler;



import cn.popo.news.core.exception.WebException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.NestedServletException;

/**
 * @author licoy.cn
 * @version 2017/11/18
 */
@ControllerAdvice
@Slf4j
public class GlobalDefaultExceptionHandler {

    /**
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NestedServletException.class)
    public ModelAndView pageNotFoundExceptionHandler(NestedServletException e){

        return new ModelAndView("/error/404");
    }

    /**
     * 身份未验证
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public ModelAndView unAuthorizationExceptionHandler(UnauthenticatedException e){

        return new ModelAndView("pages/login");
    }

    /**
     * 无权限
     * @param e
     * @return
     */
    @ExceptionHandler(AuthorizationException.class)
    public ModelAndView authorizationExceptionHandler(AuthorizationException e){

        return new ModelAndView("error/403");
    }

    @ExceptionHandler(WebException.class)
    public ModelAndView webExceptionHandler(WebException e){
        return new ModelAndView("error/400");
    }






}
