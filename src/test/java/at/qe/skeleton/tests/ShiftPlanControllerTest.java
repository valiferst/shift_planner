package at.qe.skeleton.tests;

import at.qe.skeleton.controllers.ShiftPlanController;
import at.qe.skeleton.dtos.ShiftPlanDTO;
import at.qe.skeleton.mappers.ShiftPlanMapper;
import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.services.ShiftPlanService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Tests for {@link ShiftPlanController}.
 */
@WebMvcTest(ShiftPlanController.class)
@AutoConfigureMockMvc
public class ShiftPlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShiftPlanService shiftPlanService;

    @MockBean
    private ShiftPlanMapper shiftPlanMapper;

    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    void getAllShiftPlans() throws Exception {
        Long id = 1L;
        String name = "Test Plan";
        ShiftPlan shiftPlan = new ShiftPlan();
        shiftPlan.setId(id);
        shiftPlan.setName(name);

        ShiftPlanDTO shiftPlanDTO = new ShiftPlanDTO(id, LocalDateTime.now(), LocalDateTime.now(), name, LocalDateTime.now(), LocalDateTime.now(), null);

        Mockito.when(shiftPlanService.getAllShiftPlans()).thenReturn(List.of(shiftPlan));
        Mockito.when(shiftPlanMapper.mapTo(Mockito.any(ShiftPlan.class))).thenReturn(shiftPlanDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/shiftplans"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(name));
    }

    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    void getShiftPlanById() throws Exception {
        Long id = 1L;
        String name = "Test Plan";
        ShiftPlan shiftPlan = new ShiftPlan();
        shiftPlan.setId(id);
        shiftPlan.setName(name);

        ShiftPlanDTO shiftPlanDTO = new ShiftPlanDTO(id, LocalDateTime.now(), LocalDateTime.now(), name, LocalDateTime.now(), LocalDateTime.now(), null);

        Mockito.when(shiftPlanService.loadShiftPlan(id)).thenReturn(Optional.of(shiftPlan));
        Mockito.when(shiftPlanMapper.mapTo(shiftPlan)).thenReturn(shiftPlanDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/shiftplans/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name));
    }

/*
    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    void createShiftPlan() throws Exception {
        Long id = 1L;
        String name = "Test Plan";

        ShiftPlanDTO shiftPlanDTO = new ShiftPlanDTO(id, LocalDateTime.now(), LocalDateTime.now(), name, LocalDateTime.now(), LocalDateTime.now(), null);
        ShiftPlan shiftPlan = new ShiftPlan();
        shiftPlan.setId(id);
        shiftPlan.setName(name);

        Mockito.when(shiftPlanMapper.mapFrom(shiftPlanDTO)).thenReturn(shiftPlan);
        Mockito.when(shiftPlanService.saveShiftPlan(shiftPlan)).thenReturn(shiftPlan);
        Mockito.when(shiftPlanMapper.mapTo(shiftPlan)).thenReturn(shiftPlanDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/shiftplans")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(shiftPlanDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name));
    }
*/

    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    void deleteShiftPlan() throws Exception {
        Long id = 1L;
        ShiftPlan shiftPlan = new ShiftPlan();
        shiftPlan.setId(id);

        Mockito.when(shiftPlanService.loadShiftPlan(id)).thenReturn(Optional.of(shiftPlan));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/shiftplans/{id}", id)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    void deleteShiftPlanNotFound() throws Exception {
        Long id = 1L;

        Mockito.when(shiftPlanService.loadShiftPlan(id)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/shiftplans/{id}", id)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
