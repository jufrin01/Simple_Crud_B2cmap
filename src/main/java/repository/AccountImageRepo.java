package idb2camp.b2campjufrin.repository;

import idb2camp.b2campjufrin.model.AccountImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AccountImageRepo extends JpaRepository<AccountImage, Integer> {

    @Query(value = "SELECT ai.account_image_id, ai.file_path, ai.is_primary,\n" +
            "ai.account_id, a.first_name,\n" +
            "a.last_name, a.email, a.gender, a.religion,\n" +
            "a.department_id, a.created_at, a.is_deleted, a.updated_at \n" +
            "FROM account_image ai\n" +
            "JOIN account a ON ai.account_id = a.account_id \n" +
            "WHERE ai.account_id = :accountId", nativeQuery = true)
    List<Map<String, Object>> findByAccountIdNativeQuery(Integer accountId);

    @Query
    List<AccountImage> findByAccount_AccountId(Integer accountId);


}
