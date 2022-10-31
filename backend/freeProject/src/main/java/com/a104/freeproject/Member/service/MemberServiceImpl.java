package com.a104.freeproject.Member.service;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.Challenge.entity.ChlTime;
import com.a104.freeproject.Challenge.repository.ChallengeRepository;
import com.a104.freeproject.Challenge.repository.ChlTimeRepository;
import com.a104.freeproject.Challenge.response.ChlSimpleResponse;
import com.a104.freeproject.HashTag.entity.ChlTag;
import com.a104.freeproject.HashTag.entity.Hashtag;
import com.a104.freeproject.HashTag.repository.HashtagRepository;
import com.a104.freeproject.Log.entity.Log;
import com.a104.freeproject.Log.repository.LogRepository;
import com.a104.freeproject.Member.Jwt.TokenProvider;
import com.a104.freeproject.Member.entity.Authority;
import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.Member.repository.MemberRepository;
import com.a104.freeproject.Member.request.*;
import com.a104.freeproject.Member.response.*;
import com.a104.freeproject.PrtChl.entity.PrtChl;
import com.a104.freeproject.PrtChl.repository.PrtChlRepository;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.*;
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
    private final PrtChlRepository prtChlRepository;
    private final ChlTimeRepository chlTimeRepository;
    private final ChallengeRepository challengeRepository;
    private final LogRepository logRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    private static final String AUTHORITIES_KEY = "auth";

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
    public boolean emailCheck(String email) throws NotFoundException {
        email = email.replaceAll("\"","");
        if(memberRepository.existsByEmail(email)) throw new NotFoundException("중복 이메일 >> "+email);
        if(!Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?", email)) throw new NotFoundException("이메일 형식이 잘못됨 >> "+email);
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

    @Override
    public Member findEmailbyToken(HttpServletRequest req) throws NotFoundException {
        try{
//            System.out.println(req.getHeader("Authorization"));
            String token = (String) req.getHeader("Authorization");
//            System.out.println(">>>>>>>>>>>>>>>>> token = " + token);
            Claims claim = tokenProvider.parseClaims(token);
//            System.out.println(">>>>>>>>>>>>>>>>>>>> 클레임 잘 뽑음~~~~~");
            String email = claim.get("sub").toString();
//            System.out.println(">>>>>>>>>>>>>>>>>>>> email = " + email);

            if(!memberRepository.existsByEmail(email)) throw new NotFoundException("유효한 회원이 아닙니다.");
            Member member = memberRepository.findByEmail(email);



            if(member.isDelete()) throw new NotFoundException("유효한 회원이 아닙니다.");
            return member;

        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new NotFoundException("토큰이 넘어오지 않았거나, 유효한 토큰이 아닙니다.");
        }
    }

    @Override
    public boolean regWatch(WatchRequest input, HttpServletRequest req) throws NotFoundException {

        Member member = findEmailbyToken(req);
        member.setWatchInfo(input.getSerialNum());
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean changeNick(NickRequest input, HttpServletRequest req) throws NotFoundException {
        try{
            Member member = findEmailbyToken(req);
            member.setNickname(input.getNickname());
            memberRepository.save(member);
            return true;
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public boolean changePhone(PhoneRequest input, HttpServletRequest req) throws NotFoundException {
        try{
            Member member = findEmailbyToken(req);
            member.setHp(input.getPhoneNumber());
            memberRepository.save(member);
            return true;
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public boolean delWatch(WatchRequest input, HttpServletRequest req) throws NotFoundException {
        try{
            Member member = findEmailbyToken(req);
            member.setWatchInfo("");
            memberRepository.save(member);
            return true;
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public MyInfoResponse getMyInfo(HttpServletRequest req) throws NotFoundException {
        try{
            Member member = findEmailbyToken(req);
            return MyInfoResponse.builder().nickName(member.getNickname()).email(member.getEmail()).build();
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public EmailResponse getUserInfo(NickRequest input) {
        try{
            Member member = memberRepository.findByNickname(input.getNickname());
            return EmailResponse.builder().email(member.getEmail()).build();
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<ChlSimpleResponse> getUserChallenge(Pageable pageable, HttpServletRequest req) throws NotFoundException {
        Member member;
        try{
            member = findEmailbyToken(req);
            List<PrtChl> chlList = prtChlRepository.findByMember(member, pageable);
            List<ChlSimpleResponse> output = new LinkedList<>();

            for (PrtChl p : chlList){
                Challenge c = p.getChallenge();
                ChlTime cTime = chlTimeRepository.findByChallenge(c);

                List<String> tags = new LinkedList<>();
                for (ChlTag t : c.getTagList()){
                    tags.add(t.getHashtag().getName());
                }

                output.add(ChlSimpleResponse.builder()
                        .ChlId(c.getId()).title(c.getTitle()).imgurl(c.getImgurl()).isWatch(c.isWatch())
                        .roomtype(c.getRoomtype()).startTime(cTime.getStartTime().toLocalDateTime().toLocalDate())
                        .endTime(cTime.getEndTime().toLocalDateTime().toLocalDate()).tags(tags).build());
            }

            return output;
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public int getMyChlTotalSum(Pageable pageable, HttpServletRequest req) throws NotFoundException {
        Member member;
        try{
            member = findEmailbyToken(req);
            Page<PrtChl> page = prtChlRepository.findByMember(pageable,member);
            return page.getTotalPages();
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<ChlSimpleResponse> getMemberChallenge(Pageable pageable, NickRequest input) throws NotFoundException {
        Member member;
        try{
            member = memberRepository.findByNickname(input.getNickname());
            List<PrtChl> chlList = prtChlRepository.findByMember(member, pageable);
            List<ChlSimpleResponse> output = new LinkedList<>();

            for (PrtChl p : chlList){
                Challenge c = p.getChallenge();
                ChlTime cTime = chlTimeRepository.findByChallenge(c);

                List<String> tags = new LinkedList<>();
                for (ChlTag t : c.getTagList()){
                    tags.add(t.getHashtag().getName());
                }

                output.add(ChlSimpleResponse.builder()
                        .ChlId(c.getId()).title(c.getTitle()).imgurl(c.getImgurl()).isWatch(c.isWatch())
                        .roomtype(c.getRoomtype()).startTime(cTime.getStartTime().toLocalDateTime().toLocalDate())
                        .endTime(cTime.getEndTime().toLocalDateTime().toLocalDate()).tags(tags).build());
            }

            return output;
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public int getUserChlTotalSum(Pageable pageable, NickRequest input) throws NotFoundException {
        Member member;
        try{
            member = memberRepository.findByNickname(input.getNickname());
            Page<PrtChl> page = prtChlRepository.findByMember(pageable,member);
            return page.getTotalPages();
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public ChlSimpleStatResponse getChlSimpleStatistics(HttpServletRequest req) throws NotFoundException {
        Member member;
        try{
            member = findEmailbyToken(req);
            List<PrtChl> chlList = member.getChallenges();
            int totalChl = chlList.size();
            int ingChl=0, finChl=0, sucChl=0, failChl=0;
            for(PrtChl p : chlList){
                if(p.is_fin()) finChl++;
                else ingChl++;

                if((1.0*p.getSucDay())/(p.getSucDay()+p.getFailDay())>=0.75) sucChl++;
                else failChl++;
            }

            return ChlSimpleStatResponse.builder()
                    .totalChl(totalChl).ingChl(ingChl).failChl(failChl).sucChl(sucChl).finChl(finChl).build();
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<MonthChlResponse> monthChlInfo(Long chlId, String year, String month, HttpServletRequest req) throws NotFoundException {
        Member member;
        try{
            member = findEmailbyToken(req);
            if(!challengeRepository.existsById(chlId)) throw new NotFoundException("챌린지가 존재하지 않습니다.");
            Challenge c = challengeRepository.findById(chlId).get();
            if(!prtChlRepository.existsByChallengeAndMember(c,member))
                throw new NotFoundException("해당 멤버는 이 챌린지에 참여하지 않았습니다.");

            PrtChl p = prtChlRepository.findByChallengeAndMember(c,member);
            LocalDate st = LocalDate.parse(year+"-"+month+"-01");
            LocalDate ed = st.withDayOfMonth(st.lengthOfMonth());
            List<Log> logs = p.getLogs();

            List<MonthChlResponse> output = new LinkedList<>();
            for (Log log: logs){
                LocalDate date = log.getDate();
                if (date.compareTo(st) >= 0 || date.compareTo(ed) <= 0) {
                    output.add(MonthChlResponse.builder().date(date).isDone(log.isFin()).build());
                }
            }

            return output;
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<TodoListInfoResponse> getTodoListInfo(DayRequest day, HttpServletRequest req) throws NotFoundException {
        Member member;
        try{
            member = findEmailbyToken(req);
            List<PrtChl> chlList = member.getChallenges();
            List<TodoListInfoResponse> todoListInfo = new LinkedList<>();
            for(PrtChl p : chlList){
                Log log = logRepository.findByPrtChlAndDate(p,day.getDay());
                Challenge c = p.getChallenge();
                todoListInfo.add(TodoListInfoResponse.builder().chlId(c.getId())
                        .title(c.getTitle()).isDone(log.isFin()).build());
            }
            return todoListInfo;
        } catch (Exception e){
            throw e;
        }
    }


}
