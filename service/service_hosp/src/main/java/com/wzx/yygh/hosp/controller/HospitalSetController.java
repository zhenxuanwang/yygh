package com.wzx.yygh.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzx.yygh.model.hosp.HospitalSet;
import com.wzx.yygh.common.result.Result;
import com.wzx.yygh.hosp.service.HospitalSetService;
import com.wzx.yygh.vo.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    public Result findHospitalSet() {
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    //逻辑删除医院设置信息
    @ApiOperation("逻辑删除医院设置信息")
    @DeleteMapping("{id}")
    public Result removeHospitalSet(@PathVariable Long id) {
         boolean flag = hospitalSetService.removeById(id);
         if(flag) {
             return Result.ok();
         } else {
             return Result.fail();
         }
    }

    //条件查询带分页医院设置信息
    @ApiOperation("条件查询带分页医院设置信息")
    @PostMapping("findPage/{current}/{limit}")
    public Result findPageHospSet(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        //创建Page对象
        Page<HospitalSet> page = new Page<>(current, limit);
        //构造条件
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        String hosname = hospitalSetQueryVo.getHosname();
        String hoscode = hospitalSetQueryVo.getHoscode();
        if (!StringUtils.isEmpty(hosname)){
            wrapper.like("hosname",hospitalSetQueryVo.getHosname());
        }
        if (!StringUtils.isEmpty(hoscode)){
            wrapper.eq("hoscode",hospitalSetQueryVo.getHoscode());
        }
        //调用方法查询
        Page<HospitalSet> hospitalSetPage = hospitalSetService.page(page, wrapper);
        //返回结果
        return Result.ok(hospitalSetPage);

    }

}
