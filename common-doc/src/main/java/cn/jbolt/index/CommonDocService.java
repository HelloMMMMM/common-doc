package cn.jbolt.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;

public class CommonDocService {

	private String docPath = PathKit.getWebRootPath() + "/doc";

	public void saveDoc(String content) {
		try {
			File file = new File(docPath);
			if (file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(content.getBytes("utf-8"));
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Ret verify(String pwd) {
		Ret ret = new Ret();
		if (StrKit.equals(pwd, PropKit.get("pwd"))) {
			ret.setOk();
		} else {
			ret.setFail().set("msg", "验证失败");
		}
		return ret;
	}

	public String getDoc() {
		File file = new File(docPath);
		try {
			if (file.exists() && file.isFile()) {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
				StringBuffer sb = new StringBuffer();
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				String doc = null;
				if (!StrKit.isBlank(PropKit.get("releaseServerUrl"))
						&& !StrKit.isBlank(PropKit.get("debugServerUrl"))) {
					doc = sb.toString().replace(
							PropKit.getBoolean("debug") ? PropKit.get("releaseServerUrl")
									: PropKit.get("debugServerUrl"),
							PropKit.getBoolean("debug") ? PropKit.get("debugServerUrl")
									: PropKit.get("releaseServerUrl"));
				}
				return doc;
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}
}
