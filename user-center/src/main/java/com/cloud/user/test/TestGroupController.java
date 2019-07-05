package com.cloud.user.test;

import com.cloud.model.common.Response;
import com.cloud.model.user.SysGroup;
import com.cloud.user.UserCenterApplication;
import com.cloud.user.controller.SysGroupController;
import com.cloud.user.dao.SysGroupDao;
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
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.ResponseEntity.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserCenterApplication.class)
public class TestGroupController {
    private MockMvc mockMvc;

    @Autowired
    SysGroupDao groupDao;

 /*   @MockBean
    SysGroupService sysGroupService;
*/

    @Autowired
    SysGroupController groupController;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();
    }

    @Test
    public void saveGroup() throws Exception {
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.post("/group/saveGroup")
                .accept(MediaType.MULTIPART_FORM_DATA).param("groupName","wmy4").param("groupParentId","262").param("groupLevel","4"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().
                        getResponse().
                        getContentAsString();

        System.out.println(contentAsString);
    }

    @Test
    public void updateGroup() throws Exception {
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.post("/group/updateGroup")
                .accept(MediaType.MULTIPART_FORM_DATA).param("groupName","mayuris").param("groupParentId","2").param("groupId","260"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().
                        getResponse().
                        getContentAsString();

        System.out.println(contentAsString);
    }
    @Test
    public void deleteGroup() throws Exception {
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.post("/group/deleteGroup")
                .accept(MediaType.MULTIPART_FORM_DATA).param("groupId","261"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().
                        getResponse().
                        getContentAsString();

        System.out.println(contentAsString);
    }
    @Test
    public void findById() throws Exception {
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.post("/group/findById")
                .accept(MediaType.MULTIPART_FORM_DATA).param("groupId","261"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().
                        getResponse().
                        getContentAsString();

        System.out.println(contentAsString);
    }


}
