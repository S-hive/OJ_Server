package com.shive.shiveoj.judge;

import com.shive.shiveoj.judge.strategy.DefaultJudgeStrategy;
import com.shive.shiveoj.judge.strategy.JavaLanguageJudgeStrategy;
import com.shive.shiveoj.judge.strategy.JudgeContext;
import com.shive.shiveoj.judge.strategy.JudgeStrategy;
import com.shive.shiveoj.judge.codesandbox.model.JudgeInfo;
import com.shive.shiveoj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理 简化调用
 */
@Service
public class JudgeManager {

    /**
     * 判题
     *
     * @param judgeContext@return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if (language.equals("java")) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
