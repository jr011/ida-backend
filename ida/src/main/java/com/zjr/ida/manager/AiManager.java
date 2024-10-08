package com.zjr.ida.manager;


import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import com.zjr.ida.common.ErrorCode;
import com.zjr.ida.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于对接 AI 平台
 */
@Service
@Slf4j
public class AiManager {


    @Resource
    private ClientV4 clientV4;

    public static final String PRECONDITION = "你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容：\n" +
            "分析需求：\n" +
            "{数据分析的需求或者目标}\n" +
            "原始数据：\n" +
            "{csv格式的原始数据，用,作为分隔符}\n" +
            "请根据这两部分内容，严格按照以下指定格式生成内容（此外不要输出任何多余的开头、结尾、注释）\n" +
            "```\n" +
            "{前端 Echarts V5 的 option 配置对象 JSON 代码, 不要生成任何多余的内容，比如注释和代码块标记}\n" +
            "```\n" +
            "{明确的数据分析结论、越详细越好，不要生成多余的注释} \n"
            + "下面是一个具体的例子的模板："+
            "分析需求 网站用户增长速度分析\n" +
            "原始数据如下：num,day\n" +
            "3,12月12日\n" +
            "8,12月13日\n" +
            "5,12月14日\n" +
            "9,9月14日\n" +
            "生成的图表类型是：雷达图"
            + "```\n"
            + "{\"title\":{\"text\":\"网站用户增长速度分析\"},\"tooltip\":{},\"legend\":{\"data\":[\"用户数\"]},\"radar\":{\"indicator\":[{\"name\":\"12月12日\",\"max\":10},{\"name\":\"12月13日\",\"max\":10},{\"name\":\"12月14日\",\"max\":10},{\"name\":\"9月14日\",\"max\":10}]},\"series\":[{\"name\":\"用户数\",\"type\":\"radar\",\"data\":[{\"value\":[3,8,5,9],\"name\":\"用户数\"}]}]}"
            + "```\n" +
            "通过分析，我们可以看出网站的用户增长速度在不同日期有所波动。在12月12日，用户数为3；在12月13日，用户数增长到8，呈现出较高的增长速度；而在12月14日，用户数下降到5，增长速度减缓；最后在9月14日，用户数达到最高点，为9。平均每天的用户数为6.25。总体来看，网站的用户增长速度并不稳定，需要进一步分析原因并采取措施来提高用户增长速度";





    /*
    public  String doChat(String message) {
        // 第三步，构造请求参数
        DevChatRequest devChatRequest = new DevChatRequest();
        // 模型id，尾后加L，转成long类型
        devChatRequest.setModelId(1654785040361893889L);
        devChatRequest.setMessage(message);
        // 第四步，获取响应结果
        BaseResponse<DevChatResponse> response = yuCongMingClient.doChat(devChatRequest);
        // 如果响应为null，就抛出系统异常，提示“AI 响应错误”
        if (response == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "AI 响应错误");
        }
        return response.getData().getContent();
    }
    public  String doChat(String message,Long modelId) {
        // 第三步，构造请求参数
        DevChatRequest devChatRequest = new DevChatRequest();
        // 模型id，尾后加L，转成long类型
        devChatRequest.setModelId(modelId);
        devChatRequest.setMessage(message);
        // 第四步，获取响应结果
        BaseResponse<DevChatResponse> response = yuCongMingClient.doChat(devChatRequest);
        // 如果响应为null，就抛出系统异常，提示“AI 响应错误”
        if (response == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "AI 响应错误");
        }
        return response.getData().getContent();
    }
    public String sendMesToAIUseXingHuo(final String content) {
        List<SparkMessage> messages = new ArrayList<>();
        messages.add(SparkMessage.userContent(content));
        // 构造请求
        SparkRequest sparkRequest = SparkRequest.builder()
                // 消息列表
                .messages(messages)
                // 模型回答的tokens的最大长度,非必传,取值为[1,4096],默认为2048
                .maxTokens(2048)
                // 核采样阈值。用于决定结果随机性,取值越高随机性越强即相同的问题得到的不同答案的可能性越高 非必传,取值为[0,1],默认为0.5
                .temperature(0.2)
                // 指定请求版本，默认使用最新2.0版本
                .apiVersion(SparkApiVersion.V3_5)
                .build();
        // 同步调用
        SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
        String responseContent = chatResponse.getContent();
        log.info("星火 AI 返回的结果 {}", responseContent);
        return responseContent;
    }
    */
    public String sentMesToAIUseZhiPu(final String content){
        /**
         * 同步调用
         */
//        private static void testInvoke() {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), content);
        messages.add(chatMessage);
//        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(Boolean.FALSE)
                .invokeMethod(Constants.invokeMethod)
                .messages(messages)
//                .requestId(requestId)
                .build();
        ModelApiResponse invokeModelApiResp = clientV4.invokeModelApi(chatCompletionRequest);
//        try {
//            System.out.println("model output:" + invokeModelApiResp);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        }
        String result = invokeModelApiResp.getData().getChoices().get(0).getMessage().getContent().toString();
        return result;
    }

}
