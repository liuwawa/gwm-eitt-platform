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
    public void testSaveGroupExpand() {
        SysGroupExpand sysGroupExpand = SysGroupExpand.builder().groupId(260).gDeptopLeader("大攀").gDirectLeader("老帽").build();
        //ResultVo resultVo = sysGroupExpandController.saveGroupExpand(sysGroupExpand);
        //System.out.println(resultVo);
    }
}
