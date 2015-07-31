/**
 * <p><b>© 2015-2015 </b></p>
 * 
 **/

package com.joy.commons.web.view;

import java.util.HashMap;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆2015年7月31日
 * @version 1.0
 **/
public class ModelAndViewFactory {
	public static ModelAndViewFactory newModelAndViewFor() {
		return ModelAndViewFactory.newModelAndViewFor("/nothing", Boolean.TRUE);
	}

	public static ModelAndViewFactory newModelAndViewFor(String viewName) {
		return ModelAndViewFactory.newModelAndViewFor(viewName, Boolean.TRUE);
	}

	public static ModelAndViewFactory newModelAndViewFor(String viewName, Boolean isSuccessed) {
		if (isSuccessed.equals(Boolean.TRUE))
			return new ModelAndViewFactory(viewName).with(Responser.ModelName, new Responser.Builder().success().create());
		else
			return new ModelAndViewFactory(viewName).with(Responser.ModelName, new Responser.Builder().failure().create());
	}

	private HashMap<String, Object> model;

	private String viewName;

	private ModelAndViewFactory(String viewName) {
		this.viewName = viewName;
		this.model = new HashMap<>();
	}

	public ModelAndViewFactory with(String modelName, Object model) {
		this.model.put(modelName, model);
		return this;
	}

	public ModelAndView build() {
		return new ModelAndView(this.viewName, this.model);
	}

}
