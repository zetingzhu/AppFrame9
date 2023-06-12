package com.zzt.zt_queuelist;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import sun.rmi.runtime.Log;

/**
 * @author: zeting
 * @date: 2023/6/9
 */
public class MyTestQueue {
    private static final String TAG = MyTestQueue.class.getSimpleName();
    private static volatile CircularFifoQueue<String> tokenSentQueue = new CircularFifoQueue<>(5);

    /**
     * 添加一条记录
     */
    public void offer(String mail, String token) {
        tokenSentQueue.offer(String.format("Send [%s] to mail: [%s]", token, mail));
    }

    /**
     * 倒序输出。
     */
    public List<String> tokenSentList() {
        List<String> result = new ArrayList<>(tokenSentQueue);
        Collections.reverse(result);
        return result;
    }


    public static void main(String[] args) {

        System.out.println("start time : " + System.currentTimeMillis());
        for (int i = 0; i < 100; i++) {
            String randomCharData = createRandomCharData(5);
            boolean contains = tokenSentQueue.contains(randomCharData);

            boolean offer = tokenSentQueue.offer(randomCharData);
            if (offer) {
                System.out.println("all Queue : " + tokenSentQueue.toString() + " contains :" + contains);
            }
        }

        try {
            boolean offer = tokenSentQueue.offer(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 3; i++) {
            boolean offer = tokenSentQueue.offer("5555555");
            if (offer) {
                System.out.println("all Queue : " + tokenSentQueue.toString());
            }
        }


        System.out.println("end time : " + System.currentTimeMillis());
    }

    //根据指定长度生成字母和数字的随机数
    //0~9的ASCII为48~57
    //A~Z的ASCII为65~90
    //a~z的ASCII为97~122
    public static String createRandomCharData(int length) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();//随机用以下三个随机生成器
        Random randdata = new Random();
        int data = 0;
        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(3);
            //目的是随机选择生成数字，大小写字母
            switch (index) {
                case 0:
                    data = randdata.nextInt(10);//仅仅会生成0~9
                    sb.append(data);
                    break;
                case 1:
                    data = randdata.nextInt(26) + 65;//保证只会产生65~90之间的整数
                    sb.append((char) data);
                    break;
                case 2:
                    data = randdata.nextInt(26) + 97;//保证只会产生97~122之间的整数
                    sb.append((char) data);
                    break;
            }
        }
        String result = sb.toString();
        System.out.println("add       :  " + result);
        return result;
    }
}
