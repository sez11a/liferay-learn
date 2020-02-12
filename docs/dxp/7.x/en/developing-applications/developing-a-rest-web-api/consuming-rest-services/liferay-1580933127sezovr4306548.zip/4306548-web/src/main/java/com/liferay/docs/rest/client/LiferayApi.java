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
package com.liferay.docs.rest.client;

import com.github.scribejava.core.builder.api.DefaultApi20;

/**
 *
 * @author Rich Sezov
 */
public class LiferayApi extends DefaultApi20 {

	protected LiferayApi() {

	}

	private static class InstanceHolder {
		private static final LiferayApi INSTANCE = new LiferayApi();
	}

	public static LiferayApi instance() {
		return InstanceHolder.INSTANCE;
	}

	@Override
	public String getAccessTokenEndpoint() {
		return "http://localhost:8080/o/oauth2/token";
	}

	@Override
	protected String getAuthorizationBaseUrl() {
		return "http://localhost:8080/o/oauth2/authorize";
	}
    
}
