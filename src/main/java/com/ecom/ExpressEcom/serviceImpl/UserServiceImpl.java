package com.ecom.ExpressEcom.serviceImpl;

import com.ecom.ExpressEcom.constants.AppConstants;
import com.ecom.ExpressEcom.dto.UserDTO;
import com.ecom.ExpressEcom.entity.User;
import com.ecom.ExpressEcom.exception.DuplicateResourceException;
import com.ecom.ExpressEcom.exception.ResourceNotFoundException;
import com.ecom.ExpressEcom.exception.ValidationException;
import com.ecom.ExpressEcom.mapper.UserMapper;
import com.ecom.ExpressEcom.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl {

    @Autowired
    private UserRepo userRepository;


    public UserDTO findById(Integer id) {
        Objects.requireNonNull(id, "User ID cannot be null");

        return userRepository.findById(id)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(AppConstants.USER_NOT_FOUND, id)
                ));
    }


    public UserDTO findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new ValidationException(AppConstants.EMAIL_REQUIRED);
        }

        return userRepository.findByEmail(email)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(AppConstants.USER_EMAIL_NOT_FOUND, email)
                ));
    }


    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Transactional
    public UserDTO save(UserDTO userDTO) {
        // Note: Basic validation is now done with annotations
        // Here we only perform business rule validation

        // Check if email already exists
        if (userDTO.getEmail() != null && userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DuplicateResourceException(
                    String.format(AppConstants.EMAIL_ALREADY_EXISTS, userDTO.getEmail())
            );
        }

        User user = UserMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        log.info("User saved with ID: {}", savedUser.getUserId());

        return UserMapper.toDTO(savedUser);
    }


    @Transactional
    public UserDTO update(Integer id, UserDTO userDTO) {
        Objects.requireNonNull(id, "User ID cannot be null");

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(AppConstants.USER_NOT_FOUND, id)
                ));

        // Check if updating to an email that already exists for another user
        if (userDTO.getEmail() != null && !userDTO.getEmail().equals(existingUser.getEmail())
                && userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DuplicateResourceException(
                    String.format(AppConstants.EMAIL_ALREADY_EXISTS, userDTO.getEmail())
            );
        }

        // Update user fields
        User updatedUser = updateUserFields(existingUser, userDTO);
        User savedUser = userRepository.save(updatedUser);
        log.info("User updated with ID: {}", savedUser.getUserId());

        return UserMapper.toDTO(savedUser);
    }


    @Transactional
    public void delete(Integer id) {
        Objects.requireNonNull(id, "User ID cannot be null");

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format(AppConstants.USER_NOT_FOUND, id)
            );
        }

        userRepository.deleteById(id);
        log.info("User deleted with ID: {}", id);
    }

    private User updateUserFields(User existingUser, UserDTO userDTO) {
        // Update only non-null fields
        if (userDTO.getFirstName() != null) {
            existingUser.setFirstName(userDTO.getFirstName());
        }

        if (userDTO.getEmail() != null) {
            existingUser.setEmail(userDTO.getEmail());
        }

        if (userDTO.getDepartment() != null) {
            existingUser.setDepartment(userDTO.getDepartment());
        }

        if (userDTO.getProjectName() != null) {
            existingUser.setProjectName(userDTO.getProjectName());
        }

        // For credentials and addresses, we would need more complex logic
        // This is a simplified version
        return existingUser;
    }
}
