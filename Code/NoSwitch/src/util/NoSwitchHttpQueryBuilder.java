package util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class NoSwitchHttpQueryBuilder {
	static {
		parseConfig();
	}

	public enum serverType {
		MiddleServer, RootServer;
	}

	public enum queryFunction {
		Search, ForceFetch, UpdateOne, UpdateAll
	}

	private String midServerUrl = "localhost:3000";
	private String rootServerUrl = "localhost:3000/remoteserver";

	private String searchTerm = "";
	private String language = "";
	private int page = 0;
	private serverType server = serverType.RootServer;
	private queryFunction function = queryFunction.Search;

	public static void main(String[] args) throws UnsupportedOperationException, Exception {
		// TODO Auto-generated method stub
		NoSwitchHttpQueryBuilder builder = new NoSwitchHttpQueryBuilder();
		builder.setLanguage("Java");
		builder.setPage(5);
		builder.setSearchTerm("(JSON AND i++)");
		builder.setFunction(queryFunction.Search);
		builder.setServer(serverType.MiddleServer);
		NoSwitchHttpQuery query = builder.build();
		System.out.println(query.sendRequest());;
	}

	private static void parseConfig() {
		System.out.println("parse");
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setPage(int page) {
		if (page > 0) {
			this.page = page;
		} else {
			this.page = 0;
		}
	}

	public void setFunction(queryFunction function) {
		this.function = function;
	}

	public void setServer(serverType server) {
		this.server = server;
	}

	public NoSwitchHttpQuery build() throws UnsupportedEncodingException {
		String url = "";
		if (server.equals(serverType.RootServer)) {
			url = url + rootServerUrl;
			url = url + getEncodedParamsString(searchTerm, language, page);
			return new NoSwitchHttpQuery(url);
		}
		url =url +midServerUrl;
		String funcStr = "";
		String paramsStr = getEncodedParamsString(searchTerm, language, page);
		switch (function) {
		case Search:
			funcStr = "/search";
			break;
		case ForceFetch:
			funcStr = "/search/force_fetch";
			break;
		case UpdateAll:
			funcStr = "/update/all";
			paramsStr = "";
			break;
		case UpdateOne:
			funcStr = "/update/one";
			break;

		default:
			break;
		}
		url = url+funcStr+paramsStr;
		if(!url.startsWith("http")){
			url = "http://"+url;
		}
		NoSwitchHttpQuery query = new NoSwitchHttpQuery(url);
		return query;
	}

	private String getEncodedParamsString(String term, String lang, int page) throws UnsupportedEncodingException {
		String encodedParams = "?q=" + URLEncoder.encode(term, "utf-8");
		if (lang != null && !"".equals(lang)) {
			encodedParams = encodedParams + URLEncoder.encode(" lang:" + language, "utf-8");
		}
		encodedParams = encodedParams + "&p=" + page;

		return encodedParams;
	}
}
