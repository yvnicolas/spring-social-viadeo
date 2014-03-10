/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.viadeo.api.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.viadeo.api.impl.ViadeoTemplate;
import org.springframework.test.web.client.MockRestServiceServer;
import org.unitils.thirdparty.org.apache.commons.io.IOUtils;



public abstract class AbstractViadeoApiTest {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractViadeoApiTest.class);
	
	protected static final String ACCESS_TOKEN = "ACCESS_TOKEN";

	protected ViadeoTemplate viadeo;
	protected ViadeoTemplate unauthorizedViadeo;
	protected MockRestServiceServer mockServer;
	protected MockRestServiceServer unauthorizedMockServer;
	protected HttpHeaders responseHeaders;

	@Before
	public void setup() {
		viadeo = new ViadeoTemplate(ACCESS_TOKEN);
		unauthorizedViadeo = new ViadeoTemplate();
		mockServer = MockRestServiceServer.createServer(viadeo.getRestTemplate());
		responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		unauthorizedMockServer = MockRestServiceServer.createServer(unauthorizedViadeo.getRestTemplate());
	}

	protected String jsonResource(String filename)  {
		InputStream is;
		try {
			is = new FileInputStream(new File("src/test/java/org/springframework/social/viadeo/api/impl/"+ filename + ".json"));
			return IOUtils.toString(is);
		} catch (IOException e) {
			logger.error(String.format("Cannot read Json File %s.json: %s", filename, e.getMessage()),e);
			return ("");
		}
		
	}
}
