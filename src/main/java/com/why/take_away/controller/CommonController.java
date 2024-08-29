package com.why.take_away.controller;

import com.why.take_away.common.R;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/*文件上传和下载*/
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController  {
    @Value("${waimai.path}")
    private String basePath;


    /*
    * 文件上传方法*/
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        //file是临时文件，需要转存到指定位置
        log.info(file.toString());
        //原始文件名
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用UUID重新生成文件名
        String filename = UUID.randomUUID().toString()+suffix;//
        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(basePath + filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }

        return R.success(filename);
    }
    @GetMapping("/download")
    public void download(HttpServletResponse response, String name) throws IOException {
        //输入流，读取文件内容
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            byte[] bytes = new byte[1024];
            int len=0;
            while((len=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();

            }
            //关闭资源
            outputStream.close();
            fileInputStream.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
