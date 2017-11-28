package com.vbd.mapexam.helper;

import com.google.gson.Gson;
import com.vbd.mapexam.model.Result;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ServiceControl {

    private static final String HEADER_CONTENT_TYPE = "application/json";
    private static final String CHARSET = "charset=UTF-8";
    private static final int TIMEOUT_CONNECTION = 10000; //ms

    private static Map<String, String> mHeaders = new HashMap<String, String>();
    private static int mTimeOut = TIMEOUT_CONNECTION;

    public static void clearHeader() {
        mHeaders.clear();
    }

    public static void addHeader(String field, String value) {
        mHeaders.put(field, value);
    }

    public static void setTimeOut(int timeOutConnection) {
        mTimeOut = timeOutConnection;
    }
    
    public static Map<String, String> getHeader() {
    	return mHeaders;
    }

    public static InputStream doGet(String url, String query) throws Exception {
    	
    	if (connection != null) {
    		connection.disconnect();
    		connection = null;
    	}
    	
    	String urlStr = url + ((query == null || query.isEmpty()) ? "" : "?" + query); 

        connection = (HttpURLConnection) new URL(urlStr).openConnection();
        connection.setRequestProperty("Content-Type", HEADER_CONTENT_TYPE + ";" + CHARSET);
        connection.setReadTimeout(mTimeOut);
        if (mHeaders != null) {
            Set<Map.Entry<String, String>> entries = mHeaders.entrySet();
            for (Map.Entry<String, String> e : entries) {
                connection.setRequestProperty(e.getKey(), e.getValue());
            }
        }

        InputStream response = connection.getInputStream();

        return response;
    }
    
    private static HttpURLConnection connection = null;

    public static InputStream doPost(String url, String params) throws Exception {
    	return doPost(url, params.getBytes());
    }
    
    public static InputStream doPostFile(String url, byte[] buffer) throws Exception {
    	if (connection != null) {
    		connection.disconnect();
    		connection = null;
    	}
    	
        connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Connection", "close");
//        connection.setRequestProperty("Accept-Charset", CHARSET);
//        connection.setRequestProperty("Content-Type", HEADER_CONTENT_TYPE + ";" + CHARSET);
//        connection.setReadTimeout(mTimeOut);
        if (mHeaders != null) {
            Set<Map.Entry<String, String>> entries = mHeaders.entrySet();
            for (Map.Entry<String, String> e : entries) {
                connection.setRequestProperty(e.getKey(), e.getValue());
            }
        }

        OutputStream output = connection.getOutputStream();
        output.write(buffer);

        int code = connection.getResponseCode();
        if (code != 200) {
            throw new Exception(connection.getResponseMessage());
        }

        InputStream response = connection.getInputStream();
        
        return response;
    }
    
    public static InputStream doPostFile(String url, File file) throws Exception {
    	if (connection != null) {
    		connection.disconnect();
    		connection = null;
    	}
    	
        connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Connection", "close");
        connection.setRequestProperty("Accept-Charset", CHARSET);
        connection.setRequestProperty("Content-Type", HEADER_CONTENT_TYPE + ";" + CHARSET);
        connection.setReadTimeout(mTimeOut);
        if (mHeaders != null) {
            Set<Map.Entry<String, String>> entries = mHeaders.entrySet();
            for (Map.Entry<String, String> e : entries) {
                connection.setRequestProperty(e.getKey(), e.getValue());
            }
        }
        OutputStream output = connection.getOutputStream();
        InputStream is = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) > 0) {
        	output.write(buffer, 0, len);
        }
        is.close();
        

        int code = connection.getResponseCode();
        if (code != 200) {
            throw new Exception(connection.getResponseMessage());
        }

        InputStream response = connection.getInputStream();
        
        return response;
    }
    
    public static InputStream doPost(String url, byte[] buffer) throws Exception {
    	
    	if (connection != null) {
    		connection.disconnect();
    		connection = null;
    	}
    	
        connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Connection", "close");
        connection.setRequestProperty("Accept-Charset", CHARSET);
        connection.setRequestProperty("Content-Type", HEADER_CONTENT_TYPE + ";" + CHARSET);
        //connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//        connection.setReadTimeout(mTimeOut);
        if (mHeaders != null) {
            Set<Map.Entry<String, String>> entries = mHeaders.entrySet();
            for (Map.Entry<String, String> e : entries) {
                connection.setRequestProperty(e.getKey(), e.getValue());
            }
        }
        OutputStream output = connection.getOutputStream();
        output.write(buffer);

        int code = connection.getResponseCode();
        if (code != 200) {
            InputStream response = connection.getErrorStream();
            String resultStr = convertStreamToString(response);

            Result result = new Gson().fromJson(resultStr, Result.class);
            if (result != null) {
                if (result.error != null) {
                    throw new Exception(result.error.message);
                }
            }
            throw new Exception(connection.getResponseMessage());
        }

        InputStream response = connection.getInputStream();
        
        return response;
    }
    
    public static InputStream doPostToUpload(String url, byte[] buffer) throws Exception {
    	
    	if (connection != null) {
    		connection.disconnect();
    		connection = null;
    	}
    	
        connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Connection", "close");
        connection.setReadTimeout(mTimeOut);
        if (mHeaders != null) {
            Set<Map.Entry<String, String>> entries = mHeaders.entrySet();
            for (Map.Entry<String, String> e : entries) {
                connection.setRequestProperty(e.getKey(), e.getValue());
            }
        }

        OutputStream output = connection.getOutputStream();
        output.write(buffer);

        int code = connection.getResponseCode();
        if (code != 200) {
            throw new Exception(connection.getResponseMessage());
        }

        InputStream response = connection.getInputStream();
        
        return response;
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
    
    public static ByteArrayOutputStream readFully(InputStream input) throws IOException { 
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((bytesRead = input.read(buffer, 0, 1024)) != -1) { 
            output.write(buffer, 0, bytesRead);
        } 
        return output;
    } 
}
