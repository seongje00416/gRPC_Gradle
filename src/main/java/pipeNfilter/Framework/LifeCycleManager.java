/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package pipeNfilter.Framework;

import pipeNfilter.Middle.MiddleFilter;
import pipeNfilter.Sink.SinkFilter;
import pipeNfilter.Source.SourceFilter;

public class LifeCycleManager {
    public static void main(String[] args) {
        try {
            // Source Filter : 최초 입력 데이터 파일을 가져오는 필터
            CommonFilter filter1 = new SourceFilter("src/main/java/resources/Students.txt");
            // Sink Filter : 최종 결과 데이터 파일을 내보내는 필터
            CommonFilter filter2 = new SinkFilter("src/main/java/resources/Output.txt");
            // Middle Filter : 데이터 파일에 수행할 구체적인 로직을 수행하는 필터
            CommonFilter filter3 = new MiddleFilter();

            // Source Filter - Middle Filter 연결
            filter1.connectOutputTo(filter3);
            // Middle Filter - Sink Filter 연결
            filter3.connectOutputTo(filter2);
            
            // 필터마다 각각의 쓰레드를 생성
            Thread thread1 = new Thread(filter1);
            Thread thread2 = new Thread(filter2);
            Thread thread3 = new Thread(filter3);
            
            thread1.start();
            thread2.start();
            thread3.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
