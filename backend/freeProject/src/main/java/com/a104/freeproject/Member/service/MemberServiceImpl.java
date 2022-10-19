package com.a104.freeproject.Member.service;

import com.a104.freeproject.Member.Jwt.TokenProvider;
import com.a104.freeproject.Member.entity.Authority;
import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.Member.repository.MemberRepository;
import com.a104.freeproject.Member.request.JoinRequest;
import com.a104.freeproject.Member.request.LoginRequest;
import com.a104.freeproject.Member.response.TokenResponse;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:application-secret.properties")
})
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Value("${spring.security.authorities_key}")
    private static final String AUTHORITIES_KEY = "";

    @Override
    public TokenResponse join(JoinRequest input) throws NotFoundException {

        if(memberRepository.existsByEmail(input.getEmail())) throw new NotFoundException("이미 사용 중인 이메일입니다.");
        if(memberRepository.existsByNickname(input.getNickname())) throw new NotFoundException("이미 사용 중인 닉네임입니다.");
        if(!pwCheck(input.getPassword())) throw new NotFoundException("비밀번호는 영문자, 숫자, 특수문자를 모두 포함한 8-20자 형식입니다.");

        Member member = new Member(input.getEmail(), passwordEncoder.encode(input.getPassword()), input.getNickname(), input.getHp());
        member.setAuthority(Authority.ROLE_USER);

        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        boolean tagFlag=true;
        while(tagFlag){
            String tmpTag = "";
            int idx = 0;
            for (int i = 0; i < 10; i++) {
                idx = (int) (charSet.length * Math.random());
                tmpTag += charSet[idx];
            }

            if(!memberRepository.existsByTag(tmpTag)) {
                tagFlag = false;
                member.setTag(tmpTag);
            }
        }

        memberRepository.save(member);

        return login(new LoginRequest(member.getEmail(), input.getPassword()));
    }

    @Override
    public TokenResponse login(LoginRequest input) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = input.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 토큰 발급
        return tokenProvider.generateTokenDto(authentication);
    }


    @Override
    public boolean emailCheck(String email) {
        if(memberRepository.existsByEmail(email)) return false;
        if(!Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?", email)) return false;
        return true;
    }

    @Override
    public boolean pwCheck(String pw) {
        return Pattern.matches("^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+])(?!.*[^a-zA-z0-9$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,20}$" , pw);
    }

    @Override
    public boolean nameCheck(String name) {
        if(memberRepository.existsByNickname(name)) return false;
        return true;
    }
}
