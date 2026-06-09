package com.shive.shiveoj.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shive.shiveoj.common.ErrorCode;
import com.shive.shiveoj.constant.CommonConstant;
import com.shive.shiveoj.exception.BusinessException;
import com.shive.shiveoj.judge.JudgeService;
import com.shive.shiveoj.judge.codesandbox.model.JudgeInfo;
import com.shive.shiveoj.mapper.QuestionSubmitMapper;
import com.shive.shiveoj.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.shive.shiveoj.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.shive.shiveoj.model.entity.Question;
import com.shive.shiveoj.model.entity.QuestionSubmit;
import com.shive.shiveoj.model.entity.User;
import com.shive.shiveoj.model.enums.JudgeInfoMessageEnum;
import com.shive.shiveoj.model.enums.QuestionSubmitLanguageEnum;
import com.shive.shiveoj.model.enums.QuestionSubmitStatusEnum;
import com.shive.shiveoj.model.vo.QuestionSubmitVO;
import com.shive.shiveoj.service.QuestionService;
import com.shive.shiveoj.service.QuestionSubmitService;
import com.shive.shiveoj.service.UserService;
import com.shive.shiveoj.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Liyal
 * @description 针对表【question_submit(题目提交)】的数据库操作Service实现
 * @createDate 2026-05-04 21:40:44
 */
@Service
@Slf4j
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserService userService;

    @Lazy
    @Resource
    private JudgeService judgeService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        // 校验编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionSubmitAddRequest.getQuestionId());
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交题目
        long userId = loginUser.getId();
        // 每个用户串行提交题目
        // 锁必须要包裹住事务方法
        /*QuestionSubmitService questionSubmitService = (QuestionSubmitService) AopContext.currentProxy();
        synchronized (String.valueOf(userId).intern()) {
            return questionSubmitService.doQuestionSubmitInner(userId, questionId);
        }*/
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(loginUser.getId());
        questionSubmit.setQuestionId(questionSubmitAddRequest.getQuestionId());
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(language);
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean result = this.save(questionSubmit);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目提交失败");
        }
        Long questionSubmitId = questionSubmit.getId();
        // 执行判题服务
        CompletableFuture.runAsync(() -> {
            try {
                judgeService.doJudge(questionSubmitId);
            } catch (Exception e) {
                log.error("判题失败, questionSubmitId={}", questionSubmitId, e);
                QuestionSubmit failedSubmit = new QuestionSubmit();
                failedSubmit.setId(questionSubmitId);
                failedSubmit.setStatus(QuestionSubmitStatusEnum.FAILED.getValue());
                JudgeInfo judgeInfo = new JudgeInfo();
                judgeInfo.setMessage(JudgeInfoMessageEnum.SYSTEM_ERROR.getValue());
                failedSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
                this.updateById(failedSubmit);
            }
        });
        return questionSubmitId;
    }

    /**
     * 获取查询包装类
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        return getQueryWrapper(questionSubmitQueryRequest, null);
    }

    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                        User loginUser) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }
        String language = questionSubmitQueryRequest.getLanguage();
        Integer status = questionSubmitQueryRequest.getStatus();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        Long userId = questionSubmitQueryRequest.getUserId();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();

        // 拼接查询条件
        queryWrapper.eq(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq(questionId != null && questionId > 0, "questionId", questionId);
        // 普通用户强制只能查自己的提交
        if (loginUser != null && !userService.isAdmin(loginUser)) {
            queryWrapper.eq("userId", loginUser.getId());
        } else if (userId != null && userId > 0) {
            queryWrapper.eq("userId", userId);
        }
        queryWrapper.eq(QuestionSubmitStatusEnum.getEnumByValue(status) != null, "status", status);
        boolean sortAsc = CommonConstant.SORT_ORDER_ASC.equals(sortOrder);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortAsc, sortField);
        return queryWrapper;
    }

    @Override
    public Page<QuestionSubmitVO> listQuestionSubmitVOByPage(QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                             User loginUser) {
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 普通用户强制只能查自己的提交；管理员可查看全部（也可按 userId 筛选）
        if (!userService.isAdmin(loginUser)) {
            questionSubmitQueryRequest.setUserId(loginUser.getId());
        }
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        Page<QuestionSubmit> questionSubmitPage = this.page(new Page<>(current, size),
                getQueryWrapper(questionSubmitQueryRequest, loginUser));
        return getQuestionSubmitVOPage(questionSubmitPage, loginUser);
    }


    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        // 脱敏
        Long userId = loginUser.getId();
        // 处理脱敏
        if (!userService.isAdmin(loginUser) && !Objects.equals(userId, questionSubmit.getUserId())) {
            questionSubmitVO.setCode(null);
        }
        return questionSubmitVO;
    }


    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollUtil.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }
        /*// 1. 关联查询用户信息
        Set<Long> userIdSet = questionSubmitList.stream().map(QuestionSubmit::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 2. 已登录，获取用户点赞、收藏状态
        Map<Long, Boolean> questionIdHasThumbMap = new HashMap<>();
        Map<Long, Boolean> questionIdHasFavourMap = new HashMap<>();
        User loginUser = userService.getLoginUserPermitNull(request);
        // 填充信息
        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream().map(question -> {
            QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(question);
            Long userId = question.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            questionSubmitVO.setUserVO(userService.getUserVO(user));
            return questionSubmitVO;
        }).collect(Collectors.toList());*/
        Set<Long> questionIdSet = questionSubmitList.stream()
                .map(QuestionSubmit::getQuestionId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Map<Long, Question> questionMap = questionService.listByIds(questionIdSet).stream()
                .collect(Collectors.toMap(Question::getId, Function.identity(), (a, b) -> a));
        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream().map(questionSubmit -> {
            QuestionSubmitVO questionSubmitVO = getQuestionSubmitVO(questionSubmit, loginUser);
            Question question = questionMap.get(questionSubmit.getQuestionId());
            if (question != null) {
                questionSubmitVO.setQuestionTitle(question.getTitle());
            }
            return questionSubmitVO;
        }).collect(Collectors.toList());
        if (!userService.isAdmin(loginUser)) {
            Long loginUserId = loginUser.getId();
            questionSubmitVOList = questionSubmitVOList.stream()
                    .filter(vo -> Objects.equals(vo.getUserId(), loginUserId))
                    .collect(Collectors.toList());
        }
        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;
    }

}




