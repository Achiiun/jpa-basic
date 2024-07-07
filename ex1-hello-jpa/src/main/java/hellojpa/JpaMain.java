package hellojpa;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

//            Team team = new Team();
//            team.setName("teamA");
//            em.persist(team);
//
//            Member member1 = new Member();
//            member1.setUsername("member1");
//            member1.setTeam(team);
//            em.persist(member1);
//
//            Member member2 = new Member();
//            member2.setUsername("member2");
//            em.persist(member2);
//
//            em.flush();
//            em.clear();

//            Member refMember = em.getReference(Member.class, member1.getId());
//            System.out.println("refMember = " + refMember.getClass()); //Proxy
//            refMember.getUsername(); //강제 초기화
//            Hibernate.initialize(refMember); //강제 초기화
//            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember));

//            Member m = em.find(Member.class, member1.getId());
//            System.out.println("m.getTeam().getClass() = " + m.getTeam().getClass());
//
//            System.out.println("==============");
//            m.getTeam().getName(); //초기화
//            System.out.println("==============");

//            Member findMember = em.find(Member.class, member1.getId());
//            System.out.println("findMember = " + findMember.getClass());

//            System.out.println("refMember == findMember: " + (refMember == findMember));

            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0);

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
