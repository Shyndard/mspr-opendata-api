package fr.mspr.opendata.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomFilter extends GenericFilterBean {

	private static final List<String> WHITELIST = Arrays.asList("/swagger-resources", "/swagger-ui.html", "/v2/api-docs", "/webjars",
			"/swagger-ui.html", "/error" );

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = asHttp(request);
		HttpServletResponse httpResponse = asHttp(response);
		
		String pathWithinApplication = new UrlPathHelper().getPathWithinApplication(httpRequest);

		if(WHITELIST.stream().filter(path -> pathWithinApplication.contains(path)).count() > 0) {
			chain.doFilter(request, response);
			return;
		}
		
		if(httpRequest.getHeader("Authorization").contains("mangerDesChats")) {
			chain.doFilter(request, response);
		} else {
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			Map<String, String> reponse = new HashMap<>();
			reponse.put("error", "Invalid bearer token, fuck you !");
			httpResponse.getWriter().print(new ObjectMapper().writeValueAsString(reponse));
		}
	}

	private HttpServletRequest asHttp(ServletRequest request) {
		return (HttpServletRequest) request;
	}

	private HttpServletResponse asHttp(ServletResponse response) {
		return (HttpServletResponse) response;
	}
}