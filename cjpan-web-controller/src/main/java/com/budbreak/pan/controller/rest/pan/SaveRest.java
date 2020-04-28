package com.budbreak.pan.controller.rest.pan;

import com.budbreak.pan.common.*;
import com.budbreak.pan.controller.assembler.pan.SaveAssembler;
import com.budbreak.pan.controller.command.pan.SaveCreateCommand;
import com.budbreak.pan.controller.command.pan.SaveUpdateCommand;
import com.budbreak.pan.entity.pan.Save;
import com.budbreak.pan.vo.pan.SaveVO;
import com.budbreak.pan.service.pan.SaveService;
import com.budbreak.pan.manager.pan.SaveManager;
import com.budbreak.pan.mapper.pan.SaveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @Description: 代码生成器自动生成
 * @author:
 * @Createed Date: 2020-4-24 14:46:16
 * @ModificationHistory: Who  When  What
 * ---------     -------------   --------------------------------------
 **/
@RestController
@RequestMapping("api/v1/pan/save")
@Api(value = "saveService", description = "save相关AIP")
public class SaveRest  {

    @Autowired
    private SaveMapper saveMapper;

    @Autowired
    private SaveService saveService;

    @Autowired
    private SaveManager saveManager;

    @PostMapping("add")
    @ApiOperation(value = "add Save")
    public InvokeResult add(@RequestBody @Valid SaveCreateCommand command) {
        Save entity = SaveAssembler.toSave(command);
        saveService.addEntity(entity);
        return InvokeResult.success();
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "delect Save by id")
    public InvokeResult delete(@RequestBody CommonDTO commonDTO){
        saveService.removeByIds(Arrays.asList(commonDTO.getIds()));
        return InvokeResult.success();
    }

    @PutMapping("update")
    @ApiOperation(value = "update Save by id")
    public InvokeResult update(@RequestBody @Valid SaveUpdateCommand command) {
        Save entity = SaveAssembler.toSave(command);
        saveService.updateEntity(entity);
        return InvokeResult.success();
    }

    @GetMapping("detail")
    @ApiOperation(value = "get Save detail by id")
    public InvokeResult detail(@RequestParam Integer id) {
        SaveVO vo = saveManager.selectDetailVoById(id);
        return InvokeResult.success(vo);
    }

    @GetMapping("page")
    @ApiOperation(value = "get Save page")
    public IPage<SaveVO> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10")Integer pageSize){

        PageResult page = new PageResult(pageNum, pageSize);

        Map<String, Object> map = new HashMap<>(4);

        return saveManager.getPage(page, map);
    }
}
