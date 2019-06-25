package com.cloud.user;

import com.cloud.model.user.SysGroupExpand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserCenterApplication.class)
public class GroupExpandTest {
    @Test
    public void test(){
        SysGroupExpand sysGroupExpand = SysGroupExpand.builder().build();
    }
}
