package com.budbreak.pan.controller.rest.hdfs;

import com.budbreak.pan.common.InvokeResult;
import com.budbreak.pan.service.hdfs.HdfsFileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author baoqi
 * @date 2020/5/10 18:30
 */
@RestController
public class HdfsFileController {

    @Autowired
    private HdfsFileService hdfsFileService;

    @ApiOperation("文件上传")
    @PostMapping("hdfsUpload")
    public InvokeResult hdfsUpload(HttpServletRequest request,
                                   MultipartFile file,
                                   String path){
        return hdfsFileService.hdfsUpload(request, file, path);
    }

    @ApiOperation("文件下载")
    @GetMapping("hdfsDownload")
    public InvokeResult hdfsDownload(@RequestParam(value = "",required = false) String fileName,
                                     String path,
                                     HttpServletRequest request){
        return hdfsFileService.hdfsDownload(fileName, path, request);
    }
}
