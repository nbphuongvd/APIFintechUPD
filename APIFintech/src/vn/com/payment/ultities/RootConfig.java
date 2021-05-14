package vn.com.payment.ultities;

import java.net.URISyntaxException;

public class RootConfig {
	public RootConfig() {
		super();
		root = "./";
		try {
			this.root = this.getClass().getClassLoader().getResource("/").toURI().getPath();
			root = root.replaceAll("classes/", "");
//			System.out.println("Root:" + root);
		} catch (URISyntaxException e) {
			this.root = "./";
			//Debug.Error(e);
		} catch (Exception e){
			//Debug.Error(e);
			this.root = "./";
		}
	}


	public String getRoot() {
		return root;
	}

	private String root;
	
}
