package com.gaols.study.studyboot.core;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * default error handler to handle all exceptions.
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }

    // uncomment the following method if your app is api based
//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public Object apiErrorHandler(HttpServletRequest req, Exception e) {
//        return null;
//    }

}
