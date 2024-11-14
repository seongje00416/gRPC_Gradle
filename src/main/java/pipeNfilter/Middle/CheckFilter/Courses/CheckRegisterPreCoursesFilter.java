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

        byte[] courseBuffer = new byte[128];
        int courseBufferIndex = 0;

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
            for( int i = 0; i<idx; i++ ) {
                //System.out.print( buffer[i] );   // 선행 이수 과목이 있는 전체 과목 출력
                courseBuffer[courseBufferIndex] = buffer[i];
                courseBufferIndex++;
            }
            courseBuffer[courseBufferIndex] = -1;
            if (byte_read == -1) break;
            idx = 0;
            preIdx = 0;
            blankNum = 0;
            byte_read = '\0';
        }
        //for( int i = 0; i < courseBufferIndex; i++ ) System.out.print( (char)courseBuffer[i] );

        // Student Flow에 대한 변수들
        int checkBlank2 = 4;
        int blankNum2 = 0;
        int idx2 = 0;
        byte[] buffer2 = new byte[64];
        int byte_read2 = 0;
        boolean isNeedPreCourse = false;
        boolean[] isRegisterPreCourses = new boolean[2];
        int booleanIndex = 0;
        while( true ){
            while( byte_read2 != '\n' && byte_read2 != -1 ){
                byte_read2 = ins[1].read();
                if(byte_read2 == ' ') blankNum2++;
                if(byte_read2 != -1) buffer2[idx2++] = (byte)byte_read2;
                // 대상 강의 수강 여부 확인
                boolean isPreCourse = false;
                // 강좌 매칭 로직
                // 17651을 듣는 학생들을 가져오는 로직
                if( blankNum2 > 4 ){
                    int pos = 0;
                    byte current = 0;       // courseBuffer에서 가져오는 현재 바이트값
                    while( current != -1 ){
                        while( current != 10 ){
                            //System.out.println( current );
                            int courseBlank = 0;
                            current = courseBuffer[pos];
                            if( current == -1 ) break;
                            if( current == ' ' ) courseBlank++;
                            if( current == 32 ){
                                // CourseBuffer에서 공백을 발견한 경우
                                if( courseBlank == 1 ){
                                    // Student 목록에서 공백을 발견한 경우
                                    if( byte_read2 == 32 ){
                                        if(
                                            courseBuffer[pos-5] == buffer2[idx2-6] &&
                                            courseBuffer[pos-4] == buffer2[idx2-5] &&
                                            courseBuffer[pos-3] == buffer2[idx2-4] &&
                                            courseBuffer[pos-2] == buffer2[idx2-3] &&
                                            courseBuffer[pos-1] == buffer2[idx2-2]
                                        ) isNeedPreCourse = true;
                                    }
                                    // Student 목록에서 개행을 발견한 경우
                                    else if( byte_read2 == 13 ){
                                        if(
                                            courseBuffer[pos-5] == buffer2[idx2-8] &&
                                            courseBuffer[pos-4] == buffer2[idx2-7] &&
                                            courseBuffer[pos-3] == buffer2[idx2-6] &&
                                            courseBuffer[pos-2] == buffer2[idx2-5] &&
                                            courseBuffer[pos-1] == buffer2[idx2-4]
                                        ) isNeedPreCourse = true;
                                    }
                                } else if( courseBlank > 1 ){
                                    if( byte_read2 == 32 ){
                                        if(
                                            courseBuffer[pos-5] == buffer2[idx2-6] &&
                                            courseBuffer[pos-4] == buffer2[idx2-5] &&
                                            courseBuffer[pos-3] == buffer2[idx2-4] &&
                                            courseBuffer[pos-2] == buffer2[idx2-3] &&
                                            courseBuffer[pos-1] == buffer2[idx2-2]
                                        ) {
                                            isRegisterPreCourses[booleanIndex] = true;
                                            booleanIndex++;
                                        }
                                    }
                                    // Student 목록에서 개행을 발견한 경우
                                    else if( byte_read2 == 13 ){
                                        if(
                                            courseBuffer[pos-5] == buffer2[idx2-8] &&
                                            courseBuffer[pos-4] == buffer2[idx2-7] &&
                                            courseBuffer[pos-3] == buffer2[idx2-6] &&
                                            courseBuffer[pos-2] == buffer2[idx2-5] &&
                                            courseBuffer[pos-1] == buffer2[idx2-4]
                                        ) {
                                            isRegisterPreCourses[booleanIndex] = true;
                                            booleanIndex++;
                                        }
                                    }
                                }
                            }
                            else if( current == 13 ){
                                if( byte_read2 == 32 ){
                                    if(
                                        courseBuffer[pos-7] == buffer2[idx2-6] &&
                                        courseBuffer[pos-6] == buffer2[idx2-5] &&
                                        courseBuffer[pos-5] == buffer2[idx2-4] &&
                                        courseBuffer[pos-4] == buffer2[idx2-3] &&
                                        courseBuffer[pos-3] == buffer2[idx2-2]
                                    ) {
                                        isRegisterPreCourses[booleanIndex] = true;
                                        booleanIndex++;
                                    }
                                }
                                // Student 목록에서 개행을 발견한 경우
                                else if( byte_read2 == 13 ){
                                    if(
                                        courseBuffer[pos-7] == buffer2[idx2-8] &&
                                        courseBuffer[pos-6] == buffer2[idx2-7] &&
                                        courseBuffer[pos-5] == buffer2[idx2-6] &&
                                        courseBuffer[pos-4] == buffer2[idx2-5] &&
                                        courseBuffer[pos-3] == buffer2[idx2-4]
                                    ) {
                                        isRegisterPreCourses[booleanIndex] = true;
                                        booleanIndex++;
                                    }
                                }
                            }
                            pos++;
                        }
                        if( current == 10 ) current = courseBuffer[pos];;
                    }
                }
            }
            // 학생 한 줄에 대한 로직
            // byte_read == 10: 강의 1개에 대한 작업 종료 ( 단, 10이 나오기 -1 ~ -2 인덱스에는 13 값이 들어있음
            // blankNum2 == 1: 대상 강의
            // blankNum2 > 1: 선행 이수 강의
            if( isNeedPreCourse ){
                for( int i = 0; i < idx2; i++ ){
                    System.out.print( (char)buffer2[i] );
                }
            }
            // 대상 강의 수강 여부 확인

            // ---------------------
            if (byte_read2 == -1) return true;
            idx2 = 0;
            blankNum2 = 0;
            byte_read2 = '\0';
            isNeedPreCourse = false;
            booleanIndex = 0;
        }
    }
}
