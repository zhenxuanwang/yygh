package com.wzx.yygh.cmn.service;

import com.wzx.yygh.model.cmn.Dict;

import java.util.List;

public interface DictService {
    //根据数据id查询子数据列表
    List<Dict> findChildData(Long id);
}
