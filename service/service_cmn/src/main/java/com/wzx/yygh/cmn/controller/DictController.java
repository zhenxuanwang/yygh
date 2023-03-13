package com.wzx.yygh.cmn.controller;

import com.wzx.yygh.cmn.service.DictService;
import com.wzx.yygh.common.result.Result;
import com.wzx.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "数据字典接口")
@RestController
@RequestMapping("/admin/cmn/dict")
@CrossOrigin
public class DictController {
    @Autowired
    private DictService dictService;

    //根据数据id查询子数据列表
    @ApiOperation(value = "根据数据id查询子数据列表")
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id) {
        List<Dict> list = dictService.findChildData(id);
        return Result.ok(list);
    }

    //导出数据字典
    @ApiOperation(value = "导出数据字典")
    @GetMapping("exportData")
    public Result exportDict(HttpServletResponse response) {
        dictService.exportData(response);
        return Result.ok();
    }

    //导入数据字典
    @ApiOperation(value = "导入数据字典")
    @PostMapping("importData")
    public Result importDict(MultipartFile file) {
        dictService.importData(file);
        return Result.ok();
    }
}
