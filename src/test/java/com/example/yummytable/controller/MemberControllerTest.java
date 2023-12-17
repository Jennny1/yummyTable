package com.example.yummytable.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.yummytable.dto.sign.CreateSignUp;
import com.example.yummytable.dto.member.MemberDto;
import com.example.yummytable.service.SignService;
import com.example.yummytable.type.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

  @MockBean
  private SignService signService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void successCreateMember() throws Exception {
    // given
    // Mock Member 객체 생성
    CreateSignUp.Request request = new CreateSignUp.Request("test@example.com", "password", "Test User");
    MemberDto mockMember = MemberDto.builder()
        .memberId(1L)
        .memberStatus(Status.EXISTENT)
        .registeredAt(LocalDateTime.now())
        .build();

    // MemberService의 createMember 메서드가 호출될 때 모의 객체로 생성된 데이터 반환 설정
    given(signService.signup(any(CreateSignUp.Request.class))).willReturn(mockMember);

    // POST 요청 페이로드를 JSON 형식으로 변환
    String requestBody = objectMapper.writeValueAsString(request);

    // when
    // then
    // POST /member 엔드포인트에 대한 POST 요청을 수행하고 응답 검증
    mockMvc.perform(post("/member")
            .header("Origin", "http://localhost:8080")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk()) // 200 OK를 예상합니다. 필요에 따라 적절한 응답 코드를 사용하세요.
        .andExpect(jsonPath("$.memberId").value(1L)); // memberId에 해당하는 값을 검증합니다.


  }
}