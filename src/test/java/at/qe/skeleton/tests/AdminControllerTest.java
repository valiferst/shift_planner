package at.qe.skeleton.tests;

import at.qe.skeleton.controllers.AdminController;
import at.qe.skeleton.dtos.UserxCreateDTO;
import at.qe.skeleton.dtos.UserxDTO;
import at.qe.skeleton.mappers.UserxCreateMapper;
import at.qe.skeleton.mappers.UserxMapper;
import at.qe.skeleton.model.Userx;
import at.qe.skeleton.services.UserxService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Some very basic tests for {@link AdminController}.
 *
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean    
    private UserxService userService;

    @MockBean
    private UserxMapper userMapper;

    @MockBean
    private UserxCreateMapper userCreateMapper;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getAllUsers() throws Exception {
        Long id = 1L;
        String username = "testUser";
        Userx user1 = new Userx();
        user1.setId(id);
        user1.setUsername(username);
        user1.setFirstName("First");
        user1.setLastName("Last");
        List<Userx> users = List.of(user1);
        
        Mockito.when(userService.getAllUsers()).thenReturn(users);
        Mockito.when(userMapper.mapTo(Mockito.any(Userx.class))).thenReturn(new UserxDTO(
                id, null, null, null, null, "testUser", "First", "Last", null, null, false, null, null, null));

        
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value(username));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getUserUserExists() throws Exception {
        Long id = 1L;
        String username = "testUser";
        Userx user1 = new Userx();
        user1.setId(id);
        user1.setUsername(username);
        user1.setFirstName("First");
        user1.setLastName("Last");
        Mockito.when(userService.loadUser(id)).thenReturn(Optional.of(user1));
        Mockito.when(userMapper.mapTo(user1)).thenReturn(new UserxDTO(id, null, null, null, null, username, "First", "Last", null, null, false, null, null, null));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getUserUserDoesNotExist() throws Exception {
        Mockito.when(userService.loadUser(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createUserValidInput() throws Exception {
        Long id = 1L;
        String username = "newUser";
        String password = "password";
        String firstName = "first";
        String lastName = "last";
        String email = "new@example.com";
        boolean isEnabled = true;
        
        UserxCreateDTO newUser = new UserxCreateDTO(username, password, firstName, lastName, email, "", true, null);
        Userx user = new Userx();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setEnabled(isEnabled);
        
        Mockito.when(userCreateMapper.mapFrom(newUser)).thenReturn(user);
        Mockito.when(userService.saveUser(user)).thenReturn(user);
        Mockito.when(userMapper.mapTo(user)).thenReturn(new UserxDTO(id, null, null, null, null, username, firstName, lastName, email, "", isEnabled, null, null, null));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newUser)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username));
    }
    
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteUserUserExists() throws Exception {
        Long id = 1L;
        String username = "newUser";
        Userx user = new Userx();
        user.setId(id);
        user.setUsername(username);
        
        Mockito.when(userService.loadUser(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/{id}", id)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteUserUserDoesNotExist() throws Exception {
        Long id = 1L;
        Mockito.when(userService.loadUser(id)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/{id}", id)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
}
