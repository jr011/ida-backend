package com.zjr.ida.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjr.ida.model.entity.Chart;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 */
public interface ChartService extends IService<Chart> {
  public  void createTable(Long chartId, MultipartFile multipartFile);
}
