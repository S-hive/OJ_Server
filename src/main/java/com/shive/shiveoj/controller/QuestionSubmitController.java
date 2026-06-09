package com.shive.shiveoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shive.shiveoj.common.BaseResponse;
import com.shive.shiveoj.common.ErrorCode;
import com.shive.shiveoj.common.ResultUtils;
import com.shive.shiveoj.exception.BusinessException;
import com.shive.shiveoj.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.shive.shiveoj.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.shive.shiveoj.model.entity.QuestionSubmit;
import com.shive.shiveoj.model.entity.User;
import com.shive.shiveoj.model.vo.QuestionSubmitVO;
import com.shive.shiveoj.service.QuestionSubmitService;
import com.shive.shiveoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 *
 */
//@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return resultNum 提交记录Id
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
                                               HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
        Long result = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        //todo: 提交代码到代码沙箱
        return ResultUtils.success(result);
    }

    /**
     * 分页获取题目提交列表 除了管理员外 普通用户只能看到非答案,提交代码等公开信息
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest, HttpServletRequest request) {
        final User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(
                questionSubmitService.listQuestionSubmitVOByPage(questionSubmitQueryRequest, loginUser));
    }
}
