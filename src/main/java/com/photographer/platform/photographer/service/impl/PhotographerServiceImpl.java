package com.photographer.platform.photographer.service.impl;

import com.photographer.platform.exception.BadRequestException;
import com.photographer.platform.exception.ResourceNotFoundException;
import com.photographer.platform.photographer.dto.request.CreatePhotographerRequest;
import com.photographer.platform.photographer.dto.response.PhotographerResponse;
import com.photographer.platform.photographer.entity.Photographer;
import com.photographer.platform.photographer.repository.PhotographerRepository;
import com.photographer.platform.photographer.service.PhotographerService;
import com.photographer.platform.user.entity.User;
import com.photographer.platform.user.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class PhotographerServiceImpl implements PhotographerService {

    private final PhotographerRepository photographerRepository;
    private final UserRepository userRepository;

    public PhotographerServiceImpl(
            PhotographerRepository photographerRepository,
            UserRepository userRepository) {

        this.photographerRepository = photographerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PhotographerResponse createProfile(
            String email,
            CreatePhotographerRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (photographerRepository.existsByUser(user)) {
            throw new BadRequestException("Photographer profile already exists.");
        }

        Photographer photographer = new Photographer();

        photographer.setUser(user);
        photographer.setStudioName(request.getStudioName());
        photographer.setBio(request.getBio());
        photographer.setExperience(request.getExperience());
        photographer.setCity(request.getCity());
        photographer.setState(request.getState());
        photographer.setCountry(request.getCountry());
        photographer.setStartingPrice(request.getStartingPrice());

        photographer.setVerified(false);
        photographer.setProfileImage(null);

        Photographer saved = photographerRepository.save(photographer);

        return PhotographerResponse.builder()
                .id(saved.getId())
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .studioName(saved.getStudioName())
                .bio(saved.getBio())
                .experience(saved.getExperience())
                .city(saved.getCity())
                .state(saved.getState())
                .country(saved.getCountry())
                .startingPrice(saved.getStartingPrice())
                .profileImage(saved.getProfileImage())
                .verified(saved.isVerified())
                .build();
    }

    @Override
    public PhotographerResponse getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Photographer photographer = photographerRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Photographer profile not found"));

        return PhotographerResponse.builder()
                .id(photographer.getId())
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .studioName(photographer.getStudioName())
                .bio(photographer.getBio())
                .experience(photographer.getExperience())
                .city(photographer.getCity())
                .state(photographer.getState())
                .country(photographer.getCountry())
                .startingPrice(photographer.getStartingPrice())
                .profileImage(photographer.getProfileImage())
                .verified(photographer.isVerified())
                .build();
    }
}