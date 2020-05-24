package cn.jbolt.index;
import com.jfinal.core.Controller;


public class IndexController extends Controller {
	/**
	 * 首页Action
	 */
	public void index() {
		render("index.html");
	}
}