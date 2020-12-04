package com.acme.e8m8.info.list.renderer;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.info.item.renderer.InfoItemRendererTracker;
import com.liferay.info.list.renderer.InfoListRenderer;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, service = InfoListRenderer.class)
public class E8M8InfoListRenderer implements InfoListRenderer<BlogsEntry> {

	@Override
	public List<InfoItemRenderer<?>> getAvailableInfoItemRenderers() {
		return infoItemRendererTracker.getInfoItemRenderers(
			BlogsEntry.class.getName());
	}

	@Override
	public String getKey() {
		return "Example";
	}

	@Override
	public void render(
		List<BlogsEntry> entries, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher("/blogs_entry_list.jsp");

		httpServletRequest.setAttribute("entries", entries);

		try {
			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.acme.e8m8.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Reference
	protected InfoItemRendererTracker infoItemRendererTracker;

	private ServletContext _servletContext;

}