package com.ectosense.nightowl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Ectosense {

	public static void main(String[] args) {
		SpringApplication.run(Ectosense.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
//	@Autowired
//	public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository userRepository) throws Exception
//	{
//		builder.userDetailsService(email -> new CustomUserDetails(userRepository.findByEmail(email).get() ));
////		builder.userDetailsService(	new UserDetailsService() {
////			@Override
////			public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
////			{
////				return new CustomUserDetails( userRepository.findByEmail(email).get() );
////			}
////		});
//	}

}
