package at.qe.skeleton.tests;

import at.qe.skeleton.controllers.ShiftController;
import at.qe.skeleton.dtos.ShiftDTO;
import at.qe.skeleton.mappers.ShiftMapper;
import at.qe.skeleton.model.Shift;
import at.qe.skeleton.services.ShiftService;
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
import java.util.Optional;

@WebMvcTest(ShiftController.class)
@AutoConfigureMockMvc
public class ShiftControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShiftService shiftService;

    @MockBean
    private ShiftMapper shiftMapper;

    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    void getCurrentShift() throws Exception {
        Long id = 1L;
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(4);
        Shift shift = new Shift();
        shift.setId(id);
        shift.setStartTime(startTime);
        shift.setEndTime(endTime);

        ShiftDTO shiftDTO = new ShiftDTO(id, startTime, endTime, null, null);

        Mockito.when(shiftService.loadShift(id)).thenReturn(Optional.of(shift));
        Mockito.when(shiftMapper.mapTo(shift)).thenReturn(shiftDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/shifts/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));
    }

/*
    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    void createShift() throws Exception {
        Long id = 1L;
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(4);

        ShiftDTO shiftDTO = new ShiftDTO(id, startTime, endTime, null, null);
        Shift shift = new Shift();
        shift.setId(id);
        shift.setStartTime(startTime);
        shift.setEndTime(endTime);

        Mockito.when(shiftMapper.mapFrom(shiftDTO)).thenReturn(shift);
        Mockito.when(shiftService.saveShift(shift)).thenReturn(shift);
        Mockito.when(shiftMapper.mapTo(shift)).thenReturn(shiftDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/shifts")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(shiftDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startTime").value(startTime.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endTime").value(endTime.toString()));
    }
*/

    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    void deleteShift() throws Exception {
        Long id = 1L;
        Shift shift = new Shift();
        shift.setId(id);

        Mockito.when(shiftService.loadShift(id)).thenReturn(Optional.of(shift));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/shifts/{id}", id)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(username = "manager", authorities = {"MANAGER"})
    void deleteShiftNotFound() throws Exception {
        Long id = 1L;

        Mockito.when(shiftService.loadShift(id)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/shifts/{id}", id)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
