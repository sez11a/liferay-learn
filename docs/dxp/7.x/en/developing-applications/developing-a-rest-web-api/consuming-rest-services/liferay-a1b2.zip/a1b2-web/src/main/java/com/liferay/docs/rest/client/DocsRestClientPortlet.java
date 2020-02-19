package com.liferay.docs.rest.client;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rich Sezov
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
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
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class DocsRestClientPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {
		
		PortletPreferences prefs = renderRequest.getPreferences();
		String token = (String)renderRequest.getAttribute("token");
		
		if (token == null) {
			// Get token from preferences
			token = prefs.getValue("token", "none");
		}

		if (token.equals("none")) {
						
			renderRequest.setAttribute("authURL", authorize());
			
		} else {

			long timeStamp = Long.valueOf(prefs.getValue("tokenExpires",  "600000"));
			long now = System.currentTimeMillis();
			if ((now - timeStamp) > 600000) {
				// We have a previously stored token, but it has expired. Refresh it.
				String refreshToken = prefs.getValue("refreshToken", "none");
				
				if (refreshToken.equals("none")) {
					// should never happen; something weird is going on
					renderRequest.setAttribute("authURL", authorize());
				
				} else {
					renderRequest.setAttribute("refreshToken", refreshToken);
				}
			}
		}
		
		super.render(renderRequest, renderResponse);
	}

	private String authorize() {

		// HP Machine ID

		//String clientId = "id-e0ce2b84-35a1-cff7-5a84-d11854fc5a";

		// Dell Machine ID

		String clientId = "id-e1bbc8d0-ac24-4560-9f41-3287876d1cd3";

		// HP Machine secret

		//String clientSecret = "secret-8c5c3548-11c6-2620-7d19-48f68bf7c13";

		//Dell Machine secret
		String clientSecret = "secret-b4e63d45-5ae1-1952-b526-6890c685320";

		final OAuth20Service service = new ServiceBuilder(
			clientId
		).apiSecret(
			clientSecret
		).defaultScope(
			"Liferay.Headless.Delivery.everything"
		).callback(
			"http://localhost:8080/rest/-/rest/view.jsp"
		).build(
			LiferayApi.instance()
		);
		final String authorizationUrl = service.getAuthorizationUrl(
			"response_type=client_credentials");
		System.out.println("Authorization URL: " + authorizationUrl);

		return authorizationUrl;

	}

}