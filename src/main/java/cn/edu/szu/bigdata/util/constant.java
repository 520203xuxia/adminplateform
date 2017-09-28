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
//        String ip="longhaofeixia.6655.la";
//        String port="15341";
//        String office_online_server_ip="longbigdata.wicp.net";
        String office_online_server_ip="172.31.238.77";
        String ip=null;
        String port="8080";
        try{
            ip = InetAddress.getLocalHost().getHostAddress();
        }catch(UnknownHostException e){
            e.printStackTrace();
        }
        return "http://"+office_online_server_ip+"//we/wordeditorframe.aspx?WOPISrc=http://"+ip+":"+port+"/wopi/files/";
    }
}
