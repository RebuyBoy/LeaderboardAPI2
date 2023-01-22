package com.rebuy.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebuy.service.dto.client.Response;
import com.rebuy.service.dto.client.gg.GGResultResponse;
import com.rebuy.service.dto.client.gg.GroupsResponse;
import com.rebuy.service.exceptions.NoResultException;
import com.rebuy.service.service.interfaces.GGRequestService;
import com.rebuy.service.util.Aes;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class GGRequestServiceImpl implements GGRequestService {
    public static final String SECRET_KEY = "milliseconds";

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    public GGRequestServiceImpl(ObjectMapper mapper) {
        this.restTemplate = new RestTemplate();
        this.mapper = mapper;
    }

    @Override
    public List<GGResultResponse> promotionIdRequest(String url) {
        try {
            ResponseEntity<Response> promotionIdResponse = restTemplate.exchange(url, HttpMethod.GET, null, Response.class);
            String secret = promotionIdResponse.getHeaders().get(SECRET_KEY).get(0);
            String decrypt = Aes.decrypt(secret, promotionIdResponse.getBody().getData());
            return mapper.readValue(decrypt, new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new NoResultException("Request failed url: " + url);
    }

    @Override
    public String getHTMLBody(String url) {
        return restTemplate.exchange(url, HttpMethod.GET, null, String.class).getBody();
    }

    @Override
    public GroupsResponse groupIdRequest(String url) {
        try {
            ResponseEntity<Response> response = restTemplate.exchange(url, HttpMethod.GET, null, Response.class);
            String secret = Objects.requireNonNull(response.getHeaders().get(SECRET_KEY)).get(0);
            String decrypt = Aes.decrypt(secret, Objects.requireNonNull(response.getBody()).getData());
            return mapper.readValue(decrypt, GroupsResponse.class);
        } catch (JsonProcessingException | NullPointerException e) {
            e.printStackTrace();
        }
        throw new NoResultException("groupIdRequest failed");
    }

}
