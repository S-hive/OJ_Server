package com.shive.shiveoj.judge.codesandbox;

import com.shive.shiveoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.shive.shiveoj.judge.codesandbox.model.ExecuteCodeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SpringBootTest
class CodeSandboxTest {

    @Value("${CodeSandbox.type}")
    private String type;

    @Test
    void executeCode() {
        System.out.println("请输入代码沙箱(example, remote, thirdParty):");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String type = scanner.next();
            String code = "int main() { }";
            String language = "Java";
            List<String> inputList = Arrays.asList("1 2", "3 4");
            CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
            ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                    .code(code)
                    .language(language)
                    .inputList(inputList)
                    .build();
            ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
            System.out.println(executeCodeResponse);
        }
    }

    @Test
    void executeCodeByProxy() {
        CodeSandbox sandbox = CodeSandboxFactory.newInstance(type);
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(sandbox);
        String code = "public class Main {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        int a = Integer.parseInt(args[0]);\n" +
                "        int b = Integer.parseInt(args[1]);\n" +
                "        System.out.println(\"结果:\" + (a + b));" +
                "    }" +
                "}";
        String language = "Java";
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.executeCode(executeCodeRequest);
        System.out.println(executeCodeResponse);
    }

    @Test
    void executeCodeByValue() {
        String code = "int main() { }";
        String language = "Java";
        List<String> inputList = Arrays.asList("1 2", "3 4");
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        System.out.println(executeCodeResponse);
    }

    public static void main(String[] args) {
        System.out.println("请输入代码沙箱(example, remote, thirdParty):");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String type = scanner.next();
            String code = "int main() { }";
            String language = "Java";
            List<String> inputList = Arrays.asList("1 2", "3 4");
            CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
            ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                    .code(code)
                    .language(language)
                    .inputList(inputList)
                    .build();
            ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
            System.out.println(executeCodeResponse);
        }
    }
}