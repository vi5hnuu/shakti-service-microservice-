package com.vi5hnu.auth_service.specifications;

import com.vi5hnu.auth_service.Entity.user.UserModel;
import jakarta.persistence.criteria.Predicate;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class UserSpecifications {
    public static Specification<UserModel> findUserById(@NonNull String userId,boolean isLocked,boolean isEnabled,boolean isDeleted) {
        return (root, query, criteriaBuilder) -> {
            assert query != null;
            // Build predicates
            Predicate userPredicate = criteriaBuilder.equal(root.get("id"), userId);
            Predicate isNotDeletedPredicate = criteriaBuilder.equal(root.get("isDeleted"), isDeleted);
            Predicate isEnabledPredicate = criteriaBuilder.equal(root.get("isEnabled"), isEnabled);
            Predicate isLockedPredicate = criteriaBuilder.equal(root.get("isLocked"), isLocked);

            return criteriaBuilder.and(userPredicate,isNotDeletedPredicate,isEnabledPredicate,isLockedPredicate);
        };
    }

    public static Specification<UserModel> activeUsers() {
        return (root, query, criteriaBuilder) -> {
            assert query != null;

            Predicate isNotDeletedPredicate = criteriaBuilder.equal(root.get("isDeleted"), false);
            Predicate isEnabledPredicate = criteriaBuilder.equal(root.get("isEnabled"), true);
            Predicate isLockedPredicate = criteriaBuilder.equal(root.get("isLocked"), false);

            // Combine predicates
            return criteriaBuilder.and(isNotDeletedPredicate, isEnabledPredicate, isLockedPredicate);
        };
    }

    public static Specification<UserModel> activeUserById(@NonNull String userId) {
        return (root, query, criteriaBuilder) -> {
            assert query != null;
            // Build predicates
            Predicate userPredicate = criteriaBuilder.equal(root.get("id"), userId);
            Predicate isNotDeletedPredicate = criteriaBuilder.equal(root.get("isDeleted"), false);
            Predicate isEnabledPredicate = criteriaBuilder.equal(root.get("isEnabled"), true);
            Predicate isNotLockerPredicate = criteriaBuilder.equal(root.get("isLocked"), false);

            return criteriaBuilder.and(userPredicate,isNotDeletedPredicate,isNotLockerPredicate,isEnabledPredicate);
        };
    }
    public static Specification<UserModel> activeUserByUsername(@NonNull String username) {
        return (root, query, criteriaBuilder) -> {
            assert query != null;
            // Build predicates
            Predicate userPredicate = criteriaBuilder.equal(root.get("username"), username);
            Predicate isNotDeletedPredicate = criteriaBuilder.equal(root.get("isDeleted"), false);
            Predicate isNotLockedPredicate = criteriaBuilder.equal(root.get("isLocked"), false);
            Predicate isEnabledPredicate = criteriaBuilder.equal(root.get("isEnabled"), true);

            return criteriaBuilder.and(userPredicate,isNotDeletedPredicate,isEnabledPredicate,isNotLockedPredicate);
        };
    }
    public static Specification<UserModel> activeUserByUsernameOrEmail(String username,String email) {
        return (root, query, criteriaBuilder) -> {
            assert query != null && (username!=null || email!=null);
            // Build predicates
            Predicate usernamePredicate = username!=null ? criteriaBuilder.equal(root.get("username"), username) : null;
            Predicate useremailPredicate = email!=null ? criteriaBuilder.equal(root.get("email"), email) : null;
            Predicate isNotDeletedPredicate = criteriaBuilder.equal(root.get("isDeleted"), false);
            Predicate isNotLockedPredicate = criteriaBuilder.equal(root.get("isLocked"), false);
            Predicate isEnabledPredicate = criteriaBuilder.equal(root.get("isEnabled"), true);

            if(useremailPredicate!=null && usernamePredicate!=null){
                return criteriaBuilder.and(criteriaBuilder.or(useremailPredicate,usernamePredicate),isNotDeletedPredicate,isNotLockedPredicate,isEnabledPredicate);
            }else{
                return criteriaBuilder.and(useremailPredicate!=null ? useremailPredicate : usernamePredicate,isNotDeletedPredicate,isNotLockedPredicate,isEnabledPredicate);
            }
        };
    }
    public static Specification<UserModel> activeUserByUsernameOrEmail(@NonNull String usernameEmail,Boolean isEnabled,Boolean isLocked,Boolean isDeleted) {
        return (root, query, criteriaBuilder) -> {
            assert query != null;
            // Build predicates
            final var predicates=new ArrayList<Predicate>();
            Predicate usernamePredicate = criteriaBuilder.equal(root.get("username"), usernameEmail);
            Predicate useremailPredicate = criteriaBuilder.equal(root.get("email"), usernameEmail);

            predicates.add(criteriaBuilder.or(useremailPredicate,usernamePredicate));
            if(isDeleted!=null) predicates.add(criteriaBuilder.equal(root.get("isDeleted"), isDeleted));
            if(isLocked!=null) predicates.add(criteriaBuilder.equal(root.get("isLocked"), isLocked));
            if(isEnabled!=null) predicates.add(criteriaBuilder.equal(root.get("isEnabled"), isEnabled));
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
