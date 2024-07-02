package hellojpa;

import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            Member member = new Member();
//            member.setId(1L);
//            member.setName("helloA");
//
//            em.persist(member);
//
//            Member findMember = em.find(Member.class, member.getId());
//            System.out.println("findMember = " + findMember.getId());
//            System.out.println("findMember = " + findMember.getName());
//
//            findMember.setName("helloJPA");
//            System.out.println("=============update=============");
//            System.out.println("findMember = " + findMember.getId());
//            System.out.println("findMember = " + findMember.getName());

            Member member = new Member();
            member.setUsername("A");

            em.persist(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
