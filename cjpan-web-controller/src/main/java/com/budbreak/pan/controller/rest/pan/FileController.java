package com.budbreak.pan.controller.rest.pan;

import com.budbreak.pan.common.InvokeResult;
import com.budbreak.pan.model.FileMsg;
import com.budbreak.pan.service.pan.FileService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @ApiOperation("文件上传")
    @PostMapping("upload")
    public InvokeResult upload(@RequestParam MultipartFile file, String path, HttpServletRequest request) {
        return fileService.upload(file, request, path);
    }
//    @Autowired
//    private LinkSecretServiceImpl linkSecretService;
//
//    @Autowired
//    private SaveServiceImpl saveService;

    @ApiOperation("文件下载")
    @GetMapping("download")
    public InvokeResult download(@RequestParam String fileName, String path, HttpServletRequest request) {
        return fileService.download(fileName, path, request);
    }


    @PostMapping(value = "/uploadsevlet")
    @ApiOperation("分块上传 有断点续传的功能")
    public InvokeResult uploadSevlet(HttpServletRequest request, MultipartFile file,
                                     String path) {
        return fileService.uploadSevlet(request, file, path);
    }

    @PostMapping(value = "/search")
    @ApiOperation("搜索接口")
    public List<FileMsg> search(@RequestParam String key, String path, HttpServletRequest request) {
        return fileService.search(key, path, request);
    }


    @ApiOperation("获取当前用户的文件目录")
    @PostMapping("getFileList")
    public List<FileMsg> userFileList(String path, HttpServletRequest request) {
        List<FileMsg> fileMsgList = fileService.userFileList(request, path);
        return fileService.fileConvert(fileMsgList);
    }


//    // 查看单个文件转码状态
//    @RequestMapping(value = "/transcodestatus", produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public ResponseMsg transCodeStatus(String path, HttpServletRequest request) {
//        if (path == null) {
//            path = "/";
//        }
//        logger.warn("transCodeStatus():" + path);
//        logger.warn("ffmpegTaskMap:" + JSONObject.toJSONString(FfmpegUtil.ffmpegTaskMap));
//        ResponseMsg responseMsg = new ResponseMsg();
//        responseMsg.setMsg("noneed");
//        responseMsg.setSuccess(true);
//        // 判断文件转码情况
//        Map<String, Object> map = FfmpegUtil.ffmpegTaskMap.get(path);
//        if (null != map) {
//            Boolean transcode = (Boolean) map.get("flag");
//            if (transcode) {
//                responseMsg.setMsg("complete");
//            } else {
//                responseMsg.setMsg("transcoding");
//            }
//        }
//        return responseMsg;
//    }

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

//    // 文件提取码->真实地址（验证提取码是否正确）
//    @RequestMapping(value = "/shareCallBack", produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public ResponseMsg shareCallBack(String link) {
//        logger.warn("执行shareCallBack接口！：" + link);
//        ResponseMsg j = new ResponseMsg();
//        if (link.isEmpty()) {
//            j.setMsg("提取码为空！");
//            return j;
//        }
//        String downloadLink = fileService.fileShareCodeDecode(link);
//        logger.warn("downloadLink:" + downloadLink);
//        if (!"null".equals(downloadLink)) {
//            j.setSuccess(true);
//            j.setMsg(downloadLink);
//        } else {
//            j.setMsg("提取码不正确！");
//        }
//        return j;
//    }
//
//    // 文件分享下载地址sharefile（创建链接）-----share（定位到分享页面）-shareCallBack(验证提取码是否正确)
//    @RequestMapping(value = "/sharefile", produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public ModelAndView shareFile(String link) {
//        logger.warn("shareFie接口的运行！");
//        String downloadLink = "";
//        if (!link.isEmpty()) {
//            //           downloadLink= fileService.fileShareCodeDecode(link);
//            downloadLink = link;
//        }
//        //在数据库查找这个链接
//        logger.warn("zc1+downloadLink:" + downloadLink);
//        LinkSecret linkSecret = linkSecretService.findLinkSecretBysecretLink(downloadLink);
//        if (linkSecret == null) {
//            ModelAndView modelAndView = new ModelAndView("errorPage");
//            modelAndView.addObject("message", "链接失效");
//            return modelAndView;
//        } else {
//            Date date1 = linkSecret.getExpireDate();
//            //表示链接永久有效
//            if (date1 == null) {
//                ModelAndView modelAndView = new ModelAndView("shareSecret");
//                modelAndView.addObject("link", link);
//                return modelAndView;
//            } else {
//                //得到long类型当前时间
//                long dataTemp = System.currentTimeMillis();
//                Date date2 = new Date(dataTemp);
//                if (date1.before(date2)) {
//                    //不能下载
//                    ModelAndView modelAndView = new ModelAndView("errorPage");
//                    modelAndView.addObject("message", link + "链接失效");
//                    //删除本地共享文件夹的内容
//                    FileUtil.delete(downloadLink);
//                    return modelAndView;
//                } else {
//                    ModelAndView modelAndView = new ModelAndView("shareSecret");
//                    modelAndView.addObject("link", link);
//                    return modelAndView;
//                }
//            }
//
//        }
//    }
//
//    //定位到分享页面
//    @RequestMapping("/share")
//    public ModelAndView share(String link, HttpServletRequest request) {
//
//        logger.warn("zzc:" + link);
//        EncryptUtil des = null;
//        String linkDecoder = "";
//        try {
//            des = new EncryptUtil(key, "utf-8");
//            linkDecoder = des.decode(link);
//        } catch (Exception e) {
//            logger.error("Exception:", e);
//        }
//        ModelAndView modelAndView = new ModelAndView("share");
//        modelAndView.addObject("link", link);
//        modelAndView.addObject("linkDecoder", linkDecoder);
//        User user = (User) request.getSession().getAttribute("user");
//        if (user != null) {
//            modelAndView.addObject("author", user.getUserName());
//        }
//        return modelAndView;
//    }
//
//    @RequestMapping("/errorPage")
//    public ModelAndView errorPage(String message) {
//        logger.warn("zzc:" + message);
//        ModelAndView modelAndView = new ModelAndView("errorPage");
//        modelAndView.addObject("message", message);
//        return modelAndView;
//
//    }
//
//    @RequestMapping(value = "/sharefileSecret", produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public ResponseMsg shareFileSecret(String link, String secret, HttpServletRequest request,
//        HttpServletResponse response) {
//        logger.warn("执行sharefileSecret接口");
//        String downloadLink = "";
//        Map<Object, String> map = new HashMap<>();
//        if (!link.isEmpty()) {
//            //            downloadLink= fileService.fileShareCodeDecode(link);
//            downloadLink = link;
//        }
//        //在数据库查找这个链接
//        LinkSecret linkSecret = linkSecretService.findLinkSecretBysecretLink(downloadLink);
//        String secret1 = linkSecret.getSecret();
//        if (secret1.toLowerCase().equals(secret.toLowerCase())) {
//            linkSecretService.addOneToDownloadNum(linkSecret);
//            logger.warn("密码正确！");
//            logger.warn("codeArray[1]路径：" + link);
//            ResponseMsg responseMsg = new ResponseMsg();
//            responseMsg.setSuccess(true);
//            responseMsg.setMsg(link);
//            return responseMsg;
//        } else {
//            logger.warn("密码不正确！");
//            ResponseMsg responseMsg = new ResponseMsg();
//            responseMsg.setSuccess(false);
//            responseMsg.setMsg("密码不正确！");
//            return responseMsg;
//        }
//    }
//
//    // 文件提取码生成,当再次分享同一个文件，只更新过期时间
//    @RequestMapping(value = "/generateShareLink", produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public ResponseMsg generateShareLink(@RequestParam String expireDay, String fileName, String path,
//                                         HttpServletRequest request) {
//        String expireDayString = expireDay;
//        int expireDays = 3;
//        if (expireDayString != null) {
//            if (expireDayString.equals("永久有效")) {
//                expireDays = -1;
//            } else {
//                expireDays = Integer.parseInt(expireDayString);
//            }
//        }
//        if (path == null) {
//            path = "/";
//        }
//        ResponseMsg j = new ResponseMsg();
//        if (fileName.isEmpty() || path.isEmpty()) {
//            j.setMsg("文件夹名字为空！");
//            return j;
//        }
//        // 获取用户名
//        String userName = WebUtil.getUserNameByRequest(request);
//        String filePathAndName = userName + "/" + path + "/" + fileName;
//        filePathAndName = StringUtil.stringSlashToOne(filePathAndName);
//        logger.warn("filePathAndName:" + filePathAndName);
//        String b = fileService.fileShareCodeEncode(filePathAndName);
//        String secret = "";
//        File file = new File(fileRootPath + "/" + filePathAndName);
//        String localLink = "/data/share/" + filePathAndName;
//        //存入数据库
//        LinkSecret linkSecret = linkSecretService.findLinkSecretByLocalLinkAndUserName(localLink, userName);
//        if (linkSecret == null) {
//            //设置提取密码
//            secret = PassWordCreate.createPassWord(secretLen);
//            linkSecret = new LinkSecret();
//            linkSecret.setLocalLink(localLink);
//            linkSecret.setSecret(secret);
//            linkSecret.setUserName(userName);
//            linkSecret.setDownloadNum(0);
//            linkSecret.setFileName(fileName);
//
//            if (expireDays != -1) {
//                Calendar cal = Calendar.getInstance();
//                cal.add(Calendar.DATE, expireDays);
//                Date date = cal.getTime();
//                linkSecret.setExpireDate(date);
//            }
//
//            logger.warn("b:" + b);
//            linkSecret.setSecretLink(b);
//            linkSecretService.save(linkSecret);
//            //test
//            LinkSecret linkSecretTemp = linkSecretService.findLinkSecretByLocalLinkAndUserName(localLink, userName);
//            logger.warn(linkSecretTemp.getSecretLink());
//            //test
//
//        } else {
//            if (expireDays != -1) {
//                Calendar cal = Calendar.getInstance();
//                cal.add(Calendar.DATE, expireDays);
//                Date date = cal.getTime();
//                linkSecretService.updateExpireDay(linkSecret, date);
//                linkSecretService.updateShareDate(linkSecret, new Date());
//                secret = linkSecret.getSecret();
//            } else {
//                linkSecretService.updateExpireDay(linkSecret, null);
//                secret = linkSecret.getSecret();
//                linkSecretService.updateShareDate(linkSecret, new Date());
//            }
//        }
//        if (SystemUtil.isWindows()) {
//            b = linkSecret.getSecretLink() + "##" + secret;
//        } else {
//            b = b + "##" + secret;
//        }
//        if (!"null".equals(b)) {
//            j.setSuccess(true);
//            j.setMsg(b);
//        } else {
//            j.setMsg("提取码生成失败！");
//        }
//        return j;
//    }
//
//    // 文件、文件夹 移动 文件夹移动时fileName=@dir@
//    @RequestMapping(value = "/filemove", produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public ResponseMsg fileMove(String fileName, String oldPath, String newPath, HttpServletRequest request) {
//        if (fileName == null) {
//            fileName = "@dir@";
//        }
//        ResponseMsg j = new ResponseMsg();
//        if (oldPath.isEmpty() || newPath.isEmpty()) {
//            j.setMsg("路径名字为空！");
//            return j;
//        }
//        // 获取用户名
//        String userName = WebUtil.getUserNameByRequest(request);
//        // 移动文件
//        boolean b = fileService.userFileDirMove(fileName, oldPath, newPath, userName);
//        if (b) {
//            j.setSuccess(true);
//            j.setMsg("移动成功！");
//        } else {
//            j.setMsg("移动失败！");
//        }
//        return j;
//    }

    @ApiOperation("上传前检查")
    @PostMapping("check")
    public InvokeResult checkChunk(HttpServletRequest request) {
        return fileService.checkChunk(request);
    }

//    /**
//     * 所有分块上传完成后合并
//     *
//     * @param request
//     * @param path
//     */
//    @RequestMapping(value = "/merge")
//    @ResponseBody
//    public void mergeChunks(HttpServletRequest request, String path) throws InterruptedException {
//        fileService.mergeChunks(request, path);
//    }
//
//    /**
//     * 调用后台转码的功能
//     *
//     * @param request
//     * @param filepath
//     */
//    @PostMapping(value = "/videoconvert")
//    @ResponseBody
//    public ResponseMsg videoconvert(HttpServletRequest request, String filepath) {
//        ResponseMsg j = new ResponseMsg();
//        if (filepath.isEmpty()) {
//            j.setMsg("源文件路径名字为空！");
//            return j;
//        }
//        String ffmepgpath = fileRootPath + "/ffmpeg/bin";
//        Map<String, Object> retmap = ConvertVideo.convertVideo(filepath, ffmepgpath);
//        String retstr = (String) retmap.get("flag");
//
//        j.setMsg((String) retmap.get("path"));
//        // 成功true并有路径，失败false也有路径，转码中false并且没路径
//        if ("complete".equals(retstr)) {
//            j.setSuccess(true);
//        } else if ("transcoding".equals(retstr)) {
//            // 这里转码中属于bug情况
//            j.setMsg("");
//        }
//        return j;
//    }

    @ApiOperation("获取云盘服务器所在盘磁盘空间大小")
    @GetMapping(value = "/getSpaceSize")
    public InvokeResult getSpaceSize(HttpServletRequest request) {
        return fileService.getSpaceSize(request);
    }

}
