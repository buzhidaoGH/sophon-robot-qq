package pvt.example.sophon.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 类&emsp;&emsp;名：SensitiveWordUtils <br/>
 * 描&emsp;&emsp;述：DFA敏感词过滤工具类
 */
@SuppressWarnings("unchecked")
public final class SensitiveWordUtils {
    // 存储敏感词 数据结构
    private static Map<Object, Object> sensitiveWordMap;
    // 遇到这些字符就会跳过，例如,如果"AB"是敏感词，那么"A B","A=B"也会被屏蔽
    private final static char[] skip = new char[]{'!', '*', '-', '+', '_', '=', ',', '.', '@', '！', '？', '，', '。', '、'};

    /**
     * 获取敏感词数据结构
     */
    public static Map<Object, Object> getSensitiveWordMap() {
        return sensitiveWordMap;
    }

    public static void loadSensitiveWords(InputStream inputStream, Charset encoding) {
        try (InputStream is = inputStream; InputStreamReader isr = new InputStreamReader(is, encoding);
             BufferedReader bufferedReader = new BufferedReader(isr)) {
            String line;
            Set<String> keyWord = new HashSet<String>();
            while ((line = bufferedReader.readLine()) != null) {
                keyWord.add(line);
            }
            initSensitiveWordToHashMap(keyWord);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadSensitiveWords(String path, Charset encoding) {
        InputStream ras = SensitiveWordUtils.class.getResourceAsStream(path);
        loadSensitiveWords(ras, encoding);
    }

    /**
     * 初始化 敏感词数据结构
     * @param keyWordSet 关键词set集合
     */
    private static void initSensitiveWordToHashMap(Set<String> keyWordSet) {
        // 初始化HashMap对象并控制容器的大小;防止扩容耗时
        sensitiveWordMap = new HashMap<Object, Object>(keyWordSet.size());
        // 用来辅助构建敏感词库
        Map<Object, Object> newWorMap = null;
        // 存储当前遍历的Map节点
        Map<Object, Object> nowMap = null;
        // 使用一个迭代器来循环敏感词集合
        for (String key : keyWordSet) {
            // 等于敏感词库，HashMap对象在内存中占用的是同一个地址，所以此nowMap对象的变化，sensitiveWordMap对象也会跟着改变
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {
                char keyChar = key.charAt(i);
                // 判断这个字是否存在于敏感词库中
                Object wordMap = nowMap.get(keyChar);
                if (wordMap == null) {
                    newWorMap = new HashMap<Object, Object>();
                    newWorMap.put("isEnd", false);
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                } else {
                    nowMap = (Map<Object, Object>) wordMap;
                }
                // 如果该字是当前敏感词的最后一个字，则标识为结尾字
                if (i == key.length() - 1) {
                    nowMap.put("isEnd", true);
                }
            }
        }
    }

    /**
     * 检测文本包含敏感词
     * @return false 不包含; true 包含
     */
    public static boolean checkSensitiveWord(String text) {
        if (sensitiveWordMap == null || sensitiveWordMap.keySet().size() == 0) {
            throw new RuntimeException("敏感词文本数据未初始化");
        }
        Map<Object, Object> nowMap = sensitiveWordMap; // 暂存Map节点
        char word; // 暂存单词
        int j;// 存储遍历范围
        for (int i = 0; i < text.length(); i++) {
            word = text.charAt(i);
            nowMap = (Map) nowMap.get(word); // 通过key获取map对象
            if (nowMap != null) {
                if (Boolean.parseBoolean(nowMap.get("isEnd").toString())) {
                    return true;
                }
                j = i + 1;
                while (j < text.length()) {
                    if (skip(text.charAt(j))) { // 跳过空格之类的无关字符
                        j++;
                        continue;
                    }
                    nowMap = (Map) nowMap.get(text.charAt(j));
                    if (nowMap == null) {
                        nowMap = sensitiveWordMap;
                        break;
                    }
                    if (Boolean.parseBoolean(nowMap.get("isEnd").toString())) {
                        return true;
                    }
                    j++;
                }
            } else {
                nowMap = sensitiveWordMap;
            }
        }
        return false;
    }

    /**
     * 判断此字符需不需要跳过
     */
    private static boolean skip(char c) {
        for (char c1 : skip) {
            if (c1 == c) { return true; }
        }
        return false;
    }
}
