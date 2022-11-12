package com.a104.freeproject.Challenge.service;

import com.a104.freeproject.Category.entity.Category;
import com.a104.freeproject.Category.entity.DetailCategory;
import com.a104.freeproject.Category.repository.CategoryRepository;
import com.a104.freeproject.Category.repository.DetailCategoryRepository;
import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.Challenge.entity.ChlTime;
import com.a104.freeproject.Challenge.repository.ChallengeRepository;
import com.a104.freeproject.Challenge.repository.ChlTimeRepository;
import com.a104.freeproject.Challenge.request.JudgeRequest;
import com.a104.freeproject.Challenge.request.registerRequest;
import com.a104.freeproject.Challenge.response.*;
import com.a104.freeproject.HashTag.entity.ChlTag;
import com.a104.freeproject.HashTag.repository.HashtagRepository;
import com.a104.freeproject.HashTag.service.ChltagServiceImpl;
import com.a104.freeproject.Log.entity.Log;
import com.a104.freeproject.Log.repository.LogRepository;
import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.Member.repository.MemberRepository;
import com.a104.freeproject.Member.response.MonthChlResponse;
import com.a104.freeproject.Member.service.MemberServiceImpl;
import com.a104.freeproject.PrtChl.entity.PrtChl;
import com.a104.freeproject.PrtChl.repository.PrtChlRepository;
import com.a104.freeproject.PrtChl.service.PrtChlServiceImpl;
import com.a104.freeproject.Statbpm.entity.Statbpm;
import com.a104.freeproject.Statbpm.repository.StatbpmRepository;
import com.a104.freeproject.Statbpm.response.BpmMiddleInterface;
import com.a104.freeproject.Statgps.entity.Statgps;
import com.a104.freeproject.Statgps.repository.StatgpsRepository;
import com.a104.freeproject.Statgps.response.GpsResultResponse;
import com.a104.freeproject.Statgps.service.StatgpsServiceImpl;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final CategoryRepository categoryRepository;
    private final DetailCategoryRepository detailRepository;
    private  final MemberRepository memberRepository;
    private final HashtagRepository hashtagRepository;
    private final ChltagServiceImpl chltagService;
    private final PrtChlRepository prtChlRepository;
    private final MemberServiceImpl memberService;
    private final ChlTimeServiceImpl chlTimeService;
    private final PrtChlServiceImpl prtChlService;
    private final LogRepository logRepository;
    private final StatbpmRepository bpmRepository;
    private final StatgpsRepository gpsRepository;
    private final ChlTimeRepository chlTimeRepository;
    private final static int  EARTH_RADIUS = 6371;

    @Override
    public Long register(registerRequest input, HttpServletRequest req) throws NotFoundException {

        Member member;

        try{
            member = memberService.findEmailbyToken(req);
        } catch (Exception e){
            throw e;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
        TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
        sdf.setTimeZone(tz);
        input.setStartTime(Timestamp.valueOf(sdf.format(input.getStartTime())));
        input.setEndTime(Timestamp.valueOf(sdf.format(input.getEndTime())));

        if(!categoryRepository.existsById(input.getCategoryId())) throw new NotFoundException("카테고리를 다시 입력해주세요.");
        Category category = categoryRepository.findById(input.getCategoryId()).get();
        Challenge challenge;

        if(category.getName().equals("기타")){ // 세부 카테고리가 없음
            challenge = Challenge.builder().title(input.getTitle()).contents(input.getContents()).imgurl(input.getImgurl()).
                    watch(input.isWatch()).roomtype(input.getRoomtype()).password(input.getPassword()).writer(member.getId())
                    .limitPeople(input.getLimitPeople()).alarm(input.getAlarm())
                    .goal(input.getGoal()).unit(input.getUnit()).category(category)
                    .build();
        }
        else{ // 세부 카테고리가 있음
            if(!detailRepository.existsByCategoryAndId(category,input.getDetailCategoryId())) throw new NotFoundException("세부 카테고리를 다시 입력해주세요.");
            DetailCategory detail = detailRepository.findById(input.getDetailCategoryId()).get();

            challenge = Challenge.builder().title(input.getTitle()).contents(input.getContents()).imgurl(input.getImgurl()).
                    watch(input.isWatch()).roomtype(input.getRoomtype()).password(input.getPassword()).writer(member.getId())
                    .limitPeople(input.getLimitPeople()).alarm(input.getAlarm())
                    .goal(input.getGoal()).unit(input.getUnit()).category(category).detailCategory(detail)
                    .build();
        }

        // 시간 체크하고 지금시간보다 전이면(입력하느라 시간 지난거 등을 생각해서)
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Timestamp now = Timestamp.valueOf(sdf.format(time));

        if(input.getStartTime().equals(now) || input.getStartTime().before(now)) challenge.setStatus(1);

        Long cid = challengeRepository.save(challenge).getId();
        Challenge c = challengeRepository.findById(cid).get();

        //해시태그 추가
        chltagService.addChallengeTag(c,input.getTagList());

        //ChlTime 추가
        chlTimeService.addRow(c,input.getStartTime(), input.getEndTime());

        //PrtChl 추가
        prtChlService.participate(c.getId(),req, input.getAlarmDir());

        //kkobak 값 업데이트(임시로 만들었어요)
        PrtChl ptrChl = prtChlRepository.findByChallengeAndMember(challenge,member);
        if(input.getKkobak()==0||input.getKkobak()==1) {
            ptrChl.setKkobak(input.getKkobak());
        }
        
        return cid;
    }

    @Override
    public List<ChlUserNameResponse> getUserInfo(Long chlId) throws NotFoundException {
        if(!challengeRepository.existsById(chlId)) throw new NotFoundException("챌린지가 존재하지 않습니다.");
        Challenge c = challengeRepository.findById(chlId).get();

        List<PrtChl> chlList = c.getChlList();
        List<ChlUserNameResponse> output = new LinkedList<>();

        for (PrtChl p:chlList){
            output.add(ChlUserNameResponse.builder().nickname(p.getMember().getNickname()).build());
        }

        return output;
    }

    @Override
    public List<ChlUserSimpleStatResponse> getUserSimpleStatInfo(Long chlId) throws NotFoundException {
        if(!challengeRepository.existsById(chlId)) throw new NotFoundException("챌린지가 존재하지 않습니다.");
        Challenge c = challengeRepository.findById(chlId).get();

        List<PrtChl> chlList = c.getChlList();
        List<ChlUserSimpleStatResponse> output = new LinkedList<>();

        for (PrtChl p:chlList){
            Member member = p.getMember();
            double ratio = (1.0*p.getSucDay())/(p.getSucDay()+p.getFailDay());
            output.add(ChlUserSimpleStatResponse.builder()
                    .nickname(member.getNickname())
                    .imgurl(member.getImgurl())
                    .sucRatio(Math.round(ratio*1000)/1000.0)
                    .sucCnt(p.getSucDay())
                    .failCnt(p.getFailDay())
                    .build());
        }

        return output;
    }

    @Override
    public List<ChallengeListResponse> getChallengePageList(int page) throws NotFoundException{
        PageRequest pageRequest = PageRequest.of(page-1,6, Sort.Direction.DESC, "id");
        Page<Challenge> challengePage = challengeRepository.findAllChallenge(pageRequest);
        List<Challenge> content = challengePage.getContent();

        return makeResponse(content);
    }
    @Override
    public List<ChallengeListResponse> getChallengePageListByCategory(int page, Long id) throws NotFoundException{
        PageRequest pageRequest = PageRequest.of(page-1,6, Sort.Direction.DESC, "id");
        Page<Challenge> challengePage = challengeRepository.findAllByCategoryId(id, pageRequest);
        List<Challenge> content = challengePage.getContent();

        return makeResponse(content);
    }

    @Override
    public List<ChallengeListResponse> getChallengePageListByDetailCategory(int page, Long id) throws NotFoundException{
        PageRequest pageRequest = PageRequest.of(page-1,6, Sort.Direction.DESC, "id");
        Page<Challenge> challengePage = challengeRepository.findAllByDetailCategoryId(id, pageRequest);
        List<Challenge> content = challengePage.getContent();

        return makeResponse(content);
    }
    @Override
    public List<ChallengeListResponse> getChallengePageListByTitle(int page, String word) throws NotFoundException{
        PageRequest pageRequest = PageRequest.of(page-1,6, Sort.Direction.DESC, "id");
        Page<Challenge> challengePage = challengeRepository.findByTitleContains(word, pageRequest);
        List<Challenge> content = challengePage.getContent();

        return makeResponse(content);
    }

    @Override
    public List<ChallengeListResponse> getChallengePageListByNickName(int page, String nickName) throws NotFoundException{
        PageRequest pageRequest = PageRequest.of(page-1,6, Sort.Direction.DESC, "id");
        if(!memberRepository.existsByNickname(nickName)) return new LinkedList<ChallengeListResponse>();
        Long writerId = memberRepository.findByNickname(nickName).getId();
        Page<Challenge> challengePage = challengeRepository.findByWriter(writerId, pageRequest);
        List<Challenge> content = challengePage.getContent();

        return makeResponse(content);
    }

    @Override
    public List<ChallengeListResponse> getChallengePageListByTag(int page, String tag) throws NotFoundException{
        PageRequest pageRequest = PageRequest.of(page-1,6, Sort.Direction.DESC, "id");
        if(!hashtagRepository.existsByName(tag)) return new LinkedList<ChallengeListResponse>();
        List<ChlTag> chlTags= hashtagRepository.findByName(tag).getChlTags();
        //페이지네이션용
        List<Long> challengeIdList = new ArrayList<>();
        for (ChlTag chlTag : chlTags) {
            challengeIdList.add(chlTag.getChallenge().getId());
        }
        Page<Challenge> challengePage = challengeRepository.findByIdIn(challengeIdList, pageRequest);
        List<Challenge> content = challengePage.getContent();

        return makeResponse(content);
    }

    @Override
    public ChallengeListResponse getChallenge(Long id) throws NotFoundException{
        if(!challengeRepository.existsById(id)) throw new NotFoundException("존재하지 않는 챌린지 입니다.");
        Challenge c = challengeRepository.findById(id).get();
        List<ChlTag> tagList= c.getTagList();
        List<String> tagListName = new ArrayList<>();
        for (ChlTag a: tagList){
            tagListName.add(a.getHashtag().getName());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");

        ChallengeListResponse result = ChallengeListResponse.builder()
                .id(c.getId())
                .categoryId(c.getCategory().getId())
                .detailCategoryId(c.getDetailCategory().getId())
                .writer(c.getWriter())
                .title(c.getTitle())
                .contents(c.getContents())
                .imgurl(c.getImgurl())
                .watch(c.isWatch())
                .roomtype(c.getRoomtype())
                .password(c.getPassword())
                .limitPeolple(c.getLimitPeople())
                .currentNum(c.getCurrentNum())
                .alarm(c.getAlarm())
                .goal(c.getGoal())
                .unit(c.getUnit())
                .isFin(c.isFin())
                .nickName(memberRepository.findById(c.getWriter()).get().getNickname())
                .startTime(c.getChlTime().getStartTime())
                .endTime(c.getChlTime().getEndTime())
                .tagList(tagListName)
                .build();

        return result;
    }

    @Override
    public boolean checkPassword(Long id, String password) throws  NotFoundException{
        if(!challengeRepository.existsById(id)) throw new NotFoundException("해당 방이 없습니다.");
        Challenge challenge = challengeRepository.findById(id).get();
        if(password.equals(challenge.getPassword())){
            return true;
        }
        return false;
     }

    @Override
    public int getChallengePageCnt(Pageable pageable) {
        Page<Challenge> page = challengeRepository.findAllChallengeSet(pageable);
        return page.getTotalPages();
    }

    @Override
    public List<String> findDoneDate(long chlId, int year, int month, HttpServletRequest req) throws NotFoundException {

        Member m = memberService.findEmailbyToken(req);
        Challenge c = challengeRepository.findById(chlId).get();
        PrtChl p;
        try{
            p = prtChlRepository.findByChallengeAndMember(c,m);
        }catch (Exception e){
            throw e;
        }

        LocalDate st = LocalDate.parse(year+"-"+month+"-01");
        LocalDate ed = st.withDayOfMonth(st.lengthOfMonth());
        List<Log> logs = p.getLogs();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
        TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
        sdf.setTimeZone(tz);

        List<String> output = new LinkedList<>();
        for (Log log: logs){
            LocalDate date = log.getDate();
            if (date.compareTo(st) >= 0 && date.compareTo(ed) <= 0 && log.isFin()) {
                String successDate = log.getDate().toString();
                output.add(successDate);
            }
        }

        return output;
    }

    @Override
    public List<useWatchResponse> findWatchUse(boolean useWatch, Pageable pageable, HttpServletRequest req) throws NotFoundException {
        Member m = memberService.findEmailbyToken(req);
        List<PrtChl> prtList = prtChlRepository.findAllByWatchAndMember(useWatch, pageable, m);

        List<Challenge> chlList = new LinkedList<>();
        for(PrtChl prt : prtList){
            chlList.add(prt.getChallenge());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
        TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
        sdf.setTimeZone(tz);

        List<useWatchResponse> output = new LinkedList<>();
        for(Challenge c : chlList){

            List<String> tagList = new LinkedList<>();
            for(ChlTag tag : c.getTagList()){
                tagList.add(tag.getHashtag().getName());
            }

            output.add(useWatchResponse.builder()
                    .id(c.getId()).categoryId(c.getCategory().getId())
                    .detailCategoryId(c.getDetailCategory().getId())
                    .writer(c.getWriter()).title(c.getTitle()).contents(c.getContents()).imgurl(c.getImgurl())
                    .roomtype(c.getRoomtype()).password(c.getPassword())
                    .limitPeople(c.getLimitPeople()).currentNum(c.getCurrentNum()).alarm(c.getAlarm())
                    .goal(c.getGoal()).unit(c.getUnit())
                    .startTime(c.getChlTime().getStartTime())
                    .endTime(c.getChlTime().getEndTime()).tagList(tagList).build());
        }

        return output;
    }

    @Override
    public int findWatchCnt(boolean useWatch, Pageable pageable, HttpServletRequest req) throws NotFoundException {
        Member m = memberService.findEmailbyToken(req);
        List<PrtChl> totalList = prtChlRepository.findAllByWatchAndMember(m,useWatch);
        int size = totalList.size();
        List<PrtChl> prtList = prtChlRepository.findAllByWatchAndMember(useWatch, pageable, m);

        if(size%prtList.size()!=0) return (size/prtList.size())+1;
        else return (size/prtList.size());
    }

    @Override
    public boolean participateChl(Long chlId, int alarmType, HttpServletRequest req) throws NotFoundException {
        Member member = memberService.findEmailbyToken(req);
        Challenge c = challengeRepository.findById(chlId).get();


        if(c.isFin()||c.getStatus()==2) throw new NotFoundException("종료된 챌린지입니다.");

        if(prtChlRepository.existsByChallengeAndMember(c,member))
            throw new NotFoundException("이미 진행 중인 챌린지입니다.");

        if(c.getCurrentNum()==c.getLimitPeople())
            throw new NotFoundException("챌린지 참여 인원이 다 찼습니다.");

        prtChlService.participate(chlId,req,alarmType);
        c.setCurrentNum(c.getCurrentNum()+1);
        challengeRepository.save(c);
        return true;
    }

    @Override
    public boolean findChlDone(Long chlId, HttpServletRequest req) throws NotFoundException {

        Member member = memberService.findEmailbyToken(req);
        if(!challengeRepository.existsById(chlId)) throw new NotFoundException("존재하지 않는 챌린지입니다.");
        Challenge c = challengeRepository.findById(chlId).get();

        if(c.isFin()||c.getStatus()==2) throw new NotFoundException("종료된 챌린지입니다.");

        if(!prtChlRepository.existsByChallengeAndMember(c,member))
            throw new NotFoundException("참여하지 않은 챌린지 입니다.");

        PrtChl p = prtChlRepository.findByChallengeAndMember(c,member);
        if(p.is_fin()) throw new NotFoundException("종료된 챌린지입니다.");

        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));

        if(!logRepository.existsByPrtChlAndDate(p,now))
            throw new NotFoundException("해당 챌린지의 당일 로그가 존재하지 않습니다.");
        Log log = logRepository.findByPrtChlAndDate(p,now);

        return log.isFin();
    }

    @Override
    public boolean judgeDone(JudgeRequest input, HttpServletRequest req) throws NotFoundException {
        Member member = memberService.findEmailbyToken(req);

        if(!challengeRepository.existsById(input.getCid())) throw new NotFoundException("존재하지 않는 챌린지입니다.");
        Challenge c = challengeRepository.findById(input.getCid()).get();

        if(c.isFin()||c.getStatus()==2) throw new NotFoundException("종료된 챌린지입니다.");

        if(!prtChlRepository.existsByChallengeAndMember(c,member))
            throw new NotFoundException("참여하지 않은 챌린지 입니다.");

        PrtChl p = prtChlRepository.findByChallengeAndMember(c,member);
        if(p.is_fin()) throw new NotFoundException("종료된 챌린지입니다.");

        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));

        if(!logRepository.existsByPrtChlAndDate(p,now))
            throw new NotFoundException("해당 챌린지의 당일 로그가 존재하지 않습니다.");
        Log log = logRepository.findByPrtChlAndDate(p,now);
        if (log.isFin()) return true;  // 이미 진행한 챌린지의 경우 판단하지 않고 끝내기

        Long dc = c.getDetailCategory().getId();

        if(dc==1 || dc==2){ // 달리기 KM, 걷기 KM
            System.out.println("dc = " + dc);

            int goal = c.getGoal();
            List<Statgps> gpsList = gpsRepository.findByChkTimeAndPrtChl(p,input.getStartTime());

            double dist = 0;
            for (int i = 0; i < gpsList.size()-1; i++) {
                Statgps s1 = gpsList.get(i);
                Statgps s2 = gpsList.get(i+1);
                dist += getDistance(s1.getLat(),s1.getLng(),s2.getLat(),s2.getLng());
            }

            if(dist >= goal) { // 성공
                if(!log.isFin()){ // 실패 -> 성공
                    log.setFin(true);
                    log.setCnt(log.getCnt()+(int)dist);
                    logRepository.save(log);

                    p.setFailDay(p.getFailDay()-1);
                    p.setSucDay(p.getSucDay()+1);
                    prtChlRepository.save(p);
                }

                for(Statgps s : gpsList){
                    s.setSuccess(true);
                    gpsRepository.save(s);
                }
            }
            logRepository.save(log);

            System.out.println("현재 log 상태: cnt = "+
                    log.getCnt()+", 성공일 = "+p.getSucDay()+", 실패일 = "+p.getFailDay());
            return log.isFin();
        }
        else if (dc==3){ // 명상 분
            System.out.println("dc3 = " + dc);
            List<Statbpm> bpmList = bpmRepository.findByChkTimeAndPrtChl(p,input.getStartTime());

            // bpm 기준 100 이하 : 성공, 시간은 워치, 모바일에서 막아줌
            for(Statbpm s : bpmList){
                if(s.getBpm()>100) return false;
            }

            // 성공한 명상
            System.out.println("성공한 명상");
            for(Statbpm s : bpmList){
                s.setSuccess(true);
                bpmRepository.save(s);
            }

            if(!log.isFin()){ // 실패 -> 성공
                log.setFin(true);
                log.setCnt(log.getCnt()+c.getGoal());
                logRepository.save(log);

                p.setFailDay(p.getFailDay()-1);
                p.setSucDay(p.getSucDay()+1);
                prtChlRepository.save(p);
            }

            System.out.println("현재 log 상태: cnt = "+
                    log.getCnt()+", 성공일 = "+p.getSucDay()+", 실패일 = "+p.getFailDay());

            return log.isFin();
        }
        else if (dc==4 || dc==5 || dc==6){ // 물마시기 회, 영양제 먹기 회, 일어서기 회
            System.out.println("dc = " + dc);
            int goal = c.getGoal();

            if(log.getCnt()+1>=goal){ // 성공
                if(!log.isFin()) { // 실패 -> 성공
                    log.setFin(true);
                    p.setSucDay(p.getSucDay()+1);
                    p.setFailDay(p.getFailDay()-1);
                    prtChlRepository.save(p);
                }
            }
            else{ // 실패
                if(log.isFin()){ // 성공 -> 실패
                    log.setFin(false);
                    p.setSucDay(p.getSucDay()-1);
                    p.setFailDay(p.getFailDay()+1);
                    prtChlRepository.save(p);
                }
            }
            log.setCnt(log.getCnt()+1);
            logRepository.save(log);
            System.out.println("현재 log 상태: cnt = "+
                    log.getCnt()+", 성공일 = "+p.getSucDay()+", 실패일 = "+p.getFailDay());

            // --------------------------------

            return log.isFin();
        }
        else {  //dc==7, 출석 경도,위도
            System.out.println("dc7 = " + dc);

            String[] str = c.getUnit().split(","); // 경도 위도
            double d = getDistance(input.getLat(), input.getLng(), str[1], str[0]);

            System.out.println("거리 = " + d);

            if(d<100) {
                log.setFin(true);
                logRepository.save(log);
                if(!log.isFin()){
                    p.setSucDay(p.getSucDay()+1);
                    p.setFailDay(p.getFailDay()-1);
                    prtChlRepository.save(p);
                }
                return true;
            }
            return false;
        }
    }

    @Override
    public CntResponse chkCid(Long cid, HttpServletRequest req) throws NotFoundException {
        Member member = memberService.findEmailbyToken(req);

        if(!challengeRepository.existsById(cid)) throw new NotFoundException("존재하지 않는 챌린지입니다.");
        Challenge c = challengeRepository.findById(cid).get();

        if(c.isFin()||c.getStatus()==2) throw new NotFoundException("종료된 챌린지입니다.");

        if(!prtChlRepository.existsByChallengeAndMember(c,member))
            throw new NotFoundException("참여하지 않은 챌린지 입니다.");

        PrtChl p = prtChlRepository.findByChallengeAndMember(c,member);
        if(p.is_fin()) throw new NotFoundException("종료된 챌린지입니다.");

        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));

        if(!logRepository.existsByPrtChlAndDate(p,now))
            throw new NotFoundException("해당 챌린지의 당일 로그가 존재하지 않습니다.");
        Log log = logRepository.findByPrtChlAndDate(p,now);

        return CntResponse.builder()
                .cnt(log.getCnt()).done(log.isFin()).goal(c.getGoal())
                .build();
    }

    @Override
    public void changeCnt(Long cid, int cnt, HttpServletRequest req) throws NotFoundException {
        Member member = memberService.findEmailbyToken(req);

        if(!challengeRepository.existsById(cid)) throw new NotFoundException("존재하지 않는 챌린지입니다.");
        Challenge c = challengeRepository.findById(cid).get();

        if(c.isFin()||c.getStatus()==2) throw new NotFoundException("종료된 챌린지입니다.");

        if(!prtChlRepository.existsByChallengeAndMember(c,member))
            throw new NotFoundException("참여하지 않은 챌린지 입니다.");

        PrtChl p = prtChlRepository.findByChallengeAndMember(c,member);
        if(p.is_fin()) throw new NotFoundException("종료된 챌린지입니다.");

        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));

        if(!logRepository.existsByPrtChlAndDate(p,now))
            throw new NotFoundException("해당 챌린지의 당일 로그가 존재하지 않습니다.");
        Log log = logRepository.findByPrtChlAndDate(p,now);

        log.setCnt(cnt);
        if(cnt>=c.getGoal()){ // 성공
            if(!log.isFin()) { // 실패 -> 성공
                log.setFin(true);
                p.setSucDay(p.getSucDay()+1);
                p.setFailDay(p.getFailDay()-1);
                prtChlRepository.save(p);
            }
        }
        else{ // 실패
            if(log.isFin()){ // 성공 -> 실패
                log.setFin(false);
                p.setSucDay(p.getSucDay()-1);
                p.setFailDay(p.getFailDay()+1);
                prtChlRepository.save(p);
            }
        }
        logRepository.save(log);
        System.out.println("현재 log 상태: cnt = "+
                log.getCnt()+", 성공일 = "+p.getSucDay()+", 실패일 = "+p.getFailDay());
    }

    @Override
    public List<ChlSimpleResponse> getGroupList(Pageable pageable) throws NotFoundException {

        List<Challenge> chlList = challengeRepository.findByLimitPeople(pageable);
        List<ChlSimpleResponse> output = new LinkedList<>();

        for(Challenge c : chlList){

            ChlTime cTime = chlTimeRepository.findByChallenge(c);

            List<String> tags = new LinkedList<>();
            for (ChlTag t : c.getTagList()){
                tags.add(t.getHashtag().getName());
            }

            output.add(ChlSimpleResponse.builder()
                    .ChlId(c.getId()).title(c.getTitle()).imgurl(c.getImgurl()).isWatch(c.isWatch())
                    .roomtype(c.getRoomtype()).startTime(cTime.getStartTime().toLocalDateTime().toLocalDate())
                    .endTime(cTime.getEndTime().toLocalDateTime().toLocalDate()).tags(tags)
                    .build());
        }

        return output;
    }

    @Override
    public int getGroupListCnt(Pageable pageable) throws NotFoundException {
        Page<Challenge> page = challengeRepository.findByLimitPeopleCnt(pageable);

        return page.getTotalPages();
    }

    @Override
    public boolean changeStateChl(Long cid, int type, HttpServletRequest req) throws NotFoundException {

        Member member = memberService.findEmailbyToken(req);

        if(!challengeRepository.existsById(cid)) throw new NotFoundException("존재하지 않는 챌린지입니다.");
        Challenge c = challengeRepository.findById(cid).get();

        if(c.isFin()||c.getStatus()==2) throw new NotFoundException("종료된 챌린지입니다.");

        if(!prtChlRepository.existsByChallengeAndMember(c,member))
            throw new NotFoundException("참여하지 않은 챌린지 입니다.");

        PrtChl p = prtChlRepository.findByChallengeAndMember(c,member);
        if(p.is_fin()) throw new NotFoundException("종료된 챌린지입니다.");

        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));

        if(!logRepository.existsByPrtChlAndDate(p,now))
            throw new NotFoundException("해당 챌린지의 당일 로그가 존재하지 않습니다.");
        Log log = logRepository.findByPrtChlAndDate(p,now);

        Long dc = c.getDetailCategory().getId();
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        if(dc==1 ||dc==2 ||dc==3 ||dc==7 ) { // 센서 사용
            if(bpmRepository.findByChkAndPrtChlJPQL(today.toString(),p).size()!=0) throw new NotFoundException("워치로만 조작 가능합니다.");
            if(gpsRepository.findByChkAndPrtChl(today.toString(),p).size()!=0) throw new NotFoundException("워치로만 조작 가능합니다.");
        }

        int nowCnt = (type==1) ? 1 : -1;

        if(log.getCnt()+nowCnt >= c.getGoal()){ // 성공 시
            if(!log.isFin()){ // 실패 -> 성공
                log.setFin(true);
                p.setSucDay(p.getSucDay()+1);
                p.setFailDay(p.getFailDay()-1);
                prtChlRepository.save(p);
            }
        }
        else{ // 실패 시
            if(log.isFin()){ // 성공 -> 실패
                log.setFin(false);
                p.setSucDay(p.getSucDay()-1);
                p.setFailDay(p.getFailDay()+1);
                prtChlRepository.save(p);
            }
        }

        if(log.getCnt()+nowCnt<0) log.setCnt(0);
        else log.setCnt(log.getCnt()+nowCnt);
        logRepository.save(log);

        return log.isFin();
    }

    @Override
    public boolean findIsParticipate(Long cid, HttpServletRequest req) throws NotFoundException {

        Member member = memberService.findEmailbyToken(req);

        if(!challengeRepository.existsById(cid)) throw new NotFoundException("존재하지 않는 챌린지입니다.");
        Challenge c = challengeRepository.findById(cid).get();

        if(c.isFin()||c.getStatus()==2) throw new NotFoundException("종료된 챌린지입니다.");

        if(!prtChlRepository.existsByChallengeAndMember(c,member)) return false;
        else return true;
    }

    public List<ChallengeListResponse> makeResponse(List<Challenge> content) {
        List<ChallengeListResponse> result = new ArrayList<>();

        for (Challenge c : content) {
            List<ChlTag> tagList= c.getTagList();
            List<String> tagListName = new ArrayList<>();
            for (ChlTag a: tagList){
                tagListName.add(a.getHashtag().getName());
            }
            ChallengeListResponse temp = ChallengeListResponse.builder()
                    .id(c.getId())
                    .categoryId(c.getCategory().getId())
                    .detailCategoryId(c.getDetailCategory().getId())
                    .writer(c.getWriter())
                    .title(c.getTitle())
                    .contents(c.getContents())
                    .imgurl(c.getImgurl())
                    .watch(c.isWatch())
                    .roomtype(c.getRoomtype())
                    .password(c.getPassword())
                    .limitPeolple(c.getLimitPeople())
                    .currentNum(c.getCurrentNum())
                    .alarm(c.getAlarm())
                    .goal(c.getGoal())
                    .unit(c.getUnit())
                    .isFin(c.isFin())
                    .nickName(memberRepository.findById(c.getWriter()).get().getNickname())
                    .startTime(c.getChlTime().getStartTime())
                    .endTime(c.getChlTime().getEndTime())
                    .tagList(tagListName)
                    .build();
            System.out.println(c.getTitle());
            result.add(temp);
        }

        return result;
    }


    public static double getDistance(String lat1_s, String lng1_s, String lat2_s, String lng2_s) {

        double lat1 = Double.parseDouble(lat1_s);
        double lng1 = Double.parseDouble(lng1_s);
        double lat2 = Double.parseDouble(lat2_s);
        double lng2 = Double.parseDouble(lng2_s);

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat/2)* Math.sin(dLat/2)+ Math.cos(Math.toRadians(lat1))* Math.cos(Math.toRadians(lat2))* Math.sin(dLon/2)* Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = EARTH_RADIUS * c * 1000;    // 미터로 바꾸기
        return d;
    }
}


