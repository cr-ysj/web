package com.example.demo.pojo.db.file;

import com.example.demo.pojo.enums.FileType;
import lombok.Data;

import java.util.Date;


@Data
public class SystemFile {

    private Long id;

    private String fileName;

    private String groupName;

    private String path;

    private Date createDate;

    private String createUser;

    private FileType fileType;

    private Long fileSize;

    private String  description;

    private String originalFilename;

    private String originalFiletype;
}
