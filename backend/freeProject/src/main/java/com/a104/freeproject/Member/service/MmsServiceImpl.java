package com.a104.freeproject.Member.service;

import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.Member.repository.MemberRepository;
import com.a104.freeproject.Member.request.MessageRequest;
import com.a104.freeproject.Member.request.MmsRequest;
import com.a104.freeproject.Member.request.NickRequest;
import com.a104.freeproject.Member.request.SMSRequest;
import com.a104.freeproject.Member.response.AuthNumResponse;
import com.a104.freeproject.Member.response.SMSResponse;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:application-secret.properties")
})
public class MmsServiceImpl implements MmsService{

    private final MemberRepository memberRepository;

    @Value("${mms.accessKey}")
    private String accessKey;
    @Value("${mms.secretKey}")
    private String secretKey;
    @Value("${mms.serviceId}")
    private String serviceId;
    @Value("${mms.senderPhoneNum}")
    private String senderPhoneNum;

    @Override
    public AuthNumResponse sendMMS(MmsRequest input) throws NotFoundException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, URISyntaxException {

        if(memberRepository.existsByHp(input.getPhoneNumber())) throw new NotFoundException("이미 등록된 핸드폰 번호입니다.");
        Member member = memberRepository.findByHp(input.getPhoneNumber());

        String numStr = makeAuthNum();
        String content = makeAuthMsg(numStr);

        List<MessageRequest> list = new LinkedList<>();
        list.add(MessageRequest.builder().to(input.getPhoneNumber()).content(content).build());

        // sms 보내기
        try{
            SMSResponse response = sendSMS(list);
            return AuthNumResponse.builder().authNum(numStr).build();
        }catch (Exception e){
            return AuthNumResponse.builder().authNum("").build();
        }
    }

    @Override
    public SMSResponse sendSMS(List<MessageRequest> msg) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, URISyntaxException, JsonProcessingException {
        Long time = System.currentTimeMillis();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time.toString());
        headers.set("x-ncp-iam-access-key", accessKey);
        headers.set("x-ncp-apigw-signature-v2", makeSignature(time));

        SMSRequest request = SMSRequest.builder()
                .type("SMS")
                .contentType("COMM")
                .countryCode("82")
                .from(senderPhoneNum)
                .content(msg.get(0).getContent())
                .messages(msg)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(request);
        HttpEntity<String> httpBody = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();

        SMSResponse response = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+ serviceId +"/messages"), httpBody, SMSResponse.class);

        return response;
    }

    @Override
    public String makeSignature(Long time) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/"+ this.serviceId+"/messages";
        String timestamp = time.toString();
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;
    }

    @Override
    public String makeAuthMsg(String auth) {
        String content = "[어플이름] 인증번호는 [" + auth + "] 입니다.";
        return content;
    }

    @Override
    public String makeAuthNum() {
        Random rand = new Random();
        String numStr = "";

        // 인증 번호 생성
        for(int i=0;i<6;i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }
        return numStr;
    }

}
