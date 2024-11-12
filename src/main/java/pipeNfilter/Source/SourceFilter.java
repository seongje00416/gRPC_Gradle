/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package pipeNfilter.Source;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import pipeNfilter.Framework.CommonFilterImpl;

public class SourceFilter extends CommonFilterImpl{
    // 입력받은 데이터 파일을 저장하는 변수
    private String sourceFile;
    
    // 생성자를 통해 인스턴스가 실행될 때 입력 데이터 파일을 지정함
    public SourceFilter(String inputFile){
        this.sourceFile = inputFile;
    }
    
    @Override
    public boolean specificComputationForFilter() throws IOException {
        int byte_read;
        // read()는 readLine()과 달리 한 글자씩 받을 뿐만 아니라 받은 값을 ASCII로 리턴한다.
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(new File(sourceFile)));
        // 파일에서 한 글자씩 읽은 값( ASCII 코드 )을 Pipe에 보낸다.
        while(true) {
            byte_read = br.read();
            if (byte_read == -1) return true;
            out.write(byte_read);
        }
    }
}
