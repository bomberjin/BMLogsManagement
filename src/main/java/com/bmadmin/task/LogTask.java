package com.bmadmin.task;

import com.bmadmin.common.BMUtil;
import com.bmadmin.common.DeleteFileUtil;
import com.bmadmin.common.FileZip;
import org.apache.log4j.Logger;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * Created by 金玮良 on 2017/6/14 0014.
 */
@Configuration
@EnableScheduling //启动定时任务
public class LogTask {
    private Logger logger = Logger.getLogger(LogTask.class);

    @Value("${bm.log.file.path}")
    private String logFilePath;//待管理日志路径
    @Value("${bm.log.file.savedays}")//
    private String savedays;//需要保留天数

    /**
     * 定时将前一天日志进行压缩，并控制日志文件保存天数
     */
    @Scheduled(cron = "0/2 * * * * ?")
    public void LogHandle() throws Exception {
        logger.info("根据定时开始执行日志管理任务，此次日志管理开始时间：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-HH-dd HH:mm:ss")));
        logger.info("当前设置日志保留天数：" + savedays);
        logger.info("当前设置日志管理路径：" + logFilePath);

        int logsZipNum = 0;
        int logsOutOfDaySetNum = 0;

        LocalDate nowDate = LocalDate.now();

        if (BMUtil.isNotNull(logFilePath)) {
            String[] split = logFilePath.split(",");

            for (String pathItems : split) {

                List<String> fileName = FileZip.getFileName(pathItems);
                if (BMUtil.isNullOrEmpty(fileName)) {
                    continue;
                }
                //压缩文件处理
                for (String fileItems : fileName) {
                    if (fileItems.length() > 10 && !fileItems.substring(fileItems.length() - 3).equals("zip")) {
                        LocalDate filetime = LocalDate.parse(fileItems.substring(fileItems.length() - 10));
                        if (filetime.isBefore(nowDate)) {
                            FileZip.zip(pathItems + "\\" + fileItems + ".zip", new File(pathItems + "\\" + fileItems));//压缩日志文件
                            DeleteFileUtil.delete(pathItems + "\\" + fileItems);//删除压缩过的日志文件
                            logsZipNum++;
                        }

                    }
                }
                //删除设定日志之外的日志文件
                //TODO 2017.6.15



            }

        }

        logger.info("此次日志管理结束时间：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-HH-dd HH:mm:ss")) + ",共压缩日志数量：" + logsZipNum + ",共删除过期日志数量:" + logsOutOfDaySetNum);
    }
}
