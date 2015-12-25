/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.joy.portalet;

import com.joy.commons.web.view.ModelAndViewFactory;
import com.liferay.portal.kernel.util.ReleaseInfo;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PortletViewController {

	@RequestMapping("VIEW")
	public ModelAndView onView(RenderRequest request,RenderResponse response) throws Exception{
        System.out.println("---------------------------------------------------------------------");
        
        return ModelAndViewFactory.newModelAndViewFor("/grading-portalet/view").build();
		//return "grading-portalet/view";
	}

}