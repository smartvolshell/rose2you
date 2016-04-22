package com.volshell.wechat.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.volshell.wechat.util.SHA1;

/**
 * @author volshell
 * @version 1.0
 *
 *          核心处理，完成wechat的url验证
 */
public class CoreServlet extends HttpServlet {

	private static final long serialVersionUID = 746254629684863352L;
	private static String TOKEN = "lovestone";
	private static Logger logger = LoggerFactory.getLogger(CoreServlet.class);

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 确认请求来至微信
		if (isAccess(request, response)) {
			logger.info("access successfully");
			response.getWriter().print(request.getParameter("echostr"));
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private static boolean isAccess(HttpServletRequest request,
			HttpServletResponse response) {

		// 微信加密签名
		String signature = request.getParameter("signature");
		if (!StringUtils.hasLength(signature)) {
			return false;
		}
		// 随机字符串
		String echostr = request.getParameter("echostr");
		if (!StringUtils.hasLength(echostr)) {
			return false;
		}
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		if (!StringUtils.hasLength(timestamp)) {
			return false;
		}
		// 随机数
		String nonce = request.getParameter("nonce");
		if (!StringUtils.hasLength(nonce)) {
			return false;
		}
		String[] str = { TOKEN, timestamp, nonce };
		Arrays.sort(str); // 字典序排序
		String bigStr = str[0] + str[1] + str[2];
		// SHA1加密
		String digest = new SHA1().getDigestOfString(bigStr.getBytes())
				.toLowerCase();
		if (digest.equals(signature)) {
			return true;
		}
		return false;
	}

	private static String getRequest(HttpServletRequest request) {
		String requestStr = "";
		try {
			InputStream is = request.getInputStream();
			// 取HTTP请求流长度
			int size = request.getContentLength();
			// 用于缓存每次读取的数据
			byte[] buffer = new byte[size];
			// 用于存放结果的数组
			byte[] xmldataByte = new byte[size];
			int count = 0;
			int rbyte = 0;
			// 循环读取
			while (count < size) {
				// 每次实际读取长度存于rbyte中
				rbyte = is.read(buffer);
				for (int i = 0; i < rbyte; i++) {
					xmldataByte[count + i] = buffer[i];
				}
				count += rbyte;
			}
			is.close();
			// 转码
			requestStr = new String(xmldataByte, "UTF-8");
		} catch (IOException e) {
			logger.info("exception at get request content " + e);
			e.printStackTrace();
		}
		return requestStr;
	}
	 private void  manageMessage(String requestStr,HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{  
         
         String responseStr;  
          
         try {  
              XMLSerializer xmlSerializer=new XMLSerializer();  
              JSONObject jsonObject =(JSONObject) xmlSerializer.read(requestStr);  
              String event =jsonObject.getString("Event");  
              String msgtype =jsonObject.getString("MsgType");  
              if("CLICK".equals(event) && "event".equals(msgtype)){ //菜单click事件  
                  String eventkey =jsonObject.getString("EventKey");  
                  if("hytd_001".equals(eventkey)){ // hytd_001 这是好友团队按钮的标志值  
                      jsonObject.put("Content", "欢迎使用好友团队菜单click按钮.");  
                  }  
                   
              }  
              responseStr =creatRevertText(jsonObject);//创建XML  
              log.info("responseStr:"+responseStr);  
              OutputStream os =response.getOutputStream();  
              os.write(responseStr.getBytes("UTF-8"));  
         }   catch (Exception e) {  
             e.printStackTrace();  
         }  
           
 }  
}
