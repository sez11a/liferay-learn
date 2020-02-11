package com.liferay.docs.rest.client;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;

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
public class DocsRestClientPortlet extends MVCPortlet {
		
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		
		super.render(renderRequest, renderResponse);
	}

	private void authorize () {
		String clientId = "id-e0ce2b84-35a1-cff7-5a84-d11854fc5a";
		String clientSecret = "secret-8c5c3548-11c6-2620-7d19-48f68bf7c13";
		final OAuth20Service service = new ServiceBuilder(clientId).
			apiSecret(clientSecret)
			.defaultScope("Liferay.Headless.Delivery.everything")
			.callback("http://localhost:8080/rest/-/rest/view.jsp")
			.build(LiferayApi.instance());
		final String authorizationUrl = service.getAuthorizationUrl();
		System.out.println("Authorization URL: " + authorizationUrl);
		
	
	}
}
