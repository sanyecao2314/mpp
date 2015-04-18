package com.citsamex.app.action;

import com.citsamex.app.Server;

public class Page {
	public int count;
	public int size;
	public int current;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public Page(int recCount) {
		size = size <= 0 ? Server.PROPS_PAGESIZE : size;

		if (recCount % size == 0) {
			this.count = recCount / size;
		} else {
			this.count = recCount / size + 1;
		}

		if (current > count) {
			current = count;
		}
		if (current <= 0) {
			current = 1;
		}
	}
}
