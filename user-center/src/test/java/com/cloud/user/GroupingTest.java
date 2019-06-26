package com.cloud.user;

import com.cloud.common.vo.ResultVo;
import com.cloud.model.common.Page;
import com.cloud.model.user.SysGroup;
import com.cloud.model.user.SysGrouping;
import com.cloud.user.controller.SysGroupingController;
import com.cloud.user.service.SysGroupService;
import com.cloud.user.service.SysGroupingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserCenterApplication.class)
public class GroupingTest {
    @Autowired
    private SysGroupingController sysGroupingController;

    @Autowired
    private SysGroupService sysGroupService;
    @Autowired
    private SysGroupingService sysGroupingService;

    @Test
    public void testSave() {

        SysGrouping sysGrouping = SysGrouping.builder().groupingName("122")
                .groupingNumber(0).groupingRemark("这个是测试的").createBy("liuek")
                .createTime(new Date()).build();
        sysGrouping.setLoginAdminName("刘恩科");
        ResultVo resultVo = sysGroupingController.saveGrouping(sysGrouping);
        System.out.println(resultVo);
    }

    @Test
    public void testSelectById() {
        ResultVo<SysGrouping> detail = sysGroupingController.getGroupingDetailById(8);
        System.out.println(detail);
        SysGrouping data = detail.getData();
        System.out.println(data);
    }

    @Test
    public void testS() {
        SysGrouping sysGrouping = SysGrouping.builder().groupingName("122")
                .groupingNumber(0).groupingRemark("这个是测试的").createBy("liuek")
                .createTime(new Date()).build();
        boolean save = sysGroupingService.save(sysGrouping);
        System.out.println(save);
    }

    @Test
    public void testSaveGroup() {
        SysGroup sysGroup = SysGroup.builder().groupName("王茂云").build();
        boolean save = sysGroupService.save(sysGroup);
        System.out.println(save);
    }

    @Test
    public void testUpdateGrouping(){
        SysGrouping sysGrouping = SysGrouping.builder()
                .groupingNumber(0).groupingRemark("这测试真难搞")
                .groupingId(8).build();
        sysGrouping.setLoginAdminName("liuenke");
        ResultVo resultVo = sysGroupingController.updateGroupingById(sysGrouping);
        System.out.println(resultVo);
    }

    @Test
    public void testSelectGroupingByPage(){
        Page<SysGrouping> sysGroupingPage = sysGroupingController.selectGroupingByPage(1, null);
        System.out.println(sysGroupingPage);
        List<SysGrouping> data = sysGroupingPage.getData();
        data.forEach(System.out::println);
    }

    @Test
    public void testSelectAllGrouping(){
        Page<SysGrouping> sysGroupingPage = sysGroupingController.selectAllGrouping();
        System.out.println(sysGroupingPage);
        List<SysGrouping> data = sysGroupingPage.getData();
        data.forEach(System.out::println);
    }
    @Test
    public void testDeleteGrouping(){
        SysGrouping build = SysGrouping.builder().build();
        build.setLoginAdminName("liuenke");
        ResultVo resultVo = sysGroupingController.deleteGrouping(build);
        System.out.println(resultVo);
    }
    @Test
    public void testDeleteGroupings(){
        List<Integer> groupingIds = Arrays.asList(2,3,4,5,6,7);
        ResultVo admin = sysGroupingController.deleteGroupings(groupingIds, "admin");
        System.out.println(admin);
    }
}
