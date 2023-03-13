package com.wzx.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzx.yygh.cmn.listener.DictListener;
import com.wzx.yygh.cmn.mapper.DictMapper;
import com.wzx.yygh.cmn.service.DictService;
import com.wzx.yygh.model.cmn.Dict;
import com.wzx.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    //根据数据id查询子数据列表
    @Override
    @Cacheable(value = "dict",keyGenerator = "keyGenerator")
    public List<Dict> findChildData(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        List<Dict> dictList = baseMapper.selectList(queryWrapper);
        for (Dict dict:dictList) {
            Long dictId = dict.getId();
            boolean children = this.isChildren(dictId);
            dict.setHasChildren(children);
        }
        return dictList;
    }

    //导出数据字典
    @Override
    public void exportData(HttpServletResponse response) {
        //设置下载信息
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf8");
        String filename = "dict";
        response.setHeader("Content-disposition", "attachment;filename="+ filename + ".xlsx");
        //查询数据库
        List<Dict> dictList = baseMapper.selectList(null);
        List<DictEeVo> dictEeVoList = new ArrayList<>();
        for (Dict dict: dictList){
            DictEeVo dictEeVo = new DictEeVo();
            BeanUtils.copyProperties(dict,dictEeVo);
            dictEeVoList.add(dictEeVo);
        }
        //进行写操作
        try {
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("dict").doWrite(dictEeVoList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //导入数据字典
    @Override
    @CacheEvict(value = "dict", allEntries = true)
    public void importData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(),DictEeVo.class,new DictListener(baseMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //判断id下面是否有子节点
    private boolean isChildren(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        Integer selectCount = baseMapper.selectCount(queryWrapper);
        return selectCount > 0;
    }
}
