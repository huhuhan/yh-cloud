package com.yh.cloud.activiti.service.management;

import com.github.pagehelper.Page;
import com.yh.cloud.activiti.util.WorkflowUtils;
import com.yh.cloud.web.wrapper.ReturnMsg;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Activiti用户、组管理
 *
 * @author yanghan
 * @date 2019/10/31
 */
@Service
public class MIdentityService {

    @Autowired
    private IdentityService identityService;

    public Page<Group> getGroupList(Integer pageSize, Integer pageNum) {
        GroupQuery groupQuery = identityService.createGroupQuery();
        List<Group> groupList = groupQuery.listPage(WorkflowUtils.pageParam2(pageNum, pageSize), pageSize);
        Page<Group> page = new Page();
        page.addAll(groupList);
        page.setTotal(groupQuery.count());
        return page;
    }

    public void saveGroup(String groupId, String groupName, String type) {
        Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
        if (group == null) {
            group = identityService.newGroup(groupId);
        }
        group.setName(groupName);
        group.setType(type);
        identityService.saveGroup(group);
    }

    public void deleteGroup(String groupId) {
        identityService.deleteGroup(groupId);
    }

    public ReturnMsg getUserList(Integer pageSize, Integer pageNum) {

        UserQuery userQuery = identityService.createUserQuery();
        List<User> userList = userQuery.listPage(WorkflowUtils.pageParam2(pageNum, pageSize), pageSize);

        Map<String, List<Group>> groupOfUserMap = new HashMap<String, List<Group>>();
//        List<String> userIds = userList.stream().map(user->user.getId()).collect(Collectors.toList());
        for (User user : userList) {
            List<Group> groupList = identityService.createGroupQuery().groupMember(user.getId()).list();
            groupOfUserMap.put(user.getId(), groupList);
        }

        Page<User> page = new Page();
        page.addAll(userList);
        page.setTotal(userQuery.count());


        // 读取所有组
        List<Group> groups = identityService.createGroupQuery().list();

        return new ReturnMsg<String, Object>()
                .keyValue("userList", page)
                .keyValue("groups", groups)
                .keyValue("userGroupMap", groupOfUserMap);
    }

    public void saveUser(String userId, String firstName, String lastName, String password, String email) {
        User user = identityService.createUserQuery().userId(userId).singleResult();
        if (user == null) {
            user = identityService.newUser(userId);
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        if (StringUtils.isNotBlank(password)) {
            user.setPassword(password);
        }
        identityService.saveUser(user);
    }

    public void deleteUser(String userId) {
        identityService.deleteUser(userId);
    }

    public void groupForUser(String userId, String[] groupIds) {
        List<Group> groupInDb = identityService.createGroupQuery().groupMember(userId).list();
        for (Group group : groupInDb) {
            identityService.deleteMembership(userId, group.getId());
        }
        for (String group : groupIds) {
            identityService.createMembership(userId, group);
        }
    }
}
