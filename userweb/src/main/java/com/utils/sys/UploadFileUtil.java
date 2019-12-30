package com.utils.sys;


import com.jcraft.jsch.*;
import java.io.*;

/**
 * 在远程服务器创建文件
 */
public class UploadFileUtil {
    //jsonArray是要写入的内容，使用时可忽略，换成自己的参数。。
    public static void upLoadToIgenetech(String content, String fileName){
        String dPath="/data/";
        JSch jsch = new JSch();
        Session session = null;
        try {
            //用户名、ip地址、端口号
            session = jsch.getSession("用户名", "ip", 22);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        // 设置登陆主机的密码
        session.setPassword("密码");
        // 设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        // 设置登陆超时时间
        try {
            session.connect(300000);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        Channel channel = null;
        try {
            channel = (Channel) session.openChannel("sftp");
            channel.connect(10000000);
            ChannelSftp sftp = (ChannelSftp) channel;
            String lastPath = System.currentTimeMillis()+"";
            try {
                sftp.cd(dPath+lastPath);
            } catch (SftpException e) {

                sftp.mkdir(dPath+lastPath);
                sftp.cd(dPath+lastPath);
            }
            OutputStream o = null;
            File file = new File(dPath+lastPath + "/" + fileName);
            o = sftp.put(file.getName());
            o.write(content.toString().getBytes("UTF-8"));
            o.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
            channel.disconnect();
        }
    }
}
