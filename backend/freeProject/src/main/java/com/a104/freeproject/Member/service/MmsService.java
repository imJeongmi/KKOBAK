package com.a104.freeproject.Member.service;

import com.a104.freeproject.Member.request.MessageRequest;
import com.a104.freeproject.Member.request.MmsRequest;
import com.a104.freeproject.Member.request.NickRequest;
import com.a104.freeproject.Member.response.AuthNumResponse;
import com.a104.freeproject.Member.response.SMSResponse;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface MmsService {

    AuthNumResponse sendMMS(MmsRequest input) throws NotFoundException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, URISyntaxException;
    SMSResponse sendSMS(List<MessageRequest> msg) throws URISyntaxException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException;
    String makeSignature(Long time) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException;
    String makeAuthMsg(String auth);
    String makeAuthNum();
}
