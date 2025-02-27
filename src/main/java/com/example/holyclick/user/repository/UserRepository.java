package com.example.holyclick.user.repository;

import com.example.holyclick.user.exception.UserNotFoundException;
import com.example.holyclick.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends CrudRepository<User, Integer> {
    boolean existsByUsername(String username);

    boolean existsByUsernameAndPassword(String username, String password);

    User getUserByUsername(String username);

    default User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return getUserByUsername(username);
        }

        throw new UserNotFoundException("User not found");
    }
}
