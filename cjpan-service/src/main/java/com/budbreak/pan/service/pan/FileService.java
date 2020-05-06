package com.budbreak.pan.service.pan;

import com.budbreak.pan.common.InvokeResult;
import com.budbreak.pan.model.FileMsg;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author baoqi
 * @date 2020/4/27 12:04
 */
public interface FileService {
    /**
     * 获取当前用户的存储空间大小
     * @param request
     * @return
     */
    InvokeResult getSpaceSize(HttpServletRequest request);

    /**
     * 文件格式转换
     * @param fileMsgList
     * @return
     */
    List<FileMsg> fileConvert(List<FileMsg> fileMsgList);

    /**
     * 获取当前用户文件
     * @param request
     * @param path
     * @return
     */
    List<FileMsg> userFileList(HttpServletRequest request, String path);

    /**
     * 创建文件目录
     * @param request
     * @param dirName
     * @param path
     * @return
     */
    InvokeResult dirCreate(HttpServletRequest request, String dirName, String path);

    /**
     * 上传文件
     * @param file
     * @param request
     * @param path
     * @return
     */
    InvokeResult upload(MultipartFile file, HttpServletRequest request, String path);

    /**
     * 删除文件
     * @param fileName
     * @param path
     * @param request
     * @return
     */
    InvokeResult fileDelete(String fileName, String path, HttpServletRequest request);

    /**
     * 文件下载
     * @param fileName
     * @param path
     * @param request
     * @return
     */
    InvokeResult download(String fileName, String path, HttpServletRequest request);

    /**
     * 断点续传
     * @param request
     * @param file
     * @param path
     * @return
     */
    void uploadServlet(HttpServletRequest request, MultipartFile file, String path);

    /**
     * 上传前检查
     * @param request
     * @return
     */
    InvokeResult checkChunk(HttpServletRequest request);

    /**
     * 分片合并
     * @param request
     * @param path
     */
    void mergeChunks(HttpServletRequest request, String path);

    /**
     * 搜索文件
     * @param key
     * @param path
     * @param request
     * @return
     */
    List<FileMsg> search(String key, String path, HttpServletRequest request);

    /**
     * 文件重命名
     * @param oldName
     * @param newName
     * @param path
     * @param request
     * @return
     */
    InvokeResult fileReName(String oldName, String newName, String path, HttpServletRequest request);


    /**
     * 验证提取码是否正确
     * @param arr
     * @param localLink
     * @return
     */
    InvokeResult shareCallBack(String[] arr, String[] localLink);

    /**
     * 生成提取码
     * @param expireDay
     * @param fileName
     * @param path
     * @param request
     */
    InvokeResult generateShareLink(String expireDay, String fileName, String path, HttpServletRequest request);

    /**
     * 移动文件
     * @param fileName
     * @param oldPath
     * @param newPath
     * @param request
     * @return
     */
    InvokeResult fileMove(String fileName, String oldPath, String newPath, HttpServletRequest request);

    /**
     * 转码功能
     * @param filepath
     * @return
     */
    InvokeResult videoConvert(String filepath);

    /**
     * 分享保存到网盘
     * @param link
     * @return
     */
    String fileShareCodeDecode(String link);
}
