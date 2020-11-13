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

	@Override
	public String getKey() {
		return "Example";
	}

	@Override
	public void render(
		BlogsEntry blogsEntry, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher("/blogs_entry_item.jsp");

		httpServletRequest.setAttribute("blogsEntry", blogsEntry);

		try {
			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.acme.v7a2.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private ServletContext _servletContext;

}