package com.ecommerce.chatmanager.web.rest;

import com.ecommerce.chatmanager.client.ClientFeignClient;
import com.ecommerce.chatmanager.repository.MsgRepository;
import com.ecommerce.chatmanager.service.MsgService;
import com.ecommerce.chatmanager.service.dto.ClientDTO;
import com.ecommerce.chatmanager.service.dto.MsgDTO;
import com.ecommerce.chatmanager.web.rest.errors.BadRequestAlertException;
import com.ecommerce.chatmanager.websocket.WebSocketHandler;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ecommerce.chatmanager.domain.Msg}.
 */
@RestController
@RequestMapping("/api")
public class MsgResource {

    @Autowired
    private WebSocketHandler webSocketHandler;

    private List<ClientDTO> validUsers = new ArrayList<>();

    private ClientFeignClient cfc;

    private final Logger log = LoggerFactory.getLogger(MsgResource.class);

    private static final String ENTITY_NAME = "chatmanagerdbMsg";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MsgService msgService;

    private final MsgRepository msgRepository;

    public MsgResource(MsgService msgService, MsgRepository msgRepository, ClientFeignClient cfc) {
        this.msgService = msgService;
        this.msgRepository = msgRepository;
        this.cfc = cfc;
    }

    /**
     * {@code POST  /msgs} : Create a new msg.
     *
     * @param msgDTO the msgDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new msgDTO, or with status {@code 400 (Bad Request)} if the msg has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/msgs")
    public ResponseEntity<MsgDTO> createMsg(@Valid @RequestBody MsgDTO msgDTO) throws URISyntaxException {
        log.debug("REST request to save Msg : {}", msgDTO);
        if (msgDTO.getId() != null) {
            throw new BadRequestAlertException("A new msg cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MsgDTO result = msgService.save(msgDTO);
        return ResponseEntity
            .created(new URI("/api/msgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /msgs/:id} : Updates an existing msg.
     *
     * @param id the id of the msgDTO to save.
     * @param msgDTO the msgDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated msgDTO,
     * or with status {@code 400 (Bad Request)} if the msgDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the msgDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/msgs/{id}")
    public ResponseEntity<MsgDTO> updateMsg(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody MsgDTO msgDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Msg : {}, {}", id, msgDTO);
        if (msgDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, msgDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!msgRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MsgDTO result = msgService.update(msgDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, msgDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /msgs/:id} : Partial updates given fields of an existing msg, field will ignore if it is null
     *
     * @param id the id of the msgDTO to save.
     * @param msgDTO the msgDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated msgDTO,
     * or with status {@code 400 (Bad Request)} if the msgDTO is not valid,
     * or with status {@code 404 (Not Found)} if the msgDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the msgDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/msgs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MsgDTO> partialUpdateMsg(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody MsgDTO msgDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Msg partially : {}, {}", id, msgDTO);
        if (msgDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, msgDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!msgRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MsgDTO> result = msgService.partialUpdate(msgDTO);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, msgDTO.getId()));
    }

    /**
     * {@code GET  /msgs} : get all the msgs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of msgs in body.
     */
    @GetMapping("/msgs")
    public List<MsgDTO> getAllMsgs() {
        log.debug("REST request to get all Msgs");
        return msgService.findAll();
    }

    /**
     * {@code GET  /msgs/:id} : get the "id" msg.
     *
     * @param id the id of the msgDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the msgDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/msgs/{id}")
    public ResponseEntity<MsgDTO> getMsg(@PathVariable String id) {
        log.debug("REST request to get Msg : {}", id);
        Optional<MsgDTO> msgDTO = msgService.findOne(id);
        return ResponseUtil.wrapOrNotFound(msgDTO);
    }

    /**
     * {@code DELETE  /msgs/:id} : delete the "id" msg.
     *
     * @param id the id of the msgDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/msgs/{id}")
    public ResponseEntity<Void> deleteMsg(@PathVariable String id) {
        log.debug("REST request to delete Msg : {}", id);
        msgService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    @RequestMapping(value = "/hetuser", method = RequestMethod.POST)
    public ResponseEntity<ClientDTO> userLogin(HttpServletResponse response) {
        log.debug("--------------------logggggina");
        /*	Optional<User> user = getValidUsers()
								.stream()
								.filter(u -> u.getUserName().equalsIgnoreCase(loginRequest.getName()))
								.findFirst();
		
		if (user.isPresent()) {
			return user.get();
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		}*/

        ResponseEntity<ClientDTO> cdto = cfc.getClient("63b46ec5e935c97349fea998");
        log.debug("--------------------cdto", cdto);
        return cdto;
    }

    @RequestMapping(value = "/hetusers", method = RequestMethod.GET)
    public List<ClientDTO> listUsers() {
        List<ClientDTO> validUsers = cfc.getAllClients();
        /*	Set<ClientDTO> onlineUsers = webSocketHandler.getOnlineUsers();
		validUsers.forEach(validUser -> {
			if (onlineUsers.contains(validUser)) {
				validUser.setOnline(true);
			} else {
				validUser.setOnline(false);
			}
		});*/
        return validUsers;
    }
}
