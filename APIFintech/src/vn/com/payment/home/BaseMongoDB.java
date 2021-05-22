package vn.com.payment.home;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import vn.com.payment.config.LogType;
import vn.com.payment.config.MainCfg;
import vn.com.payment.ultities.FileLogger;

public class BaseMongoDB {

	int port_no = MainCfg.port_mongo;
	String host_name = MainCfg.host_name_mongo, db_name = MainCfg.db_name;

	// Mongodb connection string.
	String client_url = "mongodb://" + host_name + ":" + port_no + "/" + db_name;
	MongoClientURI uri = new MongoClientURI(client_url);

	// Connecting to the mongodb server using the given client uri.
	MongoClient mongo_client = new MongoClient(uri);
	// Fetching the database from the mongodb.
	MongoDatabase db = mongo_client.getDatabase(db_name);


	/**
	 * static Singleton instance.
	 */
	public static volatile BaseMongoDB instance;

	/**
	 * Private constructor for singleton.
	 */
	public BaseMongoDB() {

	}

	/**
	 * Return a singleton instance of TblWardDao.
	 */
	public static BaseMongoDB getInstance() {
		// Double lock for thread safety.
		if (instance == null) {
			synchronized (BaseMongoDB.class) {
				if (instance == null) {
					instance = new BaseMongoDB();
				}
			}
		}
		return instance;
	}

	
	
	public Boolean insertDocument(ArrayList<Document> illustration, String collection) {
		// TODO Auto-generated method stub
		try {
			// Fetching the collection from the mongodb.
			MongoCollection<Document> coll = db.getCollection(collection);
			coll.insertMany(illustration);
		} catch (Exception e) {
			// TODO: handle exception
			FileLogger.log("insertDocument mongo Exception: " + e, LogType.ERROR);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		BaseMongoDB mongoDB = new BaseMongoDB();
//		mongoDB.insertDocument(listDoc, collection)
	}
}
