package com.example.bill.controller;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


@Controller
public class Core {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String retMsg(String result){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("result",result);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObject.toString();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public String register(@RequestParam String username,@RequestParam String password) {

        String result = "";

        if ("".equals(username)) {
            return retMsg("Username can not be empty");
        }
        if ("".equals(password)) {
            return retMsg("password can not be blank");
        }

        //检查用户名是否被注册
        String selectSql = "select * from userinfo";

        List<Map<String, Object>> list = jdbcTemplate.queryForList(selectSql);

        for(Map<String, Object> li:list){

            String usernameSelect = String.valueOf(li.get("username"));

            if (username.equals(usernameSelect)) {
                result = "This username has already been registered";
            }

        }

        if ("This username has already been registered".equals(result)) {
            return retMsg(result);
        }

        String insertSql = "insert into userinfo (username,password) VALUES(?,?)";

        if(jdbcTemplate.update(insertSql,username,password)==1)
            result = "registration success";

        return retMsg(result);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public String login(@RequestParam String username,@RequestParam String password) {

        String result = "Login failed, user name or password is wrong！";

        if ("".equals(password)) {
            return retMsg("password can not be blank！");
        }
        if ("".equals(username)) {
            return retMsg("Username can not be empty！");
        }

        String selectSql = "select * from userinfo";

        List<Map<String, Object>> list = jdbcTemplate.queryForList(selectSql);

        String idSelect = "";


        for(Map<String, Object> li:list){
            idSelect = String.valueOf(li.get("id"));
            String usernameSelect = String.valueOf(li.get("username"));
            String passwordSelect = String.valueOf(li.get("password"));

            if (username.equals(usernameSelect) && password.equals(passwordSelect)) {
                return retMsg("login successful-"+idSelect);
            }

        }

        return retMsg(result);
    }



}
