package org.mei.core.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @date 2015. 10. 1.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 */
public class PathMatchingResourceResolver implements ResourcePatternResolver {
	private static Logger logger = LoggerFactory.getLogger(PathMatchingResourceResolver.class);

	private final PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver;

	public PathMatchingResourceResolver() {
		this.pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
	}

	@Override
	public ClassLoader getClassLoader() {
		return pathMatchingResourcePatternResolver.getClassLoader();
	}

	@Override
	public Resource getResource(String location) {
		return pathMatchingResourcePatternResolver.getResource(location);
	}

	@Override
	public Resource[] getResources(String locationPattern) throws IOException {
		return getResources(new String[]{locationPattern});
	}

	public Resource[] getResources(String[] locationPattern) throws IOException {
		List<Resource> listResource = new ArrayList<>();

		for (String path : locationPattern) {
			Resource[] resources = pathMatchingResourcePatternResolver.getResources(path);
			for (Resource resource : resources) {
				listResource.add(resource);
				if (logger.isDebugEnabled()) {
					logger.debug("Resource Loaded: " + resource.getURL().toString());
				}
			}
		}

		return listResource.toArray(new Resource[listResource.size()]);
	}
}
