package com.marco.marioni;

import com.marco.marioni.domain.dtos.ShipDTO;
import com.marco.marioni.domain.entities.ShipEntity;
import com.marco.marioni.mappers.ShipMapper;
import com.marco.marioni.repositories.ShipRepository;
import com.marco.marioni.services.ShipService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShipServiceTest {

    @Mock
    private ShipRepository shipRepository;

    @Mock
    private ShipMapper shipMapper;

    @Mock
    private EntityManager entityManager;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    private ShipService shipService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        shipService = new ShipService(shipRepository, shipMapper, entityManager);
    }

    @Test
    public void testFindById_Success() {
        Integer id = 1;
        ShipEntity shipEntity = new ShipEntity(id, "Ship 1", null, null);
        ShipDTO shipDTO = new ShipDTO(id, "Ship 1");

        when(shipRepository.findById(id)).thenReturn(Optional.of(shipEntity));
        when(shipMapper.toDTO(shipEntity)).thenReturn(shipDTO);

        ShipDTO result = shipService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Ship 1", result.getName());
    }

    @Test
    public void testFindById_NotFound() {
        Integer id = 1;

        when(shipRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> shipService.findById(id));
    }

    @Test
    public void testSave_Success() {
        ShipDTO shipDTO = new ShipDTO(1, "New Ship");
        ShipEntity shipEntity = new ShipEntity(1, "New Ship", null, null);

        when(shipMapper.toEntity(shipDTO)).thenReturn(shipEntity);
        when(shipRepository.save(shipEntity)).thenReturn(shipEntity);
        when(shipMapper.toDTO(shipEntity)).thenReturn(shipDTO);

        ShipDTO result = shipService.save(shipDTO);

        assertNotNull(result);
        assertEquals("New Ship", result.getName());
    }

    @Test
    public void testUpdate_Success() {
        Integer id = 1;
        ShipDTO shipDTO = new ShipDTO(id, "Updated Ship");
        ShipEntity existingEntity = new ShipEntity(id, "Old Ship", null, null);
        ShipEntity updatedEntity = new ShipEntity(id, "Updated Ship", null, null);

        when(shipRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(shipMapper.toEntity(shipDTO)).thenReturn(updatedEntity);
        when(shipRepository.save(updatedEntity)).thenReturn(updatedEntity);
        when(shipMapper.toDTO(updatedEntity)).thenReturn(shipDTO);

        ShipDTO result = shipService.update(shipDTO);

        assertNotNull(result);
        assertEquals("Updated Ship", result.getName());
    }

    @Test
    public void testDeleteById_Success() {
        Integer id = 1;
        ShipEntity shipEntity = new ShipEntity(id, "Ship 1", null, null);

        when(shipRepository.findById(id)).thenReturn(Optional.of(shipEntity));
        doNothing().when(shipRepository).deleteById(id);

        shipService.deleteById(id);

        verify(shipRepository, times(1)).deleteById(id);
    }

    @Test
    public void testFindAll_WithNameFilter() {
        String name = "Ship 1";
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ShipEntity shipEntity = new ShipEntity(1, "Ship 1", null, null);
        ShipDTO shipDTO = new ShipDTO(1, "Ship 1");

        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<ShipEntity> criteriaQuery = mock(CriteriaQuery.class);
        Root<ShipEntity> root = mock(Root.class);
        Predicate predicate = mock(Predicate.class);
        TypedQuery<ShipEntity> typedQuery = mock(TypedQuery.class);
        Order order = mock(Order.class);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(ShipEntity.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ShipEntity.class)).thenReturn(root);

        Expression<String> nameExpression = mock(Expression.class);
        when(criteriaBuilder.lower(root.get("name"))).thenReturn(nameExpression);
        when(criteriaBuilder.like(nameExpression, "%" + name.toLowerCase() + "%")).thenReturn(predicate);

        when(criteriaQuery.orderBy(anyList())).thenReturn(criteriaQuery);

        when(criteriaQuery.where(any(Predicate[].class))).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.setFirstResult(anyInt())).thenReturn(typedQuery);
        when(typedQuery.setMaxResults(anyInt())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(shipEntity));

        when(shipMapper.toDTOList(List.of(shipEntity))).thenReturn(List.of(shipDTO));

        List<ShipDTO> result = shipService.findAll(name, pageNumber, pageSize);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Ship 1", result.get(0).getName());
    }
}
