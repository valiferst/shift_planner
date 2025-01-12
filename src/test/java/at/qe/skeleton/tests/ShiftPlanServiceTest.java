package at.qe.skeleton.services;

import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.model.Userx;
import at.qe.skeleton.repositories.ShiftPlanRepository;
import at.qe.skeleton.repositories.UserxRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ShiftPlanServiceTest {

    @Mock
    private ShiftPlanRepository shiftPlanRepository;

    @Mock
    private UserxRepository userRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ShiftPlanService shiftPlanService;

    private ShiftPlan shiftPlan;
    private Userx user;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Create mock user and shift plan
        user = new Userx();
        user.setUsername("admin");
        user.setId(1L);

        shiftPlan = new ShiftPlan();
        shiftPlan.setId(1L);
        shiftPlan.setCreateUser(user);
        shiftPlan.setCreateDate(LocalDateTime.now());
        shiftPlan.setDate(LocalDateTime.now());
    }

    @Test
    void testSaveShiftPlan_New() {
        // Mock the repository methods
        when(shiftPlanRepository.save(any(ShiftPlan.class))).thenReturn(shiftPlan);
        when(userRepository.findFirstByUsername(anyString())).thenReturn(Optional.of(user));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getName()).thenReturn("admin");

        // Call the service method
        ShiftPlan savedShiftPlan = shiftPlanService.saveShiftPlan(shiftPlan);

        // Verify the result
        assertNotNull(savedShiftPlan);
        assertEquals(user.getUsername(), savedShiftPlan.getCreateUser().getUsername());
        verify(shiftPlanRepository, times(1)).save(shiftPlan);
    }

    @Test
    void testSaveShiftPlan_Existing() {
        // Update shift plan with an existing ID
        shiftPlan.setId(1L);

        // Mock repository and authentication
        when(shiftPlanRepository.save(any(ShiftPlan.class))).thenReturn(shiftPlan);
        when(userRepository.findFirstByUsername(anyString())).thenReturn(Optional.of(user));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getName()).thenReturn("admin");

        // Call the service method
        ShiftPlan updatedShiftPlan = shiftPlanService.saveShiftPlan(shiftPlan);

        // Verify the result
        assertNotNull(updatedShiftPlan);
        assertEquals(user.getUsername(), updatedShiftPlan.getUpdateUser().getUsername());
        verify(shiftPlanRepository, times(1)).save(shiftPlan);
    }

    @Test
    void testLoadShiftPlan() {
        // Mock repository and authentication
        when(shiftPlanRepository.findById(anyLong())).thenReturn(Optional.of(shiftPlan));

        // Call the service method
        Optional<ShiftPlan> loadedShiftPlan = shiftPlanService.loadShiftPlan(1L);

        // Verify the result
        assertTrue(loadedShiftPlan.isPresent());
        assertEquals(shiftPlan.getId(), loadedShiftPlan.get().getId());
    }

    @Test
    void testDeleteShiftPlan() {
        // Mock repository and authentication
        when(shiftPlanRepository.findById(anyLong())).thenReturn(Optional.of(shiftPlan));
        doNothing().when(shiftPlanRepository).delete(any(ShiftPlan.class));

        // Call the service method
        shiftPlanService.deleteShiftPlan(shiftPlan);

        // Verify the result
        verify(shiftPlanRepository, times(1)).delete(shiftPlan);
    }

    @Test
    void testGetAuthenticatedUser() {
        // Mock authentication and user
        when(authentication.getName()).thenReturn("admin");
        when(userRepository.findFirstByUsername("admin")).thenReturn(Optional.of(user));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Call the service method
        Userx authenticatedUser = shiftPlanService.getAuthenticatedUser();

        // Verify the result
        assertNotNull(authenticatedUser);
        assertEquals("admin", authenticatedUser.getUsername());
    }
}
