package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new ConcurrentHashMap<>(); // static 사용 , interface 구현
    private static long sequence = 0l; //static 사용


    public Member save(Member member){
        member.setId(++sequence);
        log.info("save: member={}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    //로그인 아이디 찾기 Optional 없는 버전
   /* public Member findByLoginId(String loginId){
        List<Member> all = findAll();
        for (Member m : all) {
            if(m.getLoginId().equals(loginId))
                return m;
        }
        return null;
    }*/

    //Optional 있는 버전
    /* public Optional<Member> findByLoginId(String loginId){
        List<Member> all = findAll();
        for (Member m : all) {
            if(m.getLoginId().equals(loginId))
                return Optional.of(m);
        }
       return Optional.empty();
    }*/

    //lambda 식을 활용한 메소드
    public Optional<Member> findByLoginId(String loginId){
        return findAll().stream()
                .filter(m ->m.getLoginId().equals(loginId))
                .findAny();

    }

    public List<Member> findAll(){
        ArrayList<Member> members = new ArrayList<>(store.values());
        return members;
    }

    public void clearStore(){
        store.clear();
    }
}
