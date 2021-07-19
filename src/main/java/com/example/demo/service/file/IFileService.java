package com.example.demo.service.file;

import com.example.demo.pojo.db.file.SystemFile;
import com.example.demo.pojo.page.PageRequest;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IFileService {
    Map uploadFile(MultipartFile file) throws IOException;

    PageInfo getFileList(PageRequest pageRequest);

    void saveFile(SystemFile file);

    void delFiles(List<SystemFile> files);

    byte[] downloadFile(SystemFile systemFile);
}
