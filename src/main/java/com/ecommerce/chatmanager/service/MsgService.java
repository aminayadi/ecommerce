package com.ecommerce.chatmanager.service;

import com.ecommerce.chatmanager.domain.Msg;
import com.ecommerce.chatmanager.repository.MsgRepository;
import com.ecommerce.chatmanager.service.dto.MsgDTO;
import com.ecommerce.chatmanager.service.mapper.MsgMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Msg}.
 */
@Service
public class MsgService {

    private final Logger log = LoggerFactory.getLogger(MsgService.class);

    private final MsgRepository msgRepository;

    private final MsgMapper msgMapper;

    public MsgService(MsgRepository msgRepository, MsgMapper msgMapper) {
        this.msgRepository = msgRepository;
        this.msgMapper = msgMapper;
    }

    /**
     * Save a msg.
     *
     * @param msgDTO the entity to save.
     * @return the persisted entity.
     */
    public MsgDTO save(MsgDTO msgDTO) {
        log.debug("Request to save Msg : {}", msgDTO);
        Msg msg = msgMapper.toEntity(msgDTO);
        msg = msgRepository.save(msg);
        return msgMapper.toDto(msg);
    }

    /**
     * Update a msg.
     *
     * @param msgDTO the entity to save.
     * @return the persisted entity.
     */
    public MsgDTO update(MsgDTO msgDTO) {
        log.debug("Request to update Msg : {}", msgDTO);
        Msg msg = msgMapper.toEntity(msgDTO);
        msg = msgRepository.save(msg);
        return msgMapper.toDto(msg);
    }

    /**
     * Partially update a msg.
     *
     * @param msgDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MsgDTO> partialUpdate(MsgDTO msgDTO) {
        log.debug("Request to partially update Msg : {}", msgDTO);

        return msgRepository
            .findById(msgDTO.getId())
            .map(existingMsg -> {
                msgMapper.partialUpdate(existingMsg, msgDTO);

                return existingMsg;
            })
            .map(msgRepository::save)
            .map(msgMapper::toDto);
    }

    /**
     * Get all the msgs.
     *
     * @return the list of entities.
     */
    public List<MsgDTO> findAll() {
        log.debug("Request to get all Msgs");
        return msgRepository.findAll().stream().map(msgMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one msg by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<MsgDTO> findOne(String id) {
        log.debug("Request to get Msg : {}", id);
        return msgRepository.findById(id).map(msgMapper::toDto);
    }

    /**
     * Delete the msg by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Msg : {}", id);
        msgRepository.deleteById(id);
    }
}
