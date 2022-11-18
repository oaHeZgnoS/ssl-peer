package com.szh.peer.rpt;

import com.szh.peer.domain.RawPrescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface RawPrescriptionRpt extends JpaRepository<RawPrescription, Long>, JpaSpecificationExecutor<RawPrescription> {

    @Query(value = "select * from tb_raw_prescription where logic_delete = 0 order by publish_time desc limit 1", nativeQuery = true)
    RawPrescription lastRawPrescription();

}
