package com.example.demo.controller.file;

import com.example.demo.pojo.db.file.SystemFile;
import com.example.demo.pojo.page.PageRequest;
import com.example.demo.pojo.response.ResponseResult;
import com.example.demo.service.file.IFileService;
import com.github.tobato.fastdfs.domain.StorePath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class FileController {

    @Autowired
    private IFileService fileService;


    @RequestMapping("/file/getFileList")
    @ResponseBody
    public ResponseResult getFileList(PageRequest pageRequest){
        return  new ResponseResult("200","查询成功",fileService.getFileList(pageRequest));
    }
    /**
     * 实现文件上传
     * */
    @RequestMapping("/file/uploadFile")
    @ResponseBody
    public ResponseResult uploadFile(@RequestParam("fileName") MultipartFile file){
        try {
            Map fileInfo=fileService.uploadFile(file);
            return new ResponseResult("200","上传成功",fileInfo);

        }
        catch (Exception e){
            e.printStackTrace();
          return   new ResponseResult("500","上传失败",null);
        }
    }
    @RequestMapping("/file/saveFile")
    @ResponseBody
    public ResponseResult saveFile(@RequestBody SystemFile file){
        try {
            fileService.saveFile(file);
            return new ResponseResult("200","上传成功",null);

        }
        catch (Exception e){
            e.printStackTrace();
            return   new ResponseResult("500","上传失败",null);
        }
    }
    @RequestMapping("/file/delFiles")
    @ResponseBody
    public ResponseResult delFiles(@RequestBody List<SystemFile> files){
        try {
            fileService.delFiles(files);
            return new ResponseResult("200","删除成功",null);

        }
        catch (Exception e){
            e.printStackTrace();
            return   new ResponseResult("500","删除失败",null);
        }
    }
    /**
     * 实现文件下载
     * */
    @RequestMapping("/file/downloadFile")
    @ResponseBody
    public void download(@RequestBody SystemFile file, HttpServletResponse response){
        try {
            String fileName = file.getFileName();
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename="+new String( fileName.getBytes("utf-8"), "ISO8859-1" ));
            byte[] bytes=fileService.downloadFile(file);
            ByteArrayInputStream bais=new ByteArrayInputStream(bytes);
            ServletOutputStream out = response.getOutputStream();
            byte buff[] = new byte[1024];
            int length = 0;
            while((length=bais.read(buff))>0){
                out.write(buff,0,length);
            }
            bais.close();
            out.close();
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
