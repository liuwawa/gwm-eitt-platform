package com.gwm.one.hr.user;

import com.gwm.one.common.vo.ResultVo;
import com.gwm.one.model.hr.user.SysGroup;
import com.gwm.one.model.hr.user.SysGroupGrouping;
import com.gwm.one.model.hr.user.SysGrouping;
import com.gwm.one.hr.user.controller.SysGroupingController;
import com.gwm.one.hr.user.controller.SysUserGroupingController;
import com.gwm.one.hr.user.dao.SysGroupGroupingDao;
import com.gwm.one.hr.user.service.SysGroupService;
import com.gwm.one.hr.user.service.SysGroupingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserCenterApplication.class)
public class GroupingTest {
    @Autowired
    private SysGroupGroupingDao sysGroupGroupingDao;

    @Autowired
    private SysGroupingController sysGroupingController;

    @Autowired
    private SysGroupService sysGroupService;
    @Autowired
    private SysGroupingService sysGroupingService;

    @Autowired
    private SysUserGroupingController controller;

    @Test
    public void testQuery(){
        ResultVo<List<SysGrouping>> groupingsByUserId = controller.getGroupingsByUserId(2);
        List<SysGrouping> data = groupingsByUserId.getData();
        for (SysGrouping datum : data) {
            System.out.println(datum);
        }
    }



    @Test
    public void testS() {
        SysGrouping sysGrouping = SysGrouping.builder().groupingName("122")
                .groupNumber(0).groupingRemark("这个是测试的").createBy("liuek")
                .createTime(new Date()).build();
        boolean save = sysGroupingService.save(sysGrouping);
        System.out.println(save);
    }

    @Test
    public void testSaveGroup() {
        SysGroup sysGroup = SysGroup.builder().label("王茂云").build();
        boolean save = sysGroupService.save(sysGroup);
        System.out.println(save);
    }



    @Test
    public void testDeleteGroupings(){
        List<Integer> groupingIds = Arrays.asList(2,3,4,5,6,7);
        //ResultVo admin = sysGroupingController.deleteGroupings(groupingIds, "admin");
       // System.out.println(admin);
    }

    @Test
    public void testDelete(){
        SysGrouping sysGrouping = SysGrouping.builder().groupingId(4564).groupingRemark("123").build();
        System.out.println(sysGrouping.updateById());
    }

    @Test
    public void testInsertList(){
        List<SysGroupGrouping> list = new ArrayList<>();
        SysGroupGrouping sysGroupGrouping = new SysGroupGrouping();

        sysGroupGrouping.setGroupId(1);
        sysGroupGrouping.setGroupingId(1);
        list.add(sysGroupGrouping);
        SysGroupGrouping sysGroupGrouping1 = new SysGroupGrouping();
        sysGroupGrouping1.setGroupId(1);
        sysGroupGrouping1.setGroupingId(2);
        list.add(sysGroupGrouping1);
        System.out.println(list);
        sysGroupGroupingDao.insertList(list);
    }
}
