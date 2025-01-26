package at.qe.skeleton.tests;

import at.qe.skeleton.model.Department;
import at.qe.skeleton.repositories.DepartmentRepository;
import at.qe.skeleton.services.DepartmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@WebAppConfiguration
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveNewDepartment() {
        // Arrange
        Department newDepartment = new Department();
        newDepartment.setName("Doctors office 1");
        newDepartment.setOpeningTime(LocalDateTime.of(2025, 1, 1, 8, 0));
        newDepartment.setClosingTime(LocalDateTime.of(2025, 1, 1, 18, 0));

        when(departmentRepository.save(newDepartment)).thenReturn(newDepartment);

        // Act
        Department savedDepartment = departmentService.saveDepartment(newDepartment);

        // Assert
        assertNotNull(savedDepartment);
        assertEquals("Doctors office 1", savedDepartment.getName());
        verify(departmentRepository, times(1)).save(newDepartment);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testSaveDuplicateDepartmentThrowsException() {
        // Arrange
        Department firstDepartment = new Department();
        firstDepartment.setName("Clinic");
        firstDepartment.setOpeningTime(LocalDateTime.of(2025, 1, 1, 8, 0));
        firstDepartment.setClosingTime(LocalDateTime.of(2025, 1, 1, 18, 0));

        departmentService.saveDepartment(firstDepartment);

        Department duplicateDepartment = new Department();
        duplicateDepartment.setName("Clinic");
        duplicateDepartment.setOpeningTime(LocalDateTime.of(2025, 1, 2, 9, 0));
        duplicateDepartment.setClosingTime(LocalDateTime.of(2025, 1, 2, 17, 0));

        // Act & Assert
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            departmentService.saveDepartment(duplicateDepartment);
        });
        Assertions.assertEquals("Department with the name Clinic already exists.", exception.getMessage());

    }


}