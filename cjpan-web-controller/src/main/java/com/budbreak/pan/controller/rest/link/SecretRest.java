package com.budbreak.pan.controller.rest.link;

import com.budbreak.pan.common.*;
import com.budbreak.pan.controller.assembler.link.SecretAssembler;
import com.budbreak.pan.controller.command.link.SecretCreateCommand;
import com.budbreak.pan.controller.command.link.SecretUpdateCommand;
import com.budbreak.pan.entity.link.Secret;
import com.budbreak.pan.vo.link.SecretVO;
import com.budbreak.pan.service.link.SecretService;
import com.budbreak.pan.manager.link.SecretManager;
import com.budbreak.pan.mapper.link.SecretMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description: 代码生成器自动生成
 * @author:
 * @Createed Date: 2020-4-24 14:46:16
 * @ModificationHistory: Who  When  What
 * ---------     -------------   --------------------------------------
 **/
@RestController
@RequestMapping("api/v1/link/secret")
@Api(value = "secretService", description = "secret相关AIP")
public class SecretRest  {

    @Autowired
    private SecretMapper secretMapper;

    @Autowired
    private SecretService secretService;

    @Autowired
    private SecretManager secretManager;

    @PostMapping("add")
    @ApiOperation(value = "add Secret")
    public InvokeResult add(@RequestBody @Valid SecretCreateCommand command) {
        Secret entity = SecretAssembler.toSecret(command);
        secretService.addEntity(entity);
        return InvokeResult.success();
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "delect Secret by id")
    public InvokeResult delete(@RequestBody CommonDTO commonDTO){
        secretService.removeByIds(Arrays.asList(commonDTO.getIds()));
        return InvokeResult.success();
    }

    @PutMapping("update")
    @ApiOperation(value = "update Secret by id")
    public InvokeResult update(@RequestBody @Valid SecretUpdateCommand command) {
        Secret entity = SecretAssembler.toSecret(command);
        secretService.updateEntity(entity);
        return InvokeResult.success();
    }

    @GetMapping("detail")
    @ApiOperation(value = "get Secret detail by id")
    public InvokeResult detail(@RequestParam Integer id) {
        SecretVO vo = secretManager.selectDetailVoById(id);
        return InvokeResult.success(vo);
    }

    @GetMapping("page")
    @ApiOperation(value = "get Secret page")
    public IPage<SecretVO> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10")Integer pageSize){

        PageResult page = new PageResult(pageNum, pageSize);

        Map<String, Object> map = new HashMap<>(4);

        return secretManager.getPage(page, map);
    }
}
