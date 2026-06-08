-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: my_db
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `content` text COLLATE utf8mb4_unicode_ci COMMENT '内容',
  `tags` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标签列表（json 数组）',
  `answer` text COLLATE utf8mb4_unicode_ci COMMENT '题目答案',
  `submitNum` int NOT NULL DEFAULT '0' COMMENT '题目提交数',
  `acceptedNum` int NOT NULL DEFAULT '0' COMMENT '题目通过数',
  `judgeCase` text COLLATE utf8mb4_unicode_ci COMMENT '判题用例（json 数组）',
  `judgeConfig` text COLLATE utf8mb4_unicode_ci COMMENT '判题配置（json 对象）',
  `thumbNum` int NOT NULL DEFAULT '0' COMMENT '点赞数',
  `favourNum` int NOT NULL DEFAULT '0' COMMENT '收藏数',
  `userId` bigint NOT NULL COMMENT '创建用户 id',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_userId` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=2051987252871946282 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='题目';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (2051987252871946272,'两数之和','# 题目描述\n计算两数之和\n\n# 输入\n两个整数 a、b（取值范围：0 ≤ a,b ≤ 1000）\n\n# 输出\n输出 a 与 b 的和。\n\n# 样例输入\n```plaintext\n1 2\n```\n\n# 样例输出\n```plaintext\n3\n```\n\n# 提示\n问：数据从哪里读取、结果输出到哪里？\n\n答：你的程序必须从标准输入（stdin）读取数据，并向标准输出（stdout）打印结果。','[\"入门\",\"数学\"]','```java\nimport java.util.Scanner;\n\npublic class Main {\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        int a = sc.nextInt();\n        int b = sc.nextInt();\n        System.out.println(a + b);\n    }\n}\n\n```',0,0,'[{\"input\":\"1 2\",\"output\":\"3\"},{\"input\":\"10 20\",\"output\":\"30\"}]','{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}',0,0,1,'2026-06-08 15:49:01','2026-06-08 15:51:29',0),(2051987252871946273,'两数之积','# 题目描述\n计算两数之积\n\n# 输入\n两个整数 a、b（取值范围：0 ≤ a,b ≤ 100）\n\n# 输出\n输出 a 与 b 的乘积。\n\n# 样例输入\n```plaintext\n3 4\n```\n\n# 样例输出\n```plaintext\n12\n```\n\n# 提示\n问：数据从哪里读取、结果输出到哪里？\n\n答：你的程序必须从标准输入（stdin）读取数据，并向标准输出（stdout）打印结果。','[\"入门\",\"数学\"]','import java.util.Scanner;\n\npublic class Main {\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        int a = sc.nextInt();\n        int b = sc.nextInt();\n        System.out.println(a * b);\n    }\n}',0,0,'[{\"input\":\"3 4\",\"output\":\"12\"},{\"input\":\"6 7\",\"output\":\"42\"}]','{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}',0,0,1,'2026-06-08 15:49:01','2026-06-08 15:49:01',0),(2051987252871946274,'判断奇偶','# 题目描述\n判断一个整数是奇数还是偶数\n\n# 输入\n一个整数 n（取值范围：-1000 ≤ n ≤ 1000）\n\n# 输出\n若 n 为偶数输出 `偶数`，否则输出 `奇数`。\n\n# 样例输入\n```plaintext\n4\n```\n\n# 样例输出\n```plaintext\n偶数\n```\n\n# 提示\n问：数据从哪里读取、结果输出到哪里？\n\n答：你的程序必须从标准输入（stdin）读取数据，并向标准输出（stdout）打印结果。','[\"入门\",\"分支\"]','import java.util.Scanner;\n\npublic class Main {\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        int n = sc.nextInt();\n        System.out.println(n % 2 == 0 ? \"偶数\" : \"奇数\");\n    }\n}',0,0,'[{\"input\":\"4\",\"output\":\"偶数\"},{\"input\":\"7\",\"output\":\"奇数\"}]','{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}',0,0,1,'2026-06-08 15:49:01','2026-06-08 15:49:01',0),(2051987252871946275,'三数最大值','# 题目描述\n求三个整数中的最大值\n\n# 输入\n三个整数 a、b、c（取值范围：-1000 ≤ a,b,c ≤ 1000）\n\n# 输出\n输出三者中的最大值。\n\n# 样例输入\n```plaintext\n1 5 3\n```\n\n# 样例输出\n```plaintext\n5\n```\n\n# 提示\n问：数据从哪里读取、结果输出到哪里？\n\n答：你的程序必须从标准输入（stdin）读取数据，并向标准输出（stdout）打印结果。','[\"入门\",\"数学\"]','import java.util.Scanner;\n\npublic class Main {\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        int a = sc.nextInt();\n        int b = sc.nextInt();\n        int c = sc.nextInt();\n        System.out.println(Math.max(a, Math.max(b, c)));\n    }\n}',0,0,'[{\"input\":\"1 5 3\",\"output\":\"5\"},{\"input\":\"-2 -8 -1\",\"output\":\"-1\"}]','{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}',0,0,1,'2026-06-08 15:49:01','2026-06-08 15:49:01',0),(2051987252871946276,'矩形面积','# 题目描述\n计算矩形面积\n\n# 输入\n两个正整数，分别表示矩形的长和宽（取值范围：1 ≤ 长,宽 ≤ 1000）\n\n# 输出\n输出矩形的面积。\n\n# 样例输入\n```plaintext\n3 4\n```\n\n# 样例输出\n```plaintext\n12\n```\n\n# 提示\n问：数据从哪里读取、结果输出到哪里？\n\n答：你的程序必须从标准输入（stdin）读取数据，并向标准输出（stdout）打印结果。','[\"入门\",\"数学\"]','import java.util.Scanner;\n\npublic class Main {\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        int length = sc.nextInt();\n        int width = sc.nextInt();\n        System.out.println(length * width);\n    }\n}',0,0,'[{\"input\":\"3 4\",\"output\":\"12\"},{\"input\":\"10 10\",\"output\":\"100\"}]','{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}',0,0,1,'2026-06-08 15:49:01','2026-06-08 15:49:01',0),(2051987252871946277,'求1到n的和','# 题目描述\n计算 1 到 n 的整数和\n\n# 输入\n一个正整数 n（取值范围：1 ≤ n ≤ 1000）\n\n# 输出\n输出 1 + 2 + ... + n 的结果。\n\n# 样例输入\n```plaintext\n5\n```\n\n# 样例输出\n```plaintext\n15\n```\n\n# 提示\n问：数据从哪里读取、结果输出到哪里？\n\n答：你的程序必须从标准输入（stdin）读取数据，并向标准输出（stdout）打印结果。','[\"入门\",\"循环\"]','import java.util.Scanner;\n\npublic class Main {\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        int n = sc.nextInt();\n        int sum = 0;\n        for (int i = 1; i <= n; i++) {\n            sum += i;\n        }\n        System.out.println(sum);\n    }\n}',0,0,'[{\"input\":\"5\",\"output\":\"15\"},{\"input\":\"10\",\"output\":\"55\"}]','{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}',0,0,1,'2026-06-08 15:49:01','2026-06-08 15:49:01',0),(2051987252871946278,'绝对值','# 题目描述\n求整数的绝对值\n\n# 输入\n一个整数 n（取值范围：-1000 ≤ n ≤ 1000）\n\n# 输出\n输出 n 的绝对值。\n\n# 样例输入\n```plaintext\n-8\n```\n\n# 样例输出\n```plaintext\n8\n```\n\n# 提示\n问：数据从哪里读取、结果输出到哪里？\n\n答：你的程序必须从标准输入（stdin）读取数据，并向标准输出（stdout）打印结果。','[\"入门\",\"数学\"]','import java.util.Scanner;\n\npublic class Main {\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        int n = sc.nextInt();\n        System.out.println(Math.abs(n));\n    }\n}',0,0,'[{\"input\":\"-8\",\"output\":\"8\"},{\"input\":\"0\",\"output\":\"0\"}]','{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}',0,0,1,'2026-06-08 15:49:01','2026-06-08 15:49:01',0),(2051987252871946279,'摄氏度转华氏度','# 题目描述\n将摄氏度转换为华氏度\n\n转换公式：F = C × 9 / 5 + 32\n\n# 输入\n一个整数 C，表示摄氏温度（取值范围：-100 ≤ C ≤ 100）\n\n# 输出\n输出对应的华氏温度（整数）。\n\n# 样例输入\n```plaintext\n0\n```\n\n# 样例输出\n```plaintext\n32\n```\n\n# 提示\n问：数据从哪里读取、结果输出到哪里？\n\n答：你的程序必须从标准输入（stdin）读取数据，并向标准输出（stdout）打印结果。','[\"入门\",\"数学\"]','import java.util.Scanner;\n\npublic class Main {\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        int c = sc.nextInt();\n        System.out.println(c * 9 / 5 + 32);\n    }\n}',0,0,'[{\"input\":\"0\",\"output\":\"32\"},{\"input\":\"100\",\"output\":\"212\"}]','{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}',0,0,1,'2026-06-08 15:49:01','2026-06-08 15:49:01',0),(2051987252871946280,'整数反转','# 题目描述\n将一个正整数各位数字反转后输出\n\n若反转后存在前导零，则忽略前导零。例如 1200 反转后为 21。\n\n# 输入\n一个正整数 n（取值范围：1 ≤ n ≤ 100000）\n\n# 输出\n输出反转后的整数。\n\n# 样例输入\n```plaintext\n123\n```\n\n# 样例输出\n```plaintext\n321\n```\n\n# 提示\n问：数据从哪里读取、结果输出到哪里？\n\n答：你的程序必须从标准输入（stdin）读取数据，并向标准输出（stdout）打印结果。','[\"入门\",\"字符串\"]','import java.util.Scanner;\n\npublic class Main {\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        String s = sc.next();\n        StringBuilder sb = new StringBuilder(s).reverse();\n        System.out.println(Integer.parseInt(sb.toString()));\n    }\n}',0,0,'[{\"input\":\"123\",\"output\":\"321\"},{\"input\":\"1200\",\"output\":\"21\"}]','{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}',0,0,1,'2026-06-08 15:49:01','2026-06-08 15:49:01',0),(2051987252871946281,'两数较小值','# 题目描述\n求两个整数中的较小值\n\n# 输入\n两个整数 a、b（取值范围：-1000 ≤ a,b ≤ 1000）\n\n# 输出\n输出 a 与 b 中较小的那个数。\n\n# 样例输入\n```plaintext\n8 3\n```\n\n# 样例输出\n```plaintext\n3\n```\n\n# 提示\n问：数据从哪里读取、结果输出到哪里？\n\n答：你的程序必须从标准输入（stdin）读取数据，并向标准输出（stdout）打印结果。','[\"入门\",\"数学\"]','import java.util.Scanner;\n\npublic class Main {\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        int a = sc.nextInt();\n        int b = sc.nextInt();\n        System.out.println(Math.min(a, b));\n    }\n}',0,0,'[{\"input\":\"8 3\",\"output\":\"3\"},{\"input\":\"-5 -2\",\"output\":\"-5\"}]','{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}',0,0,1,'2026-06-08 15:49:01','2026-06-08 15:49:01',0);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_submit`
--

DROP TABLE IF EXISTS `question_submit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question_submit` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `language` varchar(128) NOT NULL COMMENT '编程语言',
  `code` text NOT NULL COMMENT '用户代码',
  `judgeInfo` text COMMENT '判题信息（json 对象）',
  `status` int NOT NULL DEFAULT '0' COMMENT '判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）',
  `questionId` bigint NOT NULL COMMENT '题目 id',
  `userId` bigint NOT NULL COMMENT '创建用户 id',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_questionId` (`questionId`),
  KEY `idx_userId` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='题目提交';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_submit`
--

LOCK TABLES `question_submit` WRITE;
/*!40000 ALTER TABLE `question_submit` DISABLE KEYS */;
/*!40000 ALTER TABLE `question_submit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userAccount` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
  `userPassword` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `unionId` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信开放平台id',
  `mpOpenId` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公众号openId',
  `userName` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  `userAvatar` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户头像',
  `userProfile` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户简介',
  `userRole` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user' COMMENT '用户角色：user/admin/ban',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_unionId` (`unionId`)
) ENGINE=InnoDB AUTO_INCREMENT=2051202623982268418 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'liyl','b0dd3697a192885d7c055db46155b26a',NULL,NULL,'lyl',NULL,NULL,'admin','2026-05-04 15:29:49','2026-06-08 15:42:54',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-08 18:21:41
