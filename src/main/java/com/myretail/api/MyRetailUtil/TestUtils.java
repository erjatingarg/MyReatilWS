package com.myretail.api.MyRetailUtil;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class TestUtils {

    private TestUtils() {
    }

    @SuppressWarnings("deprecation")
	public static String getFileAsString(String fileName) throws IOException {
        ClassLoader classLoader = TestUtils.class.getClassLoader();
        return IOUtils.toString(classLoader.getResourceAsStream(fileName));
    }
}
