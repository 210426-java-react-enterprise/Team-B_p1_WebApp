package com.revature.p1.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.p1.dtos.Credentials;
import com.revature.p1.exceptions.AuthenticationException;
import com.revature.p1.models.account.BankUser;
import com.revature.p1.services.BankUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * This class writes client requests to appropriate POJO's before calling appropriate service functions
 * then formats appropriate status codes based on server response.
 */
public class BankUserController {

    private BankUserService bankUserService;
    private ObjectMapper mapper;


    public BankUserController(BankUserService bankUserService, ObjectMapper mapper) {

        this.bankUserService = bankUserService;
        this.mapper = mapper;

    }

    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            BankUser newUser = mapper.readValue(req.getInputStream(), BankUser.class);
            bankUserService.register(newUser);
            writer.write("Registration success!");

        } catch (JsonParseException e) {
            writer.write("Invalid register data provided.");
            e.printStackTrace();
            resp.setStatus(500);
        }catch(Exception e){
            writer.write("User already exists!");
            resp.setStatus(500);
        }
    }

    public void authenticate(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {

            Credentials creds = mapper.readValue(req.getInputStream(), Credentials.class);
            BankUser authUser = bankUserService.authenticate(creds);
            req.getSession().setAttribute("this-user", authUser);

            if(authUser.getuID() == 0){
                writer.write("Invalid credentials.");
                resp.setStatus(401);
                return;
            } else {
                writer.write(mapper.writeValueAsString(authUser));
            }

        } catch (MismatchedInputException e) {
            e.printStackTrace();
            resp.setStatus(400);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            resp.setStatus(401);
        } catch (JsonParseException e) {
            writer.write("Invalid login data provided.");
            e.printStackTrace();
            resp.setStatus(500);
        }

    }

    public boolean delete(BankUser user) {
        return bankUserService.delete(user);
    }

    public boolean updateUser(BankUser user) {
        return bankUserService.update(user);
    }
}