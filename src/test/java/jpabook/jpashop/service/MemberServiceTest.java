package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    @Rollback(value = false)
    void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("Park");

        //when
        Long savedId = memberService.join(member);

        //then
        System.out.println("member = " + member);
        System.out.println("memberRepository.findOne() = " + memberRepository.findOne(savedId));
        assertThat(member).isEqualTo(memberRepository.findOne(savedId));
    }

    @Test
    void 중복회원예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("Park");

        Member member2 = new Member();
        member2.setName("Park");
        //when
        memberService.join(member1);

        //then
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }
}