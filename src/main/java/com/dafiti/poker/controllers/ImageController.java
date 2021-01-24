/**
 * 
 */
package com.dafiti.poker.controllers;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vin001
 *
 */
@RestController
public class ImageController {
	
	@Autowired
	ServletContext context;  
	
	@GetMapping("/get-text")
	public @ResponseBody String getText() {
	    return "Hello world "+context;
	}
	
	
	@GetMapping(value = "/image")
	public @ResponseBody void getImage(HttpServletResponse response) throws IOException {
	    InputStream in = getClass()
	      .getResourceAsStream("/static/images/Sign.jpg");
	    IOUtils.copy(in, response.getOutputStream());
	}
}
