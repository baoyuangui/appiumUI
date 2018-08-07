package gby.appium.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class putMap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  Map<String,String> map1 = new HashMap<String,String>();
	        map1.put("1", "1");
	        map1.put("2", "2");
	        map1.put("3", "3");
	        map1.put("4", "4");
	        map1.put(null, "null1");
	          
	        Map<String,String> map2 = new HashMap<String,String>();
	        map2.put("3", "33");
	        map2.put("4", "44");
	        map2.put("5", "5");
	        map2.put("6", "6");
	        map2.put(null, "null2");
	          
	        Map<String,String> map3 = new HashMap<String,String>();
	        map3.putAll(map1);
	        // map2中含有和map1中相同的key，那么执行如下方法之后
	        // map2中的值会覆盖掉map1中的值
	        map3.putAll(map2);
	        
	        for (Entry<String,String> entry : map3.entrySet()) {
	            System.out.println(entry.getKey() + " ==== " + entry.getValue());
	        }
	    }
		

	
}
