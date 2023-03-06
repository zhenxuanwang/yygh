package com.wzx.yygh.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzx.yygh.common.exception.HospitalException;
import com.wzx.yygh.common.utils.MD5;
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
import java.util.Random;

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
        //模拟异常
        int a = 1 / 0;
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

    //添加医院设置信息
    @ApiOperation("添加医院设置信息")
    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet) {
        //设置状态0/1
        hospitalSet.setStatus(1);
        //签名密钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
        //调用service
        boolean save = hospitalSetService.save(hospitalSet);
        if(save) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //根据id获取医院设置信息
    @ApiOperation("根据id获取医院设置信息")
    @GetMapping("getHospSet/{id}")
    public Result getHospSet(@PathVariable Long id) {
        try {
            //模拟异常
            int a = 1 / 0;
        } catch (Exception e) {
            throw new HospitalException("失败",201);
        }
        HospitalSet byId = hospitalSetService.getById(id);
        return Result.ok(byId);
    }

    //修改医院设置
    @ApiOperation("修改医院设置")
    @PostMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet) {
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if(flag) {
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    //批量删除医院设置信息
    @ApiOperation("批量删除医院设置信息")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        hospitalSetService.removeByIds(idList);
        return Result.ok();
    }

    //医院设置状态的锁定和解锁
    @ApiOperation("医院设置状态的锁定和解锁")
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        hospitalSet.setStatus(status);
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }

    //发送医院设置签名密钥
    @ApiOperation("发送医院设置签名密钥")
    @PutMapping("sendKey/{id}")
    public Result sendKey(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String hosname = hospitalSet.getHosname();
        String hoscode = hospitalSet.getHoscode();
        //发送短信
        return Result.ok();
    }
}
