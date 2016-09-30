package httpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

//public class TestHttpClient {
//	@Test
//	public void testGet() throws Exception{
//		//1.创建httpClient对象
//		CloseableHttpClient client = HttpClients.createDefault();
//		
//		//2.模拟一个get请求
//		String uri = "http://news.sohu.com/20160923/n468994259.shtml";
//		HttpGet get = new HttpGet(uri);
//		CloseableHttpResponse response = client.execute(get);
//		
//		//4.从相应对象中回去页面源文件内容
//		String str = EntityUtils.toString(response.getEntity());
//		System.out.println(str);
//		//5.释放资源
//		client.close();
//	}
//	@Test
//	public void testPost() throws Exception{
//		//1.创建httpClient对象
//		CloseableHttpClient client = HttpClients.createDefault();
//		
//		//2.模拟一个get请求
//		String uri = "http://search.jd.com/Search";
//		HttpPost post = new HttpPost(uri);
//		
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		
//		params.add(new BasicNameValuePair("keyword", "iphone"));
//		
//		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"utf-8");
//		post.setEntity(entity);
//		
//		CloseableHttpResponse response = client.execute(post);
//		
//		//4.从相应对象中回去页面源文件内容
//		String str = EntityUtils.toString(response.getEntity(),"utf-8");
//		System.out.println(str);
//		//5.释放资源
//		client.close();
//	}
//}
