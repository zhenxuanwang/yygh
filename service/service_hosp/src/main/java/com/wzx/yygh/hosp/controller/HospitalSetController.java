package com.wzx.yygh.hosp.controller;

import com.wzx.model.hosp.HospitalSet;
import com.wzx.yygh.hosp.service.HospitalSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {
    //注入Service
    @Autowired
    private HospitalSetService hospitalSetService;

    //查询医院设置所有信息
    @ApiOperation("查询医院设置所有信息")
    @GetMapping("findAll")
    public List<HospitalSet> findHospitalSet() {
        List<HospitalSet> list = hospitalSetService.list();
        return list;
    }

    //逻辑删除医院设置信息
    @ApiOperation("逻辑删除医院设置信息")
    @DeleteMapping("{id}")
    public boolean removeHospitalSet(@PathVariable Long id) {
        return hospitalSetService.removeById(id);
    }

}
