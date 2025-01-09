package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import model.Order;
import model.OrderLine;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager em;


    @Transactional
    public Order insertOrder(Order order) {

        double total = 0.0;
        for (OrderLine ol : order.getOrderLines()) {
            total += ol.getPrice();
        }

        order.setTotal(total);

        if (order.getId() == null) {
            em.persist(order);
        } else {
            em.merge(order);
        }
        return order;
    }

    @Transactional
    public Order getOrderById(Long id) {

        TypedQuery<Order> query = em.createQuery("select o from Order o where o.id = :id", Order.class);
        query.setParameter("id", id);

        return query.getSingleResult();
    }

    public List<Order> getAllOrders() {

        return em.createQuery("select  o from Order  o", Order.class).getResultList();
    }

    @Transactional
    public void deleteOrderById(Long id) {

        Query query = em.createQuery("delete from Order  o where o.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

    }

}
