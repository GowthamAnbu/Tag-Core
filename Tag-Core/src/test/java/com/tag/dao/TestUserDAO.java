package com.tag.dao;

import com.google.gson.Gson;

public class TestUserDAO {

	public static void main(String[] args) {
		UserDAO userDAO = new UserDAO();
		/*if(userDAO.isValidPassword("gautamgow@gmail.com","Gowtham")){
			System.out.println("yes");
		}
		else{
			System.out.println("No");
		}*/
		System.out.println(userDAO.findAll());
		String json=new Gson().toJson( userDAO.findAll());
		System.out.println(json);
	}

}
