package com.example.demo.controller.resource;

import com.example.demo.pojo.db.auth.Auth;
import com.example.demo.pojo.db.resource.Resource;
import com.example.demo.pojo.db.role.Role;
import com.example.demo.pojo.enums.OptionLog;
import com.example.demo.pojo.response.ResponseResult;
import com.example.demo.service.auth.IAuthService;
import com.example.demo.service.resource.IResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.example.demo.pojo.constant.GlobalConstant.*;

@Slf4j
@Controller
public class ResourceController {
    @Autowired
    private IResourceService resourceService;

    @ResponseBody
    @RequestMapping("/resource/getResourceList")
    public ResponseResult getResourceList(@RequestParam("page") int page,
                                      @RequestParam("limit") int limit){
        return  new ResponseResult("200","查询成功",resourceService.getResourceList(page,limit));
    }


    @OptionLog(
            optionModel=optionModel_Resource,
            optionType= optionType_Save,
            optionDesc="添加资源")
    @ResponseBody
    @RequestMapping("/resource/saveResource")
    public ResponseResult saveResource(@RequestBody Resource resource){
        try {
            resourceService.saveResource(resource);
            return new ResponseResult("200","保存成功",null);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return  new ResponseResult("500","保存失败",null);
        }
    }

    @OptionLog(
            optionModel=optionModel_Resource,
            optionType= optionType_Del,
            optionDesc="删除资源")
    @ResponseBody
    @RequestMapping("/resource/delReources")
    public ResponseResult delReources(@RequestParam("ids") List list){
        try {
            resourceService.delReources(list);
            return new ResponseResult("200","删除成功",null);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return  new ResponseResult("500","删除失败",null);
        }
    }

    @OptionLog(
            optionModel=optionModel_Resource,
            optionType= optionType_Edit,
            optionDesc="编辑资源")
    @ResponseBody
    @RequestMapping("/resource/editResource")
    public ResponseResult editResource(@RequestBody Resource resource){
        try {
            resourceService.editResource(resource);
            return new ResponseResult("200","修改成功",null);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return  new ResponseResult("500","修改失败",null);
        }
    }

}
