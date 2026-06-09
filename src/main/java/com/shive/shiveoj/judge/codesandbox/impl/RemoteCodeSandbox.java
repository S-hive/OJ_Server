package com.shive.shiveoj.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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
        try {
            String responseStr = HttpUtil.createPost(url)
                    .body(json)
                    .execute()
                    .body();
            if (StringUtils.isBlank(responseStr) || !responseStr.trim().startsWith("{")) {
                return buildErrorResponse("沙箱无响应，请确认 code-sandbox 已启动");
            }
            ExecuteCodeResponse response = JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
            if (response == null) {
                return buildErrorResponse("沙箱响应解析失败");
            }
            return response;
        } catch (Exception e) {
            return buildErrorResponse("沙箱调用失败: " + e.getMessage());
        }
    }

    private ExecuteCodeResponse buildErrorResponse(String message) {
        ExecuteCodeResponse response = new ExecuteCodeResponse();
        response.setStatus(2);
        response.setMessage(message);
        return response;
    }
}
