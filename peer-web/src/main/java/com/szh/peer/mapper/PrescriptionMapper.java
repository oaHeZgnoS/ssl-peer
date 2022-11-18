package com.szh.peer.mapper;

import com.szh.peer.res.PrescriptionDto;

import java.util.List;

public interface PrescriptionMapper {

    List<List<PrescriptionDto>> listPrescription();
}
