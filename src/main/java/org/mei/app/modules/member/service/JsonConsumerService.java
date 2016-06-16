package org.mei.app.modules.member.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mei.core.security.service.Consumer;
import org.mei.core.security.service.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * member.json 데이터를 조회하여 사용자를 리턴함.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 7.
 */
public class JsonConsumerService implements ConsumerService {
	private static Logger logger = LoggerFactory.getLogger(JsonConsumerService.class);

	@Override
	public Consumer getConsumerDetails(String username) throws Exception{

		PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
		Resource resource = pathMatchingResourcePatternResolver.getResource("classpath:org/mei/app/modules/member/member.json");

		ObjectMapper json = new ObjectMapper();
		List<Consumer> consumers = json.readValue(resource.getURL(), new TypeReference<List<Consumer>>(){});

		for(Consumer consumer : consumers) {
			String userId = consumer.getUserId();

			if (userId == null) continue;

			if (userId.equals(username)) return consumer;
		}

		return null;
	}

	@Override
	public Consumer saveMemberLoginSuccess(Authentication authentication, UserDetails userDetails) {
		return null;
	}
}
