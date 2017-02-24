package com.tag.dao;

import com.tag.dao.UserDAO;

public class TestUserDAO {

	public static void main(String[] args) {
		UserDAO userDAO = new UserDAO();
		if(userDAO.isValidPassword("gautamgow@gmail.com","Gowtham")){
			System.out.println("yes");
		}
		else{
			System.out.println("No");
		}

	}

}
