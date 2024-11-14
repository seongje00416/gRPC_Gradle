package pipeNfilter.Middle.CheckFilter.Courses;

import pipeNfilter.Framework.MultiFilterImpl;
import java.io.IOException;

public class CheckRegisterPreCoursesFilter extends MultiFilterImpl {
    @Override
    public boolean specificComputationForFilter() throws IOException {
        // 현재 과목 이름을 담는 배열
        byte[] course = new byte[5];
        // 선행 이수 과목들을 담는 배열
        byte[] preCourses = new byte[32];

        // Course Flow에 대한 변수들
        int idx = 0;
        // 한 줄씩 정보를 담을 배열
        byte[] buffer = new byte[64];
        int preIdx = 0;
        int byte_read = 0;
        int blankNum = 0;

        // Student Flow에 대한 변수들
        int idx2 = 0;
        byte[] buffer2 = new byte[64];
        int byte_read2 = 0;
        int blankNum2 = 0;
        boolean isNeedPreCourses = false;
        boolean isExistedPreCourses = false;

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
                else if( blankNum >= 1 ) {
                    preCourses[preIdx] = (byte)byte_read;
                    preIdx++;
                }
            }
            // 해당 코스에 대해 학생 이수 여부 판단
            // 1사이클 = 1학생
            while( true ){
                while( byte_read2 != '\n' && byte_read2 != -1) {
                    byte_read2 = ins[1].read();
                    if( byte_read2 == ' ' || byte_read2 == 10 ) blankNum2++;
                    if( byte_read2 != -1 ) buffer2[idx2++] = (byte)byte_read2;
                    // 수강 신청된 과목 목록
                    if( blankNum2 > 4 && ( byte_read2 == ' ' || byte_read2 == 10 ) ) {
                        // 선행 이수 과목이 필요한 과목을 신청했는지 확인
                        if( byte_read2 != 10 ) {
                            if(
                                buffer2[idx2-6] == course[0] &&
                                buffer2[idx2-5] == course[1] &&
                                buffer2[idx2-4] == course[2] &&
                                buffer2[idx2-3] == course[3] &&
                                buffer2[idx2-2] == course[4]
                            ) {
                                isNeedPreCourses = true;
                            }
                            System.out.print( (char) buffer2[idx2-6] );
                            System.out.print( (char) buffer2[idx2-5] );
                            System.out.print( (char) buffer2[idx2-4] );
                            System.out.print( (char) buffer2[idx2-3] );
                            System.out.print( (char) buffer2[idx2-2] );
                            System.out.print( (char) buffer2[idx2-1] ); // 32
                        } else {
                            if(
                                buffer2[idx2-7] == course[0] &&
                                buffer2[idx2-6] == course[1] &&
                                buffer2[idx2-5] == course[2] &&
                                buffer2[idx2-4] == course[3] &&
                                buffer2[idx2-3] == course[4]
                            ) {
                                isNeedPreCourses = true;
                            }
                            System.out.print( (char) buffer2[idx2-7] );
                            System.out.print( (char) buffer2[idx2-6] );
                            System.out.print( (char) buffer2[idx2-5] );
                            System.out.print( (char) buffer2[idx2-4] );
                            System.out.print( (char) buffer2[idx2-3] );
                            System.out.print( (char) buffer2[idx2-2] ); // 13
                            System.out.print( (char) buffer2[idx2-1] ); // 10
                        }
                    }
                }
                System.out.println( isNeedPreCourses );
                if( isNeedPreCourses ) isNeedPreCourses = false;
                idx2 = 0;
                if ( byte_read2 == -1 ) break;
                byte_read2 = '\0';
                blankNum2 = 0;
            }

            // DEBUGGING
            //for( int i = 0; i<idx; i++ ) System.out.print( (char)buffer[i] );   // 선행 이수 과목이 있는 전체 과목 출력
            //for( byte b : course ) System.out.print( (char)b );               // 현재 과목 번호 출력
//            System.out.println();
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
