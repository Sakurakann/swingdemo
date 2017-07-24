package utils.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.gs.biz.BizApplication;
import com.jeesoon.tnits.client.web.WebClient;

public class XMLUtil {

	public XMLUtil() {
	}

	public static void RMIConn() throws Exception{

		new BizApplication("server.properties.xml").run();			
		WebClient.login("test", "test");

	}

	public static void ModifyRMIUrl(String IP, String port) {
		String url = "rmi://" + IP + ":" + port + "/server";
		try {
			Document doc = new SAXReader().read(new File(
					"server.properties.xml"));

			FileOutputStream fos = new FileOutputStream("server.properties.xml");

			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");

			@SuppressWarnings("unchecked")
			List<Element> elements = doc.getRootElement().element("modules")
					.elements();
			for (Element element : elements) {
				Attribute urlAttribute = element.attribute("url");
				if (urlAttribute != null) {
					urlAttribute.setValue(url);
				}
			}
			XMLWriter writer = new XMLWriter(fos);
			writer.write(doc);
			
			fos.flush();
			fos.close();
			writer.close();
		} catch (DocumentException e) {
			System.out.println("文件不存在");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("文件写出失败");
			e.printStackTrace();
		}
	}

}
