package com.me.activity.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;


/**
 * @program: activity(BaseEntityEventListener事件监听器的base类)
 * @description: activiti事件监听
 * @author: lic
 * @create: 2020-01-08 14:16
 **/
public class MyEventListener implements ActivitiEventListener {
    @Override
    public void onEvent(ActivitiEvent event) {
        switch (event.getType()) {

            case JOB_EXECUTION_SUCCESS:
                System.out.println("A job well done!");
                break;

            case JOB_EXECUTION_FAILURE:
                System.out.println("A job has failed...");
                break;

            default:
                System.out.println("Event received: " + event.getType());
        }
    }
    
    /**
    *@Description: 当事件发送抛出异常时，会调用isFailOnException，false代表异常将被忽略
    * true 代表异常不被忽略，直接被抛出，当事件是事务的一部分，事务将被回滚。推荐返回false
    *@Param: []
    *@return: boolean
    *@Author: lic
    *@date: 2020/1/8
    */
    @Override
    public boolean isFailOnException() {
        // The logic in the onEvent method of this listener is not critical, exceptions
        // can be ignored if logging fails...
        return false;
    }
}
