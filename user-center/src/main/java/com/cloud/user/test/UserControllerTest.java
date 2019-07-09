package com.cloud.user.test;

import com.alibaba.fastjson.JSON;
import com.cloud.model.user.SysDept;
import com.cloud.model.user.SysGroup;
import com.cloud.user.UserCenterApplication;

import com.cloud.user.controller.UserController;


import com.cloud.user.dao.SysDeptDao;
import com.cloud.user.service.SysGroupService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserCenterApplication.class)
@SpringBootApplication
@WebAppConfiguration
public class UserControllerTest {
    private MockMvc mockMvc;



    @Autowired
    SysDeptDao sysDeptDao;

    @Autowired
    private UserController userController;

    @Autowired
    private SysGroupService sysGroupService;

    @Autowired
    RestTemplate restTemplate;

    @Before
    public void testBefore() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }


    @Test
    public void testFingByUserName() throws Exception {
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.get("/users-anon/internal")
                .accept(MediaType.MULTIPART_FORM_DATA).param("username","wmy"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);
    }

    @Test
    public void testFindByPhone() throws Exception {
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.get("/phone-anon/internal")
                .accept(MediaType.MULTIPART_FORM_DATA).param("phone","13337061322"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);
    }
    @Test
    public void testFindById() throws Exception {
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.get("/users/1")
                .accept(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);
    }

//    注册接口测试
    @Test
    public void testRegister() throws Exception {
        Map map = new HashMap();
        map.put("username","wmy");
        map.put("password","wmy920715");

        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.post("/users-anon/register")
               .content(JSON.toJSONString(map)).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);
    }

    @Test
    public void  testMapper(){

        SysDept sysDept = new SysDept();
        sysDept.setParentId(1L);
//        sysDept.setLeader("fdsafds");
        sysDept.setDeptName("f");
        sysDept.setLeader("wmy");
        sysDept.setPhone("1234565676");
        sysDept.setEmail("1234@456.com");
        sysDept.setCreateTime(new Date());
        sysDept.setUpdateTime(new Date());
        sysDept.setDeptId(120L);
        List<SysDept> data = sysDeptDao.findData(sysDept);
        for (SysDept sys:
             data) {
            System.out.println(sys);
        }
        sysDeptDao.insertDept(sysDept);
        sysDeptDao.updateSysDept(sysDept);
    }

    @Test
    public void  testGroupService(){
        SysGroup sysGroup = SysGroup.builder().id(277).build();
        boolean b = sysGroup.deleteById();
        System.out.println("11111111111111111111111111111111111"+b);
    }

    @Test
    public void testRestTemplate(){
        String uri = "http://wthrcdn.etouch.cn/weather_mini?city=";

    }



}
