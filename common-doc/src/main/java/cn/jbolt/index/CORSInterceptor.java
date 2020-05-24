package cn.jbolt.index;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;

public class CORSInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		HttpServletRequest req = controller.getRequest();
		HttpServletResponse res = controller.getResponse();
		// String origin = req.getHeader("Origin");
		// if (origin == null) {
		// controller.renderNull();
		// return;
		// }
		// if (Constant.BASE_ADDRESS.equals(origin)) {
		// res.setHeader("Access-Control-Allow-Origin", Constant.BASE_ADDRESS);
		// } else if (Constant.BASE_ADDRESS_ADMIN.equals(origin)) {
		// res.setHeader("Access-Control-Allow-Origin",
		// Constant.BASE_ADDRESS_ADMIN);
		// } else {
		// controller.renderNull();
		// return;
		// }
//		res.setHeader("Access-Control-Allow-Origin", Constant.BASE_ADDRESS_ADMIN);
		res.setHeader("Access-Control-Allow-Origin", PropKit.get("baseUrl"));
		// 允许携带cookie
		res.setHeader("Access-Control-Allow-Credentials", "true");
		// 允许的头部内容
		res.setHeader("Access-Control-Allow-Headers", "x-requested-with,Content-Type");
		// 允许的动作
		res.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS");

		// OPTIONS直接返回
		if (StrKit.equals("OPTIONS", req.getMethod())) {
			controller.renderNull();
			return;
		}
		inv.invoke();

		// //允许地址
		// getResponse().setHeader("Access-Control-Allow-Origin",
		// "http://localhost");
		//
		// getResponse().setHeader("Access-Control-Allow-Credentials", "true");
		// //允许的方法
		// getResponse().setHeader("Access-Control-Allow-Methods", "POST,
		// GET,PUT, OPTIONS, DELETE,OPTIONS");
		//
		// //所能看到的请求头
		// getResponse().setHeader("Access-Control-Allow-Headers","Authorization,Origin,
		// No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified,
		// Cache-Control, Expires, Content-Type, X-E4M-With");
		//
		// //浏览器所能获取到的请求头
		// getResponse().setHeader("Access-Control-Expose-Headers","Authorization,Origin,
		// No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified,
		// Cache-Control, Expires, Content-Type, X-E4M-With");
	}
}
