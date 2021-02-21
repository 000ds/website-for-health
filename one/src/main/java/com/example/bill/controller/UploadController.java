package com.example.bill.controller;

import com.example.bill.config.Config;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Controller
public class UploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    @Value("${upload.filePath}")
    private String filePath;

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }

        //String fileName = file.getOriginalFilename();

        String fileName = System.currentTimeMillis()+".png";
        String okPath = filePath + fileName;
        File dest = new File(okPath);
        try {
            file.transferTo(dest);
            LOGGER.info("上传成功");
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("result", Config.IMAGE_URL + fileName);

            return jsonObject.toString();
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }


}
