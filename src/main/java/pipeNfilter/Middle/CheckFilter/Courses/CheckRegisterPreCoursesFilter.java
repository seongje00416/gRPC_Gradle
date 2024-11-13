package pipeNfilter.Middle.CheckFilter.Courses;

import pipeNfilter.Framework.MultiFilterImpl;
import java.io.IOException;

public class CheckRegisterPreCoursesFilter extends MultiFilterImpl {
    @Override
    public boolean specificComputationForFilter() throws IOException {
        int idx = 0;
        // 한 줄씩 정보를 담을 배열
        byte[] buffer = new byte[64];
        byte[] course = new byte[5];
        byte[] preCourses = new byte[32];
        int preIdx = 0;
        int byte_read = 0;
        int blankNum = 0;
        // 선행 이수 과목이 존재하는 과목 Flow
        // 1사이클 = 1 과목
        while(true) {
            while(byte_read != '\n' && byte_read != -1) {
                byte_read = ins[0].read();
                if( byte_read == ' ' || byte_read == 10 ) blankNum++;
                if(byte_read != -1) buffer[idx++] = (byte)byte_read;
                // 대상 과목 번호 추출
                if( blankNum == 1 && byte_read == ' ' ) {
                    // course 배열에 현재 과목 번호 저장
                    for( int i = 0; i<5; i++ ) course[i] = buffer[i];
                }
                else if( blankNum > 1 && ( byte_read == ' ' || byte_read == 10 ) ) {
                    // buffer[idx-6] ~ buffer[idx-2]: 선행 이수 과목 번호
                    for( int i = 6; i > 0; i-- ) {
                        preCourses[preIdx] = buffer[idx-i];
                        preIdx++;
                    }

                }
            }
            // DEBUGGING
            //for( int i = 0; i<idx; i++ ) System.out.print( (char)buffer[i] );   // 선행 이수 과목이 있는 전체 과목 출력
            //for( byte b : course ) System.out.print( (char)b );               // 현재 과목 번호 출력
            System.out.println();
//            for( int i = 0; i<preIdx; i++ ){
//                System.out.print( (char)preCourses[i] );                      // 해당 과목에 대한 선행 이수 과목 출력
//            }

            if (byte_read == -1) return true;
            idx = 0;
            preIdx = 0;
            blankNum = 0;
            byte_read = '\0';
        }
    }
}
