package com.example.demo.service.file.impl;

import com.example.demo.dao.file.FileMapper;
import com.example.demo.pojo.db.file.SystemFile;
import com.example.demo.pojo.db.log.SysLog;
import com.example.demo.pojo.db.resource.Resource;
import com.example.demo.pojo.db.user.User;
import com.example.demo.pojo.page.PageRequest;
import com.example.demo.service.file.IFileService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.FdfsClientConfig;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Import(FdfsClientConfig.class)//注册扫描 FastFileStorageClient组件
//解决jmx重复注册bean的问题
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@SuppressWarnings("all")
@Service
public class FileServiceImpl implements IFileService {

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * MultipartFile类型的文件上传ַ
     * @param file
     * @return
     * @throws IOException
     */
    public Map uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()), null);
        Map map=new HashMap();
        map.put("path",storePath.getPath());
        map.put("groupName",storePath.getGroup());
        map.put("fileSize",file.getSize());
        String originalFilename = file.getOriginalFilename();
        map.put("originalFilename",originalFilename.substring(0,originalFilename.lastIndexOf(".")));
        map.put("originalFiletype",originalFilename.substring(originalFilename.lastIndexOf(".")+1));
        return map;
    }

    @Autowired
    private FileMapper fileMapper;

    @Override
    public PageInfo getFileList(PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getLimit());
        List<SystemFile> list= fileMapper.getFileList(pageRequest.getQueryParams());
        PageInfo<SystemFile> pageBean=new PageInfo(list);
        return pageBean;
    }

    @Transactional
    @Override
    public void saveFile(SystemFile file) {
        file.setCreateDate(new Date());
        User principal =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        file.setCreateUser(principal.getUsername());
        fileMapper.saveFile(file);
    }

    @Transactional
    @Override
    public void delFiles(List<SystemFile> files) {
        fileMapper.delFiles(files);
        for (int i = 0; i < files.size(); i++) {
            SystemFile file=files.get(i);
            String groupName = file.getGroupName();
            String path = file.getPath();
            storageClient.deleteFile(groupName,path);
        }
    }

    @Override
    public byte[] downloadFile(SystemFile systemFile) {
        String groupName=(String) systemFile.getGroupName();
        String path=systemFile.getPath();
        byte[] bytes = storageClient.downloadFile(groupName, path, new DownloadByteArray());
        return bytes;
    }
}
