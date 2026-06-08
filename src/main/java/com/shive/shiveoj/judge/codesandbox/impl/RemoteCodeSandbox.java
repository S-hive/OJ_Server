package com.shive.shiveoj.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.shive.shiveoj.common.ErrorCode;
import com.shive.shiveoj.exception.BusinessException;
import com.shive.shiveoj.judge.codesandbox.CodeSandbox;
import com.shive.shiveoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.shive.shiveoj.judge.codesandbox.model.ExecuteCodeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 远程代码沙箱 (调用接口)
 */
@Component
public class RemoteCodeSandbox implements CodeSandbox {

    @Value("${codesandbox.url:http://localhost:8090/executeCode}")
    private String sandboxUrl;

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String url = sandboxUrl;
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .body(json)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR,
                    "executeCode remoteSandbox error, message = " + responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);

    }
}
