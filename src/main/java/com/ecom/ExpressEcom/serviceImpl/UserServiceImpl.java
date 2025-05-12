package com.ecom.ExpressEcom.serviceImpl;

import com.ecom.ExpressEcom.constants.AppConstants;
import com.ecom.ExpressEcom.dto.UserDTO;
import com.ecom.ExpressEcom.entity.Address;
import com.ecom.ExpressEcom.entity.Credentials;
import com.ecom.ExpressEcom.entity.User;
import com.ecom.ExpressEcom.exception.DuplicateResourceException;
import com.ecom.ExpressEcom.exception.ResourceNotFoundException;
import com.ecom.ExpressEcom.exception.ValidationException;
import com.ecom.ExpressEcom.mapper.AddressMapper;
import com.ecom.ExpressEcom.mapper.CredentialsMapper;
import com.ecom.ExpressEcom.mapper.UserMapper;
import com.ecom.ExpressEcom.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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

        // Update all user fields including nested objects
        updateUserFields(existingUser, userDTO);

        // Save the updated user
        User savedUser = userRepository.save(existingUser);
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

    private void updateUserFields(User existingUser, UserDTO userDTO) {
        // Update basic fields
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

        // Update credentials if provided
        if (userDTO.getCredentialsDTO() != null) {
            Credentials updatedCredentials = CredentialsMapper.toEntity(userDTO.getCredentialsDTO());
            if (existingUser.getCredentials() == null) {
                // Create new credentials if none exist
                updatedCredentials.setUser(existingUser);
                existingUser.setCredentials(updatedCredentials);
            } else {
                // Update existing credentials
                Credentials existingCredentials = existingUser.getCredentials();
                if (updatedCredentials.getUserName() != null) {
                    existingCredentials.setUserName(updatedCredentials.getUserName());
                }
                if (updatedCredentials.getPazzword() != null) {
                    existingCredentials.setPazzword(updatedCredentials.getPazzword());
                }
                if (updatedCredentials.getRoleBasedAuthority() != null) {
                    existingCredentials.setRoleBasedAuthority(updatedCredentials.getRoleBasedAuthority());
                }
            }
        }

        // Update addresses if provided
        if (userDTO.getAddressDTOSet() != null && !userDTO.getAddressDTOSet().isEmpty()) {
            // Clear existing addresses and add new ones
            if (existingUser.getAddress() == null) {
                existingUser.setAddress(new HashSet<>());
            } else {
                existingUser.getAddress().clear();
            }

            // Convert DTOs to entities and add to user
            Set<Address> newAddresses = userDTO.getAddressDTOSet().stream()
                    .map(AddressMapper::toAddressEntity)
                    .collect(Collectors.toSet());

            // Set the user reference for each address
            newAddresses.forEach(address -> address.setUser(existingUser));
            existingUser.getAddress().addAll(newAddresses);
        }
    }
}
