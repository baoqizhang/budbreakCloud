package com.budbreak.pan.service.pan.impl;

import com.alibaba.fastjson.JSONObject;
import com.budbreak.pan.common.*;
import com.budbreak.pan.entity.link.Secret;
import com.budbreak.pan.model.FileMsg;
import com.budbreak.pan.service.WebUtil;
import com.budbreak.pan.service.link.SecretService;
import com.budbreak.pan.service.pan.FileService;
import com.budbreak.pan.vo.link.SecretVO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.budbreak.pan.common.StringUtil.stringSlashToOne;

/**
 * @author baoqi
 * @date 2020/4/27 12:04
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FileServiceImpl implements FileService {

    @Autowired
    private SecretService secretService;

    @Value("${fileRootPath}")
    public String fileRootPath;

    @Value("${tempPath}")
    public String tempPath;

    @Value("${size}")
    public  int size;

    @Value("${secretLen}")
    private  int secretLen;

    @Value("${key}")
    private  String key;


    @Override
    public InvokeResult getSpaceSize(HttpServletRequest request) {
        // 普通用户限制80G，guest用户限制40G，
        String username = WebUtil.getUserNameByRequest(request);
        Map<String, String> spaceMap = new HashMap<>();
        spaceMap.put("totalSpace", "80");
        double totalSpace = 80;
        if ("guest".equals(username)) {
            spaceMap.put("totalSpace", "40");
            totalSpace = 40;
        }
        long dirlength = SystemUtil.getDirSpaceSize(fileRootPath + username);
        double dirlengthDouble = dirlength / 1024.0 / 1024 / 1024;
        String usedeSpace = String.format("%.2f", dirlengthDouble);
        String freeSpace = String.format("%.2f", totalSpace - Double.valueOf(usedeSpace));
        spaceMap.put("freeSpace", freeSpace);
        return InvokeResult.success(JSONObject.toJSONString(spaceMap));
    }

    @Override
    public List<FileMsg> fileConvert(List<FileMsg> fileMsgList) {

        List<FileMsg> fileMsgLists = fileMsgList;
        // 判断文件转码情况
        for (FileMsg fileMsg : fileMsgLists) {
            // 跳过文件夹
            if (fileMsg.getSize().equals("Directory")) {
                continue;
            }
            // 正常文件
            int suffixidx = (int) StringUtil.getfilesuffix(fileMsg.getName(), true);
            String suffix = fileMsg.getName().substring(suffixidx);
            if (suffix.equals("mkv") || suffix.equals("rmvb") || suffix.equals("avi") || suffix.equals("wmv")
                    || suffix.equals("3gp") || suffix.equals("rm")) {
                // 取非文件夹的所有文件名
                List<String> namelist = fileMsgList.stream()
                        .filter(f -> !f.getSize().equals("Directory"))
                        .map(FileMsg::getName)
                        .collect(Collectors.toList());
                // 含有转码文件的情况下
                if (namelist.contains(fileMsg.getName().substring(0, suffixidx) + "mp4")) {
                    Map<String, Object> map = FfmpegUtil.ffmpegTaskMap.get(fileMsg.getLink());
                    // 含有转码文件且有转码记录
                    if (null != map) {
                        String transcode = (String) map.get("flag");
                        fileMsg.setTranscode(transcode);
                    }
                    // 含有转码文件但没有转码记录，说明之前已完成转码
                    else {
                        fileMsg.setTranscode("complete");
                    }
                }
                // 没有转码文件说明可转码
                else {
                    fileMsg.setTranscode("transcodable");
                }
            }
        }
        return fileMsgLists;
    }

    @Override
    public List<FileMsg> userFileList(HttpServletRequest request, String path) {
        if (path == null) {
            path = "/";
        }
        String userName = WebUtil.getUserNameByRequest(request);
        List<FileMsg> fileMsgList = new ArrayList<>();
        // 拉取文件列表-本地磁盘
        String webSaveFilePath = fileRootPath + userName + "/" + path;
        File files = new File(webSaveFilePath);
        if (!files.exists()) {
            return fileMsgList;
        }
        File[] tempList = files.listFiles();
        if (tempList == null) {
            return fileMsgList;
        }
        for (File file : tempList) {
            if (file.isFile()) {
                FileMsg fileMsg = new FileMsg();
                // 获取文件名和下载地址
                String link = file.toString().replace("\\", "/");
                String[] nameArr = link.split("/");
                String name = nameArr[nameArr.length - 1];
                link = link.replace(fileRootPath, "/data/");
                link = link.replace("/root/pan/", "/data/");
                String size = FileUtil.fileSizeToString(file.length());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String lastModTime = formatter.format(file.lastModified());
                // 赋值到json
                fileMsg.setName(name);
                fileMsg.setLink(link);
                fileMsg.setSize(size);
                fileMsg.setTime(lastModTime);
                if (FileUtil.isMp4(name)) {
                    fileMsg.setType("mp4");
                } else if (FileUtil.isVideo(name)) {
                    fileMsg.setType("video");
                } else {
                    fileMsg.setType("file");
                }
                fileMsgList.add(fileMsg);
            } else {
                FileMsg fileMsg = new FileMsg();
                String link = file.toString().replace("\\", "/");
                String[] nameArr = link.split("/");
                String name = nameArr[nameArr.length - 1];
                String dirPath = link.replace(fileRootPath + userName, "");
                if (!name.equals("userIcon")) {
                    fileMsg.setName(name);
                    fileMsg.setSize("Directory");
                    fileMsg.setType("dir");
                    fileMsg.setLink(dirPath);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String lastModTime = formatter.format(file.lastModified());
                    fileMsg.setTime(lastModTime);
                    fileMsgList.add(fileMsg);
                }
            }
        }
        //排序
        ListUtil.listSort(fileMsgList);
        return fileMsgList;
    }

    @Override
    public InvokeResult dirCreate(HttpServletRequest request, String dirName, String path) {
        if (path == null) {
            path = "/";
        }
        if (dirName.isEmpty() || path.isEmpty()) {
            return InvokeResult.failure("文件夹名为空！");
        }
        // 获取用户名
        String userName = WebUtil.getUserNameByRequest(request);
        if (!SystemUtil.isWindows()) {
            path = "/pan/" + userName + path;
        } else {
            path = fileRootPath + userName + path;
        }
        // 重命名文件
        File file = new File(path + "/" + dirName);
        if (!file.mkdir()) {
            return InvokeResult.failure("文件夹创建失败");
        }
        return InvokeResult.success();
    }

    @Override
    public InvokeResult upload(MultipartFile file, HttpServletRequest request, String path) {
        if (path == null) {
            path = "/";
        }
        if (file.isEmpty()) {
            return InvokeResult.failure("请选择要上传的文件！");
        }
        // 获取用户名
        String userName = WebUtil.getUserNameByRequest(request);
        // 上传文件
        boolean b = false;
        // 服务器上传的文件所在路径
        String saveFilePath = fileRootPath + userName + "/" + path;
        // 判断文件夹是否存在-建立文件夹
        File filePathDir = new File(saveFilePath);
        if (!filePathDir.exists()) {
            filePathDir.mkdir();
        }
        // 获取上传文件的原名 例464e7a80_710229096@qq.com.zip
        String saveFileName = file.getOriginalFilename();
        // 上传文件到-磁盘
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(saveFilePath, saveFileName));
            b = true;
        } catch (Exception e) {
            return InvokeResult.error();
        }
        if (b) {
            return InvokeResult.success("上传成功");
        } else {
            return InvokeResult.failure("上传失败");
        }
    }

    @Override
    public InvokeResult fileDelete(String fileName, String path, HttpServletRequest request) {
        if (path == null) {
            path = "/";
        }
        if (fileName.isEmpty()) {
            return InvokeResult.failure("文件名为空");
        }
        // 获取用户名
        String userName = WebUtil.getUserNameByRequest(request);
        // 删除文件
        Boolean[] b = userFileDelete(fileName, userName, path);
        boolean flag = true;
        for (int i = 0; i < b.length; i++) {
            if (b[i] == false) {
                flag = false;
            }
        }
        if (flag) {
            return InvokeResult.success();
        } else {
            return InvokeResult.failure("删除失败");
        }
    }

    @Override
    public InvokeResult download(String fileName, String path, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>(2, 1);
        if (path == null) {
            path = "/";
        }
        if (fileName.isEmpty()) {
            return InvokeResult.failure("文件名为空！");
        }
        // 获取用户名
        String userName = WebUtil.getUserNameByRequest(request);
        // 下载文件，获取下载路径,这个是 个映射的路径
        // 服务器下载的文件所在的本地路径的文件夹
        String saveFilePath = fileRootPath + userName + "/" + path;
        // 判断文件夹是否存在-建立文件夹
        File filePathDir = new File(saveFilePath);
        if (!filePathDir.exists()) {
            filePathDir.mkdir();
        }
        // 本地路径
        saveFilePath = saveFilePath + "/" + fileName;
        String link = saveFilePath.replace(fileRootPath, "/data/");
        link = stringSlashToOne(link);
        try {
            //这里校验要填真实的路经
            String newLink = link.replace("/data/", fileRootPath);
            String[] md5Array = FileSplit.splitBySizeSubSection(newLink, size,
                    fileRootPath + "/tempMd5/" + userName + "/");
            map.put("md5Array", md5Array);
        } catch (Exception e) {
            return InvokeResult.error();
        }
        if (!link.isEmpty()) {
            map.put("link", link);
            return InvokeResult.success(map);
        } else {
            return InvokeResult.failure("下载失败！");
        }
    }

    @Override
    public void uploadServlet(HttpServletRequest request, MultipartFile file, String path) {
        String chunk = request.getParameter("chunk");
        String fileName = file.getOriginalFilename();
        String userName = WebUtil.getUserNameByRequest(request);
        MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> files = Murequest.getFileMap();
        if (null != files && !files.isEmpty()) {
            for (MultipartFile item : files.values()) {
                String tempDir = FileUtil.getTempDir(tempPath, userName, fileName);
                tempDir = stringSlashToOne(tempDir);
                File dir = new File(tempDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                // 创建分片文件并保存
                File chunkFile = new File(tempDir + "/" + chunk);
                try {
                    chunkFile.createNewFile();
                    item.transferTo(chunkFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public InvokeResult checkChunk(HttpServletRequest request) {
        String fileName = request.getParameter("fileName");
        String chunk = request.getParameter("chunk");
        String chunkSize = request.getParameter("chunkSize");
        String userName = WebUtil.getUserNameByRequest(request);
        String tempDir = FileUtil.getTempDir(tempPath, userName, fileName);
        tempDir = stringSlashToOne(tempDir);
        File chunkFile = new File(tempDir + "/" + chunk);
        boolean result = false;
        // 分片文件是否存在，尺寸是否一致
        if (chunkFile.exists() && chunkFile.length() == Integer.parseInt(chunkSize)) {
            return InvokeResult.success(chunk);
        }
        return InvokeResult.error();
    }

    @Override
    public void mergeChunks(HttpServletRequest request, String path) {
        if (path == null) {
            return ;
        }
        String userName = WebUtil.getUserNameByRequest(request);
        String fileName = request.getParameter("fileName");

        boolean b = false;
        String savePath = fileRootPath + userName + "/" + path;
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        String tempDirPath = FileUtil.getTempDir(tempPath, userName, fileName);
        File tempDir = new File(tempDirPath);
        // 获得分片文件列表
        File[] fileArray = tempDir.listFiles(new FileFilter() {
            // 只需要文件
            @Override
            public boolean accept(File pathname) {
                if (pathname.isDirectory()) {
                    return false;
                } else {
                    return true;
                }
            }
        });
        //        logger.warn("【要合成的文件有】："+fileArray);
        //       while (fileArray==null){
        //       }
        // 转成集合进行排序后合并文件
        List<File> fileList = new ArrayList<File>(Arrays.asList(fileArray));
        Collections.sort(fileList, new Comparator<File>() {
            // 按文件名升序排列
            @Override
            public int compare(File o1, File o2) {
                if (Integer.parseInt(o1.getName()) < Integer.parseInt(o2.getName())) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        // 目标文件
        File outfile = new File(savePath + File.separator + fileName);
        try {
            outfile.createNewFile();
        } catch (IOException e) {
            b = false;
        }

        // 执行合并操作
        FileChannel outChannel = null;
        FileChannel inChannel;
        try {
            outChannel = new FileOutputStream(outfile).getChannel();
            for (File file1 : fileList) {
                inChannel = new FileInputStream(file1).getChannel();
                inChannel.transferTo(0, inChannel.size(), outChannel);
                inChannel.close();
                file1.delete();
            }
            outChannel.close();
        } catch (FileNotFoundException e) {
            b = false;
        } catch (IOException e) {
            b = false;
        }
        // 删除临时文件夹 根目录/temp/userName/fileName
        File tempFileDir = new File(tempPath + File.separator + userName + File.separator + fileName);
        FileUtil.deleteDir(tempFileDir);
    }

    @Override
    public List<FileMsg> search(String key, String path, HttpServletRequest request) {
        if (path == null) {
            path = "/";
        }
        // 获取用户名
        String userName = WebUtil.getUserNameByRequest(request);
        List<FileMsg> fileMsgList = new ArrayList<>();
        // 拉取文件列表-本地磁盘
        String webSaveFilePath = fileRootPath + userName + "/" + path;
        File files = new File(webSaveFilePath);
        if (!files.exists()) {
            files.mkdir();
        }
        //            File[] tempList = files.listFiles();
        List<File> tempList = new ArrayList<>();
        tempList = SearchFileByKey.searchFile(webSaveFilePath, key, false, tempList);
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).isFile()) {
                //                logger.warn("用户：" + userName + " 文件：" + tempList[i]);
                FileMsg fileMsg = new FileMsg();
                // 获取文件名和下载地址
                String link = tempList.get(i).toString().replace("\\", "/");
                String[] nameArr = link.split("/");
                String name = nameArr[nameArr.length - 1];
                link = link.replace(fileRootPath, "/data/");
                link = link.replace("/root/pan/", "/data/");
                String size = FileUtil.fileSizeToString(tempList.get(i).length());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String lastModTime = formatter.format(tempList.get(i).lastModified());
                // 赋值到json
                fileMsg.setName(name);
                fileMsg.setLink(link);
                fileMsg.setSize(size);
                fileMsg.setTime(lastModTime);
                fileMsgList.add(fileMsg);
            } else {
                FileMsg fileMsg = new FileMsg();
                String link = tempList.get(i).toString().replace("\\", "/");
                String[] nameArr = link.split("/");
                String name = nameArr[nameArr.length - 1];
                if (!name.equals("userIcon")) {
                    fileMsg.setLink(link);
                    fileMsg.setName(name);
                    fileMsg.setSize("Directory");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String lastModTime = formatter.format(tempList.get(i).lastModified());
                    fileMsg.setTime(lastModTime);
                    fileMsgList.add(fileMsg);
                }
            }
        }
        List<FileMsg> fileMsgs = fileConvert(fileMsgList);
        return fileMsgs;
    }

    @Override
    public InvokeResult fileReName(String oldName, String newName, String path, HttpServletRequest request) {
        if (path == null) {
            path = "/";
        }
        if (oldName.isEmpty() || newName.isEmpty()) {
            return InvokeResult.failure("文件名为空！");
        }
        // 获取用户名
        String userName = WebUtil.getUserNameByRequest(request);
        // 重命名文件
        // 重命名-本地磁盘文件
        String oldNameWithPath;
        String newNameWithPath;
        if ("@dir@".equals(oldName)) {
            oldNameWithPath = stringSlashToOne(fileRootPath + userName + "/" + path);
            newNameWithPath =
                    oldNameWithPath.substring(0, (int) StringUtil.getfilesuffix(oldNameWithPath, true, "/")) + "/" + newName;
            newNameWithPath = stringSlashToOne(newNameWithPath);
        } else {
            oldNameWithPath = stringSlashToOne(fileRootPath + userName + "/" + path + "/" + oldName);
            newNameWithPath = stringSlashToOne(fileRootPath + userName + "/" + path + "/" + newName);
        }
        boolean b = FileUtil.renameFile(oldNameWithPath, newNameWithPath);
        String saveFilePath = fileRootPath + userName + "/" + path;
        String NameWithPath = saveFilePath + "/" + oldName;
        File file = new File(NameWithPath);
        if (b) {
            return InvokeResult.success("重命名成功！");
        } else if (!file.exists()) {
            return InvokeResult.failure("没有重命名的权限！");
        } else {
            return InvokeResult.failure("重命名失败！");
        }
    }

    @Override
    public InvokeResult shareCallBack(String[] arr, String[] localLink) {
        String userName = localLink[3];
        //            String userName = arr[0];
        String fileName = arr[arr.length - 1];
        arr[arr.length - 1] = "";
        //            String path = StringUtils.join(arr, "/");
        String path = userName + "/";
        if (localLink.length > 5) {
            for (int k = 4; k < localLink.length - 1; k++) {
                path = path + localLink[k] + "/";
            }
        }

        // 服务器下载的文件所在的本地路径的文件夹
        String saveFilePath = fileRootPath + "share" + "/" + path;
        //            String saveFilePath = fileRootPath + "/" + path;
        // 判断文件夹是否存在-建立文件夹
        File filePathDir = new File(saveFilePath);
        if (!filePathDir.exists()) {
            // mkdirs递归创建父目录
            boolean b = filePathDir.mkdirs();
        }
        saveFilePath = fileRootPath + "/" + path + "/" + fileName;
        String link = saveFilePath.replace(fileRootPath, "/data/");
        link = stringSlashToOne(link);
        // 返回下载路径
        return InvokeResult.success(link);
}

    @Override
    public InvokeResult generateShareLink(String expireDay, String fileName, String path, HttpServletRequest request) {
        String expireDayString = expireDay;
        int expireDays = 3;
        if (expireDayString != null) {
            if (expireDayString.equals("永久有效")) {
                expireDays = -1;
            } else {
                expireDays = Integer.parseInt(expireDayString);
            }
        }
        if (path == null) {
            path = "/";
        }
        if (fileName.isEmpty() || path.isEmpty()) {
            return InvokeResult.failure("文件夹名为空！");
        }
        // 获取用户名
        String userName = WebUtil.getUserNameByRequest(request);
        String filePathAndName = userName + "/" + path + "/" + fileName;
        filePathAndName = StringUtil.stringSlashToOne(filePathAndName);
        String b = fileShareCodeEncode(filePathAndName);
        String secret = "";
        File file = new File(fileRootPath + "/" + filePathAndName);
        String localLink = "/data/share/" + filePathAndName;
        //存入数据库
        SecretVO linkSecrets = secretService.selectLinkSecretByLocalLinkAndUserName(localLink, userName);
        Secret linkSecret = new Secret();
        Map map = new HashMap(4,1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (linkSecrets == null) {
            //设置提取密码
            secret = PassWordCreate.createPassWord(secretLen);
            linkSecret.setLocalLink(localLink);
            linkSecret.setSecret(secret);
            linkSecret.setUserName(userName);
            linkSecret.setDownloadNum(0);
            linkSecret.setFileName(fileName);
            if (expireDays != -1) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, expireDays);
                Date date = cal.getTime();
                linkSecret.setExpireDate(date);
            }

            linkSecret.setSecretLink(b);
            secretService.addEntity(linkSecret);
        } else {
            if (expireDays != -1) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, expireDays);
                Date date = cal.getTime();
                linkSecret.setId(linkSecrets.getId());
                linkSecret.setExpireDate(date);
                linkSecret.setShareDate(new Date());
                linkSecret.setSecretLink(linkSecrets.getSecretLink());
                secretService.updateById(linkSecret);
                secret = linkSecrets.getSecret();
            } else {
                linkSecret.setId(linkSecrets.getId());
                linkSecret.setExpireDate(null);
                linkSecret.setShareDate(new Date());
                linkSecret.setSecretLink(linkSecrets.getSecretLink());
                secretService.updateById(linkSecret);
                secret = linkSecrets.getSecret();
            }
        }
        if (SystemUtil.isWindows()) {
            b = linkSecret.getSecretLink() + "##" + secret;
        } else {
            b = b + "##" + secret;
        }
        if (!"null".equals(b)) {
            return InvokeResult.success(b);
        } else {
            return InvokeResult.failure("提取码生成失败");
        }
    }

    @Override
    public InvokeResult fileMove(String fileName, String oldPath, String newPath, HttpServletRequest request) {
        if (fileName == null) {
            fileName = "@dir@";
        }
        if (oldPath.isEmpty() || newPath.isEmpty()) {
          return InvokeResult.failure("路径名为空！");
        }
        // 获取用户名
        String userName = WebUtil.getUserNameByRequest(request);
        // 移动-本地磁盘文件
        String saveFilePath = fileRootPath + userName + "/";
        String lfilename = ("@dir@".equals(fileName) ? "" : "/" + fileName);
        String oldNameWithPath = stringSlashToOne(saveFilePath + oldPath + lfilename);
        String tmpnewfilename = "@dir@".equals(fileName) ?
                (String) StringUtil.getfilesuffix(oldNameWithPath, false, "/", false) : "";
        String newNameWithPath = stringSlashToOne(saveFilePath + newPath + lfilename + tmpnewfilename);
        boolean b = FileUtil.renameFile(oldNameWithPath, newNameWithPath);
        if (b) {
            return InvokeResult.success();
        } else {
            return InvokeResult.failure("移动失败");
        }
    }

    @Override
    public InvokeResult videoConvert(String filepath) {
        if (filepath.isEmpty()) {
            return InvokeResult.failure("源文件路径名字为空！");
        }
        String ffmepgpath = fileRootPath + "/ffmpeg/bin";
        Map<String, Object> retmap = ConvertVideo.convertVideo(filepath, ffmepgpath);
        String retstr = (String) retmap.get("flag");

        // 成功true并有路径，失败false也有路径，转码中false并且没路径
        if ("complete".equals(retstr)) {
            return InvokeResult.success(retmap.get("path"));
        } else if ("transcoding".equals(retstr)) {
            // 这里转码中属于bug情况
            return InvokeResult.success("");
        }
        return InvokeResult.error();
    }

    public Boolean[] userFileDelete(String fileName, String userName, String path) {
        //解析fileName: 以$$符号分割
        String[] fileNames = null;
        if (fileName.contains("$$")) {
            fileNames = fileName.split("\\$\\$");
        } else {
            fileNames = new String[1];
            fileNames[0] = fileName;
        }
        Boolean[] b = new Boolean[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            // 删除-本地文件
            String saveFilePath = fileRootPath + userName + "/" + path;
            File file = new File(saveFilePath);
            File[] listFiles = file.listFiles();
            boolean b1 = false;
            //判断是否是文件夹
            if (fileName.equals("@dir@")) {
                //是文件夹
                b1 = FileUtil.delete(saveFilePath);
            } else {
                b1 = FileUtil.delete(saveFilePath + "/" + fileNames[i]);
            }

            //                if (!b1){
            //                    FileSave fileSave=saveService.findFileSaveByUserNameAndFileName(userName,
            //                    fileNames[i]);
            //                    saveService.delete(fileSave);
            //                    b1=true;
            //                }
            b[i] = b1;

        }
        return b;
    }

    public String fileShareCodeEncode(String filePathAndName) {
        EncryptUtil des;
        try {
            des = new EncryptUtil(key, "utf-8");
            return des.encode(filePathAndName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "null";
    }
}
