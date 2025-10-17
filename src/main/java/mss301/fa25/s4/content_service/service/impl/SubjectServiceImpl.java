package mss301.fa25.s4.content_service.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.dto.request.SubjectRequest;
import mss301.fa25.s4.content_service.dto.response.SubjectResponse;
import mss301.fa25.s4.content_service.entity.Subject;
import mss301.fa25.s4.content_service.constant.EntityStatus;
import mss301.fa25.s4.content_service.exception.AppException;
import mss301.fa25.s4.content_service.exception.ErrorCode;
import mss301.fa25.s4.content_service.mapper.SubjectMapper;
import mss301.fa25.s4.content_service.repository.SubjectRepository;
import mss301.fa25.s4.content_service.service.SubjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SubjectServiceImpl implements SubjectService {
    SubjectRepository subjectRepository;
    SubjectMapper subjectMapper;

    @Override
    @Transactional
    public SubjectResponse createSubject(SubjectRequest request) {
        log.info("Creating new subject: {}", request.getName());

        if (subjectRepository.existsByNameAndStatus(request.getName(), EntityStatus.ACTIVE)) {
            throw new AppException(ErrorCode.SUBJECT_ALREADY_EXISTS);
        }

        Subject subject = subjectMapper.toEntity(request);
        subject = subjectRepository.save(subject);

        return subjectMapper.toResponse(subject);
    }

    @Override
    public SubjectResponse getSubjectById(Integer id) {
        log.info("Getting subject by id: {}", id);
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_FOUND));

        return subjectMapper.toResponse(subject);
    }

    @Override
    public List<SubjectResponse> getAllSubjects() {
        log.info("Getting all subjects");
        return subjectRepository.findByStatus(EntityStatus.ACTIVE).stream()
                .map(subjectMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubjectResponse> searchSubjectsByName(String name) {
        log.info("Searching subjects by name: {}", name);
        return subjectRepository.findByNameContainingIgnoreCaseAndStatus(name, EntityStatus.ACTIVE).stream()
                .map(subjectMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SubjectResponse updateSubject(Integer id, SubjectRequest request) {
        log.info("Updating subject id: {}", id);
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_FOUND));

        if (!subject.getName().equals(request.getName()) &&
                subjectRepository.existsByNameAndStatus(request.getName(), EntityStatus.ACTIVE)) {
            throw new AppException(ErrorCode.SUBJECT_ALREADY_EXISTS);
        }

        subjectMapper.updateEntity(subject, request);
        subject = subjectRepository.save(subject);

        return subjectMapper.toResponse(subject);
    }

    @Override
    @Transactional
    public void deleteSubject(Integer id) {
        log.info("Deleting subject id: {}", id);
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_FOUND));

        subject.setStatus(EntityStatus.INACTIVE);
        subjectRepository.save(subject);
    }
}