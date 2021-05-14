/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package vn.com.payment.ultities;

import java.io.File;
import java.io.IOException;

/**
 * Main class that can bootstrap an ActiveMQ broker console. Handles command
 * line argument parsing to set up and run broker tasks.
 * 
 * @version $Revision$
 */
public class Classloader {
	public static void loadLib() {
		String home = ".";
		String lib = home + "/lib/";
		File dir = new File(lib);
		String[] libFile = dir.list();

		if (libFile == null)
			return;
		for (int i = 0; i < libFile.length; i++) {
			if ((libFile[i].indexOf(".jar") < 0)
					&& (libFile[i].indexOf(".zip") < 0))
				continue;
			System.out.println(lib + "/" + libFile[i]);
			File jar = new File(libFile[i]);
			try {
				ClassLoaderUtil.addFile(lib + "/" + libFile[i]);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

}
