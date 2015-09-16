package com.pr.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.omg.PortableInterceptor.SUCCESSFUL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import com.pr.client.services.AWSImageService;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = { "/", " * ", "home" })
public class HomeController {
	private static final String SUFFIX = "/";
	AWSImageService service;
	private String message = "";
	private List<String> s3keys;
	private String selectedKey = "";
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);;
	// local file name
	String forUploadFilename;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = { "/", " * ", "home" }, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/unlogin", method = RequestMethod.GET)
	public String unlogin(Locale locale, Model model) {
		logger.info("Logout! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);
		System.out.print("odlogowanie");
		return "unlogin";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {
		logger.info("Logowanie! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);
		System.out.print("logowanie");
		return "login";
	}

	@RequestMapping(value = "/myimages", method = RequestMethod.GET)
	public String myimages(Locale locale, Model model) {
		logger.info("MyImages! ", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		try {
			String formattedDate = dateFormat.format(date);

			AWSImageService service = new AWSImageService();
			this.s3keys = new LinkedList<String>();
			this.s3keys = service.getKeyList();

			// s3Files=ImageFile.convert(s3filesList, "nitager", "nitager");
			logger.info(s3keys.size() == 0 ? " Brak elementów " : new StringBuilder(s3keys.size()).toString(), locale);
			String linkVisibility = "hidden";
			String fileUrl = "";
			model.addAttribute("linkVisibility", linkVisibility);
			model.addAttribute("fileUrl", fileUrl);
			model.addAttribute("keys", s3keys);
			model.addAttribute("serverTime", formattedDate);
			System.out.print("List files");
			model.addAttribute("message", "WSIO DOBRZE!");
			return "myimages";
		} catch (Exception e) {
			message = e.toString();
			model.addAttribute("message", message);
			logger.info(e.getMessage() + " " + e.getStackTrace().toString());

			return "myimages";
		}
	}

	@RequestMapping(value = "/process", method = RequestMethod.GET)
	public String process(@RequestParam(value = "imgkey", required = false) String imgkey, Model model) {
		logger.info("Process file ! ", imgkey);

		try {
			AWSImageService service = new AWSImageService();
			message = "File to process " + imgkey;
			logger.info("Process file ! ", message);
			// model.addAttribute("serverTime", formattedDate);
			String linkVisibility = "visible";
			String fileUrl = service.convertImg(imgkey);
			model.addAttribute("linkVisibility", linkVisibility);
			model.addAttribute("fileUrl", fileUrl);
			model.addAttribute("message", message + fileUrl != null ? "<a href=" + fileUrl + "/>Click me!</a>" : "");
			return "myimages";
		} catch (Exception e) {
			message = e.toString();
			model.addAttribute("message", message);
			logger.info(e.getMessage() + " " + e.getStackTrace().toString());

			return "myimages";
		}
	}

	@RequestMapping(value = "/processBySQS", method = RequestMethod.GET)
	public String processBySQS(@RequestParam(value = "imgkey", required = false) String imgkey, Model model) {
		logger.info("Process file sendomg to other webworker by sqs ! ", imgkey);

		try {
			AWSImageService service = new AWSImageService();
			message = "File to process " + imgkey;
//			List<String> queues=service.listQueus();
//			for(String q:queues){
//				logger.info("QUEUE : ", message);
//				
//			}
			logger.info("Process file ! ", message);
			// model.addAttribute("serverTime", formattedDate);
			String linkVisibility = "visible";
			service.sendSQSMsg(imgkey);
			model.addAttribute("linkVisibility", linkVisibility);

			model.addAttribute("message", message + " Wysłano plik do obróbki, odśwież my images by go zobaczyć");
			return "myimages";
		} catch (Exception e) {
			message = e.toString();
			model.addAttribute("message", message);
			logger.info(e.getMessage() + " " + e.getStackTrace().toString());

			return "myimages";
		}
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String download(@RequestParam(value = "imgkey", required = false) String imgkey, Model model) {
		logger.info("File to download! ", imgkey);

		try {
			service = new AWSImageService();
			// Fileservice.deleteImage(imgkey);
			logger.info("Download file ! ", imgkey);

			// s3Files=ImageFile.convert(s3filesList, "nitager", "nitager");
			message = "File to download : " + imgkey;
			// model.addAttribute("serverTime", formattedDate);
			service = new AWSImageService();

			model.addAttribute("message", message);
			return "myimages";
		} catch (Exception e) {
			message = e.toString();
			model.addAttribute("message", message);
			logger.info(e.getMessage() + " " + e.getStackTrace().toString());

			return "myimages";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam(value = "imgkey", required = false) String imgkey, Model model) {
		logger.info("File to delete! ", imgkey);

		try {
			service = new AWSImageService();
			service.deleteImage(imgkey);
			logger.info("Deleted file ! ", imgkey);

			// s3Files=ImageFile.convert(s3filesList, "nitager", "nitager");
			logger.info(s3keys.size() == 0 ? " Brak elementów " : new StringBuilder(s3keys.size()).toString());
			message = "File to deleteted : " + imgkey;
			// model.addAttribute("serverTime", formattedDate);
			System.out.print("Delete file...");
			model.addAttribute("message", message);
			return "myimages";
		} catch (Exception e) {
			message = e.toString();
			model.addAttribute("message", message);
			logger.info(e.getMessage() + " " + e.getStackTrace().toString());

			return "myimages";
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String uploadFormHandler(Locale locale, Model model) {
		try {
			logger.info("Welcome to image upload! The client locale is {}.", locale);

			Date date = new Date();
			DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

			String formattedDate = dateFormat.format(date);

			model.addAttribute("serverTime", formattedDate);

			return "upload";
		} catch (Exception e) {
			return "upload";
		} finally {
			return "upload";
		}
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody String uploadFileHandler(@RequestParam("file") MultipartFile file, Locale locale,
			Model model) {

		if (!file.isEmpty()) {
			try {
				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_");

				String formattedDate = dateFormat.format(date);
				byte[] bytes = file.getBytes();
				forUploadFilename = new StringBuilder(file.getOriginalFilename()).toString();
				logger.info("Local file neme=" + forUploadFilename);
				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + forUploadFilename);

				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				logger.info("Server File Location=" + serverFile.getAbsolutePath());
				String s3FileName = new StringBuilder(formattedDate.toString() + forUploadFilename).toString();
				logger.info("File name :" + s3FileName);
				s3FileName = new StringBuilder("nitager" + SUFFIX + s3FileName).toString();
				logger.info("Orginal name with date suffix :" + s3FileName);
				logger.info("S3 key filename :" + s3FileName);
				AWSImageService service = new AWSImageService();
				service.initService();
				service.putImageToS3(serverFile, s3FileName);

				return "You successfully uploaded file=" + s3FileName;
			} catch (Exception e) {
				return "You failed to upload " + file.getName() + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + file.getName() + " because the file was empty or is not image file.";
		}
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Locale locale, Model model) {
		logger.info("Registration! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);
		System.out.print("register");
		return "register";

	}

}
