package com.citsamex.app.validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.citsamex.app.util.CommonUtil;
import com.citsamex.app.validate.db.ValidDao;
import com.citsamex.app.validate.db.ValidObjectPo;
import com.citsamex.app.validate.db.ValidatorPo;

public class ValidatorManager {

	private ValidDao dao;
	private Map<String, IValidator> validators = new HashMap<String, IValidator>();
	private Map<String, Map<String, String>> validObject = new HashMap<String, Map<String, String>>();
	private boolean initflag = false;

	public boolean isInitflag() {
		return initflag;
	}

	public void setInitflag(boolean initflag) {
		this.initflag = initflag;
	}

	public ValidDao getDao() {
		return dao;
	}

	public void setDao(ValidDao dao) {
		this.dao = dao;
	}

	private void init() {
		List<ValidatorPo> list = dao.listValidators();
		for (int i = 0; i < list.size(); i++) {
			String name = list.get(i).getName();
			String classname = list.get(i).getClassname();
			IValidator v = (IValidator) CommonUtil.newInstance(classname);
			validators.put(name, v);
		}

		List<ValidObjectPo> list2 = dao.listValidObject();
		for (int i = 0; i < list2.size(); i++) {
			String name = list2.get(i).getName();
			String validatorName = list2.get(i).getValidator();
			String parameters = list2.get(i).getParameters();
			String message = list2.get(i).getMessage();

			if (!validObject.containsKey(name)) {
				validObject.put(name, new HashMap<String, String>());
			}

			if (!validObject.containsKey(name + "_m")) {
				validObject.put(name + "_m", new HashMap<String, String>());
			}

			Map<String, String> map = validObject.get(name);
			Map<String, String> map_m = validObject.get(name + "_m");
			map.put(validatorName, parameters);
			map_m.put(validatorName, message);
		}
		initflag = true;
	}

	public IValidator getValidator(String objName, String validatorName) {
		if (!initflag) {
			init();
		}

		String parameters = validObject.get(objName).get(validatorName);
		IValidator validator = validators.get(validatorName);
		validator.setParameter(parameters);
		return validator;
	}

	public List<IValidator> getValidators(String objName) {
		if (!initflag) {
			init();
		}

		List<IValidator> list = new ArrayList<IValidator>();
		Map<String, String> map = validObject.get(objName);
		Map<String, String> map_m = validObject.get(objName + "_m");
		if (map != null) {
			Iterator<String> it = map.keySet().iterator();
			while (it.hasNext()) {
				String validatorName = it.next();
				String parameters = map.get(validatorName);
				String message = map_m.get(validatorName);
				IValidator validator = validators.get(validatorName);
				validator.setParameter(parameters);
				validator.setMessage(message);
				list.add(validator);
			}
		}

		return list;
	}

}
