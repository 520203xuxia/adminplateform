package cn.edu.szu.bigdata.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by longhao on 2017/8/29.
 * Email: longhao1@email.szu.edu.cn
 */
public class constant {
    public static String currentUser="longhao";
    public static String defalutContentType="docx";

    public static String getOffice_online_addr(){
        String ip=null;
        try{
            ip = InetAddress.getLocalHost().getHostAddress();
        }catch(UnknownHostException e){
            e.printStackTrace();
        }
        return "http://172.31.238.77/we/wordeditorframe.aspx?WOPISrc=http://"+ip+":8080/wopi/files/";
    }
}
