package jpql;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            for (int i = 2; i < 100; i++) {
                Member member = new Member();
                member.setUsername("member"+i);
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
            Query query3 = em.createQuery("select m.username, m.age from Member m");

            List<Member> query4 = em.createQuery("select m from Member m", Member.class)
              .getResultList(); // 결과가 없으면 빈 리스트 반환
//            Member query5 = em.createQuery("select m from Member m where m.id = 10", Member.class)
//              .getSingleResult(); // 결과가 없거나 두개 이상이면 Exception 발생

            TypedQuery<Member> query6 = em.createQuery("select m from Member m where m.username=:username", Member.class)
              .setParameter("username", member.getUsername());

            TypedQuery<Member> query7 = em.createQuery("select m from Member m where m.username=?1", Member.class)
              .setParameter(1, member.getUsername());

            em.flush();
            em.clear();

            List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();

            Member findMember = result.get(0);
            findMember.setAge(20);

            List resultList = em.createQuery("select m.username, m.age from Member m")
              .getResultList();
            Object o = resultList.get(0);
            Object[] resultObjectList =  (Object[]) o;
            System.out.println("username = " + resultObjectList[0]);
            System.out.println("age = " + resultObjectList[1]);

            List<Object[]> resultList1 = em.createQuery("select m.username, m.age from Member m")
              .getResultList();
            Object[] resultObjectList1 = resultList1.get(0);
            System.out.println("username = " + resultObjectList1[0]);
            System.out.println("age = " + resultObjectList1[1]);

            List<MemberDTO> resultList2 = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
              .getResultList();
            MemberDTO memberDTO = resultList2.get(0);
            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
            System.out.println("memberDTO.getAge() = " + memberDTO.getAge());

            List<Member> resultList3 = em.createQuery("select m from Member m order by m.age desc", Member.class)
              .setFirstResult(0)
              .setMaxResults(10)
              .getResultList();

            for (Member memberList : resultList3) {
                System.out.println("memberList = " + memberList);
            }

            em.flush();
            em.clear();

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member2 = new Member();
            member2.setUsername("member1");
            member2.setAge(10);
            member2.setTeam(team);
            member2.setType(MemberType.ADMIN);
            em.persist(member2);

            List<Member> resultList4 = em.createQuery("select m from Member m left join m.team t", Member.class)
              .getResultList();

            List<Member> resultList5 = em.createQuery("select m from Member m, Team t where m.username = t.name", Member.class)
              .getResultList();

            List<Member> resultList6 = em.createQuery("select m.username from Member m where m.type = jpql.MemberType.ADMIN", Member.class)
              .getResultList();

            String query8 =
              "select " +
                "case when m.age <= 10 then '학생요금' " +
                "     when m.age >= 60 then '경로요금' " +
                "     else '일반요금' " +
                "end " +
                "from Member m";
            String query9 = "select coalesce(m.username, '이름 없는 회원') as username " +
              "from Member m ";
            String query10 = "select nullif(m.username, '관리자') as username " +
              "from Member m ";
            String query11 = "select function('group_concat', m.username) from Member m ";

            List<String> resultList7 = em.createQuery(query11, String.class)
              .getResultList();

            for (String s : resultList7) {
                System.out.println("s = " + s);
            }

            String query12 = "select m.username, m.age from Member m";
            String query13 = "select m.team from Member m";
            String query14 = "select t.member.username from Team t"; // 실패
            String query15 = "select m.username from Team t join t.members m";
            String query16 = "select m from Member m join fetch m.team"; // fetch join
            String query17 = "select t from Team t join fetch t.members where t.name = '팀A'"; // collection fetch join

            List<Team> resultList8 = em.createQuery(query17, Team.class)
              .setFirstResult(0)
              .setMaxResults(1) // collection fetch join 에서 페이징을 사용할수 없다(데이터가 뻥튀기 되기 때문에)
              .getResultList();

            String query18 = "select count(m) from Member m";
            List<Member> resultList9 = em.createQuery(query18, Member.class).getResultList();

            String query19 = "select m from Member m where m = :member";
            List<Member> resultList10 = em.createQuery(query19, Member.class)
              .setParameter("member", member)
              .getResultList();

            String query20 = "select m from Member m where m.team = :team";
            List<Member> resultList11 = em.createQuery(query20, Member.class)
              .setParameter("team", team)
              .getResultList();

            for (Member memberList : resultList11) {
                System.out.println("memberList = " + memberList);
            }

            em.flush();
            em.clear();

            List<Member> resultList12 = em.createNamedQuery("Member.findByUsername", Member.class)
              .setParameter("username", "member1")
              .getResultList();

            for (Member memberList : resultList12) {
                System.out.println("memberList = " + memberList);
            }

            //FLUSH 자동 호출
            int resultCount = em.createQuery("update Member m set m.age = 20")
              .executeUpdate();
            System.out.println("resultCount = " + resultCount);

            em.clear();
            Member findMember2 = em.find(Member.class, member.getId());

            System.out.println("findMember2 = " + findMember2.getAge());


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }


}
