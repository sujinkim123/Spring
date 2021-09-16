package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
	
	@Autowired
	MemberService memberService;
	@Autowired
	MemberRepository memberRepository;
	
	@Test
	public void join() throws Exception {
		
		Member member = new Member();
		member.setName("hello");
		
		Long saveId = memberService.join(member);
		
		Member findMember = memberRepository.findById(saveId).get();
		assertEquals(member.getName(), findMember.getName());
	}
	
	@Test
	public void 중복_회원_예외() throws Exception {
		
		Member member1 = new Member();
		member1.setName("spring");
		
		Member member2 = new Member();
		member2.setName("spring");
		
		memberService.join(member1);
		IllegalStateException e = assertThrows(IllegalStateException.class, 
				() -> memberService.join(member2)); // 예외가 발생해야 한다.
		
		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
	}
	
}