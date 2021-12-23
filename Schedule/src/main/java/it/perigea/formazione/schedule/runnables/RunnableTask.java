package it.perigea.formazione.schedule.runnables;


import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import it.perigea.formazione.schedule.entity.URLEntity;

public class RunnableTask implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RunnableTask.class);

	private URLEntity urlMethod;
	private String parameter;

	public URLEntity getUrlMethod() {
		return urlMethod;
	}

	public void setUrlMethod(URLEntity urlMethod) {
		this.urlMethod = urlMethod;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String param) {
		this.parameter = param;
	}

	public RunnableTask(URLEntity urlMethod, String parameter) {
		super();
		this.urlMethod = urlMethod;
		this.parameter = parameter;
	}

	RestTemplate restTemplate = new RestTemplate();

	@Override
	public void run() {
		logger.debug("Extractor thread started. current thread name {}", Thread.currentThread().getName());
		RestTemplate restTemplate = new RestTemplate();
		URI url = URI.create(urlMethod.getURLString());
		String province = this.parameter;
		try {
			// chiamata rest
			if (urlMethod.getReturnType().equals("List")) {
				runList(url);
			} else {
				runString(url, province);
			}

			logger.info("I dati sono stati estratti");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void runList(URI url) {
		ResponseEntity<List<?>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<?>>() {
				});
		List<?> list = responseEntity.getBody();
		for (int i = 0; i < list.size(); i++) {
			logger.info(list.get(i).toString());
		}

	}

	public void runString(URI url, String province) {
		if (getParameter().equals("null")) {
			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
					new ParameterizedTypeReference<String>() {
					});
			String body = responseEntity.getBody();
			logger.info(body);
		} else {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			Map<String, String> params = new HashMap<String, String>();
			params.put("province", province);
			ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
			};
			ResponseEntity<String> responseEntity = restTemplate.exchange(this.urlMethod.getURLString(), HttpMethod.GET,
					entity, typeRef, params);
			String body = responseEntity.getBody();
			logger.info(body);
//			Map<String, String> params = new HashMap<String, String>();
//			params.put("province", getParameter());
//			ResponseEntity<String> responseEntity = restTemplate.getForEntity(urlMethod.getURLString(),
//					String.class, params);
//			String body = responseEntity.getBody();
//			logger.info(body);

		}
	}
}