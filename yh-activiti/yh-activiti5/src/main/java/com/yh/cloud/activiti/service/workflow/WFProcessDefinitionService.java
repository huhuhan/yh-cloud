package com.yh.cloud.activiti.service.workflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.Page;
import com.yh.cloud.activiti.model.bo.DeploymentBo;
import com.yh.cloud.activiti.model.bo.ProcessDefinitionBo;
import com.yh.cloud.activiti.util.WorkflowUtils;
import com.yh.common.web.wrapper.ReturnMsg;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * 流程定义管理
 *
 * @author yanghan
 * @date 2019/10/29
 */
@Service
public class WFProcessDefinitionService {
    @Autowired
    private RepositoryService repositoryService;
    @Value("${file.upload.path}")
    private String exportDir;

    /**
     * 两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    public Page<Map<String, Object>> getList(Integer pageSize, Integer pageNum) {
        Page<Map<String, Object>> page = new Page();

        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().orderByDeploymentId().desc();
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(WorkflowUtils.pageParam2(pageNum, pageSize),pageSize);
        processDefinitionList.forEach(processDefinition -> {
            Map<String, Object> map = new HashMap<>(2);
            map.put("processDefinition", ProcessDefinitionBo.copyBySource(processDefinition));
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            map.put("deployment", DeploymentBo.copyBySource(deployment));//new DeploymentBo(deployment));
            page.add(map);
        });
        long count = processDefinitionQuery.count();
        page.setTotal(count);

        return page;
    }


    public ReturnMsg updateState(String state, String processDefinitionId) throws Exception {
        String message;
        if (state.equals("active")) {
            repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
            message = "已激活ID为[" + processDefinitionId + "]的流程定义。";
        } else if (state.equals("suspend")) {
            repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
            message = "已挂起ID为[" + processDefinitionId + "]的流程定义。";
        } else {
            throw new Exception("state: active|suspend");
        }
        return new ReturnMsg<>().keyValue("message", message);
    }

    public void delete(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
    }

    public void deployByUpload(MultipartFile file) throws Exception {

        String fileName = file.getOriginalFilename();

        InputStream fileInputStream = file.getInputStream();
        Deployment deployment;

        String extension = FilenameUtils.getExtension(fileName);
        // 格式【demo.bpmn20.xml】
        boolean isBpmn20Xml = "xml".equals(extension) && "bpmn20".equals(FilenameUtils.getExtension(FilenameUtils.getBaseName(fileName)));
        // 格式【demo.bpmn】
        boolean isBpmn = "bpmn".equals(extension);

        if (isBpmn20Xml || isBpmn) {
            deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
        } else if ("zip".equals(extension)) {
            //todo 压缩包内容待验证
            ZipInputStream zip = new ZipInputStream(fileInputStream);
            deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
        } else {
            throw new Exception("文件不符合要求");
        }

        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
        //生成图片
        for (ProcessDefinition processDefinition : list) {
            WorkflowUtils.exportDiagramToFile(repositoryService, processDefinition, exportDir);
        }

    }

    public void convertToModel(String processDefinitionId) throws Exception {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId).singleResult();
        InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
                processDefinition.getResourceName());
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in = new InputStreamReader(bpmnStream, Charset.defaultCharset());
        XMLStreamReader xtr = xif.createXMLStreamReader(in);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

        BpmnJsonConverter converter = new BpmnJsonConverter();
        ObjectNode modelNode = converter.convertToJson(bpmnModel);
        Model modelData = repositoryService.newModel();
        modelData.setKey(processDefinition.getKey());
        modelData.setName(processDefinition.getResourceName());
        modelData.setCategory(processDefinition.getDeploymentId());

        ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
        modelData.setMetaInfo(modelObjectNode.toString());

        repositoryService.saveModel(modelData);

        repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes(Charset.defaultCharset()));
    }
}
