package com.budbreak.pan.service.hdfs;

import com.budbreak.pan.common.InvokeResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author baoqi
 * @date 2020/5/10 18:35
 */
public interface HdfsFileService {

    /**
     * hdfs文件上传
     * @param request
     * @param file
     * @param path
     * @return
     */
    InvokeResult hdfsUpload(HttpServletRequest request, MultipartFile file, String path);

    /**
     * hdfs文件下载
     * @param fileName
     * @param path
     * @param request
     * @return
     */
    InvokeResult hdfsDownload(String fileName, String path, HttpServletRequest request);
}
