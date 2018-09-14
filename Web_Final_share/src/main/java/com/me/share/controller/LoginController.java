package com.me.share.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.me.share.DAO.FollowerDAO;
import com.me.share.DAO.UploadDAO;
import com.me.share.DAO.UserDAO;
import com.me.share.exception.FollowerException;
import com.me.share.exception.UserException;
import com.me.share.pojo.Content;
import com.me.share.pojo.User;
import com.me.share.validator.UserValidator;

import sun.misc.BASE64Decoder;


@Controller
public class LoginController {

	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;
	@Autowired
	@Qualifier("followerDao")
	FollowerDAO followerDao;
	@Autowired
	@Qualifier("uploadDao")
	UploadDAO uploadDao;
	
	@Autowired
	@Qualifier("userValidator")
	UserValidator userValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(userValidator);
	}

	// to redirect to registration page

	@RequestMapping(value = "**/user/register", method = RequestMethod.GET)
	protected ModelAndView registerUser() throws Exception {
		System.out.print("redirectToRegister");
		return new ModelAndView("register", "user", new User());

	}

	// to redirect to login page (replaced by modal)

	@RequestMapping(value = "**/user/login", method = RequestMethod.GET)
	protected ModelAndView redirectUserPage(HttpServletRequest request) throws Exception {
		System.out.print("redirectToLogin");
		
		HttpSession session = request.getSession();
		
		User u = (User) session.getAttribute("user");
//		System.out.println("redirectToLogin"+u.getPersonID());
		List<Content> contentlist = null;
		if (u == null) {
			System.out.println("UserName/Password does not exist");
			session.setAttribute("errorMessage", "UserName/Password does not exist");
			return new ModelAndView("error");
		}
		else if(u.getRole().equals("customer")){
			List<User> following = new ArrayList<User>();
			
			following = getFollowing(u);
			following.add(u);
			contentlist = new ArrayList<Content>();
			for (User user : following) {
				for (Content pic : user.getContents()) {
					contentlist.add(pic);
				}
			}
			Collections.sort(contentlist, new Comparator<Content>() {
				public int compare(Content p1, Content p2) {
					return p2.getUploadDate().compareTo(p1.getUploadDate());
				}
			});

			return new ModelAndView("user-home", "feeds", contentlist);
		}
		else if(u.getRole().equals("admin")){

			contentlist = uploadDao.getAllContents();
			return new ModelAndView("admin-home", "contentList", contentlist);
		}
		else {
			String status = "you are being blocked by admin";
			return new ModelAndView("home","status",status);
			
		}

		}
		
	

	// to redirect to register/login page

	@RequestMapping(value = "/", method = RequestMethod.GET)
	protected String goToUserHome(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
				return "home";
			
	}
	
	

	// to login existing user and redirect to home page

	@RequestMapping(value = "**/user/login", method = RequestMethod.POST)
	protected ModelAndView loginUser(HttpServletRequest request) throws Exception {

		HttpSession session = (HttpSession) request.getSession();

		try {

			System.out.print("loginUser");

			User u = userDao.get(request.getParameter("username"), request.getParameter("password"));
			
			if (u == null) {
				System.out.println("UserName/Password does not exist");
				session.setAttribute("errorMessage", "UserName/Password does not exist");
				return new ModelAndView("error");
			}
			
			session.setAttribute("user", u);
			if(u.getRole().equals("customer")&&u.getStatus().equals("true")){
				
				List<User> following = new ArrayList<User>();

				List<Content> contentlist = null;
				following = getFollowing(u);
				following.add(u);
				contentlist = new ArrayList<Content>();
				for (User user : following) {
					for (Content pic : user.getContents()) {
						contentlist.add(pic);
					}
				}

				Collections.sort(contentlist, new Comparator<Content>() {
					public int compare(Content p1, Content p2) {
						return p2.getUploadDate().compareTo(p1.getUploadDate());
					}
				});
				
				return new ModelAndView("user-home", "feeds", contentlist);
			}
			else if(u.getRole().equals("admin")){
				List<Content> contentlist = null;
				System.out.println("====================to admin");
				contentlist = uploadDao.getAllContents();
				return new ModelAndView("admin-home", "contentList", contentlist);
			}
			else {
				String status = "you are being blocked by admin";
				return new ModelAndView("home","status",status);
			}
		} catch (UserException e) {
			System.out.println("Exception: " + e.getMessage());
			session.setAttribute("errorMessage", "error while login");
			return new ModelAndView("error");
		}
	}

	// to register new user and redirect to feeds page

	@RequestMapping(value = "**/user/register", method = RequestMethod.POST)
	protected ModelAndView registerNewUser(HttpServletRequest request, @ModelAttribute("user") User user,
			BindingResult result) throws Exception {

		userValidator.validate(user, result);

		if (result.hasErrors()) {
			return new ModelAndView("register", "user", user);
		}

		try {
			User u = userDao.register(user);

			request.getSession().setAttribute("user", u);
			if(u.getRole().equals("customer")&&u.getStatus().equals("true")) {
				System.out.print("===================customer===================");
				return new ModelAndView("user-home", "user", u);
			}
			else{
				System.out.print("===================admin go to adminhome===================");
				return new ModelAndView("admin-home", "user", u);
			}
		

		} catch (UserException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
		
	}

	// to retrieve all users

	@RequestMapping(value = "**/user/available", method = RequestMethod.GET)
	protected ModelAndView retrieveAllUsers(HttpServletRequest request) throws Exception {

		try {
			Map<String, Object> model = new HashMap<String, Object>();
			List<Boolean> cc = new ArrayList<Boolean>();
			HttpSession session = request.getSession();
			User u = (User) session.getAttribute("user");

			List<User> userList = userDao.getAllUser();
			for (User user : userList) {
				if (checkExist(user, u)) {
					cc.add(false);
				} else {
					cc.add(true);
				}
			}
			model.put("userlist", userList);
			model.put("checklist", cc);
			return new ModelAndView("user-list", "model", model);

		} catch (UserException e) {
			
			return new ModelAndView("error");
		}
	}
	
	@RequestMapping(value = "**/user/blockStatus", method = RequestMethod.GET)
	protected ModelAndView getStatusofUser(HttpServletRequest request) throws Exception {

		try {
			List<User> userList = userDao.getAllUser();
			return new ModelAndView("user-list", "userList", userList);
		} catch (UserException e) {
			return new ModelAndView("error");
		}
	}


	// to logout a user

	@RequestMapping(value = "**/logout", method = RequestMethod.GET)
	protected String returnToIndex(HttpServletRequest request) throws Exception {
		HttpSession session = (HttpSession) request.getSession();
		session.invalidate();
		return "home";

	}


	public List<User> getFollowing(User user) throws UserException, FollowerException {
		List<User> following = new ArrayList<User>();
		List<BigInteger> listfo = followerDao.getFollowersbyUser(user);
		if(listfo!=null) {
		for(BigInteger i: listfo)
	        {
	           for(User u:userDao.getAllUser()) {
	               if(u.getPersonID()==i.longValue()) {
	                   following.add(u);
	               }
	           }
	        }
		}
		return following;
	}
	// check if already follows
	public boolean checkExist(User followee, User follower) throws UserException, FollowerException {
		boolean flag = false;
		List<BigInteger> listfo = followerDao.getFollowersbyUser(follower);
		if(listfo!=null) {
		for(BigInteger i: listfo)
	        {
	               if(followee.getPersonID()==i.longValue()) {
	                   flag=true;
	                   break;
	           }
	        }
		}
		return flag;
	}

	// to redirect interceptor to error page
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String errorcall() {
		return "error";
	}
	
	@RequestMapping(value = "/user/block", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public boolean switchStatus(HttpServletRequest request, @RequestParam String followeeID) throws Exception {
		System.out.println("followeeID"+followeeID);
		boolean f = false;
		int id = Integer.parseInt(followeeID);

		try {
			User u = (User) userDao.get(id);
			if (u.getStatus().equals("true")) {
				u.setStatus("false");
				userDao.changeStatus(u);
				f = true;
			} else {
				u.setStatus("true");
				userDao.changeStatus(u);	
				f=false;
			}

		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return f;

	}

}