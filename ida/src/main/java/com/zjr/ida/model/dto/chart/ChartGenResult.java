package com.zjr.ida.model.dto.chart;

import com.zjr.ida.common.ErrorCode;
import com.zjr.ida.exception.ThrowUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@NoArgsConstructor
public class ChartGenResult {
    /**
     * ai生成的图表数据
     */
    private String genChart;
    /**
     * ai生成的图表分析结果
     */
    private String genResult;
    public ChartGenResult(String genChart, String genResult) {
        ThrowUtils.throwIf(StringUtils.isAnyBlank(genChart, genResult), ErrorCode.PARAMS_ERROR);
        this.genChart = genChart;
        this.genResult = genResult;
    }
}
