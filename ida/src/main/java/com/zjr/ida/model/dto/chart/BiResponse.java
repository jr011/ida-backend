package com.zjr.ida.model.dto.chart;

import lombok.Data;


@Data
public class BiResponse {
    //生成的图表代码
    private String genChart;
    //生成的图表分析结果
    private String genResult;
    //图表id
    private Long chartId;
}
