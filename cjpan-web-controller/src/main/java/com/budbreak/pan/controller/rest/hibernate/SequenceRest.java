package com.budbreak.pan.controller.rest.hibernate;

import com.budbreak.pan.common.*;
import com.budbreak.pan.controller.assembler.hibernate.SequenceAssembler;
import com.budbreak.pan.controller.command.hibernate.SequenceCreateCommand;
import com.budbreak.pan.controller.command.hibernate.SequenceUpdateCommand;
import com.budbreak.pan.entity.hibernate.Sequence;
import com.budbreak.pan.vo.hibernate.SequenceVO;
import com.budbreak.pan.service.hibernate.SequenceService;
import com.budbreak.pan.manager.hibernate.SequenceManager;
import com.budbreak.pan.mapper.hibernate.SequenceMapper;
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
@RequestMapping("api/v1/hibernate/sequence")
@Api(value = "sequenceService", description = "sequence相关AIP")
public class SequenceRest  {

    @Autowired
    private SequenceMapper sequenceMapper;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private SequenceManager sequenceManager;

    @PostMapping("add")
    @ApiOperation(value = "add Sequence")
    public InvokeResult add(@RequestBody @Valid SequenceCreateCommand command) {
        Sequence entity = SequenceAssembler.toSequence(command);
        sequenceService.addEntity(entity);
        return InvokeResult.success();
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "delect Sequence by id")
    public InvokeResult delete(@RequestBody CommonDTO commonDTO){
        sequenceService.removeByIds(Arrays.asList(commonDTO.getIds()));
        return InvokeResult.success();
    }

    @PutMapping("update")
    @ApiOperation(value = "update Sequence by id")
    public InvokeResult update(@RequestBody @Valid SequenceUpdateCommand command) {
        Sequence entity = SequenceAssembler.toSequence(command);
        sequenceService.updateEntity(entity);
        return InvokeResult.success();
    }

    @GetMapping("detail")
    @ApiOperation(value = "get Sequence detail by id")
    public InvokeResult detail(@RequestParam Integer id) {
        SequenceVO vo = sequenceManager.selectDetailVoById(id);
        return InvokeResult.success(vo);
    }

    @GetMapping("page")
    @ApiOperation(value = "get Sequence page")
    public IPage<SequenceVO> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10")Integer pageSize){

        PageResult page = new PageResult(pageNum, pageSize);

        Map<String, Object> map = new HashMap<>(4);

        return sequenceManager.getPage(page, map);
    }
}
