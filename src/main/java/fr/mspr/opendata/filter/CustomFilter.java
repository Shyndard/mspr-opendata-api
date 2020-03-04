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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomFilter extends GenericFilterBean {

	@Value( "${auth.token}" )
	private String token;

	private static final List<String> BLACKLIST = Arrays.asList("/upload");
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String pathWithinApplication = new UrlPathHelper().getPathWithinApplication(httpRequest);
		
		if(BLACKLIST.stream().filter(path -> pathWithinApplication.startsWith(path)).count() == 0) {
			chain.doFilter(request, response);
		} else {
			String authorization = httpRequest.getHeader("Authorization");
			System.out.println(authorization);
			if(authorization != null && authorization.contains(token)) {
				chain.doFilter(request, response);
			} else {
				httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				Map<String, String> reponse = new HashMap<>();
				reponse.put("error", "Invalid token. Bad token, you're not nice, i hate you !");
				httpResponse.getWriter().print(new ObjectMapper().writeValueAsString(reponse));
			}
		}
	}
}