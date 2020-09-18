<%@ taglib uri="http://liferay.com/tld/ratings" prefix="liferay-ratings" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.journal.model.JournalArticle" %><%@
page import="com.liferay.journal.service.JournalArticleLocalServiceUtil" %>

<%@ page import="java.util.List" %>

<liferay-theme:defineObjects />

<%
List<JournalArticle> journalArticles = JournalArticleLocalServiceUtil.getArticles(themeDisplay.getScopeGroupId());

JournalArticle firstArticle = journalArticles.get(0);
%>

<h1><%= firstArticle.getUrlTitle() %></h1>

<liferay-ratings:ratings
	className="<%= JournalArticle.class.getName() %>"
	classPK="<%= Long.valueOf(firstArticle.getArticleId()) %>"
	type="stars"
/>