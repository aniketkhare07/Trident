package com.bioguard.trident;

import com.bioguard.trident.entity.Role;
import com.bioguard.trident.entity.User;
import com.bioguard.trident.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class TridentApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(TridentApplication.class, args);
	}

	@Override
	public void run (String... args) throws Exception {
		Optional<List<User>> optional = userRepository.findByRole(Role.ADMIN);

		if(optional.isEmpty()) {
			User user = new User();

			user.setName("Admin");
			user.setUsername("admin");
			user.setPassword(new BCryptPasswordEncoder().encode("admin123"));
			user.setRole(Role.ADMIN);
			user.setIsActive(true);
			user.setContact("1234567890");

			userRepository.save(user);


		}
	}
}
