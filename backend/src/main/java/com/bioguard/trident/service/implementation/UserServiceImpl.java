package com.bioguard.trident.service.implementation;

import com.bioguard.trident.dto.BatchRequestDto;
import com.bioguard.trident.dto.CenterRequestDto;
import com.bioguard.trident.entity.*;
import com.bioguard.trident.exception.TridentException;
import com.bioguard.trident.repository.*;
import com.bioguard.trident.service.UserService;
import com.bioguard.trident.utility.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private CenterRequestRepository centerRequestRepository;

    @Autowired
    private BatchRequestRepository batchRequestRepository;

    @Override
    public UserDetailsService userDetailsService () {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
                return userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User Not found"));
            }
        };
    }

    public CenterRequestDto requestCenter (CenterRequestDto centerRequestDto) throws TridentException {
        Optional<CenterRequest> optional = centerRequestRepository.findByCenterCodeAndUsername(centerRequestDto.getCenterCode(), centerRequestDto.getUsername());
        if(optional.isPresent()) {
            throw new TridentException("You have already requested same center");
        }

        Optional<UserDetails> optionalUser = userRepository.findByUsername(centerRequestDto.getUsername());
        User user;
        if (optionalUser.isPresent()) {
            UserDetails userDetails = optionalUser.get();
            if (userDetails instanceof User) {
                user = (User) userDetails;
            } else {
                throw new TridentException("User not found");
            }
        } else {
            throw new TridentException("User not found");
        }

        Optional<Center> optionalCenter = centerRepository.findByCenterCodeAndUserUsername(centerRequestDto.getCenterCode(), centerRequestDto.getUsername());
        if (optionalCenter.isPresent()) {
            throw new TridentException("Center is already registered");
        }

        CenterRequest centerRequest = Convertor.convertToCenterRequestEntity(centerRequestDto);
        centerRequest = centerRequestRepository.save(centerRequest);

        return Convertor.convertToCenterRequestDto(centerRequest);
    }

    @Override
    public BatchRequestDto requestBatch (BatchRequestDto batchRequestDto) throws TridentException {
        Optional<BatchRequest> optional = batchRequestRepository.findByBatchCodeAndCenterCode(batchRequestDto.getBatchCode(), batchRequestDto.getCenterCode());
        if(optional.isPresent()) {
            throw new TridentException("You have already requested same batch");
        }

        Optional<UserDetails> optionalUser = userRepository.findByUsername(batchRequestDto.getUsername());
        User user;
        if (optionalUser.isPresent()) {
            UserDetails userDetails = optionalUser.get();
            if (userDetails instanceof User) {
                user = (User) userDetails;
            } else {
                throw new TridentException("User not found");
            }
        } else {
            throw new TridentException("User not found");
        }

        Optional<Center> optionalCenter = centerRepository.findByCenterCodeAndUserUsername(batchRequestDto.getCenterCode(), batchRequestDto.getUsername());
        Center center = optionalCenter.orElseThrow(() -> new TridentException("Center not found"));

        Optional<Batch> optionalBatch = batchRepository.findByBatchCode(batchRequestDto.getBatchCode());
        if(optionalBatch.isPresent()) {
            throw new TridentException("Batch is already registered in this center");
        }

        BatchRequest batchRequest = Convertor.convertToBatchRequestEntity(batchRequestDto);
        batchRequest = batchRequestRepository.save(batchRequest);

        return Convertor.convertToBatchRequestDto(batchRequest);
    }
}
