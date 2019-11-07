package com.utils.sys;

import com.afterInit.QuartzInit;
import org.apache.commons.lang3.time.DateUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Quartz调度管理器
 */
public class QuartzManager {
    private static String JOB_GROUP_NAME = "BASE_JOBGROUP_NAME";
    private static String TRIGGER_GROUP_NAME = "BASE_TRIGGERGROUP_NAME";
    private static final Logger logger = LoggerFactory.getLogger(QuartzInit.class);
    /**
     * @param sched   调度器
     * @param jobName 任务名
     * @param cls     任务
     * @param time    时间设置，参考quartz说明文档
     * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     * @Title: QuartzManager.java
     */

//    private Scheduler scheduler;


    public static void addJob(Scheduler sched, String jobName, @SuppressWarnings("rawtypes") Class cls, String time) {
        try {
            JobKey jobKey = new JobKey(jobName, JOB_GROUP_NAME);// 任务名，任务组，任务执行类

            JobDetail jobDetail = newJob(cls).withIdentity(jobKey).build();
            TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);// 触发器
            Trigger trigger = newTrigger().withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(time)).build();// 触发器时间设定
            sched.scheduleJob(jobDetail, trigger);
            if (!sched.isShutdown()) {
                sched.start();// 启动
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param sched            调度器
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass         任务
     * @param time             时间设置，参考quartz说明文档
     * @Description: 添加一个定时任务
     * @Title: QuartzManager.java
     */
    public static void addJob(Scheduler sched, String jobName,
                              String jobGroupName, String triggerName, String triggerGroupName,
                              @SuppressWarnings("rawtypes") Class jobClass, String time) {
        try {
            JobKey jobKey = new JobKey(jobName, jobGroupName);
            @SuppressWarnings("unchecked")
            JobDetail jobDetail = newJob(jobClass).withIdentity(jobKey).build();
            // 触发器
            TriggerKey triggerKey = new TriggerKey(triggerName,
                    triggerGroupName);
            Trigger trigger = newTrigger().withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
            sched.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //执行simpletrigger
    public static void runOneJob(Scheduler sched, String jobName,
                                 String jobGroupName, int delay, long interval, int times,
                                 @SuppressWarnings("rawtypes") Class jobClass) {
        //当前时间加上延迟时间
        Date d = new Date();
        Date runDate = DateUtils.addSeconds(d, delay);
        try {
            JobKey jobKey = new JobKey(jobName, jobGroupName);
            JobDetail jobDetail = newJob(jobClass).withIdentity(jobKey).build();
            SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
            trigger.setName(jobKey.getName());
            trigger.setJobDetail(jobDetail);
            trigger.setStartTime(runDate);
//            trigger.setRepeatCount(times);
            trigger.setRepeatInterval(interval);
            trigger.afterPropertiesSet();
            sched.scheduleJob(jobDetail, trigger.getObject());
            sched.triggerJob(jobKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param sched   调度器
     * @param jobName
     * @param time
     * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     * @Title: QuartzManager.java
     */
    @SuppressWarnings("rawtypes")
    public static void modifyJobTime(Scheduler sched, String jobName,
                                     String time) {
        try {
            TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {
                JobKey jobKey = new JobKey(jobName, JOB_GROUP_NAME);
                JobDetail jobDetail = sched.getJobDetail(jobKey);
                Class objJobClass = jobDetail.getJobClass();
                removeJob(sched, jobName);
                logger.info("修改任务：" + jobName);
                addJob(sched, jobName, objJobClass, time);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param sched            调度器 *
     * @param sched            调度器
     * @param triggerName
     * @param triggerGroupName
     * @param time
     * @Description: 修改一个任务的触发时间
     * @Title: QuartzManager.java
     */
    public static void modifyJobTime(Scheduler sched, String triggerName,
                                     String triggerGroupName, String time) {
        try {
            TriggerKey triggerKey = new TriggerKey(triggerName,
                    triggerGroupName);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {
                // 修改时间
                trigger.getTriggerBuilder().withSchedule(CronScheduleBuilder.cronSchedule(time));
                // 重启触发器
                sched.resumeTrigger(triggerKey);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param sched   调度器
     * @param jobName
     * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     * @Title: QuartzManager.java
     */
    public static void removeJob(Scheduler sched, String jobName) {
        try {
            TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);
            sched.pauseTrigger(triggerKey);// 停止触发器
            sched.unscheduleJob(triggerKey);// 移除触发器
            JobKey jobKey = new JobKey(jobName, JOB_GROUP_NAME);
            sched.deleteJob(jobKey);// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param sched            调度器
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @Description: 移除一个任务
     * @Title: QuartzManager.java
     */
    public static void removeJob(Scheduler sched, String jobName,
                                 String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            TriggerKey triggerKey = new TriggerKey(triggerName,
                    triggerGroupName);
            sched.pauseTrigger(triggerKey);// 停止触发器
            sched.unscheduleJob(triggerKey);// 移除触发器
            JobKey jobKey = new JobKey(jobName, jobGroupName);
            sched.deleteJob(jobKey);// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param sched 调度器
     * @Description:启动所有定时任务
     * @Title: QuartzManager.java
     */
    public static void startJobs(Scheduler sched) {
        try {
            sched.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param sched 调度器
     * @Description:关闭所有定时任务
     */
    public static void shutdownJobs(Scheduler sched) {
        try {
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
