package com.liferay.learn.context.contributor;

import com.liferay.portal.kernel.template.TemplateContextContributor;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * @author Mike Williams
 */
@Component(
	immediate = true,
	property = "type=" + TemplateContextContributor.TYPE_THEME,
	service = TemplateContextContributor.class
)
public class MarketingThemeTemplateContextContributor
	implements TemplateContextContributor {

	@Override
	public void prepare(
		Map<String, Object> contextObjects,
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {

                                      contextObjects.put("colorSchemeId", themeDisplay.getColorSchemeId());


                                  }

	}

}