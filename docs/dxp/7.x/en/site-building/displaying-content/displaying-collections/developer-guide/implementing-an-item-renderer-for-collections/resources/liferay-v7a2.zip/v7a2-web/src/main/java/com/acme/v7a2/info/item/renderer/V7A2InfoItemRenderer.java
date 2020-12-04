package com.acme.v7a2.info.item.renderer;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.info.item.renderer.InfoItemRenderer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = InfoItemRenderer.class)
public class V7A2InfoItemRenderer implements InfoItemRenderer<BlogsEntry> {

	// Override the necessary methods and implement your item renderer logic here.

	@Reference(
		target = "(osgi.web.symbolicname=com.acme.v7a2.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private ServletContext _servletContext;

}