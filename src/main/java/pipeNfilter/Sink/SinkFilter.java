/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package pipeNfilter.Sink;

import java.io.FileWriter;
import java.io.IOException;

import pipeNfilter.Framework.CommonFilterImpl;

public class SinkFilter extends CommonFilterImpl{
    // 데이터를 저장할 출력 파일을 의미하는 변수
    private String sinkFile;

    // 셍성자를 통해 인스턴스를 생성할 때 출력 파일을 지정한다.
    public SinkFilter(String outputFile) {
        this.sinkFile = outputFile;
    }

    @Override
    public boolean specificComputationForFilter() throws IOException {
        int byte_read;
        // 파일에 데이터를 쓰기 위한 FileWriter 선언
        FileWriter fw = new FileWriter(this.sinkFile);
        while(true) {
            byte_read = in.read(); 
            // 더이상 넘어오는 정보가 없는 경우 파일 작성을 마무리하도록 지정
            if (byte_read == -1) {
            	 fw.close();
                 System.out.print( "::Filtering is finished; Output file is created." );  
                 return true;
            }
            // ASCII 코드로 넘어온 정보를 문자로 변환해 파일에 저장
            fw.write((char)byte_read);
        }   
    }
}
