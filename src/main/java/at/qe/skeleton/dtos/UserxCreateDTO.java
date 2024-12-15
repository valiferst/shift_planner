package at.qe.skeleton.dtos;

import at.qe.skeleton.model.UserxRole;
import java.util.Set;

/**
 * Reduced data transfer object for the Userx Entity in the create endpoint.
 *
 * This class is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
public record UserxCreateDTO(
    String username,
    String password,
    String firstName,
    String lastName,
    String email,
    String phone,
    boolean enabled,
    Set<UserxRole> roles
) {}
