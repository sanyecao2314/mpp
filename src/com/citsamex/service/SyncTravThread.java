package com.citsamex.service;

import java.util.concurrent.CountDownLatch;

public class SyncTravThread extends Thread {
	public final CountDownLatch lock = new CountDownLatch(3);
	public final static int STATUS_CANCEL = -1;
	public final static int STATUS_RUN = 1;

	private String custno;
	private int system;
	private int status;
	private int flag;

	private String message;

	private String bsmessage;
	private String psmessage;
	private String axomessage;

	public SyncTravThread() {

	}

	public SyncTravThread(String custno) {
		this.custno = custno;
	}

	public String getCustno() {
		return custno;
	}

	public void setCustno(String custno) {
		this.custno = custno;
	}

	public String getBsmessage() {
		return bsmessage;
	}

	public void setBsmessage(String bsmessage) {
		this.bsmessage = bsmessage;
	}

	public String getPsmessage() {
		return psmessage;
	}

	public void setPsmessage(String psmessage) {
		this.psmessage = psmessage;
	}

	public String getAxomessage() {
		return axomessage;
	}

	public void setAxomessage(String axomessage) {
		this.axomessage = axomessage;
	}

	public int getSystem() {
		return system;
	}

	public void setSystem(int system) {
		this.system = system;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message, int system) {
		switch (system) {
			case 1:
				psmessage = message;
				break;
			case 2:
				axomessage = message;
				break;
			case 4:
				bsmessage = message;
				break;
		}
	}

	public void run() {
		try {
			lock.await();
			if (status == STATUS_CANCEL) {
				System.out.println("run cancel.");
				return;
			}

			System.out.println("run update.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void cancel() {
		status = STATUS_CANCEL;
		for (int i = 0; i < 3; i++) {
			lock.countDown();
		}
	}

}
