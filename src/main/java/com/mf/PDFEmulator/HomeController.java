package com.mf.PDFEmulator;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	ServletContext servletContext;
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		
		
		return "home";
	}

	@RequestMapping(value = "/generate", method = RequestMethod.POST)
	public String pdf( //@ResponseBody
			@RequestParam("file") MultipartFile[] files,
			@RequestParam("pdfName") String pdfName, 
			@RequestParam("fopVersion") int fopVersion, Model model)
			throws IOException {

		String message = "";
		String[] filePaths = new String[3];
		String temp = "";
		String linesOfXsl="";
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];

			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");

				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + file.getOriginalFilename());
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				filePaths[i] = serverFile.getAbsolutePath();
				System.out.println(filePaths[i]);

				temp = dir.getAbsolutePath() + File.separator;
				stream.write(bytes);
				
				if(filePaths[i].endsWith("xsl")){
					BufferedReader br = new BufferedReader(new FileReader(serverFile));
					String line;
					while (true) {
						line = br.readLine();
						if (line == null) {
							break;
						}
						linesOfXsl=linesOfXsl+line+"\n";
					}
				}
				
				stream.close();

				message = message + "You successfully uploaded file="
						+ file.getOriginalFilename() + "<br />";

			} catch (Exception e) {
				return "You failed to upload " + file.getOriginalFilename()
						+ " => " + e.getMessage();
			}
		}
		filePaths[2] = temp + pdfName + ".pdf";
		
		//filePaths[2] ="D:\\ASIF\\STS\\workspace-sts\\PDFReport\\src\\main\\webapp\\resources\\"+pdfName.trim()+ ".pdf";
		int n=fopVersion;
		String[] fopDir ={ "D:\\ASIF\\FOP\\fop-0.20\\", "D:\\ASIF\\FOP\\fop-0.94\\", "D:\\ASIF\\FOP\\fop-1.10\\" };
		
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd "
				+ fopDir[n] + " && fop -xml  " + filePaths[0] + "  -xsl  "
				+ filePaths[1] + "  -pdf " + pdfName+".pdf", " && "+pdfName+".pdf");
		builder.redirectErrorStream(true);
		Process p = builder.start();
		
		message = message + "Report generated at = " + filePaths[2] + "<br />";
		
		
		
		model.addAttribute("myxml", linesOfXsl);
		model.addAttribute("myPdf", fopDir[n]+pdfName+".pdf");
		return "home";
		//return message;

	}
	
}
