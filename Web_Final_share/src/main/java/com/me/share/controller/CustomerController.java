package com.me.share.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.me.share.DAO.UploadDAO;
import com.me.share.DAO.UserDAO;
import com.me.share.exception.ContentException;
import com.me.share.exception.UserException;
import com.me.share.pojo.Collect;
import com.me.share.pojo.Comment;
import com.me.share.pojo.Content;
import com.me.share.pojo.User;
import com.me.share.utils.JsonUtils;



@Controller
@RequestMapping("/content/**/")
public class CustomerController {

	@Autowired
	@Qualifier("uploadDao")
	UploadDAO uploadDao;

	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;

	@Autowired
	ServletContext servletContext;

	// to redirect to page to upload picture

	@RequestMapping(value = "/content/upload", method = RequestMethod.GET)
	public String showForm(ModelMap model) {
		Content content = new Content(); // should be AutoWired

		// command object
		model.addAttribute("content", content);

		// return form view
		return "content-upload";

	}

	// ajax function to like/unlike

	@RequestMapping(value = "/content/like", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String likeUnlike(HttpServletRequest request, @RequestParam String cID) throws Exception {
		boolean flag;
		HttpSession session = request.getSession();
		long id = Long.parseLong(cID);
		User u = (User) session.getAttribute("user");
		Content p = uploadDao.getContent(id);
		Collect l = uploadDao.doCollect(u, p);
		if (l == null) {
			Collect collect = new Collect();
			collect.setCollectContent(p);
			collect.setUser(u);
			uploadDao.registerLike(collect);
			flag = true;
		} else {
			uploadDao.removeLike(l);
			flag = false;
		}
		String result = "{" + JsonUtils.toJsonField("flag", String.valueOf(flag))
				+ (", " + JsonUtils.toJsonField("count", String.valueOf(p.getCollects().size()))) + "}";
		return result;
	}

	// to redirect to view post page

	@RequestMapping(value = "/content/comment/{id}", method = RequestMethod.GET)
	protected ModelAndView viewPost(HttpServletRequest request, @PathVariable("id") String id) throws Exception {
		long contentID = Long.parseLong(id);
		boolean flag;
		Map<String, Object> map = new HashMap<String, Object>();
		Content content = uploadDao.getContent(contentID);
		User createdBy =  content.getUser();
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");
		Collect l = uploadDao.doCollect(u, content);
		if (l != null)
			flag = true;
		else
			flag = false;
		map.put("createdByUser", createdBy);
		map.put("flag", flag);
		map.put("content", content);
		map.put("count", content.getCollects().size());
		if(u.getRole().equals("customer")) {
		return new ModelAndView("view-post", "map", map);
		}
		else {return new ModelAndView("admin-view-post", "map", map);}
		
	}

	// ajax function to add comment

	@RequestMapping(value = "/content/comment/add", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String addComment(HttpServletRequest request, @RequestParam String comText, @RequestParam String contentID)
			throws Exception {
		long cID = Long.parseLong(contentID);
		Content content = uploadDao.getContent(cID);
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		System.out.println("=============================="+comText);
		Comment com = new Comment();
		com.setUser(user);
		com.setCommentContent(comText);
		com.setCommentonContent(content);
		com.setCommentTime(new Date());
		
		uploadDao.addComment(com);
		System.out.println("added comment");
		String result = "{" + JsonUtils.toJsonField("user", com.getUser().getUsername())
				+ (", " + JsonUtils.toJsonField("comment", com.getCommentContent())) + "}";
		return result;

	}


	// to retrieve the file path of picture and store it and redirect to gallery

	@RequestMapping(value = "/content/upload", method = RequestMethod.POST)
	public ModelAndView handleUpload(@ModelAttribute("content") Content content, HttpServletRequest request) {
		ModelAndView mv = null;
		try {

			HttpSession session = (HttpSession) request.getSession();
			User u = (User) session.getAttribute("user");
			String check = File.separator;
			String dir = check + "user_images_" + u.getPersonID();

			File directory;

			String path = "/Users/yiyangyang/Documents/workspace-sts-3.9.3.RELEASE/Web_Final_share/src/main/webapp/resources/images";
			path += dir;
			directory = new File(path);
			boolean temp = directory.exists();
			if (!temp) {
				temp = directory.mkdir();
			}
			if (temp) {
				CommonsMultipartFile photoInMemory = content.getPic();
				String fileName = photoInMemory.getOriginalFilename();

				File localFile = new File(directory.getPath(), fileName);
				photoInMemory.transferTo(localFile);
				String fName = check + "images" + dir + check + fileName;
				String artical = (String) request.getParameter("artical");
				String menuName = (String) request.getParameter("menuName");
				String category= (String) request.getParameter("category");
				content.setCategory(category);
				content.setFileName(fName);
				content.setArtical(artical);
				content.setMenuName(menuName);
				System.out.println("File is stored at" + localFile.getPath());
				System.out.print("registerNewUser");
				// User u = (User) session.getAttribute("user");
				content.setUser(u);
				content.setUploadDate(new Date());
				Content p = uploadDao.upload(content);
				mv = retrieveGallery(request);

			} else {
				System.out.println("Failed to create image directory!");
			}

		} catch (IllegalStateException e) {
			System.out.println("*** IllegalStateException: " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("*** IOException: " + e.getMessage());
		} catch (ContentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}

	// to retrieve pictures of a user and display

	@RequestMapping(value = "/content/gallery", method = RequestMethod.GET)
	public ModelAndView retrieveGallery(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Content> content_list = null;
		List<User> following = null;
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");
		try {
			following = formFollowingList(u);
			content_list = uploadDao.get(u);
		} catch (ContentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.put("content_list", content_list);
		model.put("following", following);
		return new ModelAndView("content-gallery", "model", model);
	}

	// to increase like counter using ajax call

	@RequestMapping(value = "/content/like", method = RequestMethod.POST)
	@ResponseBody
	public long likeCounter(HttpServletRequest request, @RequestParam String photoID) {
		System.out.println("reached");
		long count = 0;
		try {
			count = uploadDao.increment(photoID);
		} catch (ContentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}

	// retrieve list of following for the user

	public List<User> formFollowingList(User user) throws UserException {
		List<User> following = new ArrayList<User>();
		for (User u : userDao.getAllUser()) {
			for (User f : u.getFollowers()) {
				if (f.getPersonID() == user.getPersonID())
					following.add(u);
			}
		}
		return following;
	}
	
	@RequestMapping(value = "/content/explore", method = RequestMethod.GET)
	 public ModelAndView allContents(HttpServletRequest request) throws ContentException {
	  String pageNumber=request.getParameter("pageNum");
	  int pgn = Integer.parseInt(pageNumber);
	  List<Content> contentList = uploadDao.getAllContentsbyPage(pgn);
	  HttpSession session = request.getSession(true);
	  
//	  for(Content a: contentList) {
//	  System.out.println(a.getContentID());
//	  }
//	  int cnum = (Integer) session.getAttribute("num");
//	  if(cnum==0) {
	      Long anum = (Long)uploadDao.getAllContentsNum();
//		  System.out.println(anum+"=========================");
		  double pnum =(double)anum/5;
//		  System.out.println(pnum+"=========================");
		  int num = (int) Math.ceil(pnum);
          session.setAttribute("num", num);
//        }
	  Collections.sort(contentList, new Comparator<Content>() {
	   public int compare(Content a1, Content a2) {
	    return a2.getUploadDate().compareTo(a1.getUploadDate());
	   }
	  });
		 
	  return new ModelAndView("explore-contents", "contentList", contentList);
	 }
}
