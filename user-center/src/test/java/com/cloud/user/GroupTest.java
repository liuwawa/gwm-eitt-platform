package com.cloud.user;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.common.vo.ResultVo;
import com.cloud.model.user.GroupWithExpand;
import com.cloud.model.user.SysGroup;
import com.cloud.model.user.SysGroupExpand;
import com.cloud.model.user.SysGrouping;
import com.cloud.user.controller.SysGroupController;
import com.cloud.user.controller.SysGroupGroupingController;
import com.cloud.user.controller.SysUserGroupingController;
import com.cloud.user.dao.SysGroupDao;
import com.cloud.user.service.SysGroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserCenterApplication.class)
public class GroupTest {

    @Autowired
    private SysGroupService sysGroupService;
    @Autowired
    private SysGroupDao sysGroupDao;
    @Autowired
    private SysGroupController controller;
    @Autowired
    private SysUserGroupingController userGroupingController;
    @Autowired
    private SysGroupGroupingController groupGroupingController;
    @Test
    public void testSave() {
        SysGroup sysGroup = SysGroup.builder().groupAddress("工程楼四楼")
                .groupChildCount(3).groupLevel(0).groupName("测试组织")
                .groupParentId(0).groupRemark("这是一个测试用的组织")
                .groupShowOrder(0).groupTel("1333333333").createBy("liu")
                .createTime(new Date()).build();
        for (int i = 0; i < 30; i++) {
            boolean insert = sysGroup.insert();
        }
    }

    @Test
    public void testUpdate() {
        SysGroup sysGroup = SysGroup.builder().groupId(124).groupAddress("长城").build();
        System.out.println(sysGroup.updateById());
    }

    @Test
    public void testDelete() {
        SysGroup sysGroup = SysGroup.builder().groupId(124).build();
        System.out.println(sysGroup.deleteById());
    }

    @Test
    public void testSelectAll() {
        SysGroup sysGroup = new SysGroup();
        List<SysGroup> groupList = sysGroup.selectAll();
        for (SysGroup group : groupList) {
            System.out.println(group);
        }
    }

    @Test
    public void testSelectByPage() {
        SysGroup sysGroup = new SysGroup();
        IPage<SysGroup> sysGroupIPage = sysGroup.selectPage(new Page<SysGroup>(5, 2), new QueryWrapper<SysGroup>().eq("isDel",0));
        System.out.println(sysGroupIPage.getSize());
        System.out.println(sysGroupIPage.getTotal());
        System.out.println(sysGroupIPage.getCurrent());
        System.out.println(sysGroupIPage.getPages());

        List<SysGroup> sysGroups = sysGroupIPage.getRecords();
        for (SysGroup record : sysGroups) {
            System.out.println(record);
        }
    }

    @Test
    public void testInsertOrUpdate() {
        SysGroup sysGroup = SysGroup.builder().groupChildCount(100).build();
        System.out.println(sysGroup.insertOrUpdate());
    }

    @Test
    public void testSelectById() {
        SysGroup sysGroup = SysGroup.builder().groupId(155).build();
        System.out.println(sysGroup.selectById());
    }

    @Test
    public void testSelectByLike() {
        SysGroup sysGroup = new SysGroup();
        SysGroup result = sysGroup.selectOne(new QueryWrapper<SysGroup>().
                eq("groupChildCount", 100)
        .like("groupRemark","测试"));
        System.out.println(result);
    }

    @Test
    public void testTransactional(){
        SysGroup sysGroup = SysGroup.builder().groupAddress("工程楼顶楼")
                .groupChildCount(100).groupLevel(0).groupName("测试的分组")
                .groupParentId(0).groupRemark("这是一个测试事务用的组织")
                .groupShowOrder(0).groupTel("13491111").createBy("liu")
                .createTime(new Date()).build();
        try{
            boolean save = sysGroupService.save(sysGroup);
            if(save){
                System.out.println("没问题");
            }
        }catch (Exception e){
            System.out.println("出现异常了");
        }
    }
    @Test
    public void test(){
        SysGroup sysGroup = SysGroup.builder().groupName("1213154").build();
    }
    @Test
    public void testSaveSysGroup(){
        //ResultVo resultVo = controller.saveGroup(SysGroup.builder().groupTel("122345678").groupId(260).build(), null);

    }

    @Test
    public void testSave2(){
        SysGroup sysGroup = SysGroup.builder().groupTel("5555558").groupName("最新的group测试").build();

        sysGroupService.saveGroupAndGroupExpand(sysGroup,new SysGroupExpand());

    }
    @Test
    public void testFindSysGroupById(){
        ResultVo<GroupWithExpand> groupById = controller.findGroupById(260);
        GroupWithExpand data = groupById.getData();
        System.out.println(data);
        System.out.println(groupById);
    }

    @Test
    public void testDeleteGroup(){
        SysGroup sysGroup = SysGroup.builder().groupId(260).build();
//        ResultVo resultVo = controller.deleteGroup(sysGroup);
//        System.out.println(resultVo);
    }

    @Test
    public void testFindGroupsByGroupId(){
        ResultVo<SysGroup> groupsByGroupId = controller.getGroupsByGroupId(337);
        System.out.println(groupsByGroupId);
    }

    @Test
    public void testSelectUserGrouping(){
        ResultVo<List<SysGrouping>> groupingsByUserId = userGroupingController.getGroupingsByUserId(1);
        List<SysGrouping> data = groupingsByUserId.getData();
        System.out.println(groupingsByUserId);
        for (SysGrouping datum : data) {
            System.out.println(datum);
        }
    }

    @Test
    public void testSelectGroupGrouping(){
        ResultVo<List<SysGroup>> groupsByGroupingId = groupGroupingController.getGroupsByGroupingId(10);
        System.out.println(groupsByGroupingId);
        List<SysGroup> data = groupsByGroupingId.getData();
        for (SysGroup datum : data) {
            System.out.println(datum);
        }
    }
}
