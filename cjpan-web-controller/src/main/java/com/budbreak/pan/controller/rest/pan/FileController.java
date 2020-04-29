package com.budbreak.pan.controller.rest.pan;

import com.budbreak.pan.common.EncryptUtil;
import com.budbreak.pan.common.FfmpegUtil;
import com.budbreak.pan.common.FileUtil;
import com.budbreak.pan.common.InvokeResult;
import com.budbreak.pan.entity.link.Secret;
import com.budbreak.pan.manager.pan.FileManager;
import com.budbreak.pan.model.FileMsg;
import com.budbreak.pan.service.link.SecretService;
import com.budbreak.pan.service.pan.FileService;
import com.budbreak.pan.vo.link.SecretVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件管理
 *
 * @author Sandeepin
 * 2018/2/9 0009
 */
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private SecretService secretService;

    @Value("${key}")
    private  String key;

    @ApiOperation("文件上传")
    @PostMapping("upload")
    public InvokeResult upload(@RequestParam MultipartFile file, String path, HttpServletRequest request) {
        return fileService.upload(file, request, path);
    }

    @ApiOperation("文件下载")
    @GetMapping("download")
    public InvokeResult download(@RequestParam String fileName, String path, HttpServletRequest request) {
        return fileService.download(fileName, path, request);
    }


    @PostMapping(value = "/uploadServlet")
    @ApiOperation("分块上传 有断点续传的功能")
    public void uploadServlet(HttpServletRequest request,
                                     MultipartFile file,
                                     String path) {
        fileService.uploadServlet(request, file, path);
    }

    @ApiOperation("搜索接口")
    @PostMapping(value = "/search")
    public List<FileMsg> search(@RequestParam String key, String path, HttpServletRequest request) {
        return fileService.search(key, path, request);
    }

    @ApiOperation("获取当前用户的文件目录")
    @PostMapping("getFileList")
    public List<FileMsg> userFileList(String path, HttpServletRequest request) {
        List<FileMsg> fileMsgList = fileService.userFileList(request, path);
        return fileService.fileConvert(fileMsgList);
    }

    @ApiOperation("查看单个文件转码状态")
    @RequestMapping(value = "/transcodestatus", produces = "application/json; charset=utf-8")
    public InvokeResult transCodeStatus(String path, HttpServletRequest request) {
        if (path == null) {
            path = "/";
        }
        String responseMsg = "noneed";
        // 判断文件转码情况
        Map<String, Object> map = FfmpegUtil.ffmpegTaskMap.get(path);
        if (null != map) {
            Boolean transcode = (Boolean) map.get("flag");
            if (transcode) {
                responseMsg = "complete";
            } else {
                responseMsg = "transcoding";
            }
        }
        return InvokeResult.success(responseMsg);
    }

    @ApiOperation("文件删除")
    @GetMapping("filedelete")
    public InvokeResult fileDelete(String fileName, String path, HttpServletRequest request) {
        return fileService.fileDelete(fileName, path, request);
    }

    @ApiOperation("文件重命名老名字写path 新名字写newName oldName填@dir@")
    @PostMapping("filerename")
    public InvokeResult fileRename(@ApiParam("旧名") String oldName,
                                   @ApiParam("新名") String newName,
                                   String path,
                                   HttpServletRequest request) {
        return fileService.fileReName(oldName, newName, path, request);
    }

    @ApiOperation("创建文件夹")
    @PostMapping("dirCreate")
    public InvokeResult dirCreate(String dirName, String path, HttpServletRequest request) {
        return fileService.dirCreate(request, dirName, path);
    }

    @ApiOperation("验证文件提取码是否正确")
    @PostMapping("shareCallBack")
    public InvokeResult shareCallBack(String link) {
        if (link.isEmpty()) {
            return InvokeResult.failure("提取码为空！");
        }
        EncryptUtil des;
        try {
            des = new EncryptUtil(key, "utf-8");
            String filePathAndName = des.decode(link);
            String[] arr = filePathAndName.split("/");
            SecretVO secret = secretService.selectSecretBySecretLink(link);
            String[] localLink = secret.getLocalLink().split("/");
            return fileService.shareCallBack(arr, localLink);
        }catch (Exception e){
            return InvokeResult.error();
        }
    }

    @ApiOperation("文件分享下载地址")
    @GetMapping("sharefile")
    public ModelAndView shareFile(String link) {
        String downloadLink = "";
        if (!link.isEmpty()) {
//            downloadLink= fileService.fileShareCodeDecode(link);
            downloadLink = link;
        }
        //在数据库查找这个链接
        SecretVO linkSecret = secretService.selectSecretBySecretLink(downloadLink);
        if (linkSecret == null) {
            ModelAndView modelAndView = new ModelAndView("errorPage");
            modelAndView.addObject("message", "链接失效");
            return modelAndView;
        } else {
            Date date1 = linkSecret.getExpireDate();
            //表示链接永久有效
            if (date1 == null) {
                ModelAndView modelAndView = new ModelAndView("shareSecret");
                modelAndView.addObject("link", link);
                return modelAndView;
            } else {
                //得到long类型当前时间
                long dataTemp = System.currentTimeMillis();
                Date date2 = new Date(dataTemp);
                if (date1.before(date2)) {
                    //不能下载
                    ModelAndView modelAndView = new ModelAndView("errorPage");
                    modelAndView.addObject("message", link + "链接失效");
                    //删除本地共享文件夹的内容
                    FileUtil.delete(downloadLink);
                    return modelAndView;
                } else {
                    ModelAndView modelAndView = new ModelAndView("shareSecret");
                    modelAndView.addObject("link", link);
                    return modelAndView;
                }
            }

        }
    }


    @ApiOperation("分享文件加密")
    @PostMapping(value = "/sharefileSecret")
    public InvokeResult shareFileSecret(String link, String secret, HttpServletRequest request,
                                        HttpServletResponse response) {
        String downloadLink = "";
        Map<Object, String> map = new HashMap<>();
        if (!link.isEmpty()) {
            //            downloadLink= fileService.fileShareCodeDecode(link);
            downloadLink = link;
        }
        //在数据库查找这个链接
        SecretVO linkSecret = secretService.selectSecretBySecretLink(downloadLink);
        String secret1 = linkSecret.getSecret();
        if (secret1.toLowerCase().equals(secret.toLowerCase())) {
            secretService.addOneToDownloadNum(linkSecret);
            return InvokeResult.success(link);
        } else {
            return InvokeResult.failure("密码不正确！");
        }
    }

    @ApiOperation("文件提取码生成")
    @PostMapping(value = "/generateShareLink", produces = "application/json; charset=utf-8")
    public InvokeResult generateShareLink(@RequestParam String expireDay, String fileName, String path,
                                          HttpServletRequest request) {
        return fileService.generateShareLink(expireDay, fileName, path, request);
    }

    @ApiOperation("文件移动")
    @PostMapping(value = "/filemove")
    public InvokeResult fileMove(String fileName, String oldPath, String newPath, HttpServletRequest request) {
        return fileService.fileMove(fileName, oldPath, newPath, request);
    }

    @ApiOperation("上传前检查")
    @PostMapping("check")
    public InvokeResult checkChunk(HttpServletRequest request) {
        return fileService.checkChunk(request);
    }

    @ApiOperation("分块上传文件合并")
    @PostMapping(value = "/merge")
    public void mergeChunks(HttpServletRequest request, String path){
        fileService.mergeChunks(request, path);
    }

    @ApiOperation("调用后台转码的功能")
    @PostMapping(value = "/videoconvert")
    public InvokeResult videoconvert(String filepath) {
        return fileService.videoConvert(filepath);
    }

    @ApiOperation("获取云盘服务器所在盘磁盘空间大小")
    @GetMapping(value = "/getSpaceSize")
    public InvokeResult getSpaceSize(HttpServletRequest request) {
        return fileService.getSpaceSize(request);
    }

}
