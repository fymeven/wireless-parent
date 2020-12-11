package cn._51even.wireless.web.controller;

import cn._51even.wireless.bean.entity.SysDict;
import cn._51even.wireless.bean.entity.SysDictGroup;
import cn._51even.wireless.bean.enums.SysDictEnum;
import cn._51even.wireless.bean.enums.SysDictTypeEnum;
import cn._51even.wireless.bean.request.sysDict.InsertSysDictReq;
import cn._51even.wireless.bean.request.sysDict.PageSysDictReq;
import cn._51even.wireless.bean.request.sysDictGroup.InsertSysDictGroupReq;
import cn._51even.wireless.bean.request.sysDictGroup.UpdateSysDictGroupReq;
import cn._51even.wireless.core.base.bean.response.ResponseResult;
import cn._51even.wireless.core.util.BeanCopyUtil;
import cn._51even.wireless.core.util.UUIDUtil;
import cn._51even.wireless.service.api.SysDictGroupService;
import cn._51even.wireless.service.api.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/sysDict")
@Api(value = "sysDict" ,tags = "系统字典配置")
@Validated
public class SysDictController {

    @Resource
    private SysDictGroupService sysDictGroupService;
    @Resource
    private SysDictService sysDictService;

    @PostMapping(value = "/getBkType")
    @ApiOperation(value = "获取布控类型", notes = "获取布控类型")
    public ResponseResult getBkType(){
        return sysDictService.selectBkType();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(value = "字典组关键字",name = "keyWord",required = false)
    })
    @GetMapping("/queryDictGroupList")
    @ApiOperation(value = "queryDictGroupList",notes = "查询字典组列表")
    public ResponseResult queryDictGroupList(@RequestParam(required = false)String keyWord) {
        List<SysDictGroup> list = sysDictGroupService.queryDictGroupList(keyWord);
        return ResponseResult.successData(list);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(value = "字典组编码",name = "dictGroup",required = false),
            @ApiImplicitParam(value = "字典名称",name = "dictKey",required = false),
            @ApiImplicitParam(value = "字典值",name = "dictValue",required = false),
            @ApiImplicitParam(value = "字典状态",name = "dictStatus",required = false),
    })
    @GetMapping("/pageDictList")
    @ApiOperation(value = "pageDictList",notes = "查询字典列表")
    public ResponseResult pageDictList(@Valid PageSysDictReq pageSysDictReq) {
        return sysDictService.pageDictList(pageSysDictReq);
    }

    @PostMapping("/saveDictGroup")
    @ApiOperation(value = "saveDictGroup",notes = "新增字典组")
    public ResponseResult saveDictGroup(@Valid InsertSysDictGroupReq insertSysDictGroupReq){
        SysDictGroup sysDictGroup = new SysDictGroup();
        BeanCopyUtil.copyProperties(sysDictGroup, insertSysDictGroupReq);
        sysDictGroup.setId(UUIDUtil.getUUID());
        sysDictGroup.setGroupStatus(SysDictTypeEnum.dictStatus.ENABLE.getCode());
        sysDictGroup.setDeleteFlag(SysDictTypeEnum.deleteFlag.NO.getCode());
        boolean result = sysDictGroupService.save(sysDictGroup);
        return result ? ResponseResult.SUCCESS : ResponseResult.ERROR;
    }

    @PostMapping("/updateDictGroup")
    @ApiOperation(value = "updateDictGroup",notes = "修改字典组")
    public ResponseResult updateDictGroup(@Valid UpdateSysDictGroupReq updateSysDictGroupReq){
        SysDictGroup sysDictGroup = new SysDictGroup();
        BeanCopyUtil.copyProperties(sysDictGroup, updateSysDictGroupReq);
        boolean result = sysDictGroupService.updateNotNull(sysDictGroup);
        return result ? ResponseResult.SUCCESS : ResponseResult.ERROR;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(value = "id字符串(多个,号分隔)",name = "ids",required = false)
    })
    @PostMapping("/deleteDictGroup")
    @ApiOperation(value = "deleteDictGroup",notes = "删除字典组")
    public ResponseResult deleteDictGroup(@NotBlank(message = "id参数不能为空")
                                        @RequestParam(required = false)String ids){
        boolean result = sysDictGroupService.deleteBatch(ids);
        return result ? ResponseResult.SUCCESS : ResponseResult.ERROR;
    }

    @PostMapping("/saveDict")
    @ApiOperation(value = "saveDict",notes = "添加字典值")
    public ResponseResult saveDict(@Valid InsertSysDictReq insertSysDictReq){
        SysDict sysDict = new SysDict();
        BeanCopyUtil.copyProperties(sysDict,insertSysDictReq);
        sysDict.setId(UUIDUtil.getUUID());
        sysDict.setDictStatus(SysDictEnum.dictStatus.ENABLE.getCode());
        sysDict.setDeleteFlag(SysDictEnum.deleteFlag.NO.getCode());
        boolean result = sysDictService.save(sysDict);
        return result ? ResponseResult.SUCCESS : ResponseResult.ERROR;
    }

}
