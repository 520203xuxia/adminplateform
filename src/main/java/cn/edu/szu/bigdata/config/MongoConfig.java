package cn.edu.szu.bigdata.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longhao on 2017/9/5.
 * Email: longhao1@email.szu.edu.cn
 */

public class MongoConfig {

    private static  String host="172.31.238.142";

    private static String  port="27017";

    private static String username="oneUser";

    private static String password="oneUser123";

    private static String database="test";



    public static Mongo getMongo(){

        MongoCredential mongoCredential=MongoCredential.createCredential(username,database,password.toCharArray());
        List<MongoCredential> mongoCredentials=new ArrayList<>();
        mongoCredentials.add(mongoCredential);
        ServerAddress serverAddress=new ServerAddress(host,Integer.valueOf(port));
        return new MongoClient(serverAddress,mongoCredentials);
    }
}
