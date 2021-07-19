package com.example.demo.dao.file;

import com.example.demo.pojo.db.file.SystemFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FileMapper {
    List<SystemFile> getFileList(Map queryParams);

    void saveFile(SystemFile file);

    void delFile(SystemFile file);

    void delFiles(List<SystemFile> files);
}
