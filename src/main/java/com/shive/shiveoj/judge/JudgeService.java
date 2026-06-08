package com.shive.shiveoj.judge;

import com.shive.shiveoj.model.entity.QuestionSubmit;
import com.shive.shiveoj.model.vo.QuestionSubmitVO;
import com.shive.shiveoj.model.vo.QuestionVO;

/**
 * 判题服务
 */
public interface JudgeService {

    /**
     * 判题
     *
     * @param questionSubmit
     * @return
     */
    QuestionSubmit doJudge(long questionSubmit);
}
