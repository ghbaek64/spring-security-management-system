package org.mei.core.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.*;

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
		List<Resource> listResource = new ArrayList<Resource>();

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

	public String[] getClassPath(String locationPattern) throws IOException {
		return getClassPath(new String[]{locationPattern});
	}

	public String[] getClassPath(String[] locationPattern) throws IOException {
		List<String> strings = new ArrayList();

		for (String path : locationPattern) {
			Resource[] resources = pathMatchingResourcePatternResolver.getResources(path);
			for (Resource resource : resources) {

				String uri = resource.getURI().toString();
				String baseName = null;

				if (resource instanceof FileSystemResource) {
					// baseName = "classpath:" + substringBetween(uri, "/classes/", ".properties");
					baseName = substringBefore(uri, ".properties");
				} else if (resource instanceof ClassPathResource) {
					baseName = substringBefore(uri, ".properties");
				} else if (resource instanceof UrlResource) {
					baseName = "classpath:" + substringBetween(uri, ".jar!/", ".properties");
				}

				if (baseName != null) {
					baseName = processBasename(baseName);
					strings.add(baseName);
				}

				if (logger.isDebugEnabled()) {
					logger.debug("Resource classpath Loaded: " + uri + " => " + baseName);
				}
			}
		}

		return strings.toArray(new String[strings.size()]);
	}

	private String processBasename(String baseName) {
		String prefix = substringBeforeLast(baseName, "/");
		String name = substringAfterLast(baseName, "/");
		do {
			name = substringBeforeLast(name, "_");
		} while (name.contains("_"));
		return prefix + "/" + name;
	}
}
