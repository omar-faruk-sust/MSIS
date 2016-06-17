package com.msis.auth;

import java.net.*;
import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class LoginTest {

	@Test
	public void testLoginPageGETStatus() throws IOException {
		URL url = new URL("http://localhost:8080/MSIS/");
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.connect();
		assertEquals(200, conn.getResponseCode());
		conn.disconnect();
	}
}
