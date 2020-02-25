package com.liferay.docs.rest.client;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.ActionURL;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
		
		ActionURL getToken = renderResponse.createActionURL();
		PortletPreferences prefs = renderRequest.getPreferences();
		String token = (String)renderRequest.getAttribute("token");
		long now = System.currentTimeMillis();
		
		if (token == null) {
			// Get token from preferences
			token = prefs.getValue("token", "none");
		}

		if (token.equals("none")) {
						
			renderRequest.setAttribute("authURL", authorize(getToken.toString()));
			
		} else {

			long timeStamp = Long.valueOf(prefs.getValue("tokenExpires",  "600000"));
			if ((now - timeStamp) > 600000) {
				// We have a previously stored token, but it has expired. Refresh it.
				String refreshToken = prefs.getValue("refreshToken", "none");
				
				if (refreshToken.equals("none")) {
					// should never happen; something weird is going on
					renderRequest.setAttribute("authURL", authorize(getToken.toString()));
				
				} else {
					renderRequest.setAttribute("refreshToken", refreshToken);
				}
				token = refreshToken(refreshToken);
				prefs.setValue("tokenExpires", Long.toString(now));
				prefs.setValue("token", token);
				renderRequest.setAttribute("token", token);
				// Here is where we'll make our call
				
			}
		}
		
		super.render(renderRequest, renderResponse);
	}

	private String getBlogPostings(String token) {
		String url = DocsRestClientKeys.REST_APP_URL 
			+ "/" + DocsRestClientKeys.SITEID 
			+ "/" + DocsRestClientKeys.END_POINT;
		Request request = new Request.Builder()
			.url(url)
			.build();
	}

	private String refreshToken (String refreshToken) {
		String token = "";
		RequestBody formBody = new FormBody.Builder()
			.add("client_id", _clientId)
			.add("client_secret", _clientSecret)
			.add("refresh_token", refreshToken)
			.add("grant_type", "refresh_token")
			.build();
		Request httpRequest = new Request.Builder()
			.url("http://localhost:8080/o/oauth2/token")
			.post(formBody)
			.build();

		try {
			Response httpResponse = _client.newCall(httpRequest).execute();
			if (!httpResponse.isSuccessful()) throw new IOException("Unexpected code " + httpResponse);
			String jsonBody = httpResponse.body().string();
			System.out.println(jsonBody);
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(jsonBody);
			token = jsonObject.getString("access_token");
			
		} catch (IOException ioe) {
			System.out.println("Something's wrong with request: " + ioe.getMessage());
		} catch (JSONException jse) {
			System.out.println("JSON could not be parsed: " + jse.getMessage());
		}
		return token;
	}
	
	public void getToken (ActionRequest request, ActionResponse response) {
		String code = ParamUtil.getString(request, "code");
		RequestBody formBody = new FormBody.Builder()
			.add("client_id", _clientId)
			.add("client_secret", _clientSecret)
			.add("grant_type", "authorization_code")
			.add("code", code)
			.add("redirect_uri", "http://localhost:8080/web/guest/blog/-/a1b2-web")
			.build();
		Request httpRequest = new Request.Builder()
			.url("http://localhost:8080/o/oauth2/token")
			.post(formBody)
			.build();

		try {
			Response httpResponse = _client.newCall(httpRequest).execute();
			if (!httpResponse.isSuccessful()) throw new IOException("Unexpected code " + httpResponse);
			String jsonBody = httpResponse.body().string();
			System.out.println(jsonBody);
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(jsonBody);
			String token = jsonObject.getString("access_token");
			String refreshToken = jsonObject.getString("refresh_token");
			PortletPreferences prefs = request.getPreferences();
			prefs.setValue("token", token);
			prefs.setValue("refreshToken", refreshToken);
			long now = System.currentTimeMillis();
			prefs.setValue("tokenExpires", Long.toString(now));
		} catch (IOException ioe) {
			System.out.println("Something's wrong with request: " + ioe.getMessage());
		} catch (JSONException jse) {
			System.out.println("JSON could not be parsed: " + jse.getMessage());
		} catch (ReadOnlyException ex) {
			Logger.getLogger(DocsRestClientPortlet.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	private String authorize(String callbackUrl) {

		final OAuth20Service service = new ServiceBuilder(
			_clientId
		).apiSecret(
			_clientSecret
		).defaultScope(
			"Liferay.Headless.Delivery.everything"
		).callback(callbackUrl)
		.build(
			LiferayApi.instance()
		);
		final String authorizationUrl = service.getAuthorizationUrl(
			"response_type=client_credentials");
		System.out.println("Authorization URL: " + authorizationUrl);

		return authorizationUrl;

	}

	// HP Machine ID

	//String clientId = "id-e0ce2b84-35a1-cff7-5a84-d11854fc5a";

	// Dell Machine ID

	private String _clientId = "id-e1bbc8d0-ac24-4560-9f41-3287876d1cd3";

	// HP Machine secret

	//String clientSecret = "secret-8c5c3548-11c6-2620-7d19-48f68bf7c13";

	//Dell Machine secret
	private String _clientSecret = "secret-b4e63d45-5ae1-1952-b526-6890c685320";

	private final OkHttpClient _client = new OkHttpClient();

}