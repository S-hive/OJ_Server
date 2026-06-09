package com.shive.shiveoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shive.shiveoj.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.shive.shiveoj.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.shive.shiveoj.model.entity.QuestionSubmit;
import com.shive.shiveoj.model.entity.User;
import com.shive.shiveoj.model.vo.QuestionSubmitVO;

/**
 * @author Liyal
 * @description 针对表【question_submit(题目提交)】的数据库操作Service
 * @createDate 2026-05-04 21:40:44
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);


    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取查询条件（按登录用户权限过滤）
     *
     * @param questionSubmitQueryRequest
     * @param loginUser
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest, User loginUser);

    /**
     * 分页获取题目提交列表（普通用户仅自己的记录）
     *
     * @param questionSubmitQueryRequest
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> listQuestionSubmitVOByPage(QuestionSubmitQueryRequest questionSubmitQueryRequest, User loginUser);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
