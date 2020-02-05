package com.liferay.docs.rest.client;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

/**
 * @author Rich Sezov
 */

@Component(immediate = true, property= { "com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.scopable=true",
		"javax.portlet.name=" + DocsRestClientKeys.RESTCLIENT,
		"javax.portlet.display-name=Rest Client",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/", 
		"javax.portlet.init-param.view-template=/rest/view.jsp",
		"javax.portlet.resource-bundle=content.Language", 
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html" }, 
	service = Portlet.class)
public class DocsRestClient extends MVCPortlet {
		
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		
		super.render(renderRequest, renderResponse);
	}
}
 
