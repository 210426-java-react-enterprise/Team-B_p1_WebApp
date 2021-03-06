package com.revature.p1.services;

import com.revature.p1.daos.BankUserDAO;
import com.revature.p1.dtos.Credentials;
import com.revature.p1.exceptions.*;
import com.revature.p1.models.account.BankUser;
import com.revature.p1.util.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  This class validates data for user login, registration, update, delete, and authentication before sending it to appropriate DAO's
 */
public class BankUserService {

    private BankUserDAO userDao;

    public BankUserService(BankUserDAO userDao) {
        this.userDao = userDao;
    }

      public void register(BankUser newUser) {

        try {

        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid new user data provided!");
        }
            if (!userDao.isUsernameAvailable(newUser)) {
                throw new UsernameUnavailableException();
            }

            if (!userDao.isEmailAvailable(newUser)) {
                throw new EmailUnavailableException();
            }

            userDao.save(newUser);
        } catch (UsernameUnavailableException | EmailUnavailableException e) {
            e.printStackTrace();
        }
    }

    public BankUser authenticate(Credentials creds) throws AuthenticationException {
        System.out.println("in bank userservice auth " + creds.getUsername() + creds.getPassword());
        try {
            BankUser authenticatedUser = userDao.findUserByUsernameAndPassword(creds);

            if(authenticatedUser == null) throw new AuthenticationException();

            return authenticatedUser;

        } catch (DataSourceException e) {
            e.printStackTrace();
            throw new AuthenticationException();
        }
    }

    /**
     * Description: Ensures user input is valid
     *
     * @param user
     * @return boolean
     */
    public boolean isUserValid(BankUser user) {
        if (user == null) return false;
        if (user.getuName() == null || user.getuName().trim().isEmpty() || user.getuName().length() > 15) return false;
        if (user.getPassword() == null || user.getPassword().trim().isEmpty() || user.getPassword().length() > 72)
            return false;
        if (user.getEmail() == null || user.getEmail().trim().isEmpty() || user.getEmail().length() > 50) return false;
        if (user.getfName() == null || user.getfName().trim().isEmpty() || user.getfName().length() > 50) return false;
        if (user.getlName() == null || user.getlName().trim().isEmpty() || user.getlName().length() > 50) return false;

        /*
            Regular expression evaluation email input...
            Example #5 on: https://howtodoinjava.com/java/regex/java-regex-validate-email-address/
         */

        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(user.getEmail());

        if (!matcher.matches()) {
            return false;
        }

        return true;
    }

    public boolean delete(BankUser user) {
        return userDao.deleteUser(user);
    }

    public boolean update(BankUser user) {
        return userDao.updateUser(user);
    }


}
