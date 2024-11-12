package com.marco.marioni.services;

import com.marco.marioni.domain.dtos.ShipDTO;
import com.marco.marioni.domain.entities.ShipEntity;
import com.marco.marioni.mappers.ShipMapper;
import com.marco.marioni.repositories.ShipRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipService {
    private final ShipRepository shipRepository;
    private final ShipMapper shipMapper;
    private final EntityManager entityManager;

    @Cacheable(value = "ships", key = "#id")
    public ShipDTO findById(Integer id) {
        return shipMapper.toDTO(shipRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("ShipEntity", id)));
    }

    public ShipDTO save(ShipDTO shipDTO) {
        return shipMapper.toDTO(shipRepository.save(shipMapper.toEntity(shipDTO)));
    }

    @CacheEvict(value = "ships", key = "#shipDTO.id")
    public ShipDTO update(ShipDTO shipDTO) {
        findById(shipDTO.getId());
        return shipMapper.toDTO(shipRepository.save(shipMapper.toEntity(shipDTO)));
    }

    @CacheEvict(value = "ships", key = "#id")
    public void deleteById(Integer id) {
        findById(id);
        shipRepository.deleteById(id);
    }

    public List<ShipDTO> findAll(String name, Integer pageNumber, Integer pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ShipEntity> criteriaQuery = criteriaBuilder.createQuery(ShipEntity.class);

        Root<ShipEntity> root = criteriaQuery.from(ShipEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("createdAt")));

        if (name != null && !name.trim().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (!predicates.isEmpty()) {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }

        TypedQuery<ShipEntity> typedQuery = entityManager.createQuery(criteriaQuery)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize);

        List<ShipEntity> resultList = typedQuery.getResultList();

        return shipMapper.toDTOList(resultList);
    }

}
