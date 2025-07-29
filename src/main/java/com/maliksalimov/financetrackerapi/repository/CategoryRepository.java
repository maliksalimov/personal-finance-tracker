package com.maliksalimov.financetrackerapi.repository;

import com.maliksalimov.financetrackerapi.entity.Category;
import com.maliksalimov.financetrackerapi.entity.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.maliksalimov.financetrackerapi.entity.User;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUser(User user);
    List<Category> findByUserAndIsActiveTrue(User user);
    List<Category> findByUserIdAndIsActiveTrue(Long userId);
    Optional<Category> findByIdAndUser(Long id, User user);

    List<Category> findByUserAndType(User user, TransactionType type);
    List<Category> findByUserAndTypeAndIsActiveTrue(User user, TransactionType type);

    List<Category> findByUserAndNameContainingIgnoreCase(User user, String name);
    Optional<Category> findByUserAndName(User user, String name);

    List<Category> findByUserAndColor(User user, String Color);
    List<Category> findByUserAndIcon(User user, String Icon);

    List<Category> findActiveCategoriesByUserOrderByName(User user);

    @Query("SELECT c.type, COUNT (c) FROM Category c WHERE c.user = :user AND c.isActive = true ORDER BY c.name")
    List<Category> findCategoryCountByTypeGroupByUser(@Param("user") User user);

    @Query("SELECT c FROM Category c WHERE c.user = :user AND c.createdAt >= :date ORDER BY c.createdAt DESC")
    List<Category> findRecentCategoriesByUser(@Param("user") User user, @Param("date") java.time.LocalDateTime date);

    List<Category> findMostUsedCategoriesByUser(User user);

    Page<Category> findByUserAndIsActiveTrue(User user, org.springframework.data.domain.Pageable pageable);
    Page<Category> findByUserAndTypeAndIsActiveTrue(User user, TransactionType type, org.springframework.data.domain.Pageable pageable);

}
