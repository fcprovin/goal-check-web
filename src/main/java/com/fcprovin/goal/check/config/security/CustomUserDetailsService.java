package com.fcprovin.goal.check.config.security;

import com.fcprovin.goal.check.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.fcprovin.goal.check.domain.member.MemberType.MEMBER;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return CustomUserDetails.of(memberRepository.findByNameAndType(username, MEMBER)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
    }
}
