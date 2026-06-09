package com.shive.shiveoj.judge;

import cn.hutool.json.JSONUtil;
import com.shive.shiveoj.common.ErrorCode;
import com.shive.shiveoj.exception.BusinessException;
import com.shive.shiveoj.judge.codesandbox.CodeSandbox;
import com.shive.shiveoj.judge.codesandbox.CodeSandboxFactory;
import com.shive.shiveoj.judge.codesandbox.CodeSandboxProxy;
import com.shive.shiveoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.shive.shiveoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.shive.shiveoj.judge.strategy.JudgeContext;
import com.shive.shiveoj.model.dto.question.JudgeCase;
import com.shive.shiveoj.judge.codesandbox.model.JudgeInfo;
import com.shive.shiveoj.model.entity.Question;
import com.shive.shiveoj.model.entity.QuestionSubmit;
import com.shive.shiveoj.model.enums.JudgeInfoMessageEnum;
import com.shive.shiveoj.model.enums.QuestionSubmitStatusEnum;
import com.shive.shiveoj.service.QuestionService;
import com.shive.shiveoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Value("${codesandbox.type:example}")
    private String type;

    @Resource
    private JudgeManager judgeManager;


    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        // 没有题目提交
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        // 没有题目
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 判题状态不为等待中
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        // 更新判题状态
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新失败");
        }
        // 调用代码沙箱, 获取执行结果
        CodeSandbox sandbox = CodeSandboxFactory.newInstance(type);
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(sandbox);
        String code = questionSubmit.getCode();
        String language = questionSubmit.getLanguage();
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        // 判题
        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.executeCode(executeCodeRequest);
        JudgeInfo judgeInfo = buildJudgeInfo(executeCodeResponse, inputList, question, judgeCaseList, questionSubmit);
        // 更新判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新失败");
        }
        return questionSubmitService.getById(questionSubmitId);
    }

    /**
     * 根据沙箱结果构建判题信息
     * 沙箱 status: 1 成功 / 2 沙箱故障 / 3 运行错误 / 4 编译错误
     */
    private JudgeInfo buildJudgeInfo(ExecuteCodeResponse executeCodeResponse,
                                     List<String> inputList,
                                     Question question,
                                     List<JudgeCase> judgeCaseList,
                                     QuestionSubmit questionSubmit) {
        Integer sandboxStatus = executeCodeResponse.getStatus();
        JudgeInfo sandboxJudgeInfo = executeCodeResponse.getJudgeInfo();
        if (Integer.valueOf(2).equals(sandboxStatus)) {
            JudgeInfo judgeInfo = new JudgeInfo();
            judgeInfo.setMessage(JudgeInfoMessageEnum.SYSTEM_ERROR.getValue());
            return judgeInfo;
        }
        if (Integer.valueOf(4).equals(sandboxStatus)) {
            JudgeInfo judgeInfo = new JudgeInfo();
            judgeInfo.setMessage(JudgeInfoMessageEnum.COMPILE_ERROR.getValue());
            return judgeInfo;
        }
        if (Integer.valueOf(3).equals(sandboxStatus)) {
            JudgeInfo judgeInfo = new JudgeInfo();
            judgeInfo.setMessage(JudgeInfoMessageEnum.RUNTIME_ERROR.getValue());
            if (sandboxJudgeInfo != null) {
                judgeInfo.setTime(sandboxJudgeInfo.getTime());
                judgeInfo.setMemory(sandboxJudgeInfo.getMemory());
            }
            return judgeInfo;
        }
        List<String> outputList = executeCodeResponse.getOutputList();
        if (outputList == null) {
            outputList = new ArrayList<>();
        }
        if (sandboxJudgeInfo == null) {
            sandboxJudgeInfo = new JudgeInfo();
        }
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(sandboxJudgeInfo);
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setQuestion(question);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestionSubmit(questionSubmit);
        return judgeManager.doJudge(judgeContext);
    }
}
