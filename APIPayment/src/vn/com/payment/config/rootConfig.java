package vn.com.payment.config;

public class rootConfig {
	static rootConfig _shareInstance = null;
	public static String root = "";

	public static rootConfig shareInstance() {
		if (_shareInstance == null)
			new rootConfig();
		return _shareInstance;
	}

	public rootConfig() {
		_shareInstance = this;
		if (root == null || root.length() == 0)
			root = "./";
		try {
			this.root = this.getClass().getClassLoader().getResource("/")
					.toURI().getPath();
			root = root.replaceAll("classes/", "");
		} catch (Exception e) {
			if (root == null || root.length() == 0)
				root = "./";
		}
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}
}
