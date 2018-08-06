package gby.appium.utils;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by saikrisv on 23/05/17.
 */
public class JsonParser {

    private String filePath;

    public JsonParser(String filePath) {
        this.filePath = System.getProperty("user.dir")+filePath;
    }

    public JSONArray getJsonParsedObjectAsJsonArray() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
            String jsonContent = IOUtils.toString(bufferedReader);
            return JSON.parseArray(jsonContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getObjectFromJSON() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
            String jsonContent = IOUtils.toString(bufferedReader);
            return JSON.parseObject(jsonContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
