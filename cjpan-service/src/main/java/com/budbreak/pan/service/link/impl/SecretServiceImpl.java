package com.budbreak.pan.service.link.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.budbreak.pan.common.InvokeResult;
import com.budbreak.pan.entity.link.Secret;
import com.budbreak.pan.mapper.link.SecretMapper;
import com.budbreak.pan.service.WebUtil;
import com.budbreak.pan.service.link.SecretService;
import com.budbreak.pan.vo.link.SecretVO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @Description: TODO
 * @author:
 * @Createed Date: 2018/11/21-10:26
 * @ModificationHistory: Who  When  What
 * ---------     -------------   --------------------------------------
 **/

@Service
@Transactional(rollbackFor = Exception.class)
public class SecretServiceImpl extends ServiceImpl<SecretMapper, Secret> implements SecretService {

    @Autowired
    SecretMapper secretMapper;

    @Value("${fileRootPath}")
    public String fileRootPath;

    @Override
    public void addEntity(Secret entity) {
        secretMapper.insert(entity);
    }

    @Override
    public void updateEntity(Secret entity) {
        secretMapper.updateById(entity);
    }

    @Override
    public SecretVO selectSecretBySecretLink(String link) {
        return secretMapper.selectSecretBySecretLink(link);
    }

    @Override
    public void addOneToDownloadNum(SecretVO linkSecret) {
        int downloadNum = linkSecret.getDownloadNum() + 1;
        secretMapper.updateDownloadNum(downloadNum, linkSecret.getLocalLink());
    }

    @Override
    public SecretVO selectLinkSecretByLocalLinkAndUserName(String localLink, String userName) {
        return secretMapper.selectLinkSecretByLocalLinkAndUserName(localLink, userName);
    }

    @Override
    public void updateSecret(Map map) {
        secretMapper.updateSecret(map);
    }

    @Override
    public InvokeResult shareToMyPan(HttpServletRequest request, String path, String link) {
        boolean b = false;
        String userNameByRequest = WebUtil.getUserNameByRequest(request);
        if (userNameByRequest == null) {
            return InvokeResult.failure("未登录，请登录后保存！");
        } else {
            if (path == null) {
                path = "/";
            }
            b = copyFileToMyPan(userNameByRequest, link, path);
            if (b == false) {
                return InvokeResult.failure(408,"文件已被删除！");
            } else {
                return InvokeResult.success("保存成功！");
            }
        }
    }

    private boolean copyFileToMyPan(String userName, String localLink, String path) {
        boolean b = false;
        //share文件所在的地方
        localLink = localLink.replace("/data/", fileRootPath);
        File oldfile = new File(localLink);
        String[] msg = localLink.split("/");
        String saveFileName = oldfile.getName();
        String saveFilePath = fileRootPath + userName + "/" + path;
        File newfileDir = new File(saveFilePath);
        if (!newfileDir.exists()) {
            newfileDir.mkdir();
        }
        try {
            if (oldfile.exists()) {
                FileUtils.copyInputStreamToFile(new FileInputStream(oldfile), new File(saveFilePath, saveFileName));
                b = true;
            } else {
                //TODO
                b = false;
            }
        } catch (IOException e) {

            return false;
        }
        return b;
    }
}

