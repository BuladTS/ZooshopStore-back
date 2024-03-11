package ru.sfu.zooshopback.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.sfu.zooshopback.model.Product;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductSearchDao {

    private final EntityManager entityManager;

    public List<Product> findProductsLike(
            String name,
            String description
    ) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

        Root<Product> root = criteriaQuery.from(Product.class);

        Predicate namePredicate = criteriaBuilder
                .like(root.get("name"), "%" + name + "%");
        Predicate descriptionPredicate = criteriaBuilder
                .like(root.get("description"), "%" + description + "%");

        Predicate orPredicate = criteriaBuilder.or(namePredicate, descriptionPredicate);

        criteriaQuery.where(orPredicate);
        TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }


}
