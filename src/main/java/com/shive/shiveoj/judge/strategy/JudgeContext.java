package com.shive.shiveoj.judge.strategy;

import com.shive.shiveoj.model.dto.question.JudgeCase;
import com.shive.shiveoj.judge.codesandbox.model.JudgeInfo;
import com.shive.shiveoj.model.entity.Question;
import com.shive.shiveoj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文 用于定义在策略中传递的参数
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private Question question;

    private List<JudgeCase> judgeCaseList;

    private QuestionSubmit questionSubmit;
}
