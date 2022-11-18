package com.szh.peer.rpt;

import com.szh.peer.domain.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PrescriptionRpt extends JpaRepository<Prescription, Long>, JpaSpecificationExecutor<Prescription> {

    // SQL优化：全查tb_prescription的全字段，除了response_body置为null，避免sort_buffer_size溢出
    String columnStr = "id,create_time,logic_delete,update_time,version,author_id,author_name,batch_id,code,detail_content,pics,publish_time,request_url,null as response_body,synced,raw_prescription_id";

    @Query(value = "select * from tb_prescription where logic_delete = 0 AND detail_content IS NULL", nativeQuery = true)
    List<Prescription> selectEmptyPrescriptions();

    @Query(value = "SELECT " + columnStr + " FROM tb_prescription WHERE logic_delete = 0 AND synced = 1 ORDER BY publish_time DESC LIMIT 1", nativeQuery = true)
    Optional<Prescription> selectLastSynced();

    @Query(value = "SELECT * FROM tb_prescription WHERE logic_delete = 0 AND synced = 0 ORDER BY publish_time ASC", nativeQuery = true)
    List<Prescription> selectNotSynced();

    @Modifying
    @Query(value = "update tb_prescription set synced = 1 WHERE logic_delete = 0 AND id in ?1", nativeQuery = true)
    void batchMarkSynced(List<Long> ids);
}
