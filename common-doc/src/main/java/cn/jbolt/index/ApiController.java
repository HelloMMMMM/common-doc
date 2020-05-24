package cn.jbolt.index;

import java.io.File;

import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.upload.UploadFile;

public class ApiController extends Controller {

	@Inject
	private CommonDocService commonDocService;

	public void getDoc() {
		Ret ret = new Ret();
		ret.setOk();
		ret.set("doc", commonDocService.getDoc());
		renderJson(ret);
	}

	public void verify(String pwd) {
		renderJson(commonDocService.verify(pwd));
	}

	public void saveDoc(String content) {
		commonDocService.saveDoc(content);
		renderJson(new Ret().setOk());
	}

	public void upload() {
		UploadFile uploadFile = getFile();
		String uploadPath = uploadFile.getUploadPath();
		String targetUploadPath = uploadPath.substring(uploadPath.indexOf("upload"), uploadPath.length());
		String path = (PropKit.getBoolean("debug") ? PropKit.get("debugServerUrl") : PropKit.get("releaseServerUrl"))
				+ File.separator + targetUploadPath + File.separator + uploadFile.getFileName();
		Ret ret = new Ret();
		ret.setOk().set("image", path);
		renderJson(ret);
	}
}
