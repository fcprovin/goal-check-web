package com.fcprovin.goal.check.config.security;

import com.fcprovin.goal.check.domain.member.Member;
import com.fcprovin.goal.check.domain.member.MemberType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static java.util.Collections.singletonList;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final MemberType type;
    private final Collection<SimpleGrantedAuthority> authorities = singletonList(new SimpleGrantedAuthority("ROLE_USER"));

    @Builder
    public CustomUserDetails(Long id, String username, String password, MemberType type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public static CustomUserDetails of(Member member) {
        return CustomUserDetails.builder()
                .id(member.getId())
                .username(member.getName())
                .password(member.getPassword())
                .type(member.getType())
                .build();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
