package com.amit.test;

import java.net.URL;

import org.assertj.core.api.Assertions;
import org.fest.util.Files;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceUtilsTest {
	Logger log = LoggerFactory.getLogger(ResourceUtilsTest.class);
	
	@Test
	public void classpathResource() throws Exception {
		String resourceLocation = "classpath:./skip/products_no_error.txt";
		Assertions.assertThat(ResourceUtils.isUrl(resourceLocation)).isTrue();
		Assertions.assertThat(ResourceUtils.getFile(resourceLocation).exists()).isTrue();

		resourceLocation = "classpath:skip/products_no_error.txt";
		Assertions.assertThat(ResourceUtils.isUrl(resourceLocation)).isTrue();
		Assertions.assertThat(ResourceUtils.getFile(resourceLocation).exists()).isTrue();
	}

	@Test(expected = Exception.class)
	public void absoluteClasspathResource() throws Exception {

		// NOTE: 절대 경로를 사용하면 파일을 찾을 수 없습니다.
		//
		String resourceLocation = "classpath:/skip/products_no_error.txt";
		Assertions.assertThat(ResourceUtils.isUrl(resourceLocation)).isTrue();
		Assertions.assertThat(ResourceUtils.getFile(resourceLocation).exists()).isTrue();
	}

	@Test
	public void fileSystemResource() throws Exception {
		String resourceLocation = "file:./products.txt";

		log.debug("Current Folder=[{}]", Files.currentFolder());


		URL url = ResourceUtils.getURL(resourceLocation);
		Assertions.assertThat(ResourceUtils.isFileURL(url)).isTrue();
		Assertions.assertThat(ResourceUtils.getFile(resourceLocation).exists()).isTrue();

		resourceLocation = "file:products.txt";
		url = ResourceUtils.getURL(resourceLocation);
		Assertions.assertThat(ResourceUtils.isFileURL(url)).isTrue();
		Assertions.assertThat(ResourceUtils.getFile(resourceLocation).exists()).isTrue();
	}
}
