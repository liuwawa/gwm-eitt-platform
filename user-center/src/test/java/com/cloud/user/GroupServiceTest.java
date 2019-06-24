package com.cloud.user;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.model.user.SysGroup;
import com.cloud.user.UserCenterApplication;
import com.cloud.user.dao.SysGroupDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserCenterApplication.class)
public class GroupServiceTest {

    @Test
    public void testSave() {
        SysGroup sysGroup = SysGroup.builder().groupAddress("工程楼四楼")
                .groupChildCount(3).groupLevel(0).groupName("测试组织")
                .groupParentId(0).groupRemark("这是一个测试用的组织")
                .groupShowOrder(0).groupTel(1333333333).createBy("liu")
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
        List<SysGroup> sysGroups = sysGroup.selectPage(new Page<SysGroup>(1, 10), null).getRecords();
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
}
