package com.gwm.one.hr.user;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gwm.one.common.vo.ResultVo;
import com.gwm.one.model.hr.user.SysGroup;
import com.gwm.one.model.hr.user.SysGroupExpand;
import com.gwm.one.model.hr.user.SysGrouping;
import com.gwm.one.hr.user.controller.SysGroupController;
import com.gwm.one.hr.user.controller.SysGroupGroupingController;
import com.gwm.one.hr.user.controller.SysUserGroupingController;
import com.gwm.one.hr.user.dao.SysGroupDao;
import com.gwm.one.hr.user.service.SysGroupService;
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
        SysGroup sysGroup = SysGroup.builder().groupAddress("工程楼")
                .groupChildCount(3).level(0).label("测试组织")
                .parentid(0).groupRemark("这是一个测试用的组织")
                .groupShowOrder(0).groupTel("1333333333").createBy("liu")
                .createTime(new Date()).build();
        for (int i = 0; i < 30; i++) {
            boolean insert = sysGroup.insert();
        }
    }

    @Test
    public void testUpdate() {
        SysGroup sysGroup = SysGroup.builder().id(124).groupAddress("长城").build();
        System.out.println(sysGroup.updateById());
    }

    @Test
    public void testDelete() {
        SysGroup sysGroup = SysGroup.builder().id(124).build();
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
        SysGroup sysGroup = SysGroup.builder().id(155).build();
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
                .groupChildCount(100).level(0).label("测试的分组")
                .parentid(0).groupRemark("这是一个测试事务用的组织")
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
        SysGroup sysGroup = SysGroup.builder().label("1213154").build();
    }
    @Test
    public void testSaveSysGroup(){
//        ResultVo resultVo = controller.saveGroup(SysGroup.builder().groupTel("122345678").groupId(260).build(), null);

    }

    @Test
    public void testSave2(){
        SysGroup sysGroup = SysGroup.builder().groupTel("5555558").label("最新的group测试").build();

        sysGroupService.saveGroupAndGroupExpand(sysGroup,new SysGroupExpand());

    }


    @Test
    public void testGetAllGroup(){
       /* Map allGroup = controller.getAllGroup();
        List<SysGroup> data = (List<SysGroup>) allGroup.get("data");
        for (SysGroup datum : data) {
            System.out.println(datum);
        }*/
       StringBuilder stringBuilder = new StringBuilder();
       stringBuilder.insert(0,"A");
        System.out.println(stringBuilder);
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


}
