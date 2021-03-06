package com.netcracker.edu.fapi.service.impl;

import com.netcracker.edu.fapi.models.LessonViewModel;
import com.netcracker.edu.fapi.models.RestPageImpl;
import com.netcracker.edu.fapi.service.LessonDataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class LessonDataServiceImpl implements LessonDataService {

    @Value("${backend.server.url}")
    private String backendServerUrl;

    @Override
    public LessonViewModel getLessonById(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(
                backendServerUrl + "api/lessons/" + id,
                LessonViewModel.class
        );
    }

    @Override
    public LessonViewModel saveLesson(LessonViewModel schedule) {
        return new RestTemplate().postForEntity(
                backendServerUrl + "api/lessons",
                schedule,
                LessonViewModel.class).getBody();
    }

    @Override
    public void deleteLesson(Integer id) {
        new RestTemplate().delete(backendServerUrl + "api/lessons/" + id);
    }

    @Override
    public RestPageImpl<LessonViewModel> getPage(HttpServletRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                backendServerUrl + "api/lessons?" + request.getQueryString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<RestPageImpl<LessonViewModel>>() {
                }
        ).getBody();
    }

    @Override
    public List<LessonViewModel> getProfessorLessonsBetween(Integer professorId, Date from) {
        RestTemplate restTemplate = new RestTemplate();
        LessonViewModel[] lessons = restTemplate.getForObject(
                String.format("%s/api/lessons/professor_schedule?professor_id=%d&from=%s",
                        backendServerUrl, professorId, from),
                LessonViewModel[].class);
        return lessons == null ? Collections.emptyList() : Arrays.asList(lessons);
    }

    @Override
    public List<LessonViewModel> getGroupLessonsBetween(Integer groupId, Date from) {
        RestTemplate restTemplate = new RestTemplate();
        LessonViewModel[] lessons = restTemplate.getForObject(
                String.format("%s/api/lessons/group_schedule?group_id=%d&from=%s",
                        backendServerUrl, groupId, from),
                LessonViewModel[].class);
        return lessons == null ? Collections.emptyList() : Arrays.asList(lessons);
    }
}
