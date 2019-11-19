package com.yh.cloud.activiti.service.workflow;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.Page;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 模型管理
 *
 * @author yanghan
 * @date 2019/10/29
 */
@Service
public class WFModelService {

    @Autowired
    private RepositoryService repositoryService;


    public Page<Model> getList(Integer pageSize, Integer pageNum) {
        List<Model> list = repositoryService.createModelQuery()
                .orderByLastUpdateTime()
                .desc()
                .listPage(pageNum, pageSize);
        long count = repositoryService.createModelQuery().count();
        Page<Model> page = new Page();
        page.addAll(list);
        page.setTotal(count);
        return page;
    }

    public Model create(String name, String key, String description) throws UnsupportedEncodingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        Model modelData = repositoryService.newModel();

        ObjectNode modelObjectNode = objectMapper.createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        description = StringUtils.defaultString(description);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelData.setMetaInfo(modelObjectNode.toString());
        modelData.setName(name);
        modelData.setKey(StringUtils.defaultString(key));

        repositoryService.saveModel(modelData);
        repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));

        return modelData;
    }

    public Deployment deploy(String modelId) throws Exception {
        Model modelData = repositoryService.getModel(modelId);
        byte[] modelBytes = repositoryService.getModelEditorSource(modelData.getId());
        if (modelBytes == null) {
            throw new Exception("数据模型不符要求，请至少设计一条主线流程。");
        }
        JsonNode modelNode = new ObjectMapper().readTree(modelBytes);
        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if (model.getProcesses().size() == 0) {
            throw new Exception("数据模型不符要求，请至少设计一条主线流程。");
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

        String processName = modelData.getName() + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes)).deploy();
        return deployment;
    }

    public Deployment deployDemo() {
        //发布流程
        Deployment deployment = repositoryService.createDeployment()
                .name("请假流程写死Demo")
                .addClasspathResource("processes/leave.bpmn20.xml")
                .deploy();
        return deployment;
    }

    public void delete(String modelId) {
        repositoryService.deleteModel(modelId);
    }

}
