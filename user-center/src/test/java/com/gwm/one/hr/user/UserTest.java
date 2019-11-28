package com.gwm.one.hr.user;

import com.gwm.one.model.hr.user.SysGroup;
import com.gwm.one.hr.user.service.SysGroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = UserCenterApplication.class)
@RunWith(SpringRunner.class)
public class UserTest {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private SysGroupService sysGroupService;

	@Test
	public void password() {
		String string = passwordEncoder.encode("superadmin");
		System.out.println(string);
		System.out.println(string.length());
		SysGroup sysGroup = new SysGroup();
		sysGroup.setLabel("123456");
		boolean save = sysGroupService.save(sysGroup);
		System.out.println(save);
	}

}
