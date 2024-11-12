/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package pipeNfilter.Middle;

import java.io.IOException;
import pipeNfilter.Framework.CommonFilterImpl;

public class MiddleFilter extends CommonFilterImpl{
    @Override
    public boolean specificComputationForFilter() throws IOException {
    	int checkBlank = 4; 
        // 각 정보 구분을 위한 역할
        // 공백이 1걔: 학번 / 공백이 2개: 성명 / 공백이 3개: 이름 / 공백이 4개: 전공
        // 각 공백으로부터 이전으로 몇 개로 위치 판단
        int numOfBlank = 0;
        int idx = 0;
        byte[] buffer = new byte[64];
        // 전공이 CS인지 확인
        boolean isCS = false;    
        int byte_read = 0;
        
        // 1사이클 = 1문장
        while(true) {          
            // 1사이클 = 1글자
        	// 들어온 정보가 개행이 아니거나 비어있는 정보가 아니라면 반복
            while(byte_read != '\n' && byte_read != -1) {
                // byte_read = 앞선 파이르를 통해 넘어온 ASCII 코드
            	byte_read = in.read();
                // 입력받은 데이터가 공백이면 numOfBlank 값 추가
                if(byte_read == ' ') numOfBlank++;
                // 입력 받은 데이터가 비어있는 값이 아니면 byte 배열에 입력받은 데이터를 저장
                // idx에 1을 더한 위치에 값을 저장
                if(byte_read != -1) buffer[idx++] = (byte)byte_read;
                // 띄어쓰기가 checkBlank 길이(4개)랑 같고
                if(numOfBlank == checkBlank && buffer[idx-3] == 'C' && buffer[idx-2] == 'S')
                    isCS = true;
            }      
            // 만약 학생이 CS 전공이 맞다면 Sink Filter로 데이터를 전송해 저장
            if(isCS == true) {
                for(int i = 0; i<idx; i++) 
                    out.write((char)buffer[i]);
                isCS = false;
            }
            // 더 이상 넘어오는 데이터가 없는 경우 작업 종료
            if (byte_read == -1) return true;
            idx = 0;
            numOfBlank = 0;
            byte_read = '\0';
        }
    }  
}
