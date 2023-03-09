package com.wzx.yygh.cmn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzx.yygh.cmn.mapper.DictMapper;
import com.wzx.yygh.cmn.service.DictService;
import com.wzx.yygh.model.cmn.Dict;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    //根据数据id查询子数据列表
    @Override
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
    //判断id下面是否有子节点
    private boolean isChildren(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        Integer selectCount = baseMapper.selectCount(queryWrapper);
        return selectCount > 0;
    }
}
