package com.citsamex.service.action;

import java.util.List;

import com.citsamex.app.action.BaseAction;
import com.citsamex.service.ICustomerService;
import com.citsamex.service.ServiceMessage;

public class ClauseAction extends BaseAction {

	private static final long serialVersionUID = -5624898997346908442L;
	private long id;

	private Clause clause;
	private List<Clause> list;
	private ICustomerService service;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Clause getClause() {
		return clause;
	}

	public void setClause(Clause clause) {
		this.clause = clause;
	}

	public List<Clause> getList() {
		return list;
	}

	public void setList(List<Clause> list) {
		this.list = list;
	}

	public ICustomerService getService() {
		return service;
	}

	public void setService(ICustomerService service) {
		this.service = service;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String search() {
		// setPage(page);

		ServiceMessage sm = service.listClasue(map);
		if (!procServMessage(sm)) {
			return INPUT;
		}
		list = (List) sm.getData();

		return SUCCESS;
	}

	public String display() {

		ServiceMessage sm = service.findClause(id);
		if (!procServMessage(sm)) {
			return INPUT;
		}

		clause = (Clause) sm.getData();

		return SUCCESS;
	}

	public String update() {

		ServiceMessage sm = service.updateClause(clause);
		if (!procServMessage(sm)) {
			return INPUT;
		}

		clause = (Clause) sm.getData();

		return SUCCESS;
	}

	public String delete() {
		return SUCCESS;
	}

	public String create() {
		// check if name is empty.
		if (clause == null)
			return INPUT;

		// return input page if failed
		ServiceMessage sm = service.createClause(clause);
		if (!procServMessage(sm)) {
			return INPUT;
		}

		clause = (Clause) sm.getData();
		return SUCCESS;
	}

}
