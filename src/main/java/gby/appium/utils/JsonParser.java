package gby.appium.utils;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by saikrisv on 23/05/17.
 */
public class JsonParser {

    private String filePath;

    public JsonParser(String filePath) {
//    	if(filePath.contains(System.getProperty("user.dir"))){
//    		this.filePath = filePath;
//    	}
        this.filePath = System.getProperty("user.dir") + filePath;
    }
    public JsonParser() {
    }

    public JSONArray getJsonParsedObjectAsJsonArray() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
            String jsonContent = IOUtils.toString(bufferedReader);
            return JSON.parseArray(jsonContent);
        } catch (IOException e) {
            LoggerUtil.error("解析json出错：" + e.getMessage());
        }
        return null;
    }

    public JSONObject getObjectFromJSON() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
            String jsonContent = IOUtils.toString(bufferedReader);
            return JSON.parseObject(jsonContent);
        } catch (IOException e) {
        	LoggerUtil.error("解析json出错：" + e.getMessage());
        }
        return null;
    }

    // 把json格式的字符串写到文件
    public boolean writeFile(String filePath, String jsonString) {
        FileWriter fw;
        try {
            fw = new FileWriter(filePath);
            PrintWriter out = new PrintWriter(fw);
            out.write(jsonString);
            out.println();
            fw.close();
            out.close();
            LoggerUtil.debug("保存json完成，保存路径： " + filePath);
            return true;
        } catch (IOException e) {
        	LoggerUtil.error("保存json出错：" + e.getMessage());
            return false;
        }

    }
    public boolean writeFile(String jsonString) {
        boolean flag = false;
        writeFile(this.filePath, jsonString);
		return flag;
       
    }
}
