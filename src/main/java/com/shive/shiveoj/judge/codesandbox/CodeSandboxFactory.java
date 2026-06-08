package com.shive.shiveoj.judge.codesandbox;

import com.shive.shiveoj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.shive.shiveoj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.shive.shiveoj.judge.codesandbox.impl.ThirdPartyCodeSandbox;
import com.shive.shiveoj.utils.SpringContextUtils;

/**
 * 根据字符串参数创建指定的代码沙箱实例
 */
public class CodeSandboxFactory {

    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return SpringContextUtils.getBean(RemoteCodeSandbox.class);
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
