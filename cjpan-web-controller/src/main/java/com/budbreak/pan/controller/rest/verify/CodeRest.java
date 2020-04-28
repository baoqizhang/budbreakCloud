package com.budbreak.pan.controller.rest.verify;

import com.budbreak.pan.common.*;
import com.budbreak.pan.controller.assembler.verify.CodeAssembler;
import com.budbreak.pan.controller.command.verify.CodeCreateCommand;
import com.budbreak.pan.controller.command.verify.CodeUpdateCommand;
import com.budbreak.pan.entity.verify.Code;
import com.budbreak.pan.service.verify.CodeService;
import com.budbreak.pan.manager.verify.CodeManager;
import com.budbreak.pan.mapper.verify.CodeMapper;
import com.budbreak.pan.vo.verify.CodeVO;
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
@RequestMapping("api/v1/verify/code")
@Api(value = "codeService", description = "code相关AIP")
public class CodeRest  {

    @Autowired
    private CodeMapper codeMapper;

    @Autowired
    private CodeService codeService;

    @Autowired
    private CodeManager codeManager;

    @PostMapping("add")
    @ApiOperation(value = "add Code")
    public InvokeResult add(@RequestBody @Valid CodeCreateCommand command) {
        Code entity = CodeAssembler.toCode(command);
        codeService.addEntity(entity);
        return InvokeResult.success();
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "delect Code by id")
    public InvokeResult delete(@RequestBody CommonDTO commonDTO){
        codeService.removeByIds(Arrays.asList(commonDTO.getIds()));
        return InvokeResult.success();
    }

    @PutMapping("update")
    @ApiOperation(value = "update Code by id")
    public InvokeResult update(@RequestBody @Valid CodeUpdateCommand command) {
        Code entity = CodeAssembler.toCode(command);
        codeService.updateEntity(entity);
        return InvokeResult.success();
    }

    @GetMapping("detail")
    @ApiOperation(value = "get Code detail by id")
    public InvokeResult detail(@RequestParam Integer id) {
        CodeVO vo = codeManager.selectDetailVoById(id);
        return InvokeResult.success(vo);
    }

    @GetMapping("page")
    @ApiOperation(value = "get Code page")
    public IPage<CodeVO> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10")Integer pageSize){

        PageResult page = new PageResult(pageNum, pageSize);

        Map<String, Object> map = new HashMap<>(4);

        return codeManager.getPage(page, map);
    }
}
