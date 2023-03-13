package com.wzx.yygh.cmn.service;

import com.wzx.yygh.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DictService {
    //根据数据id查询子数据列表
    List<Dict> findChildData(Long id);

    //导出数据字典
    void exportData(HttpServletResponse response);

    //导入数据字典
    void importData(MultipartFile file);
}
