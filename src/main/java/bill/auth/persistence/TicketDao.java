package bill.auth.persistence;

import bill.auth.Ticket;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.Properties;

public class TicketDao {
    private final EntityManager entityManager;

    public TicketDao(Properties properties) {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("org.hibernate.tutorial.jpa", properties);
        entityManager = entityManagerFactory.createEntityManager();
    }

    public bill.auth.Ticket get(String cuit) {
        Ticket ticket;
        entityManager.getTransaction().begin();
        try {
            bill.auth.persistence.Ticket entity = (bill.auth.persistence.Ticket) entityManager
                    .createQuery("from Ticket where cuit = :cuit order by expiration desc")
                    .setParameter("cuit", cuit)
                    .setMaxResults(1)
                    .getSingleResult();
            ticket = new Ticket(entity.getTicketId(), entity.getToken(), entity.getExpiration(), entity.getSignature());
        } catch (NoResultException e) {
            ticket = null;
        }

        entityManager.getTransaction().commit();

        return ticket;
    }

    public void save(String cuit, Ticket ticket) {
        entityManager.getTransaction().begin();

        bill.auth.persistence.Ticket entity = new bill.auth.persistence.Ticket();
        entity.setCuit(cuit);
        entity.setExpiration(ticket.getExpiration());
        entity.setToken(ticket.getToken());
        entity.setTicketId(ticket.getId());
        entity.setSignature(ticket.getSignature());

        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

}
