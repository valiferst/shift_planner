package at.qe.skeleton.services;

import at.qe.skeleton.exceptions.UsernameDuplicateException;
import at.qe.skeleton.model.Userx;
import java.util.Collection;

import at.qe.skeleton.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import at.qe.skeleton.repositories.UserxRepository;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for accessing and manipulating user data.
 *
 * This class is part of the skeleton project provided for students of the
 * course "Software Architecture" offered by Innsbruck University.
 */
@Component
@Scope("application")
public class UserxService {

    private final PasswordEncoder passwordEncoder;
    private final UserxRepository userRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public UserxService(UserxRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.departmentRepository = departmentRepository;
    }
    
    /**
     * Returns a collection of all users.
     *
     * @return the userx collection
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Collection<Userx> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Loads a single user identified by its id.
     *
     * @param id the id to search for
     * @return the user with the id
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Optional<Userx> loadUser(Long id) {
        return userRepository.findById(id);
    }
    
    /**
     * Saves the user. This method will also set {@link Userx#createDate} for new
     * entities or {@link Userx#updateDate} for updated entities. The user
     * requesting this operation will also be stored as {@link Userx#createDate}
     * or {@link Userx#updateUser} respectively.
     *
     * @param user the user to save
     * @return the updated user
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Userx saveUser(Userx user) {
        if (user.isNew()) {
            if (userRepository.existsByUsername(user.getUsername())) {
                throw new UsernameDuplicateException("Username " + user.getUsername() + " not available");
            }
            user.setCreateUser(getAuthenticatedUser());
            //1. encrypt password for newly created user
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setUpdateUser(getAuthenticatedUser());
        }
        return userRepository.save(user);
    }

    private boolean isReferenced(Userx user) {
        return departmentRepository.existsByManagerId(user.getId());
    }

    /**
     * Deletes the user.
     *
     * @param user the user to delete
     */
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(Userx user) {
        userRepository.findById(user.getId()).ifPresent(userToDelete -> {
            if (isReferenced(userToDelete)) {
                // Reassign departments to a dummy manager
                Userx dummyManager = userRepository.findById(9999L)
                        .orElseThrow(() -> new IllegalStateException("Dummy manager with ID 9999L does not exist."));

                departmentRepository.getDepartmentsByManagerId(userToDelete.getId()).forEach(department -> {
                    department.setManager(dummyManager);
                    departmentRepository.save(department);
                });
            }
            // Delete the user after all references are reassigned
            userRepository.delete(userToDelete);
        });
    }

    /**
     * Returns the currently authenticated user.
     *
     * @return the authenticated user or null
     */
    public Userx getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findFirstByUsername(auth.getName()).orElse(null);
    }
}
