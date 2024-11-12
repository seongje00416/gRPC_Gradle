/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package pipeNfilter.Framework;

import java.io.EOFException;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public abstract class CommonFilterImpl implements CommonFilter {
	protected PipedInputStream in = new PipedInputStream();
	protected PipedOutputStream out = new PipedOutputStream();

	// 결과 데이터를 받게 될 다음 파이프랑 연결하는 함수
	public void connectOutputTo(CommonFilter nextFilter) throws IOException {
		out.connect(nextFilter.getPipedInputStream());
	}
	
	// 데이터를 받아올 이전 파이프랑 연결하는 함수
	public void connectInputTo(CommonFilter previousFilter) throws IOException {
		in.connect(previousFilter.getPipedOutputStream());
	}

	// 파이프 스트림 Getter/Setter
	public PipedInputStream getPipedInputStream() {
		return in;
	}
	public PipedOutputStream getPipedOutputStream() {
		return out;
	}
	
	// 각 파이프 고유의 로직을 담는 함수 -> 상속 받는 함수에서 구현
	// Source - Middle - Sink 파이프마다 각각 다른 기능을 담게 됨
	abstract public boolean specificComputationForFilter() throws IOException;
	
	// 파이프 실행 함수
	public void run() {
		try {
			specificComputationForFilter();
		} catch (IOException e) {
			if (e instanceof EOFException) return;
			else System.out.println(e);
		} finally {
			closePorts();
		}
	}
	
	// 파이프를 닫는 함수
	private void closePorts() {
		try {
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
