package com.example.yummytable.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.yummytable.dto.member.CreateMember;
import com.example.yummytable.dto.member.MemberDto;
import com.example.yummytable.service.MemberService;
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
  private MemberService memberService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void successCreateMember() throws Exception {
    // given
    given(memberService.createMember(any()))
        .willReturn(MemberDto.builder()
            .memberId(1L)
            .password("pass1111")
            .userName("니니")
            .email("aaa@gmail.com")
            .memberStatus(Status.EXISTENT)
            .registeredAt(LocalDateTime.now())
            .build());
    // when
    // then
    mockMvc.perform(post("/member")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(
                new CreateMember.Request("aaa@daum.net", "pass1111", "니니")
            )))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.memberId").value(1L))
        .andDo(print());


  }


}