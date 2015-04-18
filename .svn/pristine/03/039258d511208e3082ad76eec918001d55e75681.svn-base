package com.citsamex.service;

import com.citsamex.app.Server;

public class UpdateTravService {
	public void updateSyncFlag(String custno, int system, int flag, String message) {

		if (!Server.syncPool.containsKey(custno)) {
			SyncTravThread thread = new SyncTravThread();
			Server.syncPool.put(custno, thread);
			thread.start();
		}

		SyncTravThread thread = Server.syncPool.get(custno);

		if ((thread.getSystem() | system) != thread.getSystem()) {
			thread.lock.countDown();
		}

		thread.setMessage(message, system);
		thread.setSystem(thread.getSystem() | system);
		thread.setFlag(thread.getFlag() | flag);

	}
}
