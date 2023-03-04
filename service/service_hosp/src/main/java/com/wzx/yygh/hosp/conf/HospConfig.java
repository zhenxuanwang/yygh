package com.wzx.yygh.hosp.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 医院设置配置类
 */
@Configuration
@MapperScan("com.wzx.yygh.hosp.mapper")
public class HospConfig {
}
