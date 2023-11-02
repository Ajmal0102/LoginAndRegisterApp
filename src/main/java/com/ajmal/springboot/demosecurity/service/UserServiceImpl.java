package com.ajmal.springboot.demosecurity.service;

import com.ajmal.springboot.demosecurity.dao.UserRepository;
import com.ajmal.springboot.demosecurity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User existsByUserName(String userName) {
		Optional<User> user1 = userRepository.findById(userName);
		if (user1.isPresent()){
			return user1.get();
		}
		return null;
	}

	@Override
	public void save(User user) {

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		user.setRole("ROLE_USER");

		userRepository.save(user);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void deleteByUserName(String userName) {
		userRepository.deleteById(userName);
	}

	@Override
	public void saveUp(User user) {
		userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findById(userName).get();

		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

		SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(user.getRole());
		authorities.add(tempAuthority);

		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				authorities);
	}

}
