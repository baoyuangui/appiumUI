/**
 * 
 */
package gby.appium.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.http.util.Args;

/**
 * @author gby
 *
 */
public class EditHtmlReport {

	/**
	 * 
	 */
	public static void editCode() {
		// TODO Auto-generated constructor stub
		try{
			BufferedReader in = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/target/surefire-reports/html/output.html"));
			String content = null;
			String s = null;
			while((s = in.readLine()) != null){
				s = s.replace("&quot;", "\"");
				s = s.replace("&gt;", ">");
				s = s.replace("&lt;", "<");
				s = s.replace("&apos;", "'");
				content += s;
			}
			in.close();
			BufferedWriter out = new BufferedWriter(new FileWriter(System.getProperty("user.dir")+"/target/surefire-reports/html/output.html"));
			out.write(content);
			out.close();
			}catch(IOException e){
			e.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		EditHtmlReport.editCode();
	}

}
